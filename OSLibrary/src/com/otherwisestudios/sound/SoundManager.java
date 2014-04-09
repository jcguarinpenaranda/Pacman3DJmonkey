/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.otherwisestudios.sound;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author Asus
 */
public class SoundManager {

    private Node background, sounds;
    private AssetManager assetManager;
    private int limitOfSounds = 10;
    private String route = "";

    public SoundManager(AssetManager assetManager) {
        this.assetManager = assetManager;
        background = new Node("background");
        sounds = new Node("sounds");
    }

    public void setLimitOfSounds(int limit) {
        limitOfSounds = limit;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public void reproducir(String url, float intensity) {
        if (sounds.getChildren().size() > limitOfSounds) {
            sounds.getChildren().remove(0);
        }
        AudioNode elAudio = new AudioNode(assetManager, route + url, false);
        elAudio.setPositional(false);
        elAudio.setLooping(false);
        elAudio.setVolume(intensity);
        sounds.attachChild(elAudio);
        elAudio.play();
    }

    public void reproducirCancionDeFondo(String url, float intensity) {
        if (!background.getChildren().isEmpty()) {
            for (Spatial audioSP : background.getChildren()) {
                AudioNode audio = (AudioNode) audioSP;
                audio.stop();
                background.detachChild(audioSP);
            }
        }

        AudioNode elAudio = new AudioNode(assetManager, route + url, false);
        elAudio.setPositional(false);
        elAudio.setLooping(true);
        elAudio.setVolume(intensity);
        background.attachChild(elAudio);
        elAudio.play();
    }
}
