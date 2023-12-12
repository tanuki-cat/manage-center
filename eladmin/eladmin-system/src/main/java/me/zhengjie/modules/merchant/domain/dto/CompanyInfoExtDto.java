package me.zhengjie.modules.merchant.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.zhengjie.modules.merchant.domain.Company;
import me.zhengjie.modules.merchant.domain.CompanyBaseInfoExt;
import me.zhengjie.modules.merchant.domain.CompanyBusinessInfoExt;

/**
 * @author wangzy
 * @date 2023/12/12 21:09
 * @describe 公司信息dto 对象
 * @since version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "企业信息dto对象")
public class CompanyInfoExtDto extends Company {
    @ApiModelProperty(value = "公司基本信息扩展")
    private CompanyBaseInfoExt companyBaseInfoExt;

    @ApiModelProperty(value = "公司经营信息扩展")
    private CompanyBusinessInfoExt companyBusinessInfoExt;
}
