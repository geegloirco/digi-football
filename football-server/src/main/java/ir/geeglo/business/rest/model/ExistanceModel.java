package ir.geeglo.business.rest.model;

import ir.geeglo.business.data.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class ExistanceModel {
    private UserEntity userEntity;
    private UserModel userModel;
    private List<LocationModel> locationModels = new ArrayList<>();
    private String username = "مهمان";

    private boolean isAdmin = false;
    private boolean isGuest = true;

    public static final String GUEST_ROLE = "ROLE_GUEST";
    public static final String USER_ROLE = "ROLE_USER";
    public static final String ADMIN_ROLE = "ROLE_ADMIN";

    public ExistanceModel() {
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isAuthorized() {
        return userEntity != null;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isGuest() {
        return isGuest;
    }

    public void setGuest(boolean guest) {
        isGuest = guest;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public List<LocationModel> getLocationModels() {
        return locationModels;
    }

    public void setLocationModels(List<LocationModel> locationModels) {
        this.locationModels = locationModels;
    }

    public void addLocationModels(LocationModel locationModel) {
        if(this.locationModels == null)
            this.locationModels = new ArrayList<>();
        this.locationModels.add(locationModel);
    }
}
