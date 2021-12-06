package com.github.peacetrue.sample.mysql_batch_operate;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private SessionFactory sessionFactory;
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
    @Transactional
    public int save(List<? extends User> entities) {
        log.info("batch insert user {} records(batch 100)", entities.size());
        Session session = sessionFactory.getCurrentSession();
        for (int i = 0; i < entities.size(); i++) {
            session.save(entities.get(i));
            if (Boolean.TRUE.equals(batch) && (i + 1) % batchSize == 0) {
                session.flush();
                session.clear();
            }
        }
        return entities.size();
    }

    @Override
    @Transactional
    public int update(List<? extends User> entities) {
        log.info("batch update user {} records(batch 100)", entities.size());
        Session session = sessionFactory.getCurrentSession();
        for (int i = 0; i < entities.size(); i++) {
//            session.update(entities.get(i));
//            session.createQuery("update UserImpl set name=?1 where id=?2")
//                    .setParameter(1, entities.get(i).getName())
//                    .setParameter(2, entities.get(i).getId())
//                    .executeUpdate();
            if (Boolean.TRUE.equals(batch) && (i + 1) % batchSize == 0) {
                session.flush();
                session.clear();
            }
        }
        return entities.size();
    }

}
