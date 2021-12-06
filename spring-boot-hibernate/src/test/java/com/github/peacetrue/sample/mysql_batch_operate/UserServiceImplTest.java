package com.github.peacetrue.sample.mysql_batch_operate;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@SpringBootTest(classes = {
        DataSourceAutoConfiguration.class,
        FlywayAutoConfiguration.class,
        UserServiceImpl.class,
        HibernateJpaAutoConfiguration.class,
        TransactionAutoConfiguration.class,
})
@AutoConfigurationPackage(basePackageClasses = UserServiceImplTest.class)
@ActiveProfiles({"mysql", "batch", "log4jdbc"})
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    void save() {
        userService.setBatch(true);
        UserService.save(userService, i -> new UserImpl(i.longValue(), i.toString()), 2);
    }

}