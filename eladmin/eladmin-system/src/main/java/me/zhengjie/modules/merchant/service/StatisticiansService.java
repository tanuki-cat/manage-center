package me.zhengjie.modules.merchant.service;

import com.alibaba.fastjson.JSONObject;
import me.zhengjie.modules.merchant.domain.dto.StatisticiansDto;

/**
 * @author wangzy
 * @date 2023/12/22 18:51
 * @describe 统计服务类
 * @since version 1.0
 */
public interface StatisticiansService {
    /**
     * 按日期统计公司数、项目数、项目金额数
     * @param statisticiansDto
     * @return
     */
    JSONObject countByDay(StatisticiansDto statisticiansDto);

    /**
     * 统计公司、项目、项目金额 总数
     * @return
     */
    JSONObject countAll();

    /**
     * 统计当天的公司数、项目数、项目金额数
     * @return
     */
    JSONObject countByCurrentDay();

    /**
     * 按星期统计实际收入、预计收入
     * @return
     */
    JSONObject countByWeek();

    /**
     * 按年份统计
     * @param year
     * @return
     */
    JSONObject countByYear(String year);
}
