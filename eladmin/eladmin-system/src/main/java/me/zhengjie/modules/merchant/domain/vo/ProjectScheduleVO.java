package me.zhengjie.modules.merchant.domain.vo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;
import me.zhengjie.modules.merchant.domain.ProjectSchedule;
import me.zhengjie.modules.merchant.enums.ScheduleEnum;

@Data
public class ProjectScheduleVO extends ProjectSchedule {
    private String statusName;

    public ProjectScheduleVO(ProjectSchedule entity){
        BeanUtil.copyProperties(entity,this, CopyOptions.create().setIgnoreNullValue(true));
        this.statusName=ScheduleEnum.name(getScheduleStatus().getValue());
    }
}
