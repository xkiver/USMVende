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
        InstanceID myID = InstanceID.getInstance(this);
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
        //Toast.makeText(this,registrationToken, Toast.LENGTH_SHORT).show();//("Registration Token", registrationToken);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
      /*  GcmPubSub subscription = GcmPubSub.getInstance(this);
        try {
            //subscription.subscribe(registrationToken, "/topics/my_little_topic", null);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }
}
