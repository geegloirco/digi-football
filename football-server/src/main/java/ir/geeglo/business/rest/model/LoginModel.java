package ir.geeglo.business.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginModel {
    private String username;
    private boolean isAdmin;
    private boolean isGuest;

    public LoginModel() {
    }

    public LoginModel(
            String username, boolean isAdmin, boolean isGuest) {
        this.username = username;
        this.isAdmin = isAdmin;
        this.isGuest = isGuest;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonProperty("isAdmin")
    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @JsonProperty("isGuest")
    public boolean isGuest() {
        return isGuest;
    }

    public void setGuest(boolean guest) {
        isGuest = guest;
    }
}
