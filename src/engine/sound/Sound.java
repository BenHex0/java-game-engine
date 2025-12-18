package engine.sound;

import javax.sound.sampled.*;
import java.io.File;

public class Sound {
    Clip clip;
    FloatControl fc;
    String soundPath[] = new String[30]; // store paths
    int volumeScale = 1;
    float volume;

    public Sound() {
        // absolute or relative path from project root
        soundPath[0] = "assets/sounds/BlueBoyAdventure.wav";
        soundPath[1] = "assets/sounds/audio.wav";
        // soundPath[1] = "assets/sound/coin.wav"; etc.
    }

    public void setFile(int i) {
        try {
            File soundFile = new File(soundPath[i]);
            if (!soundFile.exists()) {
                System.err.println("Sound file not found: " + soundPath[i]);
                return;
            }
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        } catch (Exception e) {
            e.printStackTrace();
        }
        checkVolume();
    }

    public void play() {
        if (clip == null) {
            System.err.println("❌ Clip is null — sound not loaded");
            return;
        }
        clip.setFramePosition(0); // restart
        clip.start();
    }

    public void loop() {
        if (clip == null)
            return;
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        if (clip == null)
            return;
        clip.stop();
    }

    public void checkVolume() {
        switch (volumeScale) {
            case 0:
                volume = -80f;
                break;
            case 1:
                volume = -20f;
                break;
            case 2:
                volume = -12f;
                break;
            case 3:
                volume = -5f;
                break;
            case 4:
                volume = 1f;
                break;
            case 5:
                volume = 6f;
                break;
        }
        fc.setValue(volume);
    }
}
