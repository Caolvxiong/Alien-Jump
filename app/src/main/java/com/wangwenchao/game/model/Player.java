package com.wangwenchao.game.model;

import android.graphics.Rect;

import com.wangwenchao.game.state.PlayState;
import com.wangwenchao.godown.Assets;
import com.wangwenchao.godown.GameMainActivity;

public class Player {
    private float x, y;
    private int width, height, velY;
    private Rect rect, duckRect, ground;

    private boolean isAlive;
    private boolean isDucked;
    private float duckDuration = .6f;

    private static final int JUMP_VELOCITY = -400;
    private static final int ACCEL_GRAVITY = 800;

    public Player(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        //ground = new Rect(0, 405, 0 + 800, 45 + 405);
        rect = new Rect();
        duckRect = new Rect();
        isAlive = true;
        isDucked = false;
        updateRects();
    }

    public void update(float delta) {
        if (duckDuration > 0 && isDucked) {
            duckDuration -= delta;
        } else {
            isDucked = false;
            duckDuration = .6f;
        }

        velY += ACCEL_GRAVITY * delta;

        if(velY > 600){
            velY = 600;
        }
        y += velY * delta;
        updateRects();
    }

    public void moveUp(float delta, int blockSpeed) {
        velY = blockSpeed;

        y += velY * delta;
        updateRects();
    }

    public void jump() {
        Assets.playSound(Assets.onJumpID);
        isDucked = false;
        duckDuration = .6f;
        y -= 12;
        velY = JUMP_VELOCITY;
        updateRects();
    }

    public void duck() {
        isDucked = true;
    }

    public void moveRight() {
        PlayState.direction = 1;
        isDucked = false;
        duckDuration = .6f;
        if (x + 25 > GameMainActivity.GAME_WIDTH - width) {
            x = GameMainActivity.GAME_WIDTH - width;
        } else {
            x += 25;
        }
    }

    public void moveLeft() {
        PlayState.direction = 0;
        isDucked = false;
        duckDuration = .6f;
        if (x - 25 < 0) {
            x = 0;
        } else {
            x -= 25;
        }
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public boolean isGrounded() {
        return false;
        //return rect.intersects(rect, ground);
    }

    public void pushBack(int dX) {
        x -= dX;
        Assets.playSound(Assets.hitID);
        if (x < -width / 2) {
            isAlive = false;
        }
        rect.set((int) x, (int) y, (int) x + width, (int) y + height);
    }

    public void updateRects() {
        if (y < -height / 2 || y > GameMainActivity.GAME_HEIGHT) {
            isAlive = false;
        }
        rect.set((int) x + 10, (int) y, (int) x + width - 20, (int) y + height);
        duckRect.set((int) x, (int) y + 20, (int) x + width, (int) y + height);
    }

    public void updateRects(int x, int y) {
        rect.set(x + 10, y, x + width - 20, y + height);
        duckRect.set(x, y + 20, x + width, y + height);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Rect getRect() {
        return rect;
    }

    public int getvelY() {
        return velY;
    }

    public Rect getDuckRect() {
        return duckRect;
    }

    public Rect getGround() {
        return ground;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public boolean isDucked() {
        return isDucked;
    }

    public float getDuckDuration() {
        return duckDuration;
    }
}
