package me.zhengjie.modules.merchant.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * @author wangzy
 * @date 2023/12/22 19:01
 * @describe 统计dto对象
 * @since version 1.0
 */
@Data
public class StatisticiansDto implements java.io.Serializable {
    @Serial
    private final static long serialVersionUID = 1L;

    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDateTime day;
    private String dateStr;
}
