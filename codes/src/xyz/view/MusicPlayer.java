package xyz.view;

import javafx.animation.Animation;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class MusicPlayer {
    public static AudioClip aau1;
    public static AudioClip aau2;
    public static AudioClip aau3;
    public static AudioClip aau4;
    public static AudioClip aau5;
    public static AudioClip aau6;
    public static AudioClip aau7;
    public static AudioClip aau8;
    public static AudioClip aau9;
//    AudioClip aau1;
//    AudioClip aau2;
//    AudioClip aau3;
//    AudioClip aau4;

    public void musicIni(){
    try {
        File f = new File("src\\xyz\\view\\music\\boom.wav");
        URL cb1 = f.toURL();
        aau1 = Applet.newAudioClip(cb1);
        File g = new File("src\\xyz\\view\\music\\flag.wav");
        URL cb2 = g.toURL();
        aau2 = Applet.newAudioClip(cb2);
        File h = new File("src\\xyz\\view\\music\\found.wav");
        URL cb3 = h.toURL();
        aau3 = Applet.newAudioClip(cb3);
        File i = new File("src\\xyz\\view\\music\\monster.wav");
        URL cb4 = i.toURL();
        aau4 = Applet.newAudioClip(cb4);
        File j = new File("src\\xyz\\view\\music\\playgame.wav");
        URL cb5 = j.toURL();
        aau5 = Applet.newAudioClip(cb5);
        File k = new File("src\\xyz\\view\\music\\choicebackground.wav");
        URL cb6 = k.toURL();
        aau6 = Applet.newAudioClip(cb6);
        File l = new File("src\\xyz\\view\\music\\victory.wav");
        URL cb7 = l.toURL();
        aau7 = Applet.newAudioClip(cb7);
        File m = new File("src\\xyz\\view\\music\\monsterbackground.wav");
        URL cb8 = m.toURI().toURL();
        aau8 = Applet.newAudioClip(cb8);
        File s = new File("src\\xyz\\view\\music\\choose.wav");
        URL cb9 = s.toURI().toURL();
        aau9 = Applet.newAudioClip(cb9);
    } catch (
    IOException e) {
        e.printStackTrace();
    }
    }
}

