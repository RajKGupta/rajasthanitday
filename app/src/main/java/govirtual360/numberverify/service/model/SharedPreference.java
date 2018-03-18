package govirtual360.numberverify.service.model;
import android.content.Context;
import android.content.SharedPreferences;
import govirtual360.numberverify.Farmer;
public class SharedPreference {
    private Context context;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    int mode = 0;
    String prefname = "SESSION";

    public Boolean getLoggedIn() {
        return pref.getBoolean(Farmer.loggedIn, false);
    }
    public SharedPreference(Context context)
    {
        this.context = context;
        pref = context.getSharedPreferences(prefname, mode);
        editor = pref.edit();
    }

    public String getUID() {
        return pref.getString(Farmer.UID, "");
    }


    public String getName() {
        return pref.getString(Farmer.name, "");
    }


    public String getPhone() {
        return pref.getString(Farmer.phone, "");
    }


    public void setUID(String UID) {
        editor.putString(Farmer.UID,UID);
        editor.commit();
    }
    public String getDistrict() {
        return pref.getString(Farmer.district, "");
    }


    public void setDistrict(String UID) {
        editor.putString(Farmer.district,UID);
        editor.commit();
    }
    public String getState() {
        return pref.getString(Farmer.state, "");
    }


    public void setState(String UID) {
        editor.putString(Farmer.state,UID);
        editor.commit();
    }

    public void setName(String name) {
        editor.putString(Farmer.name,name);
        editor.commit();
    }
    public String getLandArea() {
        return pref.getString(Farmer.LandArea, "");
    }


    public void setLandArea(String UID) {
        editor.putString(Farmer.LandArea,UID);
        editor.commit();
    }
    public void setPhone(String UID) {
        editor.putString(Farmer.phone,UID);
        editor.commit();
    }

    public void setSharedPreference(String name, String phone,String UID,String landArea,String district,String state) {
        editor.putString(Farmer.name,name);
        editor.putString(Farmer.phone,phone);
        editor.putBoolean(Farmer.loggedIn,true);
        editor.putString(Farmer.state,state);
        editor.putString(Farmer.district,district);
        editor.putString(Farmer.UID,UID);
        editor.putString(Farmer.LandArea,landArea);
        editor.commit();
    }

    public String getFCMavail() {
        return pref.getString(Farmer.FCMToken,null);
    }

    public void setFCMavail(String panick) {
        editor.putString(Farmer.FCMToken,panick);
        editor.commit();
    }
}
