package govirtual360.numberverify;

import android.app.Activity;
import android.widget.Toast;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;

import govirtual360.numberverify.service.model.Notification;
import govirtual360.numberverify.service.model.SharedPreference;

public class Farmer extends android.support.multidex.MultiDexApplication {
    private static Farmer mInstance;
    public static DatabaseReference DBREF;
    public static SimpleDateFormat formatterWithMonthNameAndTime = new SimpleDateFormat("dd-MMM-yyyy hh:mm aa");
    public static SimpleDateFormat simpleDateFormatWithMonthName = new SimpleDateFormat("dd-MMM-yyyy");
    public static String AppName = "Farmer";
    public static String userSession = "userSession";
    public static String PersonalDetails = "PersonalDetails";
    public static String FCMToken = "FCMToken";
    public static String name="name";
    public static String phone="phone";
    public static String state="state";
    public static String district="district";
    public static String online="online";
    public static String lastSeen="lastSeen";
    public static String users = "Users";
    public static String loggedIn = "loggedIn";
    public static String UID = "UID";
    public static String phoneVsId= "phoneVsId";
    public static String LandArea="landArea";
    public static String cropChosen ="cropChosen";
    public static String Punjab = "Punjab";
    public static String Rajasthan="Rajasthan";
    public static String notification = "Notification";
    public static String myPanicResponsibilityId = "myPanicResponsibilityId";
    public static String addedGuardian = "addedGuardian";
    public SharedPreference session ;
    public static String InviteSMSNumber = "InviteSMSNumber";
    public static String InviteSMSMessage = "InviteSMSMessage";
    public static String AppLink = "";
    public static String IPanicked = "IPanicked";
    public static String Safe = "Safe";
    public static String removeGuardian = "removeGuardian";
    public static String share="share";
    public static String soundOff = "soundOff";
    public static HashMap<String,String> stateVsDistrict = new HashMap<String,String>();
    public static HashMap<String,String> disease = new HashMap<>();

    public static String type = "type";
    /*public static String notifId="notifId";
    public static String senderId = "senderId";
    public static String receiverId = "receiverId";*/
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        //TODO add the districts
        stateVsDistrict.put(Punjab,"Bhatinda");
        stateVsDistrict.put(Rajasthan,"Jaipur");
        disease.put("0","Potato___Early_blight");
        disease.put("1","Corn_(maize)___healthy");
        disease.put("2","Apple___Cedar_apple_rust");
        disease.put("3","Corn_(maize)___Cercospora_leaf_spot Gray_leaf_spot");
        disease.put("4","Corn_(maize)___Common_rust_");
        disease.put("5","Peach___healthy");
        disease.put("6","Apple___Apple_scab");
        disease.put("7","Potato___Late_blight");
        disease.put("8","Corn_(maize)___Northern_Leaf_Blight");
        disease.put("9","Peach___Bacterial_spot");
        disease.put("10","Apple___Black_rot");
        disease.put("11","Potato___healthy");
        disease.put("12","Apple___healthy");

        if (!FirebaseApp.getApps(this).isEmpty()) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }
        DBREF = FirebaseDatabase.getInstance().getReference().child(AppName).getRef();
        session = new SharedPreference(getApplicationContext());
        String UID = "";
        if (session.getUID()!=null)
            UID = session.getUID();
        setOnlineStatus(UID);
    }

    public static synchronized Farmer getInstance()
    {
        return mInstance;
    }

    public void setOnlineStatus(String userkey) {
        if (!userkey.equals("")) {
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference myConnectionsRef = DBREF.child(userSession).child(userkey).child(online).getRef();

            // stores the timestamp of my last disconnect (the last time I was seen online)
            final DatabaseReference lastOnlineRef = database.getReference().child(userSession).child(userkey).child(lastSeen).getRef();

            final DatabaseReference connectedRef = database.getReference(".info/connected");
            connectedRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    boolean connected = snapshot.getValue(Boolean.class);
                    if (connected) {
                        myConnectionsRef.setValue(Boolean.TRUE);
                        myConnectionsRef.onDisconnect().setValue(Boolean.FALSE);
                        lastOnlineRef.onDisconnect().setValue(getTimeStamp());
                    }
                }
                @Override
                public void onCancelled(DatabaseError error) {
                    System.err.println("Listener was cancelled at .info/connected");
                }
            });
        }

    }
    public static String getTimeStamp()
    {
        String timestamp = formatterWithMonthNameAndTime.format(Calendar.getInstance().getTime());
        return timestamp;
    }
    public static String getTimeStampInMs()
    {
        String timestamp = String.valueOf(Calendar.getInstance().getTimeInMillis());
        return timestamp;
    }
    public static String getReverseTimeStampInMs()
    {
        String timestamp = String.valueOf(99999999999999L-Calendar.getInstance().getTimeInMillis());
        return timestamp;
    }
    public static void showLongToast(Activity activity,String text)
    {
        Toast.makeText(activity,text,Toast.LENGTH_LONG).show();
    }
    public static void showShortToast(Activity activity,String text)
    {
        Toast.makeText(activity,text,Toast.LENGTH_SHORT).show();
    }
//Send notification to an individual

    public static void sendNotif(final String senderId, final String receiverId, final String type, final String content) {
        final String timestamp = getReverseTimeStampInMs();
        DBREF.child(FCMToken).child(receiverId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String receiverFCMToken = dataSnapshot.getValue(String.class);
                if (receiverFCMToken != null && !receiverFCMToken.equals("")) {
                    Notification newNotif = new Notification(timestamp, getTimeStamp(), type, receiverId, receiverFCMToken, content);
                    DBREF.child(notification).child(receiverId).child(timestamp).setValue(newNotif);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

// Send Notification to everybody
/*
    public static void sendNotifToAllCoordinators(final String senderId, final String type, final String content, final String taskId) {
        long idLong = Calendar.getInstance().getTimeInMillis();
        idLong = 9999999999999L - idLong;
        final String id = String.valueOf(idLong);
        final String timestamp = formatter.format(Calendar.getInstance().getTime());
        final DatabaseReference dbCoordinator = DBREF.child("Coordinator").getRef();
        dbCoordinator.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    final String receiverId = ds.getKey();
                    DBREF.child("Fcmtokens").child(receiverId).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String receiverFCMToken = dataSnapshot.getValue(String.class);
                            if (receiverFCMToken != null&&!receiverFCMToken.equals("") ) {
                                Notif newNotif = new Notif(id, timestamp, type, senderId, receiverId, receiverFCMToken, content, taskId);
                                DBREF.child("Notification").child(receiverId).child(id).setValue(newNotif);

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
}*/
    }
