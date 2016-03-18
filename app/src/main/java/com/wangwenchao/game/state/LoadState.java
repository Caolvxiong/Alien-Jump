package com.wangwenchao.game.state;

import android.view.MotionEvent;

import com.wangwenchao.godown.Assets;
import com.wangwenchao.framework.util.Painter;

public class LoadState extends State{

	@Override
	public void init() {
		Assets.load();
	}

	@Override
	public void update(float delta) {
		setCurrentState(new MenuState());
	}

	@Override
	public void render(Painter g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
		return false;
	}

}
