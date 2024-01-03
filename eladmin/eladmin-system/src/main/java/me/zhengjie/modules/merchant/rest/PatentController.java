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

import me.zhengjie.annotation.CheckCreate;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.merchant.domain.Patent;
import me.zhengjie.modules.merchant.domain.vo.PatentVO;
import me.zhengjie.modules.merchant.domain.vo.ProjectQueryCriteria;
import me.zhengjie.modules.merchant.service.PatentService;
import me.zhengjie.modules.merchant.domain.vo.PatentQueryCriteria;
import lombok.RequiredArgsConstructor;
import java.util.List;

import me.zhengjie.modules.merchant.service.PatentUpdateSerivce;
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
* @date 2024-01-02
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "patent管理")
@RequestMapping("/api/merchant/patent")
public class PatentController {
    private final UserService userService;
    private final PatentService patentService;
    private final PatentUpdateSerivce patentUpdateSerivce;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('patent:list')")
    public void exportPatent(HttpServletResponse response, PatentQueryCriteria criteria) throws IOException {
        patentService.download(patentService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询patent")
    @ApiOperation("查询patent")
    @PreAuthorize("@el.check('patent:list')")
    @CheckCreate(roles = {"专利经理"},clazz = PatentQueryCriteria.class,filedMethod = {"setCreateBy","setAssignUser","setAssignUserId"})
    public ResponseEntity<PageResult<PatentVO>> queryPatent(PatentQueryCriteria criteria, Page<Object> page){
        return new ResponseEntity<>(patentService.queryAll(criteria,page),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增patent")
    @ApiOperation("新增patent")
    @PreAuthorize("@el.check('patent:add')")
    public ResponseEntity<Object> createPatent(@Validated @RequestBody Patent resources){
        resources.setNickName(userService.findById(SecurityUtils.getCurrentUserId()).getNickName());
        patentUpdateSerivce.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改patent")
    @ApiOperation("修改patent")
    @PreAuthorize("@el.check('patent:edit')")
    public ResponseEntity<Object> updatePatent(@Validated @RequestBody Patent resources){
        patentService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除patent")
    @ApiOperation("删除patent")
    @PreAuthorize("@el.check('patent:del')")
    public ResponseEntity<Object> deletePatent(@RequestBody List<Long> ids) {
        patentService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}