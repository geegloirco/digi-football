package ir.geeglo.business.rest.model;

import ir.geeglo.business.data.entity.UserEntity;
import ir.geeglo.business.data.entity.UserInfoEntity;

import java.util.ArrayList;
import java.util.List;

public class UserModel {
    private String gmail;
    private String mobile;
    private String firstName;
    private String lastName;
    private String userName;
    private String nationalCode;
    private String image;

    public UserModel() {
    }

    public UserModel(
            String gmail, String mobile,
            String firstName, String lastName, String userName,
            String nationalCode, String image) {
        this.gmail = gmail;
        this.mobile = mobile;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.nationalCode = nationalCode;
        this.image = image;
    }

    public static UserModel createUserModel(UserEntity userEntity) {
        UserModel userModel = new UserModel();
        List<UserInfoEntity> userInfoEntities = null;
        if (userEntity.getUserInfoEntities() instanceof List)
            userInfoEntities = (List) userEntity.getUserInfoEntities();
        else
            userInfoEntities = new ArrayList(userEntity.getUserInfoEntities());
        userModel.setGmail(userEntity.getGmail());
        userModel.setMobile(userEntity.getMobile());
        if(userInfoEntities != null && !userInfoEntities.isEmpty()) {
            UserInfoEntity userInfoEntity = userInfoEntities.get(0);
            userModel.setFirstName(userInfoEntity.getFirstName());
            userModel.setLastName(userInfoEntity.getLastName());
            userModel.setUserName(userInfoEntity.getFullName());
            userModel.setImage(userInfoEntity.getImage());
            userModel.setNationalCode(userInfoEntity.getNationalCode());
        }

        return userModel;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
