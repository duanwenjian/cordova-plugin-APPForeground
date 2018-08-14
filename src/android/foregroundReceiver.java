package cordova.plugin.APPForeground;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.yourname.workshop.MainActivity;

import java.util.List;

public class foregroundReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        int actionType = intent.getIntExtra("actionType",0);
        String APP_NAME = intent.getStringExtra("APP_NAME");

        switch (actionType) {
            case 0:
                if (isAppAlive(context, APP_NAME)) {
                    // TODO: This method is called when the BroadcastReceiver is receiving
                    Log.i("foregroundReceiver", "the app process is alive");
                    Intent mainIntent = new Intent(context, MainActivity.class);
                    mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Intent[] intents = {mainIntent};
                    context.startActivities(intents);
                } else {
                    Log.i("foregroundReceiver", "the app process is dead");
                    Intent launchIntent = context.getPackageManager().
                            getLaunchIntentForPackage(APP_NAME);
                    launchIntent.setFlags(
                            Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                    context.startActivity(launchIntent);
                }
                break;
                default:
                    break;
        }
//        throw new UnsupportedOperationException("Not yet implemented");
    }

    public static boolean isAppAlive(Context context, String packageName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        if (list.size() <= 0) {
            return false;
        }
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.baseActivity.getPackageName().equals(packageName)) {
                return true;
            }
        }
        return false;
    }
}
