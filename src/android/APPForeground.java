package cordova.plugin.APPForeground;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cordova.plugin.APPForeground.serviceForeground;

import java.io.EOFException;


/**
 * This class echoes a string called from JavaScript.
 */
public class APPForeground extends CordovaPlugin {
    private CallbackContext callbackContext_all;
    private Context context;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        callbackContext_all = callbackContext;
        if (action.equals("coolMethod")) {
            String message = args.getString(0);
            this.coolMethod(message, callbackContext);
            return true;
        } else if (action.equals("startService")) {
            JSONObject message = args.getJSONObject(0);
            startService(message);
        } else if (action.equals("stopService")) {
//            JSONObject message = args.getJSONObject(0);
            stopService();
        }else if(action.equals("goSetting")){
            goSetting_cordova();
        }
        return false;
    }

    private void coolMethod(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }

    private void startService(JSONObject message) {
        try {
            Activity activity = cordova.getActivity();
            Intent intent = new Intent(activity, serviceForeground.class);
            intent.putExtra("title", message.optString("title"));
            intent.putExtra("text", message.optString("text"));
            intent.putExtra("active", "start");
            activity.getApplicationContext().startService(intent);
            try {
                JSONObject info = new JSONObject();
                info.put("code", 0);
                info.put("msg", "启动成功");
                callbackContext_all.success(info);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            try {
                JSONObject info = new JSONObject();
                info.put("code", 1);
                info.put("msg", "启动失败");
                callbackContext_all.success(info);
            } catch (JSONException err) {
                e.printStackTrace();
            }
        }
    }

    private void stopService() {
        try {
            Activity activity = cordova.getActivity();
            Intent intent = new Intent(activity, serviceForeground.class);
            intent.putExtra("active", "stop");
            activity.getApplicationContext().startService(intent);
            try {
                JSONObject info = new JSONObject();
                info.put("code", 0);
                info.put("msg", "关闭成功");
                callbackContext_all.success(info);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            try {
                JSONObject info = new JSONObject();
                info.put("code", 1);
                info.put("msg", "关闭失败");
                callbackContext_all.success(info);
            } catch (JSONException err) {
                e.printStackTrace();
            }
        }
    }

    private void goSetting_cordova() {
        context = cordova.getContext();
        context.startActivity(new Intent(Settings.ACTION_SETTINGS));
    }
}
