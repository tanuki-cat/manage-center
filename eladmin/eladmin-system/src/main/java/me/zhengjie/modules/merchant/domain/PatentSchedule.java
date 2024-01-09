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
package me.zhengjie.modules.merchant.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;
import me.zhengjie.base.BaseEntity;
import me.zhengjie.modules.merchant.enums.PatentScheduleEnum;
import javax.validation.constraints.NotNull;

/**
* @description /
* @author ChinaJoy
* @date 2024-01-02
**/
@Data
@Accessors(chain = true)
@TableName("sys_patent_schedule")
public class PatentSchedule extends BaseEntity implements Serializable {

    @TableId(value = "schedule_id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "项目id")
    private Long patentId;

    @ApiModelProperty(value = "进度状态")
    private PatentScheduleEnum scheduleStatus;

    @ApiModelProperty(value = "指派人")
    private String assignUser;

    @ApiModelProperty(value = "指派人员Id")
    private Long assignUserId;

    @NotNull
    @ApiModelProperty(value = "指派状态 1- 作废 0- 指派")
    private Integer assignStatus;

    @ApiModelProperty(value = "专利进度")
    private String progress;

    @ApiModelProperty(value = "专利申报时间")
    private String filingTime;

    @ApiModelProperty(value = "专利写好时间")
    private String writeTime;

    @ApiModelProperty(value = "授权下证时间")
    private String authorizationTime;

    @ApiModelProperty(value = "专利数")
    private String patentNum;

    @ApiModelProperty(value = "年费预警时间")
    private String forewarnTime;

    @ApiModelProperty(value = "创建者名字")
    private String nickName;
    @ApiModelProperty(value = "金额百分比")
    private Integer amountPercent;

    public void copy(PatentSchedule source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
