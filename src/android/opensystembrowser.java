
package de.inside.cordova.opensystembrowser;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.provider.Browser;
import android.net.Uri;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.LOG;
import org.apache.cordova.PluginResult;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressLint("SetJavaScriptEnabled")
public class opensystembrowser extends CordovaPlugin {

    protected static final String LOG_TAG = "opensystembrowser";

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
            final String url = args.getString(0);

            this.cordova.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String result = openExternal(url);

                    PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, result);
                    pluginResult.setKeepCallback(true);
                    callbackContext.sendPluginResult(pluginResult);
                }
            });
            return true;
        }
        return false;
    }

    /**
     * Display a new browser with the specified URL.
     *
     * @param url the url to load.
     * @return "" if ok, or error message.
     */
    public String openExternal(String url) {
        try {
            Intent intent = null;
            intent = new Intent(Intent.ACTION_VIEW);
            // Omitting the MIME type for file: URLs causes "No Activity found to handle Intent".
            // Adding the MIME type to http: URLs causes them to not be handled by the downloader.
            Uri uri = Uri.parse(url);
            if (!"file".equals(uri.getScheme())) {
                intent.setData(uri);
                intent.putExtra(Browser.EXTRA_APPLICATION_ID, cordova.getActivity().getPackageName());
                this.cordova.getActivity().startActivity(intent);
                return "";
            } else {
                return "open file:// in system browser not allowed";
            }

        // not catching FileUriExposedException explicitly because buildtools<24 doesn't know about it
        } catch (java.lang.RuntimeException e) {
            LOG.d(LOG_TAG, "opensystembrowser: Error loading url "+url+":"+ e.toString());
            return e.toString();
        }
    }
}
