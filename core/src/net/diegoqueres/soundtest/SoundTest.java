package net.diegoqueres.soundtest;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import net.diegoqueres.soundtest.utils.FontUtils;

import static net.diegoqueres.soundtest.Constants.*;
import static net.diegoqueres.soundtest.utils.FontUtils.getTamX;
import static net.diegoqueres.soundtest.utils.FontUtils.getTamY;

public class SoundTest extends ApplicationAdapter {
	SpriteBatch batch;
	BitmapFont font;
	GlyphLayout glyphLayout;
	Array<Music> sounds;
	Array<String> soundFileNames;
	Music currentSound;
	int idxSound;
	float volume;
	float inputTimer;
	
	@Override
	public void create () {
		Gdx.app.getInput().setInputProcessor(new InputCore(this));
		batch = new SpriteBatch();
		glyphLayout = new GlyphLayout();
		font = FontUtils.generateFont();
		initAudio();
	}

	@Override
	public void render () {
		ScreenUtils.clear(Color.WHITE);
		batch.begin();
		drawDisplay();
		batch.end();

		timersUpdate();
		checkMusic();
	}

	private void timersUpdate() {
		if (inputTimer > 0f) {
			inputTimer -= Gdx.app.getGraphics().getDeltaTime();
		}
	}

	private void checkMusic() {
		Music choosenSound = sounds.get(idxSound);
		if (!choosenSound.equals(currentSound)) {
			if (currentSound != null && currentSound.isPlaying()) currentSound.stop();
			currentSound = choosenSound;
			currentSound.play();
		}

		currentSound.setVolume(volume);
	}

	private void drawDisplay() {
		String textSoundName = "Playing sound: " + soundFileNames.get(idxSound);
		drawLineDisplay(1, DISPLAY_LINES, textSoundName);

		String textVolume = String.format("Volume: %.2f (Mouse wheel to change)", volume);
		drawLineDisplay(2, DISPLAY_LINES, textVolume);

		String textNext = "Press left button to play next music";
		drawLineDisplay(3, DISPLAY_LINES, textNext);
	}

	private void drawLineDisplay(int lineNumber, int maxLines, String text) {
		int screenWidth = Gdx.graphics.getWidth();
		int screenHeight = Gdx.graphics.getHeight();
		float heightDivision = screenHeight / maxLines;
		float x = (screenWidth - getTamX(glyphLayout, font, text)) / 2;
		float currentY = (screenHeight - (heightDivision*lineNumber)) + heightDivision / 2;
		float y = currentY + (getTamY(glyphLayout, font, text) / 2);
		font.draw(batch, text, x, y);
	}

	private void initAudio() {
		sounds = new Array<>();
		soundFileNames = new Array<>();
		volume = DEFAULT_VOLUME;
		idxSound = 0;

		FileHandle dirHandle = Gdx.files.internal("sounds/");
		for (FileHandle entry : dirHandle.list()) {
			for (String extension : AUDIO_EXTENSIONS) {
				if (entry.extension().equalsIgnoreCase(extension)) {
					sounds.add(Gdx.audio.newMusic(entry));
					soundFileNames.add(entry.name());
				}
			}
		}
	}

	public void changeSound() {
		if (this.inputTimer <= 0f) this.idxSound++;
		if (idxSound >= sounds.size) idxSound = 0;
		this.inputTimer = TARGET_TIME_ACCEPT_CHANGE_SOUND;
	}

	public void increaseVolume() {
		if (this.volume + .01f < MAX_VOLUME)
			this.volume += .01f;
	}

	public void decreaseVolume() {
		if (this.volume - .01f > MIN_VOLUME)
			this.volume -= .01f;
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
	}
}
