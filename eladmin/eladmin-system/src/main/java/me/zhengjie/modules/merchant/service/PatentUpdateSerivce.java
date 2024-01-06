package me.zhengjie.modules.merchant.service;

import cn.hutool.core.collection.CollUtil;
import lombok.AllArgsConstructor;
import me.zhengjie.modules.merchant.domain.Patent;
import me.zhengjie.modules.merchant.domain.PatentSchedule;
import me.zhengjie.modules.merchant.domain.Project;
import me.zhengjie.modules.merchant.domain.ProjectSchedule;
import me.zhengjie.modules.merchant.domain.vo.PatentScheduleCommand;
import me.zhengjie.modules.merchant.domain.vo.ScheduleCommand;
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

    @Transactional(rollbackFor = Exception.class)
    public void setManager(PatentScheduleCommand command) {
        PatentSchedule schedule = new PatentSchedule()
                .setPatentId(command.getPatentId())
                .setScheduleStatus(PatentScheduleEnum.MANAGER)
                .setFilingTime(command.getFilingTime())
                .setProgress(command.getProgress())
                .setWriteTime(command.getWriteTime())
                .setAuthorizationTime(command.getAuthorizationTime())
                .setPatentNum(command.getPatentNum())
                .setNickName(command.getNickName())
                .setForewarnTime(command.getForewarnTime());
        patentScheduleService.save(schedule);
        Patent patent = patentService.getById(command.getPatentId());
        patent.setFilingTime(command.getFilingTime());
        patent.setProgress(command.getProgress());
        patent.setWriteTime(command.getWriteTime());
        patent.setAuthorizationTime(command.getAuthorizationTime());
        patent.setPatentNum(command.getPatentNum());
        patent.setForewarnTime(command.getForewarnTime());

        patentService.updateById(patent);
    }
    @Transactional(rollbackFor = Exception.class)
    public void transfer(PatentScheduleCommand command) {
        Patent patent = patentService.getById(command.getPatentId());
        PatentSchedule schedule = new PatentSchedule()
                .setPatentId(command.getPatentId())
                .setScheduleStatus(PatentScheduleEnum.FINANCE)
                .setFilingTime(patent.getFilingTime())
                .setProgress(patent.getProgress())
                .setWriteTime(patent.getWriteTime())
                .setAuthorizationTime(patent.getAuthorizationTime())
                .setPatentNum(patent.getPatentNum())
                .setNickName(command.getNickName())
                .setForewarnTime(patent.getForewarnTime());
        patentScheduleService.save(schedule);
    }
    @Transactional(rollbackFor = Exception.class)
    public void complete(PatentScheduleCommand command) {
        Patent patent = patentService.getById(command.getPatentId());
        patent.setPatentStatus(PatentEnums.COMPLETION.getValue());
        patentService.updateById(patent);
        PatentSchedule schedule = new PatentSchedule()
                .setPatentId(command.getPatentId())
                .setScheduleStatus(PatentScheduleEnum.COMPLETION)
                .setFilingTime(patent.getFilingTime())
                .setProgress(patent.getProgress())
                .setWriteTime(patent.getWriteTime())
                .setAuthorizationTime(patent.getAuthorizationTime())
                .setPatentNum(patent.getPatentNum())
                .setNickName(command.getNickName())
                .setForewarnTime(patent.getForewarnTime());
        patentScheduleService.save(schedule);
    }
}
