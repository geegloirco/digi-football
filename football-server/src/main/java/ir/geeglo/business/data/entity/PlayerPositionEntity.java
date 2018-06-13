package ir.geeglo.business.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "player_position", schema = "digi_football")
public class PlayerPositionEntity {
    private int id;
    private String title;
    private List<PlayerInfoEntity> playerInfoEntities;

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

        PlayerPositionEntity that = (PlayerPositionEntity) o;

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
    @OneToMany(mappedBy = "playerPositionEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<PlayerInfoEntity> getPlayerInfoEntities() {
        return playerInfoEntities;
    }

    public void setPlayerInfoEntities(List<PlayerInfoEntity> playerInfoEntities) {
        this.playerInfoEntities = playerInfoEntities;
    }
}
