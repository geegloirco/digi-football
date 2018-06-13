package ir.geeglo.business.data.service;

import ir.geeglo.business.data.dao.GeegloBaseDao;
import ir.geeglo.business.data.entity.CountryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class CountryService {
    @Autowired
    @Qualifier("GeegloBaseDao")
    GeegloBaseDao<CountryEntity> bazarBaseDao;

    public CountryEntity findByTitle(String title) {
        return bazarBaseDao.findByTitle(CountryEntity.class, title);
    }

    public void save(CountryEntity countryEntity) {
        bazarBaseDao.insert(countryEntity);
    }

    public CountryEntity save(String title) {
        CountryEntity countryEntity = new CountryEntity();
        countryEntity.setTitle(title);
        bazarBaseDao.insert(countryEntity);
        return countryEntity;
    }
}
