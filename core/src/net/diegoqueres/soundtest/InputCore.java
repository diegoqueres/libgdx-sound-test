package net.diegoqueres.soundtest;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class InputCore extends InputAdapter {

    SoundTest soundTestApp;

    public InputCore(SoundTest soundTestApp) {
        this.soundTestApp = soundTestApp;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.DPAD_UP) {
            soundTestApp.increaseVolume();
            return true;
        }
        if (keycode == Input.Keys.DPAD_DOWN) {
            soundTestApp.decreaseVolume();
            return true;
        }
        if (keycode == Input.Keys.ENTER) {
            soundTestApp.changeSound();
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            soundTestApp.changeSound();
            return true;
        }
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        if (amountY == -1) {
            soundTestApp.increaseVolume();
            return true;
        }
        if (amountY == +1) {
            soundTestApp.decreaseVolume();
            return true;
        }
        return false;
    }
}
