package com.fenix.rufu;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.onesignal.OneSignal;
import com.onesignal.example.R;


import android.annotation.SuppressLint;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import br.com.trendzapi.TrendzAd;

public class MainActivity extends AppCompatActivity {


   public class GeoWebViewClient extends WebViewClient {
      @Override
      public boolean shouldOverrideUrlLoading(WebView view, String url) {

         view.loadUrl(url);
         return true;
      }
   }

   @SuppressLint("NewApi")
   public class GeoWebChromeClient extends WebChromeClient {
      @Override
      public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
         // Always grant permission since the app itself requires location
         // permission and the user has therefore already granted it
         callback.invoke(origin, true, false);
      }
   }

   WebView mWebView;


   private static Activity currentActivity;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.main);

      OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
         @Override
         public void idsAvailable(String userId, String registrationId) {
            String text = "OneSignal UserID:\n" + userId + "\n\n";

            if (registrationId != null)
               text += "Google Registration Id:\n" + registrationId;
            else
               text += "Google Registration Id:\nCould not subscribe for push";

            //TextView textView = (TextView)findViewById(R.id.debug_view);
            //textView.setText(text);
         }
      });

      TrendzAd ad = new TrendzAd(MainActivity.this, "86lc74zfycq9ti14xisqt8kjo1a5j6dl754qzxn863y6r2op0e");
      ad.makeAd();

      mWebView = (WebView) findViewById(R.id.webView1);
      // Brower niceties -- pinch / zoom, follow links in place
      mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
      mWebView.getSettings().setBuiltInZoomControls(false);
      mWebView.getSettings().setDomStorageEnabled(true);
      mWebView.setWebViewClient(new GeoWebViewClient());
      // Below required for geolocation
      mWebView.getSettings().setJavaScriptEnabled(true);
      mWebView.getSettings().setGeolocationEnabled(true);
      mWebView.setWebChromeClient(new GeoWebChromeClient());
      // Load google.com
      mWebView.loadUrl("file:///android_asset/index.html");


   }

   @SuppressLint("NewApi")
   @Override
   public void onBackPressed() {
      // Pop the browser back stack or exit the activity
      if (mWebView.canGoBack()) {
         mWebView.goBack();
      } else {
         super.onBackPressed();
      }
   }

}
