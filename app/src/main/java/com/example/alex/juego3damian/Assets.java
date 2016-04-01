package com.example.alex.juego3damian;

import java.io.IOException;

public class Assets {
    public static Texture background;
    public static TextureRegion backgroundRegion;

    public static Texture items;
    public static TextureRegion mainMenu;
    public static TextureRegion pauseMenu;
    public static TextureRegion ready;
    public static TextureRegion gameOver;
    public static TextureRegion highScoreRegion;
    public static TextureRegion logo;
    public static TextureRegion soundOn;
    public static TextureRegion soundOff;
    public static TextureRegion arrow;
    public static TextureRegion pause;
    public static TextureRegion spring;
    public static TextureRegion infernalDoor;

    public static Animation heartAnim;
    public static Animation jump;
    public static Animation fall;

    public static TextureRegion petada;
    public static Animation marulosVoladores;
    public static TextureRegion platform;
    public static Animation breakingPlatform;
    public static Font font;

    public static Music music;
    public static Sound jumpSound;
    public static Sound highJumpSound;
    public static Sound petadaSound;
    public static Sound heartSound;
    public static Sound clickSound;


    public static void load(GLGame game) {

        background = new Texture(game, "background.png");
        backgroundRegion = new TextureRegion(background, 0, 0, 1600, 900);

        items = new Texture(game, "juego.png");
        mainMenu = new TextureRegion(items, 0, 224, 300, 110);
        logo = new TextureRegion(items, 843, 1433, 2516, 1289);
        ready = new TextureRegion(items, 0, 0, 0, 0);
        gameOver = new TextureRegion(items, 0, 0, 0, 0);
        highScoreRegion = new TextureRegion(items, 0, 0, 0, 0);
        soundOn = new TextureRegion(items, 0, 0, 0, 0);
        soundOff = new TextureRegion(items, 0, 0, 0, 0);
        arrow = new TextureRegion(items, 0, 0, 0, 0);
        pause = new TextureRegion(items, 0, 0, 0, 0);

        spring = new TextureRegion(items, 0, 0, 0, 0);
        infernalDoor = new TextureRegion(items, 1792, 671, 622, 606);

        heartAnim = new Animation(0.2f,
                new TextureRegion(items, 860, 673, 314, 307),
                new TextureRegion(items, 1172, 673, 314, 307),
                new TextureRegion(items, 1483, 673, 314, 307)
        );

        jump = new Animation(0.2f,
                new TextureRegion(items, 867, 369, 308, 304),
                new TextureRegion(items, 1175, 369, 308, 304)
        );
        fall = new Animation(0.2f,
                new TextureRegion(items, 867, 369, 308, 304),
                new TextureRegion(items, 1175, 369, 308, 304)
        );
        petada = new TextureRegion(items, 1483, 369, 308, 304);

        marulosVoladores = new Animation(0.2f,
                new TextureRegion(items, 855, 980, 316, 299),
                new TextureRegion(items, 1170, 980, 315, 299)
        );

        platform = new TextureRegion(items, 1791, 519, 307, 74);
        breakingPlatform = new Animation(0.4f,
                new TextureRegion(items, 1791, 519, 307, 74),
                new TextureRegion(items, 2099, 519, 307, 74),
                new TextureRegion(items, 2406, 519, 307, 74),
                new TextureRegion(items, 2712, 519, 307, 74)
        );


        font = new Font(items, 2562, 821, 16, 16, 16);

        try {
            music = game.getAudio().newMusic("music.mp3");
            music.setLooping(true);
            music.setVolume(0.5f);

            if (Settings.soundEnabled) {
                music.play();
            }

            jumpSound = game.getAudio().newSound("jump.mp3");
            highJumpSound = game.getAudio().newSound("jump.mp3");
            petadaSound = game.getAudio().newSound("jump.mp3");
            heartSound = game.getAudio().newSound("jump.mp3");
            clickSound = game.getAudio().newSound("jump.mp3");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void reload() {
        background.reload();
        items.reload();
        if (Settings.soundEnabled) {
            try {
                music.play();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void playSound(Sound sound) {
        if (Settings.soundEnabled) {
            sound.play(1);
        }
    }
}
