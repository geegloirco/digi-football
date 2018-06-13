package ir.geeglo.business.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user", schema = "digi_football")
public class UserEntity {
    private int id;
    private String gmail;
    private String mobile;
    private String password;
    private Timestamp enterDate;
    private List<UserInfoEntity> userInfoEntities;
    private List<PlayerInfoEntity> playerInfoEntities;
    private List<LocationEntity> locationEntities = new ArrayList<>();
    private List<TeamEntity> teamEntities = new ArrayList<>();

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
    @Column(name = "gmail", nullable = false, length = 40)
    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    @Basic
    @Column(name = "mobile", nullable = true, length = 11)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Basic
    @Column(name = "password", nullable = true, length = 128)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "enter_date", nullable = false)
    public Timestamp getEnterDate() {
        return enterDate;
    }

    public void setEnterDate(Timestamp enterDate) {
        this.enterDate = enterDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (id != that.id) return false;
        if (gmail != null ? !gmail.equals(that.gmail) : that.gmail != null)
            return false;
        if (mobile != null ? !mobile.equals(that.mobile) : that.mobile != null)
            return false;
        if (password != null ? !password.equals(that.password) : that.password != null)
            return false;
        if (enterDate != null ? !enterDate.equals(that.enterDate) : that.enterDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (gmail != null ? gmail.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (enterDate != null ? enterDate.hashCode() : 0);
        return result;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<UserInfoEntity> getUserInfoEntities() {
        return userInfoEntities;
    }

    public void setUserInfoEntities(List<UserInfoEntity> userInfosById) {
        this.userInfoEntities = userInfosById;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<PlayerInfoEntity> getPlayerInfoEntities() {
        return playerInfoEntities;
    }

    public void setPlayerInfoEntities(List<PlayerInfoEntity> playerInfoEntities) {
        this.playerInfoEntities = playerInfoEntities;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_location",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "location_id")
    )
    public List<LocationEntity> getLocationEntities() {
        return locationEntities;
    }

    public void setLocationEntities(List<LocationEntity> locationEntities) {
        this.locationEntities = locationEntities;
    }

    public void addLocation(LocationEntity locationEntity) {
        locationEntities.add(locationEntity);
        locationEntity.getUserEntities().add(this);
    }

    public void removeLocation(LocationEntity locationEntity) {
        locationEntities.remove(locationEntity);
        locationEntity.getUserEntities().remove(this);
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_team",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    public List<TeamEntity> getTeamEntities() {
        return teamEntities;
    }

    public void setTeamEntities(List<TeamEntity> teamEntities) {
        this.teamEntities = teamEntities;
    }

    public void addTeam(TeamEntity teamEntity) {
        teamEntities.add(teamEntity);
        teamEntity.getUserEntities().add(this);
    }

    public void removeTeam(TeamEntity teamEntity) {
        teamEntities.remove(teamEntity);
        teamEntity.getUserEntities().remove(this);
    }
}
