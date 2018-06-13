package ir.geeglo.business.data.service;

import ir.geeglo.business.data.dao.GeegloBaseDao;
import ir.geeglo.business.data.entity.UserEntity;
import ir.geeglo.business.data.entity.UserInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserService {

    @Autowired
    @Qualifier("GeegloBaseDao")
    private GeegloBaseDao geegloBaseDao;

    public UserEntity selectByGmail(String gmail) {
        return (UserEntity) geegloBaseDao.findByCondition(
                UserEntity.class, "gmail", gmail);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(
            UserEntity userEntity,
            UserInfoEntity infoEntity) {

        geegloBaseDao.insert(userEntity);
        infoEntity.setUserEntity(userEntity);
        geegloBaseDao.insert(infoEntity);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void update(UserEntity userEntity) {
        geegloBaseDao.update(userEntity);
//        userRepo.save(userEntity);
    }
}
