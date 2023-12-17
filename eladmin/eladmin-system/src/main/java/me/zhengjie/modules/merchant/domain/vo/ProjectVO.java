package me.zhengjie.modules.merchant.domain.vo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;
import me.zhengjie.modules.merchant.domain.Project;
import me.zhengjie.modules.merchant.enums.ProjectEnums;
import me.zhengjie.modules.merchant.enums.ScheduleEnum;

@Data
public class ProjectVO extends Project {
    private String statusName;
    private  Integer scheduleStatus;

    public ProjectVO(Project entity){
        BeanUtil.copyProperties(entity,this, CopyOptions.create().setIgnoreNullValue(true));
        this.statusName = ProjectEnums.name(getProjectStatus());
    }
}
