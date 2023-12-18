package me.zhengjie.modules.merchant.domain;/*
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


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.update.Update;
import io.swagger.annotations.Api;
import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import java.sql.Timestamp;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import me.zhengjie.base.BaseEntity;

import javax.validation.constraints.NotNull;

/**
* @description /
* @author ChinaJoy
* @date 2023-11-22
**/
@Data
@TableName("sys_company")
public class Company extends BaseEntity implements Serializable {


    @NotNull(groups = Update.class)
    @TableId(value="company_id", type = IdType.AUTO)
    @ApiModelProperty(value = "ID", hidden = true)
    private Long id;

    @ApiModelProperty(value = "公司名称")
    private String name;

    @ApiModelProperty(value = "公司联系人")
    private String userName;

    @ApiModelProperty(value = "公司联系人移动电话")
    private String userMobile;


    @TableField(exist = false)
    @ApiModelProperty(value = "公司基础信息扩展表")
    private CompanyBaseInfoExt companyBaseInfoExt;
    @TableField(exist = false)
    @ApiModelProperty(value = "公司业务信息扩展表")
    private CompanyBusinessInfoExt companyBusinessInfoExt;

    public void copy(Company source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
