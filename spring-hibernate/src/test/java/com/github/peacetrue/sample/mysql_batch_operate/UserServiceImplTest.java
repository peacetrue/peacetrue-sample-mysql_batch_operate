package com.github.peacetrue.sample.mysql_batch_operate;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {
        HibernateConfiguration.class,
        UserServiceImpl.class
})
@Slf4j
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    void save() {
        userService.setBatch(true);
        UserService.save(userService, i -> new UserImpl(i.longValue(), "1"), 2);
        userService.update(UserService.generate(i -> new UserImpl(i.longValue(), "2"), 2));
    }

}