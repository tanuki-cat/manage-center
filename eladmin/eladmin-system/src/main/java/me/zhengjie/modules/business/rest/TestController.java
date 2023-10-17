package me.zhengjie.modules.business.rest;

import me.zhengjie.annotation.AnonymousAccess;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 这是一个业务测试类
 * @author lychee
 * @version 1.0
 * @description
 * @date 2023/10/17 20:45
 * @since 2023/10/17
 */
@RestController
@RequestMapping("/api/test")
public class TestController {

    @AnonymousAccess
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        var str = "这是一个关于jdk17特性的测试";
        return new ResponseEntity<>(str,HttpStatus.OK);
    }
}
