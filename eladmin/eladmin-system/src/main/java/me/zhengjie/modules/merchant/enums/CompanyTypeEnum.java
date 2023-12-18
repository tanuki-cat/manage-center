package me.zhengjie.modules.merchant.enums;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IEnum;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Map;

/**
 * @author wangzy
 * @date 2023/12/18 20:25
 * @describe 公司性质枚举
 * @since version 1.0
 */
@Schema(enumAsRef = true, description = """
        -NATIONALIZED 国企
        -FOREIGN 外资
        -JOINT 合资
        -PRIVATE 民营
        -OTHER 其他
        
        """)
public enum CompanyTypeEnum implements IEnum<Integer> {
    NATIONALIZED(1),
    FOREIGN(2),
    JOINT(3),
    PRIVATE(5),
    OTHER(6);
    private final int value;

    CompanyTypeEnum(int value) {
        this.value =value;
    }
    /**
     * 枚举数据库存储值
     */
    @Override
    public Integer getValue() {
        return value;
    }

    public static String name(int value) {
        return switch (value) {
            case 1 -> "国企";
            case 2 -> "外资";
            case 3 -> "合资";
            case 5 -> "民营";
            case 6 -> "其他";
            default -> "";
        };
    }
    public static CompanyTypeEnum of(int value) {
        return switch (value) {
            case 1 -> NATIONALIZED;
            case 2 -> FOREIGN;
            case 3 -> JOINT;
            case 5 -> PRIVATE;
            case 6 -> OTHER;
            default -> throw new IllegalArgumentException();
        };
    }

    public static List<Map<Integer,String>> list(){
        return List.of(
                Map.of(1,"国有企业"),
                Map.of(2,"外方独资"),
                Map.of(3,"合资企业"),
                Map.of(5,"民营企业"),
                Map.of(6,"其他")
        );
    }

    public static JSONArray listJson() {
        JSONArray jsonArray = new JSONArray();
        for (Map<Integer, String> map : list()) {
            JSONObject json = new JSONObject();
            for (Map.Entry entry : map.entrySet()) {
                json.put("key", entry.getKey());
                json.put("value", entry.getValue());
            }
            jsonArray.add(json);
        }
        return jsonArray;
    }
}
