package cordova.plugin.APPForeground;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import android.support.v4.app.NotificationCompat;


import cordova.plugin.APPForeground.foregroundReceiver;

public class serviceForeground extends Service {

    private Context context;
    private NotificationManager mNM;
    private int NOTIFICATION = 9527;
    private String actionType = "actionType";
    private final IBinder mBinder = new LocalBinder();

    private String channel_ID = "123456";
    private String channel_NAME = "plugin_dwj";

    public class LocalBinder extends Binder {
        serviceForeground getService() {
            return serviceForeground.this;
        }
    }

    @Override
    public void onCreate() {
        Log.i("serviceForeground", "onCreate");
        mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        context = getApplicationContext();
//        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("serviceForeground", "Received start id " + startId + ": " + intent);
        String action = intent.getStringExtra("active");
        if( action.equals("stop")){
            // Stop the service
            stopForeground(true);
            stopSelf();
        }else {
            showNotification(intent);
        }
//        return START_NOT_STICKY;
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void showNotification(Intent intent) {

        int icon = context.getApplicationInfo().icon;
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = null;

        String contentTitle = intent.getStringExtra("title");
        String contentText = intent.getStringExtra("text");
        long when = System.currentTimeMillis();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(channel_ID, channel_NAME, NotificationManager.IMPORTANCE_LOW);
            notificationManager.createNotificationChannel(mChannel);
            notification = new Notification.Builder(this)
                    .setChannelId(channel_ID)
                    .setContentTitle(contentTitle)
                    .setContentText(contentText)
                    .setWhen(when)
                    .setAutoCancel(false)
                    .setShowWhen(true)
//                    .setCategory()
                    .setOngoing(true)
                    .setContentIntent(pannel(0))
                    .setSmallIcon(icon).build();
        } else {
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setContentTitle(contentTitle)
                    .setContentText(contentText)
                    .setWhen(when)
                    .setAutoCancel(false)
                    .setShowWhen(true)
//                    .setCategory()
                    .setOngoing(true)
                    .setContentIntent(pannel(0))
                    .setSmallIcon(icon);
//                    .setChannel(channel_ID);//无效
            notification = notificationBuilder.build();
        }
//        notificationManager.notify(111123, notification);
        startForeground(NOTIFICATION,notification);
    }

    public PendingIntent pannel(int str) {
        //点击消息打开 app
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, newIntent(str), PendingIntent.FLAG_CANCEL_CURRENT);
        return pendingIntent;
    }

    public Intent newIntent(int type) {
        Intent intent = new Intent(context, foregroundReceiver.class);
        if (intent.hasExtra(actionType))
            intent.removeExtra(actionType);
        intent.putExtra(actionType, type);
        intent.putExtra("APP_NAME",context.getApplicationInfo().packageName);
        return intent;
    }
}
