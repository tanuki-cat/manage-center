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
import me.zhengjie.modules.merchant.domain.PatentSchedule;
import me.zhengjie.modules.merchant.domain.vo.PatentScheduleVO;
import me.zhengjie.modules.merchant.domain.vo.ScheduleVO;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.zhengjie.modules.merchant.service.PatentScheduleService;
import me.zhengjie.modules.merchant.domain.vo.PatentScheduleQueryCriteria;
import me.zhengjie.modules.merchant.mapper.PatentScheduleMapper;
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
* @date 2024-01-02
**/
@Service
@RequiredArgsConstructor
public class PatentScheduleServiceImpl extends ServiceImpl<PatentScheduleMapper, PatentSchedule> implements PatentScheduleService {

    private final PatentScheduleMapper patentScheduleMapper;

    @Override
    public PageResult<PatentScheduleVO> queryAll(PatentScheduleQueryCriteria criteria, Page<Object> page){
        IPage<PatentSchedule> page1 =new Page<>(page.getCurrent(),page.getSize());
        LambdaQueryWrapper<PatentSchedule> wrapper= new LambdaQueryWrapper<PatentSchedule>().eq(PatentSchedule::getPatentId,criteria.getPatentId());
        return PageUtil.toPage(this.page(page1,wrapper).convert(patentSchedule -> new PatentScheduleVO(patentSchedule)));
    }

    @Override
    public List<PatentSchedule> queryAll(PatentScheduleQueryCriteria criteria){
        return patentScheduleMapper.findAll(criteria);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(PatentSchedule resources) {
        save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(PatentSchedule resources) {
        PatentSchedule patentSchedule = getById(resources.getId());
        patentSchedule.copy(resources);
        saveOrUpdate(patentSchedule);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(List<Long> ids) {
        removeBatchByIds(ids);
    }

    @Override
    public void download(List<PatentSchedule> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (PatentSchedule patentSchedule : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("项目id", patentSchedule.getPatentId());
            map.put("进度状态", patentSchedule.getScheduleStatus());
            map.put("指派人", patentSchedule.getAssignUser());
            map.put("指派人员Id", patentSchedule.getAssignUserId());
            map.put("指派状态 1- 作废 0- 指派", patentSchedule.getAssignStatus());
            map.put("专利进度", patentSchedule.getProgress());
            map.put("专利申报时间", patentSchedule.getFilingTime());
            map.put("专利写好时间", patentSchedule.getWriteTime());
            map.put("授权下证时间", patentSchedule.getAuthorizationTime());
            map.put("专利数", patentSchedule.getPatentNum());
            map.put("年费预警时间", patentSchedule.getForewarnTime());
            map.put("创建者名字", patentSchedule.getNickName());
            map.put("创建者", patentSchedule.getCreateBy());
            map.put("更新者", patentSchedule.getUpdateBy());
            map.put("创建日期", patentSchedule.getCreateTime());
            map.put("更新时间", patentSchedule.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public ScheduleVO details(Long patentId) {
        List<PatentSchedule> patentSchedules = this.list(new LambdaQueryWrapper<PatentSchedule>()
                .eq(PatentSchedule::getPatentId, patentId).orderByDesc(PatentSchedule::getScheduleStatus));
        ScheduleVO scheduleVO = new ScheduleVO();
        scheduleVO.setStatus(patentSchedules.get(0).getScheduleStatus().getValue());
        return scheduleVO;
    }
}