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

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import me.zhengjie.base.BaseEntity;
import me.zhengjie.modules.merchant.domain.VisitLog;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.zhengjie.modules.merchant.service.VisitLogService;
import me.zhengjie.modules.merchant.domain.vo.VisitLogQueryCriteria;
import me.zhengjie.modules.merchant.mapper.VisitLogMapper;
import me.zhengjie.utils.StringUtils;
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
* @date 2023-11-23
**/
@Service
@RequiredArgsConstructor
public class VisitLogServiceImpl extends ServiceImpl<VisitLogMapper, VisitLog> implements VisitLogService {

    private final VisitLogMapper visitLogMapper;

    @Override
    public PageResult<VisitLog> queryAll(VisitLogQueryCriteria criteria, Page<Object> page){
        IPage<VisitLog> page1 = new Page<>(page.getCurrent(),page.getSize());
        LambdaQueryWrapper<VisitLog> wrapper= new LambdaQueryWrapper<VisitLog>().eq(VisitLog::getCompanyId,criteria.getCompanyId());
        if(StringUtils.isNotBlank(criteria.getUserName())){
            wrapper.eq(BaseEntity::getCreateBy,criteria.getUserName());
        }
        return PageUtil.toPage(this.page(page1,wrapper));
    }

    @Override
    public List<VisitLog> queryAll(VisitLogQueryCriteria criteria){
        return visitLogMapper.findAll(criteria);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(VisitLog resources) {
        save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(VisitLog resources) {
        VisitLog visitLog = getById(resources.getId());
        visitLog.copy(resources);
        saveOrUpdate(visitLog);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(List<Long> ids) {
        removeBatchByIds(ids);
    }

    @Override
    public void download(List<VisitLog> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (VisitLog visitLog : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("公司id", visitLog.getCompanyId());
            map.put("公司名称", visitLog.getCompanyName());
            map.put("日志内容", visitLog.getContent());
            map.put("创建者名字", visitLog.getNickName());
            map.put("创建者", visitLog.getCreateBy());
            map.put("更新者", visitLog.getUpdateBy());
            map.put("创建日期", visitLog.getCreateTime());
            map.put("更新时间", visitLog.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}