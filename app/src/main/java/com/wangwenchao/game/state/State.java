package com.wangwenchao.game.state;

import android.view.MotionEvent;

import com.wangwenchao.godown.GameMainActivity;
import com.wangwenchao.framework.util.Painter;

public abstract class State{

	
	public void setCurrentState(State newState) {
		GameMainActivity.sGame.setCurrentState(newState);
	}
	
	public abstract void init();
	
	public abstract void update(float delta);
	
	public abstract void render(Painter g);
	
	public abstract boolean onTouch(MotionEvent e, int scaledX, int scaledY);
	
	public void onResume(){}
	
	public void onPause(){}
	
	public void onBackPressed(){}

}
