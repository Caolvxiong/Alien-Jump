package com.wangwenchao.game.state;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.MotionEvent;

import com.wangwenchao.godown.GameMainActivity;
import com.wangwenchao.framework.util.Painter;

public class GameOverState extends State {

	private String playScore;
	private String gameOverMessage = "GAME OVER";
	
	public GameOverState(int playScore){
		this.playScore = playScore + "";
		if (playScore > GameMainActivity.getHighScore()) {
			GameMainActivity.setHighScore(playScore);
			gameOverMessage = "HIGH SCORE!";
		}
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(Painter g) {
		g.setColor(Color.rgb(255, 145, 0));
		g.fillRect(0, 0, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);
		g.setColor(Color.DKGRAY);
		g.setFont(Typeface.DEFAULT_BOLD, 50);
		g.drawString(gameOverMessage, 80, 275);
		g.drawString(playScore, 200, 360);
		g.drawString("Touch the screen", 30, 450);
	}

	@Override
	public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
		if (e.getAction() == MotionEvent.ACTION_UP) {
			setCurrentState(new MenuState());
		}
		return true;
	}

	
	@Override
	public void onBackPressed() {
	    setCurrentState(new MenuState());
	}

}
