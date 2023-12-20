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
import me.zhengjie.modules.merchant.domain.ProjectSchedule;
import me.zhengjie.modules.merchant.domain.vo.ProjectScheduleVO;
import me.zhengjie.modules.merchant.domain.vo.ScheduleCommand;
import me.zhengjie.modules.merchant.domain.vo.ScheduleVO;
import me.zhengjie.modules.merchant.enums.ScheduleEnum;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.zhengjie.modules.merchant.service.ProjectScheduleService;
import me.zhengjie.modules.merchant.domain.vo.ProjectScheduleQueryCriteria;
import me.zhengjie.modules.merchant.mapper.ProjectScheduleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import me.zhengjie.utils.PageUtil;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import me.zhengjie.utils.PageResult;

/**
* @description 服务实现
* @author ChinaJoy
* @date 2023-11-26
**/
@Service
@RequiredArgsConstructor
public class ProjectScheduleServiceImpl extends ServiceImpl<ProjectScheduleMapper, ProjectSchedule> implements ProjectScheduleService {

    private final ProjectScheduleMapper projectScheduleMapper;

    @Override
    public PageResult<ProjectScheduleVO> queryAll(ProjectScheduleQueryCriteria criteria, Page<Object> page){
        IPage<ProjectSchedule> page1 = new Page<>(page.getCurrent(),page.getSize());
        LambdaQueryWrapper<ProjectSchedule> wrapper= new LambdaQueryWrapper<ProjectSchedule>().eq(ProjectSchedule::getProjectId,criteria.getProjectId());
        return PageUtil.toPage(this.page(page1,wrapper).convert(projectSchedule -> new ProjectScheduleVO(projectSchedule)));
    }

    @Override
    public List<ProjectSchedule> queryAll(ProjectScheduleQueryCriteria criteria){
        return projectScheduleMapper.findAll(criteria);
    }

    @Override
    public ScheduleVO details(Long projectId) {
        List<ProjectSchedule> projectSchedules = this.list(new LambdaQueryWrapper<ProjectSchedule>()
                .eq(ProjectSchedule::getProjectId, projectId).orderByDesc(ProjectSchedule::getScheduleStatus));
        ScheduleVO scheduleVO = new ScheduleVO();
        scheduleVO.setStatus(projectSchedules.get(0).getScheduleStatus().getValue());
        return scheduleVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(ProjectSchedule resources) {
        save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ProjectSchedule resources) {
        ProjectSchedule projectSchedule = getById(resources.getId());
        projectSchedule.copy(resources);
        saveOrUpdate(projectSchedule);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(List<Long> ids) {
        removeBatchByIds(ids);
    }

    @Override
    public void download(List<ProjectSchedule> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ProjectSchedule projectSchedule : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("项目id", projectSchedule.getProjectId());
            map.put("进度状态", projectSchedule.getScheduleStatus());
            map.put("指派人", projectSchedule.getAssignUser());
            map.put("创建者名字", projectSchedule.getNickName());
            map.put("创建者", projectSchedule.getCreateBy());
            map.put("更新者", projectSchedule.getUpdateBy());
            map.put("创建日期", projectSchedule.getCreateTime());
            map.put("更新时间", projectSchedule.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public void assign(ScheduleCommand command) {
        ProjectSchedule schedule = new ProjectSchedule()
                .setAssignUser(command.getAssignUser())
                .setAssignUserId(command.getAssignUserId())
                .setProjectId(command.getProjectId())
                .setScheduleStatus(ScheduleEnum.TEAMLEADER)
                .setNickName(command.getNickName());
        this.save(schedule);
    }

    @Override
    public void setManager(ScheduleCommand command) {
        ProjectSchedule schedule = new ProjectSchedule()
                .setProjectId(command.getProjectId())
                .setScheduleStatus(ScheduleEnum.MANAGER)
                .setScheduleDesc(command.getScheduleDesc())
                .setNickName(command.getNickName());
        this.save(schedule);
    }

    @Override
    public void transfer(ScheduleCommand command) {
        ProjectSchedule schedule = new ProjectSchedule()
                .setProjectId(command.getProjectId())
                .setScheduleStatus(ScheduleEnum.FINANCE)
                .setNickName(command.getNickName());
        this.save(schedule);

    }
}