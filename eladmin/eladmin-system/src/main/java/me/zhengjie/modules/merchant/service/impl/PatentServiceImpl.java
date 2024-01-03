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
import me.zhengjie.modules.merchant.domain.Patent;
import me.zhengjie.modules.merchant.domain.PatentSchedule;
import me.zhengjie.modules.merchant.domain.Project;
import me.zhengjie.modules.merchant.domain.vo.PatentVO;
import me.zhengjie.modules.merchant.domain.vo.ProjectVO;
import me.zhengjie.modules.merchant.service.PatentScheduleService;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.zhengjie.modules.merchant.service.PatentService;
import me.zhengjie.modules.merchant.domain.vo.PatentQueryCriteria;
import me.zhengjie.modules.merchant.mapper.PatentMapper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import me.zhengjie.utils.PageUtil;

import java.util.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import me.zhengjie.utils.PageResult;
import org.springframework.util.CollectionUtils;

/**
* @description 服务实现
* @author ChinaJoy
* @date 2024-01-02
**/
@Service
@RequiredArgsConstructor
public class PatentServiceImpl extends ServiceImpl<PatentMapper, Patent> implements PatentService {

    private final PatentMapper patentMapper;
    private final PatentScheduleService patentScheduleService;

    @Override
    public PageResult<PatentVO> queryAll(PatentQueryCriteria criteria, Page<Object> page){
        LambdaQueryWrapper<Patent> wrapper = new LambdaQueryWrapper<>();
        //设置查询条件
        if (criteria.getCompanyId() != null && criteria.getCompanyId() > 0) {
            wrapper.eq(Patent::getCompanyId, criteria.getCompanyId());
        }
        if (criteria.getCreateBy() != null) {
            wrapper.eq(Patent::getCreateBy, criteria.getCreateBy());
        }
        if (criteria.getUserName() != null) {
            wrapper.eq(Patent::getCreateBy, criteria.getUserName());
        }
        if (Strings.isNotBlank(criteria.getAssignUserId()) && Long.parseLong(criteria.getAssignUserId()) > 0) {
             List<PatentSchedule> patentSchedules = this.patentScheduleService.lambdaQuery()
                    .eq(PatentSchedule::getAssignUserId, criteria.getAssignUserId())
                    .eq(PatentSchedule::getAssignStatus,0).list();
            if (!CollectionUtils.isEmpty(patentSchedules)) {
                List<Long> patentIds = patentSchedules.stream().map(PatentSchedule::getPatentId).toList();
                wrapper.in(Patent::getId, patentIds);
            }

        }
        wrapper.orderByDesc(Patent::getCreateTime);
        IPage<Patent> page1 = new Page<>(page.getCurrent(), page.getSize());
        return PageUtil.toPage(patentMapper.selectPage(page1, wrapper).convert(patent -> vo(patent)));
    }
    protected PatentVO vo(Patent patent){
        PatentVO patentVO = new PatentVO(patent);
        patentVO.setScheduleStatus(patentScheduleService.details(patent.getId()).getStatus());
        return patentVO;
    }
    @Override
    public List<Patent> queryAll(PatentQueryCriteria criteria){
        return patentMapper.findAll(criteria);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(Patent resources) {
        save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Patent resources) {
        Patent patent = getById(resources.getId());
        patent.copy(resources);
        saveOrUpdate(patent);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(List<Long> ids) {
        //删除专利下的流程
        patentScheduleService.remove(new LambdaQueryWrapper<PatentSchedule>().in(PatentSchedule::getPatentId, ids));
        removeBatchByIds(ids);
    }

    @Override
    public void download(List<Patent> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (Patent patent : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("公司id", patent.getCompanyId());
            map.put("公司名称", patent.getCompanyName());
            map.put("发明专利", patent.getInvention());
            map.put("实用新型专利", patent.getUtilityModel());
            map.put("外观专利", patent.getAppearance());
            map.put("软件著作", patent.getSoftwareWorks());
            map.put("版权", patent.getCopyright());
            map.put("专利进度", patent.getProgress());
            map.put("专利申报时间", patent.getFilingTime());
            map.put("专利写好时间", patent.getWriteTime());
            map.put("授权下证时间", patent.getAuthorizationTime());
            map.put("专利数", patent.getPatentNum());
            map.put("年费预警时间", patent.getForewarnTime());
            map.put("状态", patent.getPatentStatus());
            map.put("创建者名字", patent.getNickName());
            map.put("创建者", patent.getCreateBy());
            map.put("更新者", patent.getUpdateBy());
            map.put("创建日期", patent.getCreateTime());
            map.put("更新时间", patent.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}