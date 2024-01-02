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

import me.zhengjie.annotation.Log;
import me.zhengjie.modules.merchant.domain.PatentSchedule;
import me.zhengjie.modules.merchant.service.PatentScheduleService;
import me.zhengjie.modules.merchant.domain.vo.PatentScheduleQueryCriteria;
import lombok.RequiredArgsConstructor;
import java.util.List;
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
* @date 2024-01-02
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "patentSchedule管理")
@RequestMapping("/api/patentSchedule")
public class PatentScheduleController {

    private final PatentScheduleService patentScheduleService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('patentSchedule:list')")
    public void exportPatentSchedule(HttpServletResponse response, PatentScheduleQueryCriteria criteria) throws IOException {
        patentScheduleService.download(patentScheduleService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询patentSchedule")
    @ApiOperation("查询patentSchedule")
    @PreAuthorize("@el.check('patentSchedule:list')")
    public ResponseEntity<PageResult<PatentSchedule>> queryPatentSchedule(PatentScheduleQueryCriteria criteria, Page<Object> page){
        return new ResponseEntity<>(patentScheduleService.queryAll(criteria,page),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增patentSchedule")
    @ApiOperation("新增patentSchedule")
    @PreAuthorize("@el.check('patentSchedule:add')")
    public ResponseEntity<Object> createPatentSchedule(@Validated @RequestBody PatentSchedule resources){
        patentScheduleService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改patentSchedule")
    @ApiOperation("修改patentSchedule")
    @PreAuthorize("@el.check('patentSchedule:edit')")
    public ResponseEntity<Object> updatePatentSchedule(@Validated @RequestBody PatentSchedule resources){
        patentScheduleService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除patentSchedule")
    @ApiOperation("删除patentSchedule")
    @PreAuthorize("@el.check('patentSchedule:del')")
    public ResponseEntity<Object> deletePatentSchedule(@RequestBody List<Long> ids) {
        patentScheduleService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}