package me.zhengjie.modules.merchant.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.modules.merchant.domain.Company;
import me.zhengjie.modules.merchant.domain.Project;
import me.zhengjie.modules.merchant.domain.dto.StatisticiansDto;
import me.zhengjie.modules.merchant.service.CompanyService;
import me.zhengjie.modules.merchant.service.ProjectService;
import me.zhengjie.modules.merchant.service.StatisticiansService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

/**
 * @author wangzy
 * @date 2023/12/22 18:51
 * @describe 统计服务类
 * @since version 1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticiansServiceImpl implements StatisticiansService {
    private final ProjectService projectService;
    private final CompanyService companyService;


    @Override
    public JSONObject countByDay(StatisticiansDto statisticiansDto) {
        JSONObject result = new JSONObject();
        String day;
        String start;
        String end;
//        String day = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(statisticiansDto.getDay());
//        String start = day + " 00:00:00";
//        String end = day + " 23:59:59";
        if (Strings.isBlank(statisticiansDto.getDateStr())) {
            day = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
            start = day + " 00:00:00";
            end = day + " 23:59:59";
        } else {
            if (!statisticiansDto.getDateStr().contains("~")) {
                return result;
            }
            start = statisticiansDto.getDateStr().split("~")[0] + " 00:00:00";
            end = statisticiansDto.getDateStr().split("~")[1] + " 23:59:59";
        }
        //统计公司数
        long companyCount = this.companyService.count(Wrappers.<Company>lambdaQuery().between( Company::getCreateTime,start,end));
        //统计项目数
        List<Project> projects = this.projectService.list(Wrappers.<Project>lambdaQuery().between( Project::getCreateTime,start,end));
        long projectCount = projects.size();
        //统计项目金额
        BigDecimal projectAmount = projects.stream().reduce(BigDecimal.ZERO, (x,y) -> x.add(y.getProjectAmount()), BigDecimal::add);
        log.info("公司数:{},项目数:{},项目金额:{}",companyCount,projectCount,projectAmount);
        result.put("companyCount",companyCount);
        result.put("projectCount",projectCount);
        result.put("projectAmount",projectAmount);
        return result;
    }

    @Override
    public JSONObject countAll() {
        JSONObject result = new JSONObject();
        //统计公司数
        long companyCount = this.companyService.count();
        //统计项目数
        List<Project> projects = this.projectService.list();
        long projectCount = projects.size();
        //统计项目金额
        BigDecimal projectAmount = projects.stream().reduce(BigDecimal.ZERO, (x,y) -> x.add(y.getProjectAmount()), BigDecimal::add);
        result.put("companyCount",companyCount);
        result.put("projectCount",projectCount);
        result.put("projectAmount",projectAmount);
        return result;
    }
}
