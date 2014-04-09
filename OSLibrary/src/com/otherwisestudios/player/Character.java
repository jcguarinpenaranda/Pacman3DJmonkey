/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.otherwisestudios.player;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.bounding.BoundingVolume;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.Trigger;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author Asus
 */
public abstract class Character extends Node {

    protected Node body, rootNode;
    protected BetterCharacterControl bodyControl;
    private PhysicsSpace physicsSpace;
    protected int points = 0;
    protected float mass;
    protected float speed;

    public Character(float mass, Node rootNode, PhysicsSpace physicsSpace) {
        this.mass = mass;
        this.rootNode = rootNode;
        this.physicsSpace = physicsSpace;
    }

    public Character() {
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setRootNode(Node node) {
        this.rootNode = node;
    }

    public abstract void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName);

    public abstract void onAnimChange(AnimControl control, AnimChannel channel, String animName);

    public void setPhysicsSpace(PhysicsSpace physicsSpace) {
        this.physicsSpace = physicsSpace;
    }

    public void addToBody(Spatial sp) {
        body.attachChild(sp);
    }

    public void removeFromBody(Spatial sp) {
        body.detachChild(sp);
    }

    public void addToPhysicsSpace(Spatial sp) {
        physicsSpace.add(sp);
    }

    public void removeFromPhysicsSpace(Spatial sp) {
        physicsSpace.remove(sp);
    }

    public void rotateVector(Vector3f viewDirection) {
        bodyControl.setViewDirection(viewDirection);
    }

    public void addPoint() {
        points++;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public abstract void update();

    public int getPoints() {
        return points;
    }

    public void jump() {
        bodyControl.jump();
    }

    public Vector3f getLocation() {
        return body.getLocalTranslation();
    }

    public void setLocation(Vector3f loc) {
        bodyControl.warp(loc);
    }

    public boolean isOnGround() {
        return bodyControl.isOnGround();
    }

    public boolean isDucked() {
        return bodyControl.isDucked();
    }

    public float getMass() {
        return mass;
    }

    @Override
    public BoundingVolume getWorldBound() {
        return body.getWorldBound();
    }

    @Override
    public CullHint getCullHint() {
        return body.getCullHint(); //To change body of generated methods, choose Tools | Templates.
    }

    public void kill() {
        physicsSpace.remove(body);
        rootNode.detachChild(body);
    }
}
