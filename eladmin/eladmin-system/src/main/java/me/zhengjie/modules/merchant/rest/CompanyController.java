package me.zhengjie.modules.merchant.rest;/*
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

import me.zhengjie.annotation.Log;
import lombok.RequiredArgsConstructor;
import java.util.List;

import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.merchant.domain.Company;
import me.zhengjie.modules.merchant.domain.vo.CompanyQueryCriteria;
import me.zhengjie.modules.merchant.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.zhengjie.utils.PageResult;

/**
* @author ChinaJoy
* @date 2023-11-22
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "company管理")
@RequestMapping("/api/merchant/company")
public class CompanyController {

    private final CompanyService companyService;
    private static final String ENTITY_NAME = "company";
    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('company:list')")
    public void exportCompany(HttpServletResponse response, CompanyQueryCriteria criteria) throws IOException {
        companyService.download(companyService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询company")
    @ApiOperation("查询company")
    @PreAuthorize("@el.check('company:list')")
    public ResponseEntity<PageResult<Company>> queryCompany(CompanyQueryCriteria criteria, Page<Object> page){
        return new ResponseEntity<>(companyService.queryAll(criteria,page),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增company")
    @ApiOperation("新增company")
    @PreAuthorize("@el.check('company:add')")
    public ResponseEntity<Object> createCompany(@Validated @RequestBody Company resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        companyService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改company")
    @ApiOperation("修改company")
    @PreAuthorize("@el.check('company:edit')")
    public ResponseEntity<Object> updateCompany(@Validated @RequestBody Company resources){
        companyService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除company")
    @ApiOperation("删除company")
    @PreAuthorize("@el.check('company:del')")
    public ResponseEntity<Object> deleteCompany(@RequestBody List<Long> ids) {
        companyService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}