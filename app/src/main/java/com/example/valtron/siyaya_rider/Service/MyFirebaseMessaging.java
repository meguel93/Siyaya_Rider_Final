package com.example.valtron.siyaya_rider.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;
//import com.example.valtron.siyaya_rider.CommuterCall;
import com.example.valtron.siyaya_rider.Common.Common;
import com.example.valtron.siyaya_rider.Helper.NotificationHelper;
import com.example.valtron.siyaya_rider.Model.Token;
import com.example.valtron.siyaya_rider.R;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import java.util.Map;

public class MyFirebaseMessaging extends FirebaseMessagingService {

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        updateTokenToServer(s);
    }

    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {
        if (remoteMessage.getData() != null) {
            Map<String, String> data = remoteMessage.getData();
            String title = data.get("title");
            final String message = data.get("message");
            if (title.equals("Cancelled")) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MyFirebaseMessaging.this, message, Toast.LENGTH_SHORT).show();
                    }
                });

                LocalBroadcastManager.getInstance(MyFirebaseMessaging.this)
                        .sendBroadcast(new Intent(Common.CANCEL_BROADCAST_STRING));
            } else if (title.equals("Arrived")) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    showArrivedNotificationAPI26(message);
                else
                    showArrivedNotification(message);
            }
        }
    }

    private void updateTokenToServer(final String refreshedToken) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference tokens = db.getReference(Common.token_tbl);

        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
            @Override
            public void onSuccess(Account account) {
                Token token = new Token(refreshedToken);
                tokens.child(account.getId())
                        .setValue(token);;
            }

            @Override
            public void onError(AccountKitError accountKitError) {

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showArrivedNotificationAPI26(String body) {
        PendingIntent contentIntent = PendingIntent.getActivity(getBaseContext(),
                0, new Intent(), PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationHelper notificationHelper = new NotificationHelper(getBaseContext());
        Notification.Builder builder = notificationHelper.getSiyayaNotification("Arrived", body, contentIntent, defaultSound);

        notificationHelper.getManager().notify(1, builder.build());

        LocalBroadcastManager.getInstance(MyFirebaseMessaging.this)
                .sendBroadcast(new Intent(Common.BROADCAST_ARRIVED));
    }

    private void showArrivedNotification(String body) {
        PendingIntent contentIntent = PendingIntent.getActivity(getBaseContext(),
                0, new Intent(), PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext());

        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_LIGHTS|Notification.DEFAULT_SOUND)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_car)
                .setContentTitle("Arrived")
                .setContentText(body)
                .setContentIntent(contentIntent);
        NotificationManager manager = (NotificationManager)getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, builder.build());

        LocalBroadcastManager.getInstance(MyFirebaseMessaging.this)
                .sendBroadcast(new Intent(Common.BROADCAST_ARRIVED));
    }
}
