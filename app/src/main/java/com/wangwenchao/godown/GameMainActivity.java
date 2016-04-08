package com.wangwenchao.godown;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class GameMainActivity extends Activity {
    public static final int GAME_WIDTH = 450;
    public static final int GAME_HEIGHT = 800;
    public static GameView sGame;
    public static AssetManager assets;

    private static SharedPreferences prefs;
    private static final String highScoreKey = "highScoreKey";
    private static int highScore;

    private static final String mutedKey = "mutedKey";
    private static boolean muted = false;

    private static AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getPreferences(Activity.MODE_PRIVATE);
        highScore = retrieveHighScore();
        assets = getAssets();
        sGame = new GameView(this, GAME_WIDTH, GAME_HEIGHT);

        // Create and load the AdView.
        adView = new AdView(this);
        adView.setAdUnitId("ca-app-pub-3472734546302220/9316443798");
        adView.setAdSize(AdSize.SMART_BANNER);

        // Create a RelativeLayout as the main layout and add the gameView.
        RelativeLayout mainLayout = new RelativeLayout(this);
        mainLayout.addView(sGame);

        // Add adView to the bottom of the screen.
        RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        mainLayout.addView(adView, adParams);

        // Set the RelativeLayout as the main layout.
        setContentView(mainLayout);

        //setContentView(sGame);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        muted = retrieveMuted();

        showBanner();

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    public static void setHighScore(int highScore) {
        GameMainActivity.highScore = highScore;
        Editor editor = prefs.edit();
        editor.putInt(highScoreKey, highScore);
        editor.commit();
    }

    // This method is used to save a boolean value into the shared preferences.
    public static void setMuted(boolean muted) {
        GameMainActivity.muted = muted;
        Editor editor = prefs.edit();
        editor.putBoolean(mutedKey, muted);
        editor.commit();
    }

    public static void showBanner() {
        adView.setVisibility(View.VISIBLE);
        adView.loadAd(new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());
    }

    public static void hideBanner() {
        adView.setVisibility(View.GONE);
    }

    // This method is called to retrieve the existing boolean value for key = mutedKey
    private static boolean retrieveMuted() {
        return prefs.getBoolean(mutedKey, muted);
    }

    // This method returns the local copy of the muted variable, which in
    // onCreate() is set equal to the value stored in the shared preferences.
    public static boolean isMuted() {
        return muted;
    }

    private int retrieveHighScore() {
        return prefs.getInt(highScoreKey, 0);
    }

    public static int getHighScore() {
        return highScore;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Assets.onResume();
        sGame.onResume();
        showBanner();
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Assets.onPause();
        sGame.onPause();
    }

    @Override
    public void onBackPressed() {
        Log.d("Android", "onBackPressed Called");
        sGame.onBackPressed();
    }

    public void exit() {
        super.onBackPressed();
    }


}
