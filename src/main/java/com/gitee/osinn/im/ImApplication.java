package com.gitee.osinn.im;

import com.gitee.osinn.im.sys.IImUserService;
import com.gitee.osinn.im.sys.ImUser;
import com.gitee.osinn.im.sys.ImUserMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * springboot 启动类别
 *
 * @author 乐天
 * @since 2018-10-01
 */
@SpringBootApplication
@MapperScan("com.gitee.osinn.im")
public class ImApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImApplication.class, args);
    }

    @Autowired
    @Qualifier(value = "imUserService")
    private IImUserService imUserService;

    @PostConstruct
    public void init() {
        ImUser imUser1 = imUserService.getById(1);
        ImUser imUser2 = imUserService.getById(2);
        if(imUser1 == null) {
            ImUser imUser = new ImUser();
            imUser.setId(1L);
            imUser.setPassword("123456");
            imUser.setLoginName("zhangsan");
            imUser.setName("zhangsan");
            imUser.setAvatar("/image/t1.jpg");
            imUserService.registerUser(imUser);
        }
        if(imUser2 == null) {
            ImUser imUser = new ImUser();
            imUser.setId(2L);
            imUser.setPassword("123456");
            imUser.setLoginName("lisi");
            imUser.setName("lisi");
            imUser.setAvatar("/image/t2.jpg");
            imUserService.registerUser(imUser);
        }
    }
}
