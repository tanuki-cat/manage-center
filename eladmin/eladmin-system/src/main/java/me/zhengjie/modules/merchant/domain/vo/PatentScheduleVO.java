package me.zhengjie.modules.merchant.domain.vo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;
import me.zhengjie.modules.merchant.domain.PatentSchedule;
import me.zhengjie.modules.merchant.domain.ProjectSchedule;
import me.zhengjie.modules.merchant.enums.ScheduleEnum;

@Data
public class PatentScheduleVO extends PatentSchedule {
    private String statusName;

    public PatentScheduleVO(PatentSchedule entity){
        BeanUtil.copyProperties(entity,this, CopyOptions.create().setIgnoreNullValue(true));
        this.statusName=ScheduleEnum.name(getScheduleStatus().getValue());
    }
}
