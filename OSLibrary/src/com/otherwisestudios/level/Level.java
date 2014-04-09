/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.otherwisestudios.level;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.scene.Node;
import com.otherwisestudios.player.UserPlayer;

/**
 *
 * @author Asus
 */
public class Level {

    private UserPlayer player;
    private RigidBodyControl levelControl;
    private PhysicsSpace physicsSpace;

    public Level(PhysicsSpace physicsSpace) {
        this.physicsSpace = physicsSpace;
    }
}
