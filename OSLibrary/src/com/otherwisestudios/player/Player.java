/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.otherwisestudios.player;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.input.InputManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author Asus
 */
public class Player extends Node {

    private String username;
    private int points = 0;
    private CharacterControl playerPhy;
    private Node player;
    private Spatial tshirt, pants, cap, shoes, accesories;
    private PhysicsSpace physicsSpace;


    public Player(AssetManager assetManager, Node node) {
        points = 0;
        username = "defaultPlayer";
        player = new Node("player");

        playerPhy = null;
        float w = 1f;
        float numeroMagico = 2f;
        CapsuleCollisionShape actorCCS = new CapsuleCollisionShape(w, numeroMagico * w);
        playerPhy = new CharacterControl(actorCCS, 2f);
        playerPhy.setJumpSpeed(20);
        playerPhy.setFallSpeed(40);
        playerPhy.setGravity(40);
        
        player.attachChild(assetManager.loadModel("Models/Maya/nino.mesh.xml"));
        player.scale(0.2f);
//        player.move(0, -5, 0);
//        player.rotate((float)Math.toRadians(180), 0, 0);
        player.addControl(playerPhy);
        node.attachChild(player);
    }

    public void setName(String name) {
        this.username = name;
    }

    public void setLocation(Vector3f location) {
        playerPhy.setPhysicsLocation(location);
    }

    public void setPhysicsSpace(PhysicsSpace p) {
        physicsSpace = p;
//        playerPhy.setPhysicsSpace(p);
        p.add(player);
    }

    public void addPoint() {
        points++;
    }

    public void update() {
    }

    public String getName() {
        return username;
    }

    public int getPoints() {
        return points;
    }

    public void jump() {
        playerPhy.jump();
    }

    public Vector3f getPhysicsLocation() {
        return playerPhy.getPhysicsLocation();
    }
    
    public void setPhysicsLocation(Vector3f loc){
        playerPhy.setPhysicsLocation(loc);
    }

    public void setWalkDirection(Vector3f direction) {
        playerPhy.setWalkDirection(direction);
    }
    
    public Node getPlayer(){
        return player;
    }
    
    public void rotateVector(Vector3f viewDirection){
        playerPhy.setViewDirection(viewDirection);
    }

    public CharacterControl getPlayerPhy() {
        return playerPhy;
    }
    
    
    
}
