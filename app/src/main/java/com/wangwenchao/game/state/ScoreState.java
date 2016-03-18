package com.wangwenchao.game.state;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.MotionEvent;

import com.wangwenchao.godown.GameMainActivity;
import com.wangwenchao.framework.util.Painter;

public class ScoreState extends State{

	private String highScore;
	@Override
	public void init() {
		highScore = GameMainActivity.getHighScore() + "";
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Painter g) {
		g.setColor(Color.rgb(53, 156, 253));
		g.fillRect(0, 0, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);
		g.setColor(Color.WHITE);
		g.setFont(Typeface.DEFAULT_BOLD, 50);
		g.drawString("The All-Time High Score", 120, 175);
		g.setFont(Typeface.DEFAULT_BOLD, 70);
		g.drawString(highScore, 370, 260);
		g.setFont(Typeface.DEFAULT_BOLD, 50);
		g.drawString("Touch the screen", 220, 350);
	}

	@Override
	public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
		// TODO Auto-generated method stub
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
