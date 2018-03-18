package govirtual360.numberverify.service;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import govirtual360.numberverify.service.model.SharedPreference;
import static govirtual360.numberverify.Farmer.DBREF;
import static govirtual360.numberverify.Farmer.FCMToken;
import static govirtual360.numberverify.Farmer.users;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";
    SharedPreference session;
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        session = new SharedPreference(getApplicationContext());
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }
    // [END refresh_token]

    private void sendRegistrationToServer(String token) {
        String usrkey = session.getUID();
        if (!usrkey.matches("")) {
            DBREF.child(users).child(usrkey).child(FCMToken).setValue(token);

        }
        session.setFCMavail(token);
    }
}
