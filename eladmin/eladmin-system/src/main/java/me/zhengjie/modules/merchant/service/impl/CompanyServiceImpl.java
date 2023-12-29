package me.zhengjie.modules.merchant.service.impl;/*
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
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.merchant.domain.Company;
import me.zhengjie.modules.merchant.domain.CompanyExt;
import me.zhengjie.modules.merchant.domain.dto.CompanyInfoExtDto;
import me.zhengjie.modules.merchant.domain.vo.CompanyQueryCriteria;
import me.zhengjie.modules.merchant.mapper.CompanyMapper;
import me.zhengjie.modules.merchant.service.CompanyExtService;
import me.zhengjie.modules.merchant.service.CompanyInfoExtService;
import me.zhengjie.modules.merchant.service.CompanyService;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import me.zhengjie.utils.PageUtil;

import java.util.*;
import java.io.IOException;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import me.zhengjie.utils.PageResult;

/**
* @description 服务实现
* @author ChinaJoy
* @date 2023-11-22
**/
@Service
@RequiredArgsConstructor(onConstructor_= {@Lazy})
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements CompanyService {

    private final CompanyMapper companyMapper;
    private final CompanyInfoExtService companyInfoExtService;
    private final CompanyExtService companyExtService;

    @Override
    public PageResult<Company> queryAll(CompanyQueryCriteria criteria, Page<Object> page){
        LambdaQueryWrapper<Company> queryWrapper = new LambdaQueryWrapper<>();
        Page<Company> companyPage = new Page<>(page.getCurrent(), page.getSize());
        //搜索
        if (Strings.isNotBlank(criteria.getKeywords())) {
            queryWrapper.like(Company::getName, criteria.getKeywords())
                    .or().like(Company::getUserName, criteria.getKeywords());
        }
        if (Strings.isNotBlank(criteria.getPhone())) {
            queryWrapper.or().eq(Company::getUserMobile, criteria.getPhone());
        }
        if (Strings.isNotBlank(criteria.getCreateBy())) {
            queryWrapper.eq(Company::getCreateBy, criteria.getCreateBy());
        }
        IPage<Company> companyIPage = this.page(companyPage, queryWrapper);
        return PageUtil.toPage(companyIPage);
    }

    @Override
    public List<Company> queryAll(CompanyQueryCriteria criteria){
        return companyMapper.findAll(criteria);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(Company resources) {
        List<Company> company = list(new LambdaQueryWrapper<Company>().eq(Company::getName, resources.getName()));
        if(!company.isEmpty()){
            throw new BadRequestException("公司名称已存在");
        }
        save(resources);
        if (resources.getCompanyBaseInfoExt() != null && resources.getCompanyBusinessInfoExt() != null) {
            resources.getCompanyBaseInfoExt().setCompanyId(resources.getId());
            resources.getCompanyBaseInfoExt().setCompanyId(resources.getId());
            CompanyInfoExtDto companyInfoExtDto = new CompanyInfoExtDto();
            BeanUtils.copyProperties(resources, companyInfoExtDto);
            this.companyInfoExtService.create(companyInfoExtDto);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Company resources) {
        List<Company> list = list(new LambdaQueryWrapper<Company>().eq(Company::getName, resources.getName())
                .ne(Company::getId, resources.getId()));
        if(!list.isEmpty()){
            throw new BadRequestException("公司名称已存在");
        }
        Company company = getById(resources.getId());
        company.copy(resources);
        saveOrUpdate(company);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(List<Long> ids) {
        LambdaQueryWrapper<CompanyExt> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(CompanyExt::getCompanyId,ids);
        if (companyExtService.count(queryWrapper) > 0 ) {
            List<Long> extIds =  companyExtService.list(queryWrapper).stream().map(CompanyExt::getId).toList();
            companyExtService.removeBatchByIds(extIds);
        }
        removeBatchByIds(ids);
    }

    @Override
    public void download(List<Company> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (Company company : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("公司名称", company.getName());
            map.put("公司联系人", company.getUserName());
            map.put("公司联系人移动电话", company.getUserMobile());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}