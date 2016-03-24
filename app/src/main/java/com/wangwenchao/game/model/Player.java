package com.wangwenchao.game.model;

import android.graphics.Rect;

import com.wangwenchao.godown.Assets;
import com.wangwenchao.godown.GameMainActivity;

public class Player {
    private float x, y;
    private int width, height, velY;
    private Rect rect, duckRect, ground;

    private boolean isAlive;
    private boolean isDucked;
    private float duckDuration = .6f;

    private static int jumpVelocity = -600;
    private static int accelGravity = 1000;

    private Block boardSteppedOn;

    public Player(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        ground = new Rect(0, 405, 0 + 800, 45 + 405);
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

        if (!isGrounded()) {
            velY += accelGravity * delta;
        } else {
            y = 406 - height;
            velY = 0;
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
        if (isGrounded()) {
            Assets.playSound(Assets.onJumpID);
            isDucked = false;
            duckDuration = .6f;
            y -= 12;
            velY = jumpVelocity;
            updateRects();
        }
    }

    public void duck() {
        if (isGrounded()) {
            isDucked = true;
        }
    }

    public void moveRight() {
        isDucked = false;
        duckDuration = .6f;
        if(x + 25 > GameMainActivity.GAME_WIDTH - width){
            x = GameMainActivity.GAME_WIDTH - width;
        }else {
            x += 25;
        }
    }

    public void moveLeft() {
        isDucked = false;
        duckDuration = .6f;
        if (x - 25 < 0){
            x = 0;
        }else {
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
        return rect.intersects(rect, ground);
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
        if (y < -height / 2) {
            isAlive = false;
        }
        rect.set((int) x + 10, (int) y, (int) x + width - 20, (int) y + height);
        duckRect.set((int) x, (int) y + 20, (int) x + width, (int) y + height);
    }

    public void updateRects(int x, int y) {
        rect.set(x + 10, y, x + width - 20, y + height);
        duckRect.set(x, y + 20, x + width, y + height);
    }

    public void setBoardSteppedOn(Block board){
        this.boardSteppedOn = board;
    }

    public Block getBoardSteppedOn(){
        return boardSteppedOn;
    }

    public int getJumpVelocity() {
        return jumpVelocity;
    }

    public void setJumpVelocity(int jumpVelocity) {
        this.jumpVelocity = jumpVelocity;
    }

    public int getAccelGravity() {
        return accelGravity;
    }

    public void setAccelGravity(int accelGravity) {
        this.accelGravity = accelGravity;
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
