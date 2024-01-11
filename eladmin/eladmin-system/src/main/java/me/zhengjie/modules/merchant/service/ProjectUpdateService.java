package me.zhengjie.modules.merchant.service;

import cn.hutool.core.collection.CollUtil;
import lombok.AllArgsConstructor;
import me.zhengjie.modules.merchant.domain.Project;
import me.zhengjie.modules.merchant.domain.ProjectSchedule;
import me.zhengjie.modules.merchant.domain.vo.ScheduleCommand;
import me.zhengjie.modules.merchant.enums.ProjectEnums;
import me.zhengjie.modules.merchant.enums.ScheduleEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ProjectUpdateService {

    private final ProjectService projectService;
    private final ProjectScheduleService projectScheduleService;
    @Transactional(rollbackFor = Exception.class)
    public void assign(ScheduleCommand command) {
        ProjectSchedule schedule = new ProjectSchedule()
                .setAssignUser(command.getAssignUser())
                .setAssignUserId(command.getAssignUserId())
                .setProjectId(command.getProjectId())
                .setScheduleStatus(ScheduleEnum.TEAMLEADER)
                .setAmountPercent(command.getAmountPercent())
                .setScheduleDesc("未开始")
                .setNickName(command.getNickName());
        //查询该项目是否有被指派过项目经理，如果有则修改原有记录的指派状态
        List<ProjectSchedule> schedules = projectScheduleService.lambdaQuery().eq(ProjectSchedule::getProjectId,command.getProjectId())
                .eq(ProjectSchedule::getScheduleStatus,ScheduleEnum.TEAMLEADER).list();
        if (CollUtil.isNotEmpty(schedules)){
            schedules.forEach(x ->{
                x.setAssignStatus(1);
            });
            projectScheduleService.updateBatchById(schedules);
        }
        projectScheduleService.save(schedule);
        Project project = projectService.getById(command.getProjectId());
        project.setProjectStatus(ProjectEnums.INPROGRESS.getValue());
        projectService.updateById(project);
    }
    @Transactional(rollbackFor = Exception.class)
    public void finance(ScheduleCommand resources) {
        ProjectSchedule schedule = new ProjectSchedule()
                .setProjectId(resources.getProjectId())
                .setScheduleStatus(ScheduleEnum.FINANCE)
                .setScheduleDesc("完成")
                .setAmountPercent(resources.getAmountPercent())
                .setRemittanceTime(resources.getRemittanceTime())
                .setNickName(resources.getNickName());
        projectScheduleService.save(schedule);
        Project project = projectService.getById(resources.getProjectId());
        project.setAmountPercent(resources.getAmountPercent());
        projectService.updateById(project);
    }

    @Transactional(rollbackFor = Exception.class)
    public void complete(ScheduleCommand resources) {
        Project project = projectService.getById(resources.getProjectId());
        project.setProjectStatus(ProjectEnums.COMPLETION.getValue());
        projectService.updateById(project);
        ProjectSchedule schedule = new ProjectSchedule()
                .setProjectId(resources.getProjectId())
                .setScheduleStatus(ScheduleEnum.COMPLETION)
                .setScheduleDesc("完成")
                .setAmountPercent(project.getAmountPercent())
                .setNickName(resources.getNickName());
        projectScheduleService.save(schedule);


    }
}
