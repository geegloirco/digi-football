package ir.geeglo.business.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "player_info", schema = "digi_football")
public class PlayerInfoEntity {
    private int id;
    private int positionTwo;
    private int positionThree;
    private boolean goalkeeper;
    private boolean rightFoot;
    private boolean leftFoot;
    private int height;
    private int weight;
    private int birthYear;
    private PlayerPositionEntity playerPositionEntity;
    private UserEntity userEntity;

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
    @Column(name = "position_two", nullable = false)
    public int getPositionTwo() {
        return positionTwo;
    }

    public void setPositionTwo(int positionTwo) {
        this.positionTwo = positionTwo;
    }

    @Basic
    @Column(name = "position_three", nullable = false)
    public int getPositionThree() {
        return positionThree;
    }

    public void setPositionThree(int positionThree) {
        this.positionThree = positionThree;
    }

    @Basic
    @Column(name = "goalkeeper", nullable = false)
    public boolean isGoalkeeper() {
        return goalkeeper;
    }

    public void setGoalkeeper(boolean goalkeeper) {
        this.goalkeeper = goalkeeper;
    }

    @Basic
    @Column(name = "right_foot", nullable = false)
    public boolean isRightFoot() {
        return rightFoot;
    }

    public void setRightFoot(boolean rightFoot) {
        this.rightFoot = rightFoot;
    }

    @Basic
    @Column(name = "left_foot", nullable = false)
    public boolean isLeftFoot() {
        return leftFoot;
    }

    public void setLeftFoot(boolean leftFoot) {
        this.leftFoot = leftFoot;
    }

    @Basic
    @Column(name = "height", nullable = false)
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Basic
    @Column(name = "weight", nullable = false)
    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Basic
    @Column(name = "birth_year", nullable = false)
    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerInfoEntity that = (PlayerInfoEntity) o;

        if (id != that.id) return false;
        if (positionTwo != that.positionTwo) return false;
        if (positionThree != that.positionThree) return false;
        if (goalkeeper != that.goalkeeper) return false;
        if (rightFoot != that.rightFoot) return false;
        if (leftFoot != that.leftFoot) return false;
        if (height != that.height) return false;
        if (weight != that.weight) return false;
        if (birthYear != that.birthYear) return false;
        if (playerPositionEntity != null ? !playerPositionEntity.equals(that.playerPositionEntity) : that.playerPositionEntity != null)
            return false;
        return userEntity != null ? userEntity.equals(that.userEntity) : that.userEntity == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + positionTwo;
        result = 31 * result + positionThree;
        result = 31 * result + (goalkeeper ? 1 : 0);
        result = 31 * result + (rightFoot ? 1 : 0);
        result = 31 * result + (leftFoot ? 1 : 0);
        result = 31 * result + height;
        result = 31 * result + weight;
        result = 31 * result + birthYear;
        result = 31 * result + (playerPositionEntity != null ? playerPositionEntity.hashCode() : 0);
        result = 31 * result + (userEntity != null ? userEntity.hashCode() : 0);
        return result;
    }

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "position_one", referencedColumnName = "id",
            nullable = false, insertable = false, updatable = false)
    public PlayerPositionEntity getPlayerPositionEntity() {
        return playerPositionEntity;
    }

    public void setPlayerPositionEntity(PlayerPositionEntity playerPositionEntity) {
        this.playerPositionEntity = playerPositionEntity;
    }

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id",
            nullable = false, insertable = false, updatable = false)
    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
