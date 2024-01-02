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
import java.sql.Timestamp;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import me.zhengjie.base.BaseEntity;

/**
* @description /
* @author ChinaJoy
* @date 2024-01-02
**/
@Data
@TableName("sys_patent")
public class Patent extends BaseEntity implements Serializable {

    @TableId(value = "patent_id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "公司id")
    private Long companyId;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "发明专利")
    private String invention;

    @ApiModelProperty(value = "实用新型专利")
    private String utilityModel;

    @ApiModelProperty(value = "外观专利")
    private String appearance;

    @ApiModelProperty(value = "软件著作")
    private String softwareWorks;

    @ApiModelProperty(value = "版权")
    private String copyright;

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

    @ApiModelProperty(value = "状态")
    private Integer patentStatus;

    @ApiModelProperty(value = "创建者名字")
    private String nickName;


    public void copy(Patent source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
