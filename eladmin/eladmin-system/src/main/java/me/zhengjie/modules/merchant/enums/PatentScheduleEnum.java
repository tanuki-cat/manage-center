package me.zhengjie.modules.merchant.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true, description = """
        流程当前进度的枚举
        - BUSINESS 业务
        - TEAMLEADER 专利组长
        - MANAGER 专利经理
        - FINANCE 财务
        - COMPLETION 完结
        """)
public enum PatentScheduleEnum implements IEnum<Integer> {
    BUSINESS(0),
    TEAMLEADER(1),
    MANAGER(2),
    FINANCE(3),
    COMPLETION(4);
    private final int value;

    PatentScheduleEnum(int value) {
        this.value = value;
    }

    public static PatentScheduleEnum of(int value) {
        return switch (value) {
            case 0 -> BUSINESS;
            case 1 -> TEAMLEADER;
            case 2 -> MANAGER;
            case 3 -> FINANCE;
            case 4 -> COMPLETION;
            default -> throw new IllegalArgumentException();
        };
    }

    public static String name(int value) {
        return switch (value) {
            case 0 -> "项目创建";
            case 1 -> "项目组长分配";
            case 2 -> "项目经理填写";
            case 3 -> "财务";
            case 4 -> "完结";
            default -> "";
        };
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
