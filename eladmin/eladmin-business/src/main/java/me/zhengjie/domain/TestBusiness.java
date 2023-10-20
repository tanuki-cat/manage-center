package me.zhengjie.domain;

import io.swagger.annotations.ApiModel;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author lychee
 * @version 1.0
 * @description 业务模块测试实体类
 * @date 2023/10/18 11:39
 * @since 2023/10/18
 */
@Getter
@Setter
@ApiModel(value = "业务模块测试实体类")
@AllArgsConstructor
@NoArgsConstructor
public class TestBusiness implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String name;
}
