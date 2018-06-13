package ir.geeglo.business.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "province", schema = "digi_football")
public class ProvinceEntity {
    private int id;
    private String title;
    private CountryEntity countryEntity;
    private List<CityEntity> cityEntities;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title", nullable = false, length = 40)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProvinceEntity that = (ProvinceEntity) o;

        if (id != that.id) return false;
        if (title != null ? !title.equals(that.title) : that.title != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }

//    @JsonIgnore
//    @OneToMany(mappedBy = "userByUserId")
//    public List<UserInfoEntity> getUserInfosById() {
//        return userInfosById;
//    }
//
//    public void setUserInfosById(List<UserInfoEntity> userInfosById) {
//        this.userInfosById = userInfosById;
//    }

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id",
            nullable = false, insertable = false, updatable = false)
    public CountryEntity getCountryEntity() {
        return countryEntity;
    }

    public void setCountryEntity(CountryEntity countryEntity) {
        this.countryEntity = countryEntity;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "provinceEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<CityEntity> getCityEntities() {
        return cityEntities;
    }

    public void setCityEntities(List<CityEntity> cityEntities) {
        this.cityEntities = cityEntities;
    }
}
