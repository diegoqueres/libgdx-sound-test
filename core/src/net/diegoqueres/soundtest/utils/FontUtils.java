package net.diegoqueres.soundtest.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class FontUtils {
    public final static int MAX_TEXTURE_SIZE = 2048;

    public static BitmapFont generateFont() {
        FreeTypeFontGenerator.setMaxTextureSize(MAX_TEXTURE_SIZE);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
                new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int) (0.05 * Gdx.graphics.getHeight());
        parameter.color = Color.BLACK;
        return generator.generateFont(parameter);
    }

    public static float getTamX(GlyphLayout glyphLayout, BitmapFont font, String text) {
        glyphLayout.reset();
        glyphLayout.setText(font, text);
        return glyphLayout.width;
    }

    public static float getTamY(GlyphLayout glyphLayout, BitmapFont font, String text) {
        glyphLayout.reset();
        glyphLayout.setText(font, text);
        return glyphLayout.height;
    }
}
