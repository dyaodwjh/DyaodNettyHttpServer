package com.dyaod.app;

import com.dyaod.system.netty.NettyService;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:ApplicatonContext.xml")
public class SpringAppTests {
    @Autowired
    private NettyService nettyService;

    @Test
    public void testSayHello() {
        Assert.assertEquals("Hello I am netty", nettyService.sayHello());
    }
}
