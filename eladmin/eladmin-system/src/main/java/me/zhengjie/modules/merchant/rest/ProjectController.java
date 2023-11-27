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
import me.zhengjie.modules.merchant.domain.Project;
import me.zhengjie.modules.merchant.service.ProjectService;
import me.zhengjie.modules.merchant.domain.vo.ProjectQueryCriteria;
import lombok.RequiredArgsConstructor;
import java.util.List;

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
@Api(tags = "project管理")
@RequestMapping("/api/merchant/project")
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('project:list')")
    public void exportProject(HttpServletResponse response, ProjectQueryCriteria criteria) throws IOException {
        projectService.download(projectService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询project")
    @ApiOperation("查询project")
    @PreAuthorize("@el.check('project:list')")
    public ResponseEntity<PageResult<Project>> queryProject(ProjectQueryCriteria criteria, Page<Object> page){
        return new ResponseEntity<>(projectService.queryAll(criteria,page),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增project")
    @ApiOperation("新增project")
    @PreAuthorize("@el.check('project:add')")
    public ResponseEntity<Object> createProject(@Validated @RequestBody Project resources){
        resources.setNickName(userService.findById(SecurityUtils.getCurrentUserId()).getNickName());
        projectService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改project")
    @ApiOperation("修改project")
    @PreAuthorize("@el.check('project:edit')")
    public ResponseEntity<Object> updateProject(@Validated @RequestBody Project resources){
        projectService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除project")
    @ApiOperation("删除project")
    @PreAuthorize("@el.check('project:del')")
    public ResponseEntity<Object> deleteProject(@RequestBody List<Long> ids) {
        projectService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}