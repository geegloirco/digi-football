package ir.geeglo.business.rest.model;

import ir.geeglo.business.data.entity.LocationEntity;

public class LocationModel {
    private String country;
    private String province;
    private String city;
    private String title;
    private double latitude;
    private double longitude;
    private String address;
    private String phoneNumber;
    private String postCode;

    public LocationModel() {
    }

    public LocationModel(
            String country, String province, String city,
            String title, double latitude, double longitude,
            String address, String phoneNumber, String postCode) {
        this.country = country;
        this.province = province;
        this.city = city;
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.postCode = postCode;
    }

    public static LocationModel createLocationModel(LocationEntity locationEntity) {
        LocationModel locationModel = new LocationModel();
        locationModel.setTitle(locationEntity.getTitle());
        locationModel.setAddress(locationEntity.getAddress());
        locationModel.setCountry(locationEntity.getCountryEntity().getTitle());
        locationModel.setProvince(locationEntity.getProvinceEntity().getTitle());
        locationModel.setCity(locationEntity.getCityEntity().getTitle());
        locationModel.setLatitude(locationEntity.getLatitude());
        locationModel.setLongitude(locationEntity.getLongitude());
        locationModel.setPhoneNumber(locationEntity.getPhoneNumber());
        locationModel.setPostCode(locationEntity.getPostCode());
        return locationModel;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
}
