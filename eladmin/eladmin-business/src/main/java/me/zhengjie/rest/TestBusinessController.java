package me.zhengjie.rest;

import me.zhengjie.annotation.AnonymousAccess;
import me.zhengjie.annotation.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 这是一个测试的业务模块controller
 * @author lychee
 * @version 1.0
 * @description
 * @date 2023/10/18 09:44
 * @since 2023/10/18
 */

@RestController
@RequestMapping("/api/business")
public class TestBusinessController {
    @AnonymousAccess
    @RequestMapping("/test")
    @Log(value = "这是一个测试的业务模块的接口")
    public ResponseEntity<String> test(){
        var s = "hello business module";
        return ResponseEntity.ok(s);
    }
}
