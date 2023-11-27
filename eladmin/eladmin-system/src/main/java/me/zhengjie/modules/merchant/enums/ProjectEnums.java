package me.zhengjie.modules.merchant.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 功能描述
 *
 * @author ChinaJoy
 * @date 2023/11/27 14:30
 */
@Schema(enumAsRef = true, description = """
        项目的枚举
        - ESTABLISH 创建
        - INPROGRESS 进行中
        - COMPLETION 完结
        """)
public enum ProjectEnums implements IEnum<Integer> {
    ESTABLISH(0),
    INPROGRESS(1),
    COMPLETION(2);
    private final int value;

    ProjectEnums(int value) {
        this.value = value;
    }

    public static ProjectEnums of(int value) {
        return switch (value) {
            case 0 -> ESTABLISH;
            case 1 -> INPROGRESS;
            case 2 -> COMPLETION;
            default -> throw new IllegalArgumentException();
        };
    }

    public static String name(int value) {
        return switch (value) {
            case 0 -> "创建";
            case 1 -> "进行中";
            case 2 -> "完结";
            default -> "";
        };
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
