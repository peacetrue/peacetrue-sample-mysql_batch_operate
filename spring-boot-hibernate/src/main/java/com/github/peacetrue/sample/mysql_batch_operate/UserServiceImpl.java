package com.github.peacetrue.sample.mysql_batch_operate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @PersistenceContext
    private EntityManager entityManager;
    private Boolean batch;
    @Value("${batch-size:100}")
    private Integer batchSize;

    @Override
    public void setBatch(boolean batch) {
        this.batch = batch;
    }

    @Override
    @Transactional
    public int save(List<? extends User> entities) {
        log.info("batch insert user {} records(batch 100)", entities.size());
        for (int i = 0; i < entities.size(); i++) {
            User entity = entities.get(i);
            entityManager.persist(entity);
            if (batch && (i + 1) % batchSize == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
        return entities.size();
    }

}
