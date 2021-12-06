package com.github.peacetrue.sample.mysql_batch_operate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private DataSource dataSource;
    private Boolean batch;
    @Value("${batch-size:100}")
    private Integer batchSize;

    public UserServiceImpl() {
    }

    public UserServiceImpl(boolean batch) {
        this.batch = batch;
    }

    @Override
    public void setBatch(boolean batch) {
        this.batch = batch;
    }

    @Override
    public int save(List<? extends User> entities) {
        log.info("batch insert user {} records(batch 100)", entities.size());
        try {
            Connection connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            String sql = "insert into user(name) values(?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            for (int i = 0; i < entities.size(); i++) {
                User entity = entities.get(i);
                statement.setString(1, entity.getName());
                if (Boolean.TRUE.equals(batch)) {
                    statement.addBatch();
                    if ((i + 1) % batchSize == 0 || i == entities.size() - 1) {
                        statement.executeBatch();
                    }
                } else {
                    statement.execute();
                }
            }
            connection.commit();
        } catch (SQLException e) {
            log.error("save user error", e);
            return 0;
        }
        return entities.size();
    }

    @Override
    public int update(List<? extends User> entities) {
        log.info("batch insert user {} records(batch 100)", entities.size());
        try {
            Connection connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            String sql = "update user set name=?1 where id=?2";
            PreparedStatement statement = connection.prepareStatement(sql);
            for (int i = 0; i < entities.size(); i++) {
                User entity = entities.get(i);
                statement.setString(1, entity.getName());
                statement.setLong(2, entity.getId());
                if (Boolean.TRUE.equals(batch)) {
                    statement.addBatch();
                    if ((i + 1) % batchSize == 0 || i == entities.size() - 1) {
                        statement.executeBatch();
                    }
                } else {
                    statement.execute();
                }
            }
            connection.commit();
        } catch (SQLException e) {
            log.error("save user error", e);
            return 0;
        }
        return entities.size();
    }
}
