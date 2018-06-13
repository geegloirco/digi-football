package ir.geeglo.business.data;

import ir.geeglo.business.data.business.GeegloBusinessSpringConfig;
import ir.geeglo.business.data.service.UserService;
import ir.geeglo.business.data.business.PictureBusiness;
import ir.geeglo.business.data.business.UserBusiness;
import ir.geeglo.business.data.service.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by SYSTEM on 8/13/2017.
 */

public class GeegloSpringServiceProvider {
    private static ApplicationContext applicationContext;

    static {
        try {
            applicationContext = new AnnotationConfigApplicationContext(
                    GeegloBusinessSpringConfig.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static UserService getUserService() {
        return applicationContext.getBean(UserService.class);
    }

    public static CountryService getCountryService() {
        return applicationContext.getBean(CountryService.class);
    }

    public static ProvinceService getProvinceService() {
        return applicationContext.getBean(ProvinceService.class);
    }

    public static CityService getCityService() {
        return applicationContext.getBean(CityService.class);
    }

    public static LocationService getLocationService() {
        return applicationContext.getBean(LocationService.class);
    }

    public static UserBusiness getUserBusiness() {
        try {
            return applicationContext.getBean(UserBusiness.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static PictureBusiness getPictureBusiness() {
        return applicationContext.getBean(PictureBusiness.class);
    }
}
