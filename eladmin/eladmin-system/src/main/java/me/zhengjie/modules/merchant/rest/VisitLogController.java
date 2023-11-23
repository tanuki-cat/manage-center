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
package me.zhengjie.modules.merchant.rest;

import com.mchange.lang.LongUtils;
import me.zhengjie.annotation.Log;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.merchant.domain.VisitLog;
import me.zhengjie.modules.merchant.service.VisitLogService;
import me.zhengjie.modules.merchant.domain.vo.VisitLogQueryCriteria;
import lombok.RequiredArgsConstructor;
import java.util.List;

import me.zhengjie.modules.system.service.UserService;
import me.zhengjie.utils.SecurityUtils;
import me.zhengjie.utils.StringUtils;
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
* @date 2023-11-23
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "visit管理")
@RequestMapping("/api/merchant/visitLog")
public class VisitLogController  {

    private final VisitLogService visitLogService;
    private final UserService userService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('visitLog:list')")
    public void exportVisitLog(HttpServletResponse response, VisitLogQueryCriteria criteria) throws IOException {
        visitLogService.download(visitLogService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询visit")
    @ApiOperation("查询visit")
    @PreAuthorize("@el.check('visitLog:list')")
    public ResponseEntity<PageResult<VisitLog>> queryVisitLog(VisitLogQueryCriteria criteria, Page<Object> page){
        if (criteria.getCompanyId()==null|| criteria.getCompanyId()==0L){
            throw new  BadRequestException("非法操作");
        }
        return new ResponseEntity<>(visitLogService.queryAll(criteria,page),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增visit")
    @ApiOperation("新增visit")
    @PreAuthorize("@el.check('visitLog:add')")
    public ResponseEntity<Object> createVisitLog(@Validated @RequestBody VisitLog resources){
        resources.setNickName(userService.findById(SecurityUtils.getCurrentUserId()).getNickName());
        visitLogService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改visit")
    @ApiOperation("修改visit")
    @PreAuthorize("@el.check('visitLog:edit')")
    public ResponseEntity<Object> updateVisitLog(@Validated @RequestBody VisitLog resources){
        visitLogService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除visit")
    @ApiOperation("删除visit")
    @PreAuthorize("@el.check('visitLog:del')")
    public ResponseEntity<Object> deleteVisitLog(@RequestBody List<Long> ids) {
        visitLogService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}