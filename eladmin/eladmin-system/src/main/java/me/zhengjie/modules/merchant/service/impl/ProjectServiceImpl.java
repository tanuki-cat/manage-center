/*
 *  Copyright 2019-2023 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package me.zhengjie.modules.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import me.zhengjie.base.BaseEntity;
import me.zhengjie.modules.merchant.domain.Patent;
import me.zhengjie.modules.merchant.domain.Project;
import me.zhengjie.modules.merchant.domain.ProjectSchedule;
import me.zhengjie.modules.merchant.domain.vo.ProjectVO;
import me.zhengjie.modules.merchant.enums.ProjectEnums;
import me.zhengjie.modules.merchant.enums.ScheduleEnum;
import me.zhengjie.modules.merchant.service.ProjectScheduleService;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.zhengjie.modules.merchant.service.ProjectService;
import me.zhengjie.modules.merchant.domain.vo.ProjectQueryCriteria;
import me.zhengjie.modules.merchant.mapper.ProjectMapper;
import me.zhengjie.utils.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import me.zhengjie.utils.PageUtil;

import java.util.*;
import java.io.IOException;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import me.zhengjie.utils.PageResult;
import org.springframework.util.CollectionUtils;

/**
 * @author ChinaJoy
 * @description 服务实现
 * @date 2023-11-26
 **/
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {

    private final ProjectMapper projectMapper;
    private final ProjectScheduleService projectScheduleService;

    @Override
    public PageResult<ProjectVO> queryAll(ProjectQueryCriteria criteria, Page<Object> page) {
        IPage<Project> page1 = new Page<>(page.getCurrent(), page.getSize());
        LambdaQueryWrapper<Project> wrapper = new LambdaQueryWrapper<>();
        //Long companyId is not null
        if (criteria.getCompanyId() != null && criteria.getCompanyId() > 0) {
            wrapper.eq(Project::getCompanyId, criteria.getCompanyId());
        }
        if (Strings.isNotBlank(criteria.getUserName())) {
            wrapper.eq(BaseEntity::getCreateBy, criteria.getUserName());
        }
        if (Strings.isNotBlank(criteria.getCreateBy())) {
            wrapper.eq(Project::getCreateBy, criteria.getCreateBy());
        }
        if (Strings.isNotBlank(criteria.getProjectTag())) {
            wrapper.eq(Project::getProjectTag, criteria.getProjectTag());
        }
        if (Strings.isNotBlank(criteria.getProjectName())) {
            wrapper.like(Project::getProjectName, criteria.getProjectName());
        }
        if (criteria.getProjectStatus() != null) {
            wrapper.eq(Project::getProjectStatus, criteria.getProjectStatus());
        }
        if (Strings.isNotBlank(criteria.getCompanyName())) {
            wrapper.like(Project::getCompanyName, criteria.getCompanyName());
        }
        if (Strings.isNotBlank(criteria.getAreas())) {
            wrapper.like(Project::getAreas, criteria.getAreas());
        }
        if (Objects.nonNull(criteria.getAssignUserId()) && Long.parseLong(criteria.getAssignUserId()) > 0) {
            //当前角色为项目经理时
            List<ProjectSchedule> schedules = this.projectScheduleService.list(Wrappers.<ProjectSchedule>query().lambda()
                    .eq(ProjectSchedule::getAssignUserId, criteria.getAssignUserId()).eq(ProjectSchedule::getAssignStatus,0));
            if (!CollectionUtils.isEmpty(schedules)) {
                List<Long> projectIds = schedules.stream().map(ProjectSchedule::getProjectId).toList();
                wrapper.in(Project::getId, projectIds);
            }
        }
        //排序
        wrapper.orderByDesc(Project::getCreateTime);
        return PageUtil.toPage(this.page(page1, wrapper).convert(project -> vo(project)));
    }
    protected ProjectVO vo(Project project){
        ProjectVO projectVO = new ProjectVO(project);
        projectVO.setScheduleStatus(projectScheduleService.details(project.getId()).getStatus());
        return projectVO;
    }

    @Override
    public List<Project> queryAll(ProjectQueryCriteria criteria) {
        return projectMapper.findAll(criteria);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(Project resources) {
        resources.setProjectStatus(ProjectEnums.ESTABLISH.getValue());
        save(resources);
        ProjectSchedule schedule = new ProjectSchedule()
                .setProjectId(resources.getId())
                .setScheduleStatus(ScheduleEnum.BUSINESS)
                .setAmountPercent(resources.getAmountPercent())
                .setScheduleDesc("未开始")
                .setNickName(resources.getNickName());
        projectScheduleService.save(schedule);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Project resources) {
        Project project = getById(resources.getId());
        project.copy(resources);
        saveOrUpdate(project);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(List<Long> ids) {
        for (Long id : ids) {
            projectScheduleService.remove(new LambdaQueryWrapper<ProjectSchedule>().eq(ProjectSchedule::getProjectId, id));
        }
        removeBatchByIds(ids);
    }

    @Override
    public void download(List<Project> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (Project project : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("公司id", project.getCompanyId());
            map.put("公司名称", project.getCompanyName());
            map.put("项目名称", project.getProjectName());
            map.put("项目描述", project.getProjectDesc());
            map.put("项目金额", project.getProjectAmount());
            map.put("项目状态", project.getProjectStatus());
            map.put("创建者名字", project.getNickName());
            map.put("创建者", project.getCreateBy());
            map.put("更新者", project.getUpdateBy());
            map.put("创建日期", project.getCreateTime());
            map.put("更新时间", project.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}