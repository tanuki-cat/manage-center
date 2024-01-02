package me.zhengjie.modules.merchant.service;

import cn.hutool.core.collection.CollUtil;
import lombok.AllArgsConstructor;
import me.zhengjie.modules.merchant.domain.Patent;
import me.zhengjie.modules.merchant.domain.PatentSchedule;
import me.zhengjie.modules.merchant.domain.Project;
import me.zhengjie.modules.merchant.domain.ProjectSchedule;
import me.zhengjie.modules.merchant.domain.vo.PatentScheduleCommand;
import me.zhengjie.modules.merchant.enums.PatentEnums;
import me.zhengjie.modules.merchant.enums.PatentScheduleEnum;
import me.zhengjie.modules.merchant.enums.ProjectEnums;
import me.zhengjie.modules.merchant.enums.ScheduleEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class PatentUpdateSerivce {
    private final PatentService patentService;
    private final PatentScheduleService patentScheduleService;

    /**
     * 创建专利
     * @param resources
     */
    @Transactional(rollbackFor = Exception.class)
    public void create(Patent resources) {
        resources.setPatentStatus(PatentEnums.ESTABLISH.getValue());
        patentService.save(resources);
        PatentSchedule schedule = new PatentSchedule()
                .setPatentId(resources.getId())
                .setScheduleStatus(PatentScheduleEnum.BUSINESS)
                .setAssignStatus(1)
                .setNickName(resources.getNickName());
        patentScheduleService.save(schedule);
    }
    /**
     * 指派
     */
    @Transactional(rollbackFor = Exception.class)
    public void assign(PatentScheduleCommand resources) {
        PatentSchedule schedule = new PatentSchedule()
                .setPatentId(resources.getPatentId())
                .setAssignUser(resources.getAssignUser())
                .setAssignUserId(resources.getAssignUserId())
                .setScheduleStatus(PatentScheduleEnum.TEAMLEADER)
                .setNickName(resources.getNickName());
        //查询该专利是否有被指派过项目经理，如果有则修改原有记录的指派状态
        List<PatentSchedule> schedules = patentScheduleService.lambdaQuery().eq(PatentSchedule::getPatentId,resources.getPatentId())
                .eq(PatentSchedule::getScheduleStatus,PatentScheduleEnum.TEAMLEADER).list();
        if (CollUtil.isNotEmpty(schedules)){
            schedules.forEach(x ->{
                x.setAssignStatus(1);
            });
            patentScheduleService.updateBatchById(schedules);
        }
        patentScheduleService.save(schedule);
        Patent patent = patentService.getById(resources.getPatentId());
        patent.setPatentStatus(PatentEnums.INPROGRESS.getValue());
        patentService.updateById(patent);
    }
}
