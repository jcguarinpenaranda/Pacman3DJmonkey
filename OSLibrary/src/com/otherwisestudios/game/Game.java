/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.otherwisestudios.game;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.controls.ActionListener;
import com.jme3.material.Material;
import com.jme3.renderer.RenderManager;

/**
 * test
 *
 * @author normenhansen
 */
public class Game extends SimpleApplication implements ActionListener {

    public static void main(String[] args) {
        Game app = new Game();
        app.start();
    }
    
    private BulletAppState bulletAppState = new BulletAppState();

    @Override
    public void simpleInitApp() {
       stateManager.attach(bulletAppState);
    }

    public void onAction(String name, boolean isPressed, float tpf) {
       
    }

    @Override
    public void simpleUpdate(float tpf) {
        
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}

