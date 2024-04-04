package nimm.usterkify;

public enum UsterkifyAppContext {

    INSTANCE();

    private UserSessionInfo userSessionInfo;

    UsterkifyAppContext() {
        this.userSessionInfo = new UserSessionInfo(false);
    }

    public UserSessionInfo getUserSessionInfo() {
        return userSessionInfo;
    }

    public void setUserSessionInfo(UserSessionInfo userSessionInfo) {
        this.userSessionInfo = userSessionInfo;
    }

    public static UsterkifyAppContext getInstance() {
        return INSTANCE;
    }

}
