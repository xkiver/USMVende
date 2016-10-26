package cl.telematica.android.usmvende;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

public class RegistrationService extends IntentService {
    public RegistrationService() {
        super("RegistrationService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        InstanceID myID = null;
        String registrationToken="";
        try {
            myID = InstanceID.getInstance(this);
            registrationToken= myID.getToken(getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            Log.d("Registration Token", registrationToken);
            GcmPubSub subscription = GcmPubSub.getInstance(this);
            subscription.subscribe(registrationToken, "/topics/my_little_topic", null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        InstanceID myID = InstanceID.getInstance(getApplicationContext());
        String registrationToken = null;
        Intent registrationComplete = null;
        try {
            registrationToken = myID.getToken(getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            Log.v("Registration Token", "token:" +registrationToken);
            registrationComplete = new Intent("REGISTRATION_SUCCESS");
            registrationComplete.putExtra("token", registrationToken);
        } catch (IOException e) {
            e.printStackTrace();
            registrationComplete = new Intent("REGISTRATION_ERROR");
        }
        Toast.makeText(this,registrationToken, Toast.LENGTH_SHORT).show();//("Registration Token", registrationToken);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);

        */
    }
}
