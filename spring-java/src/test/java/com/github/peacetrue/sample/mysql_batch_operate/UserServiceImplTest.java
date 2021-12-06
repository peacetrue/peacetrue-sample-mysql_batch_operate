package com.github.peacetrue.sample.mysql_batch_operate;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {
        DataSourceAutoConfiguration.class,
        FlywayAutoConfiguration.class,
        UserServiceImpl.class,
})
@Slf4j
@ActiveProfiles({"log4jdbc", "h2_mem", "batch"})
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    void save() {
        userService.setBatch(true);
        userService.save(UserService.generate(i -> new UserImpl(i.longValue(), "1"), 2));
        userService.update(UserService.generate(i -> new UserImpl(i.longValue(), "2"), 2));
    }

}