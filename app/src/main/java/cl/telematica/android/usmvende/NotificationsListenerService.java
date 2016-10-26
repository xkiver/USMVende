package cl.telematica.android.usmvende;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

//import com.google.android.gms.games.Notifications;
import com.google.android.gms.gcm.GcmListenerService;
//import com.google.firebase.messaging.RemoteMessage;


public class NotificationsListenerService extends GcmListenerService {
    /*
    @Override
    public void onMessageReceived(String s, Bundle data) {
        String message = data.getString("message");
        sendNotification(message);
    }

    private void sendNotification(String message){
        Intent intent = new Intent(this, menu.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        int requestCode = 0;
        //SETUP NOTIFICATION
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_ONE_SHOT);
        //SOUND
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //BUILD NOTIFICATION
        NotificationCompat.Builder noBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("My GSM message :X :X")
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,noBuilder.build());

    }
        */

}
