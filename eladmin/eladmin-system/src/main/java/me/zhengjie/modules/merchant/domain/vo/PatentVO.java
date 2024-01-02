package me.zhengjie.modules.merchant.domain.vo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;
import me.zhengjie.modules.merchant.domain.Patent;
import me.zhengjie.modules.merchant.domain.Project;
import me.zhengjie.modules.merchant.enums.ProjectEnums;

@Data
public class PatentVO extends Patent {
    private String statusName;
    private  Integer scheduleStatus;

    public PatentVO(Patent entity){
        BeanUtil.copyProperties(entity,this, CopyOptions.create().setIgnoreNullValue(true));
        this.statusName = ProjectEnums.name(entity.getPatentStatus());
    }
}
