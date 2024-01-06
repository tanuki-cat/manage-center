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
import me.zhengjie.modules.merchant.domain.ProjectSchedule;
import me.zhengjie.modules.merchant.domain.vo.ProjectScheduleVO;
import me.zhengjie.modules.merchant.domain.vo.ScheduleCommand;
import me.zhengjie.modules.merchant.service.ProjectScheduleService;
import me.zhengjie.modules.merchant.domain.vo.ProjectScheduleQueryCriteria;
import lombok.RequiredArgsConstructor;
import java.util.List;

import me.zhengjie.modules.merchant.service.ProjectUpdateService;
import me.zhengjie.modules.system.service.UserService;
import me.zhengjie.utils.SecurityUtils;
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
* @date 2023-11-26
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "projectschedule管理")
@RequestMapping("/api/merchant/projectSchedule")
public class ProjectScheduleController {

    private final ProjectScheduleService projectScheduleService;
    private final UserService userService;
    private final ProjectUpdateService projectUpdateService;
    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('projectSchedule:list')")
    public void exportProjectSchedule(HttpServletResponse response, ProjectScheduleQueryCriteria criteria) throws IOException {
        projectScheduleService.download(projectScheduleService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询projectschedule")
    @ApiOperation("查询projectschedule")
    @PreAuthorize("@el.check('projectSchedule:list')")
    public ResponseEntity<PageResult<ProjectScheduleVO>> queryProjectSchedule(ProjectScheduleQueryCriteria criteria, Page<Object> page){
        return new ResponseEntity<>(projectScheduleService.queryAll(criteria,page),HttpStatus.OK);
    }
    @GetMapping(value = "/details/{projectId}")
    @ApiOperation("查询projectschedule详情")
    public ResponseEntity<Object> queryProjectScheduleDetails(@PathVariable Long projectId){
        return new ResponseEntity<>(projectScheduleService.details(projectId),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增projectschedule")
    @ApiOperation("新增projectschedule")
    @PreAuthorize("@el.check('projectSchedule:add')")
    public ResponseEntity<Object> createProjectSchedule(@Validated @RequestBody ProjectSchedule resources){
        resources.setNickName(userService.findById(SecurityUtils.getCurrentUserId()).getNickName());
        projectScheduleService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改projectschedule")
    @ApiOperation("修改projectschedule")
    @PreAuthorize("@el.check('projectSchedule:edit')")
    public ResponseEntity<Object> updateProjectSchedule(@Validated @RequestBody ProjectSchedule resources){
        projectScheduleService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除projectschedule")
    @ApiOperation("删除projectschedule")
    @PreAuthorize("@el.check('projectSchedule:del')")
    public ResponseEntity<Object> deleteProjectSchedule(@RequestBody List<Long> ids) {
        projectScheduleService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Log("派发项目")
    @ApiOperation("派发项目")
    @PostMapping(value = "/assign")
    @PreAuthorize("@el.check('projectSchedule:assign')")
    public ResponseEntity<Object> assignProject(@RequestBody ScheduleCommand resources){
        resources.setNickName(userService.findById(SecurityUtils.getCurrentUserId()).getNickName());
        projectUpdateService.assign(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Log("项目经理填写")
    @ApiOperation("项目经理填写")
    @PostMapping(value = "/manager")
    public ResponseEntity<Object> managerProject(@RequestBody ScheduleCommand resources){
        resources.setNickName(userService.findById(SecurityUtils.getCurrentUserId()).getNickName());
        projectScheduleService.setManager(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @Log("转交财务人员")
    @ApiOperation("转交财务人员")
    @PostMapping(value = "/transfer")
    public ResponseEntity<Object> transferProject(@RequestBody ScheduleCommand resources){
        resources.setNickName(userService.findById(SecurityUtils.getCurrentUserId()).getNickName());
        projectScheduleService.transfer(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Log("完成项目")
    @ApiOperation("完成项目")
    @PostMapping(value = "/complete")
    public ResponseEntity<Object> completeProject(@RequestBody ScheduleCommand resources){
        resources.setNickName(userService.findById(SecurityUtils.getCurrentUserId()).getNickName());
        projectUpdateService.complete(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @Log("财务提交")
    @ApiOperation("财务提交")
    @PostMapping(value = "/finance")
    public ResponseEntity<Object> financeProject(@RequestBody ScheduleCommand resources){
        resources.setNickName(userService.findById(SecurityUtils.getCurrentUserId()).getNickName());
        projectUpdateService.finance(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}