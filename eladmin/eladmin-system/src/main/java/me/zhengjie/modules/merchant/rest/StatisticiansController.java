package me.zhengjie.modules.merchant.rest;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.AnonymousAccess;
import me.zhengjie.modules.merchant.domain.dto.StatisticiansDto;
import me.zhengjie.modules.merchant.service.StatisticiansService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * @author wangzy
 * @date 2023/12/22 18:49
 * @describe 统计
 * @since version 1.0
 */
@Api(value = "统计", tags = "统计")
@RestController
@RequestMapping("/api/merchant/statisticians")
@RequiredArgsConstructor
public class StatisticiansController {
    private final StatisticiansService statisticiansService;

    @ApiOperation("统计今日公司数、项目数、项目金额数")
    @AnonymousAccess
    @RequestMapping(value = "/countByDay", produces = "application/json;charset=UTF-8", method = { RequestMethod.POST})
    public ResponseEntity<JSONObject> countByDay(@RequestBody StatisticiansDto statisticiansDto) {
//        if (Objects.isNull(statisticiansDto.getDay())) {
//            statisticiansDto.setDay(LocalDateTime.now());
//        }
//        if (Strings.isBlank(statisticiansDto.getDateStr())) {
//            statisticiansDto.setDateStr(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(statisticiansDto.getDay()));
//        }
        return ResponseEntity.ok(statisticiansService.countByDay(statisticiansDto));
    }

    @ApiOperation("统计公司、项目、项目金额 总数")
    @AnonymousAccess
    @RequestMapping(value = "/countAll", produces = "application/json;charset=UTF-8", method = { RequestMethod.GET})
    public ResponseEntity<JSONObject> countAll() {
        return ResponseEntity.ok(statisticiansService.countAll());
    }

    @ApiOperation("统计当天的公司数、项目数、项目金额数")
    @AnonymousAccess
    @RequestMapping(value = "/countByCurrentDay", produces = "application/json;charset=UTF-8", method = { RequestMethod.GET})
    public ResponseEntity<JSONObject> countByCurrentDay() {
        return ResponseEntity.ok(statisticiansService.countByCurrentDay());
    }

    @AnonymousAccess
    @ApiOperation("统计按星期统计实际收入、预计收入")
    @RequestMapping(value = "/countByWeek", produces = "application/json;charset=UTF-8", method = { RequestMethod.GET})
    public ResponseEntity<JSONObject> countByWeek() {
        return ResponseEntity.ok(statisticiansService.countByWeek());
    }
}
