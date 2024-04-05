package nimm.usterkify;

import nimm.usterkify.data.User;

public class UserSessionInfo {
    private boolean userLoggedIn;
    private User userInfo;

    public UserSessionInfo(boolean userLoggedIn, User userInfo) {
        this.userLoggedIn = userLoggedIn;
        this.userInfo = userInfo;
    }

    public UserSessionInfo(boolean userLoggedIn) {
        this.userLoggedIn = false;
    }

    public boolean isUserLoggedIn() {
        return userLoggedIn;
    }

    public void setUserLoggedIn(boolean userLoggedIn) {
        this.userLoggedIn = userLoggedIn;
    }

    public User getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(User userInfo) {
        this.userInfo = userInfo;
    }
}