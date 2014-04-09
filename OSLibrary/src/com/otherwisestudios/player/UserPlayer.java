/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.otherwisestudios.player;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;

/**
 *
 * @author Asus
 */
public class UserPlayer extends Character implements ActionListener {

    protected boolean inputEnabled = false;
    protected Camera cam;
    protected boolean isFirstPerson = false;
    private InputManager inputManager;
    private boolean up = false, down = false, left = false, right = false;

    public UserPlayer() {
    }

    @Override
    public void update() {
        Vector3f camDir;
        Vector3f camL;
        Vector3f walkDirection = new Vector3f(0, 0, 0);
        if (cam != null) {
            camDir = cam.getDirection().normalize().mult(speed);
            camL = cam.getLeft().normalize().mult(speed);
        } else {
            camDir = Vector3f.UNIT_Z;
            camL = Vector3f.UNIT_X;
        }

        if (left) {
            walkDirection.addLocal(camL);
        }
        if (right) {
            walkDirection.addLocal(camL.negate());
        }
        if (up) {
            walkDirection.addLocal(camDir);
        }
        if (down) {
            walkDirection.addLocal(camDir.negate());
        }

        bodyControl.setWalkDirection(walkDirection);

        if (isFirstPerson) {
            cam.setLocation(body.getLocalTranslation());
            bodyControl.setViewDirection(new Vector3f(cam.getDirection().x, 0, cam.getDirection().z));
        }
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        if (inputEnabled) {
            if (name.equals("up") && isPressed) {
                up = true;
            }
            if (name.equals("down") && isPressed) {
                down = true;
            }
            if (name.equals("right") && isPressed) {
                right = true;
            }
            if (name.equals("left") && isPressed) {
                left = true;
            }
        }
    }

    public void setCamera(Camera cam) {
        this.cam = cam;
    }

    @Override
    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
    }

    @Override
    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
    }

    public void removeInputListener() {
        inputManager.removeListener(this);
    }

    public void setInputManager(InputManager inputManager) {
        this.inputManager = inputManager;
    }

    public void addMapping(String name, int trigger) {
        inputManager.addMapping(name, new KeyTrigger(trigger));
        inputManager.addListener(this, name);
    }

    public void setUsualInputs(InputManager inputManager) {
        this.inputManager = inputManager;
        inputEnabled = true;
        addMapping("up", KeyInput.KEY_UP);
        addMapping("up", KeyInput.KEY_W);
        addMapping("down", KeyInput.KEY_S);
        addMapping("left", KeyInput.KEY_A);
        addMapping("right", KeyInput.KEY_D);
        addMapping("jump", KeyInput.KEY_SPACE);
    }

    public void setInputEnabled(boolean is) {
        inputEnabled = is;
    }

    public void isFirstPerson(boolean is) {
        isFirstPerson = is;
    }

    public void setFirstPersonView(Camera cam) {
        this.isFirstPerson = true;
        this.cam = cam;
        cam.setLocation(body.getLocalTranslation());
    }

    public String toString() {
        return "";

    }
}
