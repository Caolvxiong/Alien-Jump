package com.wangwenchao.game.state;

import java.util.ArrayList;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;

import com.wangwenchao.godown.Assets;
import com.wangwenchao.godown.GameMainActivity;
import com.wangwenchao.framework.util.Painter;
import com.wangwenchao.framework.util.UIButton;
import com.wangwenchao.game.model.Block;
import com.wangwenchao.game.model.Cloud;
import com.wangwenchao.game.model.Player;


public class PlayState extends State {

    private Player player;
    private ArrayList<Block> blocks;
    private Cloud cloud, cloud2;

    private int playerScore = 0;

    private static final int BLOCK_HEIGHT = 20;
    private static final int BLOCK_WIDTH = 100;
    private int blockSpeed = -100;

    private static final int PLAYER_WIDTH = 66;
    private static final int PLAYER_HEIGHT = 92;

    private float recentTouchX;
    private float recentTouchY;

    private UIButton pauseButton;
    private UIButton playButton;

    private boolean gamePaused = false;
    private String pausedString = "Game Paused. Tap to resume.";

    public static int direction = 0;//0 for left, 1 for right.
    @Override
    public void init() {
        player = new Player(210, GameMainActivity.GAME_HEIGHT - 700 - PLAYER_HEIGHT, PLAYER_WIDTH, PLAYER_HEIGHT);
        blocks = new ArrayList<Block>();
        cloud = new Cloud(100, 100);
        cloud2 = new Cloud(50, 50);

        pauseButton = new UIButton(375, 10, 425, 60, Assets.pause, Assets.pause);
        playButton = new UIButton(375, 150, 425, 200, Assets.play, Assets.play);

        for (int i = 0; i < 10; i++) {
            Block b = new Block(150, i * 120, BLOCK_WIDTH, BLOCK_HEIGHT);
            blocks.add(b);
        }
    }

    @Override
    public void onPause() {
        gamePaused = true;
    }

    @Override
    public void update(float delta) {
        if (gamePaused) {
            return;
        }

        if (!player.isAlive()) {
            setCurrentState(new GameOverState(playerScore / 100));
        }
        playerScore += 1;

        if (playerScore % 500 == 0 && blockSpeed > -580) {
            blockSpeed -= 10;
        }

        cloud.update(delta);
        cloud2.update(delta);
        if (direction == 1){
            Assets.runAnim.update(delta);
        } else {
            Assets.runLeftAnim.update(delta);
        }
        updateBlocks(delta);
        player.update(delta);
    }

    private void updateBlocks(float delta) {
        for (int i = 0; i < blocks.size(); i++) {
            Block b = blocks.get(i);
            b.updateY(delta, blockSpeed);
            if (b.isVisible()) {
                //Check collision first.
                if (Rect.intersects(b.getRect(), player.getRect())) {
                    if (player.getY() + PLAYER_HEIGHT < b.getY() + BLOCK_HEIGHT) {
                        player.moveUp(delta, blockSpeed);
                    }
                }
            }
        }
    }

    @Override
    public void render(Painter g) {
        g.setColor(Color.rgb(208, 244, 247));
        g.fillRect(0, 0, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);
        renderPlayer(g);
        renderBlocks(g);
        renderSun(g);
        renderClouds(g);
        //g.drawImage(Assets.grass, 0, 405);
        renderScore(g);
        pauseButton.render(g);


        if (gamePaused) {
            g.setColor(Color.argb(153, 0, 0, 0));
            g.fillRect(0, 0, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);
            g.drawString(pausedString, 235, 240);
            playButton.render(g);
        }

    }

    private void renderScore(Painter g) {
        g.setFont(Typeface.SANS_SERIF, 25);
        g.setColor(Color.GRAY);
        g.drawString("" + playerScore / 100, 20, 30);
    }

    private void renderClouds(Painter g) {
        g.drawImage(Assets.cloud1, (int) cloud.getX(), (int) cloud.getY(), 100, 60);
        g.drawImage(Assets.cloud2, (int) cloud2.getX(), (int) cloud2.getY(), 100, 60);
    }

    private void renderSun(Painter g) {
        g.setColor(Color.rgb(255, 165, 0));
        g.fillOval(365, -85, 170, 170);
        g.setColor(Color.YELLOW);
        g.fillOval(375, -75, 150, 150);
    }

    private void renderBlocks(Painter g) {
        for (int i = 0; i < blocks.size(); i++) {
            Block b = blocks.get(i);
            if (b.isVisible()) {
                g.drawImage(Assets.block, (int) b.getX(), (int) b.getY(), BLOCK_WIDTH, BLOCK_HEIGHT);
            }
        }
    }

    private void renderPlayer(Painter g) {
        if (direction == 1) {
            if (player.getvelY() <= 0) {
                if (player.isDucked()) {
                    g.drawImage(Assets.duck, (int) player.getX(), (int) player.getY());
                } else {
                    Assets.runAnim.render(g, (int) player.getX(), (int) player.getY(), player.getWidth(), player.getHeight());
                }
            } else {
                g.drawImage(Assets.jump, (int) player.getX(), (int) player.getY(), player.getWidth(), player.getHeight());
            }
        } else {
            if (player.getvelY() <= 0) {
                if (player.isDucked()) {
                    g.drawImage(Assets.duckLeft, (int) player.getX(), (int) player.getY());
                } else {
                    Assets.runLeftAnim.render(g, (int) player.getX(), (int) player.getY(), player.getWidth(), player.getHeight());
                }
            } else {
                g.drawImage(Assets.jumpLeft, (int) player.getX(), (int) player.getY(), player.getWidth(), player.getHeight());
            }
        }
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            recentTouchY = scaledY;
            recentTouchX = scaledX;
            pauseButton.onTouchDown(scaledX, scaledY);
            playButton.onTouchDown(scaledX, scaledY);
            if (scaledX <= player.getX()) {
                player.moveLeft();
            } else if (scaledX > player.getX() + PLAYER_WIDTH) {
                player.moveRight();
            }
        } else if (e.getAction() == MotionEvent.ACTION_UP) {
            if (gamePaused) {
                if (playButton.isPressed(scaledX, scaledY)) {
                    playButton.cancel();
                    Log.d("PlayState", "Play Button Pressed!");
                    gamePaused = false;
                } else {
                    playButton.cancel();
                }
                return true;
            }

            if (pauseButton.isPressed(scaledX, scaledY)) {
                pauseButton.cancel();
                Log.d("PlayState", "Pause Button Pressed!");
                gamePaused = true;
            }

            if (scaledY - recentTouchY < -50) {
                player.jump();
            } else if (scaledY - recentTouchY > 50) {
                player.duck();
            }
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        setCurrentState(new MenuState());
    }

}
