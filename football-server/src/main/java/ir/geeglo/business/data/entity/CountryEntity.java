package ir.geeglo.business.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "country", schema = "digi_football")
public class CountryEntity {
    private int id;
    private String title;
    private String phoneCode;
    private List<ProvinceEntity> provinceEntities;

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

    @Basic
    @Column(name = "phoneCode", nullable = false, length = 10)
    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CountryEntity that = (CountryEntity) o;

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

    @JsonIgnore
    @OneToMany(mappedBy = "countryEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<ProvinceEntity> getProvinceEntities() {
        return provinceEntities;
    }

    public void setProvinceEntities(List<ProvinceEntity> provinceEntities) {
        this.provinceEntities = provinceEntities;
    }
}
