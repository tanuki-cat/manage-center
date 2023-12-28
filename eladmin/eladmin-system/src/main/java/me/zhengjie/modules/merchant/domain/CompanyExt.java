package me.zhengjie.modules.merchant.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import me.zhengjie.base.BaseEntity;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author wangzy
 * @date 2023/12/28 10:38
 * @describe 公司扩展信息表
 * @since version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName(value = "sys_company_ext",autoResultMap = true)
public class CompanyExt extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键ID")
    private Long id;
    @ApiModelProperty(value = "公司id")
    @TableField(value = "company_id")
    private Long companyId;
    @ApiModelProperty(value = "扩展信息")
    @TableField(value = "info_ext" ,typeHandler = JacksonTypeHandler.class)
    private JSONObject infoExt;

    public void copy(CompanyExt source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
