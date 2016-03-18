package com.wangwenchao.godown;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

public class GameMainActivity extends Activity{
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 450;
	public static GameView sGame;
	public static AssetManager assets;
	
	private static SharedPreferences prefs;
	private static final String highScoreKey = "highScoreKey";
	private static int highScore;
	
	private static final String mutedKey = "mutedKey";
	private static boolean muted = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		prefs = getPreferences(Activity.MODE_PRIVATE);
		highScore = retrieveHighScore();
		assets = getAssets();
		sGame = new GameView(this, GAME_WIDTH, GAME_HEIGHT);
		setContentView(sGame);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		muted = retrieveMuted();
		
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
	protected void onResume(){
		super.onResume();
		Assets.onResume();
		sGame.onResume();
	}
	
	@Override
	protected void onPause(){
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
