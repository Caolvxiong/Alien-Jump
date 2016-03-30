package com.wangwenchao.game.model;

import android.graphics.Rect;

import com.wangwenchao.framework.util.RandomNumberGenerator;

public class Block {

	private float x, y;
	private int width, height;
	private Rect rect;
	private boolean visible;
	
	private static final int UPPER_Y = 275;
	private static final int LOWER_Y = 355;

	public Block(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		rect = new Rect((int)x, (int)y, (int)x + width, (int)y + height);
		visible = false;
	}

	public void updateY(float delta, float velY) {
		y += velY * delta;
		if (y <= -50) {
			resetX();
		}
		updateRect();
	}

    private void resetX() {
        visible = true;
        int num1 = RandomNumberGenerator.GetRandInt(4);
		int num2 = RandomNumberGenerator.GetRandInt(9);
        x = num1 * 100 + num2 *10;
        y += 820;
    }

	private void updateRect() {
		rect.set((int) x, (int) y, (int) x + width, (int) y + height);
	}

	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public Rect getRect() {
		return rect;
	}
	
}
