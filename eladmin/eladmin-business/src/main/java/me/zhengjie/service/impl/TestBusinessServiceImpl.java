package me.zhengjie.service.impl;

import me.zhengjie.service.ITestBusinessService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author lychee
 * @version 1.0
 * @description 测试用的service实现
 * @date 2023/10/19 15:35
 * @since 2023/10/19
 */
@Service
public class TestBusinessServiceImpl implements ITestBusinessService {


    /**
     * 测试缓存注解
     * @return
     */
    @Override
    @Cacheable(value = "testCaCheAble")
    public String testCaCheAble() {
        var s = "hello business module";
        return s;
    }
}
