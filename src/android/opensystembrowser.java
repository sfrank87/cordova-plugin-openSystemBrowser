
package de.inside.cordova.opensystembrowser;

import android.annotation.SuppressLint;
//import android.content.Context;
import android.content.Intent;
import android.provider.Browser;
//import android.content.res.Resources;
//import android.graphics.Bitmap;
//import android.graphics.drawable.Drawable;
import android.net.Uri;
// import android.os.Build;
// import android.os.Bundle;
// import android.text.InputType;
// import android.util.TypedValue;
// import android.view.Gravity;
// import android.view.KeyEvent;
// import android.view.View;
// import android.view.Window;
// import android.view.WindowManager;
// import android.view.WindowManager.LayoutParams;
// import android.view.inputmethod.EditorInfo;
// import android.view.inputmethod.InputMethodManager;
// import android.webkit.CookieManager;
// import android.webkit.CookieSyncManager;
// import android.webkit.HttpAuthHandler;
// import android.webkit.WebSettings;
// import android.webkit.WebView;
// import android.webkit.WebViewClient;
// import android.widget.EditText;
// import android.widget.ImageButton;
// import android.widget.ImageView;
// import android.widget.LinearLayout;
// import android.widget.RelativeLayout;

import org.apache.cordova.CallbackContext;
//import org.apache.cordova.Config;
import org.apache.cordova.CordovaArgs;
//import org.apache.cordova.CordovaHttpAuthHandler;
import org.apache.cordova.CordovaPlugin;
//import org.apache.cordova.CordovaWebView;
import org.apache.cordova.LOG;
//import org.apache.cordova.PluginManager;
import org.apache.cordova.PluginResult;
import org.json.JSONException;
import org.json.JSONObject;

//import java.lang.reflect.InvocationTargetException;
// import java.lang.reflect.Field;
// import java.lang.reflect.Method;
// import java.util.HashMap;
// import java.util.StringTokenizer;

@SuppressLint("SetJavaScriptEnabled")
public class opensystembrowser extends CordovaPlugin {

    protected static final String LOG_TAG = "opensystembrowser";
    //private CallbackContext callbackContext;

    /**
     * Executes the request and returns PluginResult.
     *
     * @param action the action to execute.
     * @param args JSONArry of arguments for the plugin.
     * @param callbackContext the callbackContext used when calling back into JavaScript.
     * @return A PluginResult object with a status and message.
     */
    public boolean execute(String action, CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        if (action.equals("open")) {
            //this.callbackContext = callbackContext;
            final String url = args.getString(0);

            this.cordova.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //String result = openExternal(url);
                    String result = "";
                    try {
                        Intent intent = null;
                        intent = new Intent(Intent.ACTION_VIEW);
                        // Omitting the MIME type for file: URLs causes "No Activity found to handle Intent".
                        // Adding the MIME type to http: URLs causes them to not be handled by the downloader.
                        Uri uri = Uri.parse(url);
                        if (!"file".equals(uri.getScheme())) {
                            //intent.setDataAndType(uri, webView.getResourceApi().getMimeType(uri));
                            intent.setData(uri);
                            intent.putExtra(Browser.EXTRA_APPLICATION_ID, cordova.getActivity().getPackageName());
                            this.cordova.getActivity().startActivity(intent);
                        }
                    // not catching FileUriExposedException explicitly because buildtools<24 doesn't know about it
                    } catch (java.lang.RuntimeException e) {
                        LOG.d(LOG_TAG, "opensystembrowser: Error loading url "+url+":"+ e.toString());
                        result = e.toString();
                    } finally {
                        PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, result);
                        pluginResult.setKeepCallback(true);
                        callbackContext.sendPluginResult(pluginResult);
                    }
                }
            });
            return true;
        }
        return false;
    }
}
