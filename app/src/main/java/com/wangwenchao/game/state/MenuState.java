package com.wangwenchao.game.state;

import android.util.Log;
import android.view.MotionEvent;

import com.wangwenchao.godown.Assets;
import com.wangwenchao.godown.GameMainActivity;
import com.wangwenchao.framework.util.Painter;
import com.wangwenchao.framework.util.UIButton;

public class MenuState extends State{
	private UIButton playButton, scoreButton, muteButton, unmuteButton;

	boolean doubleBackToExitPressedOnce;

	@Override
	public void init() {
		playButton = new UIButton(145, 427, 313, 486, Assets.start, Assets.startDown);
		scoreButton = new UIButton(145, 500, 313, 559, Assets.score, Assets.scoreDown);
		muteButton = new UIButton(0, 0, 50, 48, Assets.musicOn,
				Assets.musicOff);
		unmuteButton = new UIButton(0, 0, 50, 48, Assets.musicOff,
				Assets.musicOn);
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
	}

	@Override
	public void render(Painter g) {
		g.drawImage(Assets.welcome, 0, 0);
		
		playButton.render(g);
		scoreButton.render(g);
		
		if (GameMainActivity.isMuted()) {
			unmuteButton.render(g);
		} else {
			muteButton.render(g);
		}

	}

	@Override
	public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
		if (e.getAction() == MotionEvent.ACTION_DOWN) {
			playButton.onTouchDown(scaledX, scaledY);
			scoreButton.onTouchDown(scaledX, scaledY);
			if (GameMainActivity.isMuted()) {
				unmuteButton.onTouchDown(scaledX, scaledY);				
			} else {
				muteButton.onTouchDown(scaledX, scaledY);				
			}
		}
		
		if (e.getAction() == MotionEvent.ACTION_UP) {
			if (playButton.isPressed(scaledX, scaledY)) {
				playButton.cancel();
				Log.d("MenuState", "Play Button Pressed!");
				setCurrentState(new PlayState());
			}else if (scoreButton.isPressed(scaledX, scaledY)) {
				scoreButton.cancel();
				Log.d("MenuState", "Score Button Pressed!");
				setCurrentState(new ScoreState());
			}else if (muteButton.isPressed(scaledX, scaledY)) {
				muteButton.cancel();
				Assets.onMute();
				GameMainActivity.setMuted(true);
			} else if (unmuteButton.isPressed(scaledX, scaledY)) {
				unmuteButton.cancel();
				Assets.onUnmute();
				GameMainActivity.setMuted(false);
			}else {
				playButton.cancel();
				scoreButton.cancel();
				muteButton.cancel();
				unmuteButton.cancel();
			}
		}
		
		/*if (e.getAction() == MotionEvent.ACTION_DOWN) {
			if (playRect.contains(scaledX, scaledY)) {
				playDown = true;
				scoreDown = false;
			}else if (scoreRect.contains(scaledX,scaledY)) {
				scoreDown = true;
				playDown = false;
			}
		}
		if (e.getAction() == MotionEvent.ACTION_UP) {
			if (playDown && playRect.contains(scaledX, scaledY)) {
				playDown = false;
				Log.d("MenuState", "Play Button Pressed!");
			}else if (scoreDown && scoreRect.contains(scaledX, scaledY)) {
				scoreDown = false;
				Log.d("MenuState", "Score Button Pressed!");
			}else {
				scoreDown = false;
				playDown = false;
			}
		}*/
		
		return true;
	}
	@Override
	public void onBackPressed() {
		
		System.exit(0);
	}
}
