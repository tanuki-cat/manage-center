package me.zhengjie.modules.merchant.domain;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.zhengjie.base.BaseEntity;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author wangzy
 * @date 2023/12/12 20:00
 * @describe 企业经营信息扩展表
 * @since version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "企业经营情况信息扩展表")
@TableName(value = "biz_company_business_info_ext",autoResultMap = true)
public class CompanyBusinessInfoExt extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "公司id")
    @TableField(value = "company_id")
    private Long companyId;

    @ApiModelProperty(value = "三年知识产权情况")
    @TableField(value = "third_years_property_rights", typeHandler = JacksonTypeHandler.class)
    private List<JSONObject> thirdYearsPropertyRights;

    @TableField(value = "school_enterprise_cooperation")
    @ApiModelProperty(value = "校企合作情况")
    private String schoolEnterpriseCooperation;

    @TableField(value = "projects_under_declaration")
    @ApiModelProperty(value = "申报中项目")
    private String projectsUnderDeclaration;

    @TableField(value = "incident_third_years")
    @ApiModelProperty(value = "三年内是否有环保事故，税务处罚")
    private String incidentThirdYears;

    @ApiModelProperty(value = "三年财务状况")
    @TableField(value = "three_years_finance", typeHandler = JacksonTypeHandler.class)
    private List<JSONObject> threeYearsFinance;

    @ApiModelProperty(value = "历年来获取的资质、荣誉、项目")
    @TableField(value = "years_honors", typeHandler = JacksonTypeHandler.class)
    private List<JSONObject> yearsHonors;

    @TableField(value = "systematic_situation")
    @ApiModelProperty(value = "体系情况",allowableValues = "1-质量体系 2-环境体系 3-职业健康体系 4-其他，选择4时，填写备注")
    private Integer systematicSituation;

    @ApiModelProperty(value = "备注")
    @TableField(value = "remark")
    private String remark;

    @TableField(value = "is_deleted")
    @TableLogic(value = "0",delval = "1")
    private Integer isDeleted;
}
