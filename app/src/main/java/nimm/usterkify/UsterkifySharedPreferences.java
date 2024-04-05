package nimm.usterkify;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Optional;

public enum UsterkifySharedPreferences {
    INSTANCE();

    private final String appModeKey = "appMode";

    public Optional<AppMode> appMode(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(appModeKey, Context.MODE_PRIVATE);
        if (preferences.contains(appModeKey)) {
            return Optional.of(parseAppMode(preferences.getString(appModeKey, "LOCAL")));
        }

        return Optional.empty();
    }

    public void setAppMode(Context context, AppMode appMode) {
        SharedPreferences preferences = context.getSharedPreferences(appModeKey, Context.MODE_PRIVATE);
        preferences.edit().putString(appModeKey, appModeAsString(appMode)).apply();
    }

    public void removeAppMode(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(appModeKey, Context.MODE_PRIVATE);
        preferences.edit().remove(appModeKey).apply();
    }

    private AppMode parseAppMode(String appMode) {
        switch (appMode) {
            case "LOCAL" -> {
                return AppMode.LOCAL;
            }
            case "REMOTE" -> {
                return AppMode.REMOTE;
            }
        }
        return AppMode.LOCAL;
    }

    private String appModeAsString(AppMode appMode) {
        switch (appMode) {
            case LOCAL -> {
                return "LOCAL";
            }
            case REMOTE -> {
                return "REMOTE";
            }
        }
        return "LOCAL";
    }

    private UsterkifySharedPreferences() {
    }

    public static UsterkifySharedPreferences getInstance() {
        return INSTANCE;
    }
}
