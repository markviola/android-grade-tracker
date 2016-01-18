package managers;


import android.content.Context;

import java.util.ArrayList;

public class SettingsManager {

    private String TAG = "customFilter";

    private static SettingsManager instance;
    DBManager dbManager;

    private String _username;
    private boolean _showTitleScreen;


    public SettingsManager(Context context){
        dbManager = DBManager.getInstance(context);
        if(dbManager.settingsTableIsEmpty()){
            dbManager.addSettingsInfo("username", "User");
            dbManager.addSettingsInfo("showTitleScreen", "false");
        } else {
            setUsername(dbManager.getSettingState("username"));
            setShowTitleScreen(dbManager.getSettingState("showTitleScreen"));
        }
    }

    public static SettingsManager getInstance(Context context) {
        if (instance == null) {
            instance = new SettingsManager(context);
        }
        return instance;
    }

    public String getUsername(){
        return this._username;
    }

    public void setUsername(String newUsername){
        this._username = newUsername;
        dbManager.updateSettingInfo("username", newUsername);
    }

    public boolean getShowTitleScreen(){
        return this._showTitleScreen;
    }

    public void setShowTitleScreen(String showTitleScreen){
        if(showTitleScreen.equals("true")){
            this._showTitleScreen = true;
        } else {
            this._showTitleScreen = false;
        }

        dbManager.updateSettingInfo("showTitleScreen", String.format("%s", this._showTitleScreen));
    }
}
