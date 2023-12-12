package me.zhengjie.modules.merchant.domain;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.zhengjie.base.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author wangzy
 * @date 2023/12/3 14:31
 * @describe 企业信息扩展表
 * @since version 1.0
 */
@ApiModel(value = "企业基本信息扩展表")
@Data
@TableName(value = "biz_company_base_info_ext",autoResultMap = true)
@EqualsAndHashCode(callSuper = true)
public class CompanyBaseInfoExt extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "公司id")
    @TableField(value = "company_id")
    private Long companyId;

    @TableField(value = "industry")
    @ApiModelProperty(value = "所属行业")
    private String industry;

    @TableField(value = "main_products")
    @ApiModelProperty(value = "主营产品")
    private String mainProducts;

    @TableField(value = "technical_indicator")
    @ApiModelProperty(value = "技术指标",allowableValues = "1-高于国家标准,2-参照国家标准")
    private Integer technicalIndicator;

    @TableField(value = "company_nature")
    @ApiModelProperty(value = "公司性质",allowableValues = "1-国有企业,2-外方独资,3-合资企业,4-合资企业,5-民营企业,6-其他")
    private Integer companyNature;

    @TableField(value = "registration_time")
    @ApiModelProperty(value = "注册时间",example = "2020-01-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date registrationTime;

    @TableField(value = "registry_addr")
    @ApiModelProperty(value = "注册地址")
    private String registryAddr;

    @ApiModelProperty(value = "注册资本")
    @TableField(value = "registered_capital")
    private String registeredCapital;

    @TableField(value = "workers_num")
    @ApiModelProperty(value = "员工人数")
    private Integer workersNum;

    @TableField(value = "tertiary_education_tech_num")
    @ApiModelProperty(value = "大专及以上技术人数")
    private Integer tertiaryEducationTechNum;

    @ApiModelProperty(value = "研发人员数")
    @TableField(value = "developers_num")
    private Integer developersNum;

    @ApiModelProperty(value = "研发人员占比")
    @TableField(value = "developers_percentage")
    private String developersPercentage;

    @TableField(value = "workers_designation",typeHandler = JacksonTypeHandler.class)
    @ApiModelProperty(value = "员工职称")
    private List<JSONObject> workersDesignation;

    @TableField(value = "is_deleted")
    @TableLogic(value = "0",delval = "1")
    private Integer isDeleted;
}
