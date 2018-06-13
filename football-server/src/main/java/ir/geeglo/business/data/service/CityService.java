package ir.geeglo.business.data.service;

import ir.geeglo.business.data.dao.GeegloBaseDao;
import ir.geeglo.business.data.entity.CityEntity;
import ir.geeglo.business.data.entity.CountryEntity;
import ir.geeglo.business.data.entity.ProvinceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class CityService {
    @Autowired
    @Qualifier("GeegloBaseDao")
    GeegloBaseDao<CityEntity> bazarBaseDao;

    public CityEntity findByTitle(String title) {
        return bazarBaseDao.findByTitle(CityEntity.class, title);
    }

    public void save(CityEntity cityEntity) {
        bazarBaseDao.insert(cityEntity);
    }

    public CityEntity save(
            String title,
            CountryEntity countryEntity,
            ProvinceEntity provinceEntity) {
        CityEntity cityEntity = new CityEntity();
        cityEntity.setTitle(title);
        cityEntity.setCountryEntity(countryEntity);
        cityEntity.setProvinceEntity(provinceEntity);
        bazarBaseDao.insert(cityEntity);
        return cityEntity;
    }
}
