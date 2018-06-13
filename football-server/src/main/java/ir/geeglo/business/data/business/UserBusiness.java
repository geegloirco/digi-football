package ir.geeglo.business.data.business;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import ir.geeglo.business.data.entity.*;
import ir.geeglo.business.data.service.*;
import ir.geeglo.business.rest.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class UserBusiness {
    @Autowired
    private UserService userService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private CityService cityService;

    @Autowired
    private GoogleBusiness googleBusinessService;

    @Autowired
    private PictureBusiness pictureBusiness;

    @PostConstruct
    public void postConstruct() {

    }

    public boolean loginGoogle(ExistanceModel existanceModel, String code) {
        GoogleIdToken idToken = googleBusinessService.verify(code);
        if (idToken != null) {
            login(existanceModel, idToken.getPayload());
            return true;
        }
        return false;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void login(
            ExistanceModel existanceModel, GoogleIdToken.Payload payload) {
//                String userId = payload.getSubject();
//                boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
//                String pictureUrl = (String) payload.get("picture");
//                String locale = (String) payload.get("locale");
        String email = payload.getEmail();
        String fullName = (String) payload.get("name");

//        List<GrantedAuthority> authorities = new ArrayList<>();
        UserEntity userEntity = userService.selectByGmail(email);
        if (userEntity == null) {
            String firstName = (String) payload.get("given_name");
            String lastName = (String) payload.get("family_name");
            userEntity = new UserEntity();
            userEntity.setGmail(email);
            userEntity.setEnterDate(new Timestamp(System.currentTimeMillis()));

            UserInfoEntity infoEntity = new UserInfoEntity();
            infoEntity.setFirstName(firstName);
            infoEntity.setLastName(lastName);
            infoEntity.setFullName(fullName);

            infoEntity.setUserEntity(userEntity);
            userEntity.setUserInfoEntities(Arrays.asList(infoEntity));
//            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            try {
                userService.save(userEntity, infoEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
//            if(userEntity.getUserRoleEntities() != null
//                    && !userEntity.getUserRoleEntities().isEmpty())
//                authorities = userEntity.getUserRoleEntities().stream()
//                        .map(userRoleEntity -> {
//                            if(userRoleEntity.getRoleName().equalsIgnoreCase("admin"))
//                                existanceModel.setAdmin(true);
//                            return new SimpleGrantedAuthority(
//                                    userRoleEntity.getRoleName());
//                        }).collect(Collectors.toList());
//            else
//                authorities.add(new SimpleGrantedAuthority(ExistenceModel.GUEST_ROLE));
        }
        existanceModel.setGuest(false);
//        existanceModel.setRoles(authorities);
        existanceModel.setUserEntity(userEntity);
        existanceModel.setUserModel(UserModel.createUserModel(userEntity));
        if(userEntity.getLocationEntities() != null &&
                !userEntity.getLocationEntities().isEmpty()) {
            List<LocationModel> locationModels = new ArrayList<>();
            for(LocationEntity locationEntity
                    : userEntity.getLocationEntities())
                locationModels.add(LocationModel.createLocationModel(
                        locationEntity));
            existanceModel.setLocationModels(locationModels);
        }
        String username = userEntity.getUserInfoEntities().get(0).getFullName();
        existanceModel.setUsername(username != null ? username : email);
    }

    public void logout(ExistanceModel existenceModel) {
        existenceModel.setGuest(true);
        existenceModel.setAdmin(false);
        existenceModel.setUserEntity(null);
        existenceModel.setUserModel(null);
        existenceModel.setLocationModels(null);
//        existenceModel.setRoles(Arrays.asList(
//                new SimpleGrantedAuthority(ExistenceModel.GUEST_ROLE)));
        existenceModel.setUsername("مهمان");
    }

    public UserModel updateUserInfo(
            ExistanceModel existanceModel, UserModel userModel) {
        UserInfoEntity userInfoEntity = existanceModel.getUserEntity()
                .getUserInfoEntities().get(0);
        userInfoEntity.setFirstName(userModel.getFirstName());
        userInfoEntity.setLastName(userModel.getLastName());
        userInfoEntity.setFullName(userModel.getUserName());
        userInfoEntity.setNationalCode(userModel.getNationalCode());

        if(userModel.getImage() != null) {
            if(pictureBusiness.exist(userModel.getImage())) {
                String imagePath = pictureBusiness.saveImage(userModel.getImage());
                userInfoEntity.setImage(imagePath);
            } else {
                userInfoEntity.setImage(userModel.getImage());
            }
        }

        userInfoService.update(userInfoEntity);
        existanceModel.setUserEntity(userService.selectByGmail(
                existanceModel.getUserEntity().getGmail()));
        existanceModel.setUserModel(UserModel
                .createUserModel(existanceModel.getUserEntity()));
        return existanceModel.getUserModel();
    }

    public List<LocationModel> addLocation(
            ExistanceModel existenceModel, LocationModel locationModel) {
        LocationEntity locationEntity = null;
        if(existenceModel.getUserEntity() == null)
            return null;
        UserEntity userEntity = existenceModel.getUserEntity();
        for(LocationEntity locationIterator
                : userEntity.getLocationEntities()) {
            if (locationIterator.getTitle()
                    .equalsIgnoreCase(locationModel.getTitle())) {
                locationEntity = locationIterator;
                break;
            }
        }

        if(locationEntity != null)
            return null;
        locationEntity = new LocationEntity();
        locationEntity.setTitle(locationModel.getTitle());
        locationEntity.setAddress(locationModel.getAddress());
        locationEntity.setLatitude(locationModel.getLatitude());
        locationEntity.setLongitude(locationModel.getLongitude());
        locationEntity.setPhoneNumber(locationModel.getPhoneNumber());
        locationEntity.setPostCode(locationModel.getPostCode());
        CountryEntity countryEntity = countryService.findByTitle(
                locationModel.getCountry());
        if(countryEntity == null) {
            countryEntity = countryService.save(locationModel.getCountry());
            locationEntity.setCountryEntity(countryEntity);
        } else
            locationEntity.setCountryEntity(countryEntity);

        ProvinceEntity provinceEntity = provinceService.findByTitle(
                locationModel.getProvince());
        if(provinceEntity == null) {
            provinceEntity = provinceService
                    .save(locationModel.getProvince(), countryEntity);
            locationEntity.setProvinceEntity(provinceEntity);

        } else
            locationEntity.setProvinceEntity(provinceEntity);

        CityEntity cityEntity = cityService.findByTitle(locationModel.getCity());
        if(cityEntity == null) {
            cityEntity = cityService.save(
                    locationModel.getCity(), countryEntity, provinceEntity);
            locationEntity.setCityEntity(cityEntity);
        } else
            locationEntity.setCityEntity(cityEntity);

        userEntity.addLocation(locationEntity);

        try {
            userService.update(userEntity);
        } catch (Exception e) {
            e.printStackTrace();
            userEntity.removeLocation(locationEntity);
        }

        //locationService.save(locationEntity);

//        existenceModel.getUserEntity().addLocation(locationEntity);

//        List<LocationEntity> locationEntities = existenceModel.getUserEntity().getLocationEntities();
//        locationEntities.add(locationEntity);

//        existenceModel.getUserEntity().addLocation(
//                locationEntity, locationModel.getTitle());
//        try {
//            userService.update(existenceModel.getUserEntity());
//        } catch (Exception e) {
//            existenceModel.getUserEntity().removeLocation(locationEntity);
//        }


        existenceModel.addLocationModels(locationModel);
        return existenceModel.getLocationModels();
    }

//    public List<LocationModel> removeLocation(
//            ExistenceModel existenceModel, LocationModel locationModel) {
//        LocationEntity locationEntity = locationService.findByTitle(
//                locationModel.getTitle());
//        if(locationEntity == null)
//            return null;
//
//        existenceModel.getUserEntity().removeLocation(locationEntity);
//        userService.update(existenceModel.getUserEntity());
//        locationService.delete(locationEntity);
//
//        List<LocationModel> locationModels = existenceModel.getLocationModels();
//        if(locationModels != null) {
//            for(LocationModel loc : locationModels)
//                if(loc.getTitle().equalsIgnoreCase(locationModel.getTitle()))
//                    locationModels.remove(loc);
//        }
//        return locationModels;
//    }

    public List<LocationModel> removeLocation(
            ExistanceModel existenceModel, String locationTitle) {
        LocationEntity locationEntity = locationService.findByTitle(
                locationTitle);
        if(locationEntity == null)
            return null;

//        existenceModel.getUserEntity().removeLocation(locationEntity);
//        userService.update(existenceModel.getUserEntity());
//        locationService.delete(locationEntity);

        List<LocationModel> locationModels = existenceModel.getLocationModels();
        LocationModel forRemove = null;
        if(locationModels != null) {
            for(LocationModel loc : locationModels) {
                if (loc.getTitle().equalsIgnoreCase(locationTitle)) {
                    forRemove = loc;
                    break;
                }
            }
            if(forRemove != null)
                locationModels.remove(forRemove);
        }
        return locationModels;
    }

    static class GeoLocation {
        CountryEntity countryEntity;
        ProvinceEntity provinceEntity;
        CityEntity cityEntity;

        public GeoLocation(
                CountryEntity countryEntity,
                ProvinceEntity provinceEntity,
                CityEntity cityEntity) {
            this.countryEntity = countryEntity;
            this.provinceEntity = provinceEntity;
            this.cityEntity = cityEntity;
        }
    }

    GeoLocation getGeoLocation(String country, String province, String city) {
        CountryEntity countryEntity = countryService.findByTitle(
                country);
        if(countryEntity == null)
            countryEntity = countryService.save(country);

        ProvinceEntity provinceEntity = provinceService.findByTitle(
                province);
        if(provinceEntity == null)
            provinceEntity = provinceService
                    .save(province, countryEntity);

        CityEntity cityEntity = cityService.findByTitle(city);
        if(cityEntity == null)
            cityEntity = cityService.save(
                    city, countryEntity, provinceEntity);

        return new GeoLocation(countryEntity, provinceEntity, cityEntity);
    }
}
