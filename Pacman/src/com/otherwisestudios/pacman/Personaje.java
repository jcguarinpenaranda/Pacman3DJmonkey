/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.otherwisestudios.pacman;

import com.jme3.math.Matrix3f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.LinkedList;

/**
 *
 * @author Asus
 */
public abstract class Personaje {

    protected Vector3f direction, velocity, position, futurePosition;
    private float speed;
    protected Node characterNode;
    protected Node nodoFantasmas, nodoPacmans, murosNode, monedasPoderNode, portalesNode;
    private String name;
    protected LinkedList<Personaje> listaPersonajes;
    private boolean visible;
    private String lastDecision = "";
    private LinkedList<String> decisions;

    public Personaje(LinkedList<Personaje> listaPersonajes, Node rootNode, String name) {
        decisions = new LinkedList<String>();

        Node fantasmas = (Node) rootNode.getChild("Fantasmas");
        Node murosRootNode = (Node) rootNode.getChild("muros");
        Node pacmans = (Node) rootNode.getChild("Pacmans");
        Node monedasPoder = (Node) rootNode.getChild("MonedasPoder");
        Node portales= (Node) rootNode.getChild("Portales");
        
        this.portalesNode= portales;
        this.murosNode = murosRootNode;
        this.nodoFantasmas = fantasmas;
        this.nodoPacmans = pacmans;
        this.monedasPoderNode = monedasPoder;
        this.characterNode = new Node(name);

        pacmans.attachChild(characterNode);
        this.name = name;
        direction = new Vector3f(0, 0, 0);
        velocity = new Vector3f(0, 0, 0);
        position = new Vector3f(0, 0, 0);
        futurePosition = new Vector3f(Vector3f.ZERO);
        visible = true;

        this.listaPersonajes = listaPersonajes;
        listaPersonajes.add(this);
    }

    public void move(float x, float y, float z) {
        characterNode.move(x, y, z);
    }

    public void move(float x, float z) {
        characterNode.move(x, 0, z);
    }

    public void move(Vector3f vector) {
        characterNode.move(vector);
    }

    public void setShadowMode(ShadowMode shadowMode) {
        characterNode.setShadowMode(shadowMode);
    }

    public Vector3f getFuturePosition() {
        return futurePosition;
    }

    public Node getCharacterNode() {
        return characterNode;
    }

    public abstract void update();

    public abstract void colisionar();

    public void addGeometry(Geometry geom) {
        characterNode.attachChild(geom);
    }

    public void addSpatial(Spatial geom) {
        characterNode.attachChild(geom);
    }

    public Vector3f getVelocity() {
        return velocity;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
        this.velocity = this.velocity.normalize().mult(speed);
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Vector3f getDirection() {
        return direction;
    }

    public void setDirection(Vector3f direction) {
        this.direction = direction;
        this.velocity = direction.mult(speed);
    }

    public void setDirection(float x, float z) {
        this.direction = new Vector3f(x, 0, z);
        this.velocity = this.direction.mult(speed);
    }
    
    private float separationDistance = 1.2f;

    public void go(String direction) {
        
        lastDecision = direction;
        
        decisions.add(direction);
        
        if (direction.equals("up")) {
            characterNode.setLocalRotation(new Matrix3f(0, 0, 0, 0, 0, 0, 0, 0, -1));
            this.setDirection(0, -1);
        } else if (direction.equals("backwards")) {
            if (this.getDirection().equals(new Vector3f(0, 0, 1))) {
                characterNode.setLocalRotation(new Matrix3f(0, 0, 0, 0, 0, 0, 0, 0, -1));
            } else if (this.getDirection().equals(new Vector3f(0, 0, -1))) {
                characterNode.setLocalRotation(new Matrix3f(0, 0, 0, 0, 1, 0, 0, 0, 0));
            } else if (this.getDirection().equals(new Vector3f(1, 0, 0))) {
                characterNode.setLocalRotation(new Matrix3f(0, 0, 0, 0, 0, 0, 1, 0, 0));
            } else if (this.getDirection().equals(new Vector3f(-1, 0, 0))) {
                characterNode.setLocalRotation(new Matrix3f(0, 0, 1, 0, 0, 0, 0, 0, 0));
            }
            this.setDirection(this.getDirection().mult(-1));
        } else if (direction.equals("nowhere")) {
            this.setDirection(0, 0);
        } else if (direction.equals("down")) {
            characterNode.setLocalRotation(new Matrix3f(0, 0, 0, 0, 1, 0, 0, 0, 0));
            this.setDirection(0, 1);
        } else if (direction.equals("right")) {
            characterNode.setLocalRotation(new Matrix3f(0, 0, 1, 0, 0, 0, 0, 0, 0));
            this.setDirection(1, 0);
        } else if (direction.equals("left")) {
            characterNode.setLocalRotation(new Matrix3f(0, 0, 0, 0, 0, 0, 1, 0, 0));
            this.setDirection(-1, 0);
        } else if (direction.equals("random")) {
            int random = (int) (4 * Math.random());
            switch (random) {
                case 0:
                    if (this.getDirection().equals(new Vector3f(0, 0, 1))) {
                        float z = this.position.z - separationDistance;
                        this.position = new Vector3f(position.x, position.y, z);
                        this.getCharacterNode().setLocalTranslation(position);
                        go("up");
                    } else {
                        float z = this.position.z + separationDistance;
                        this.position = new Vector3f(position.x, position.y, z);
                        this.getCharacterNode().setLocalTranslation(position);
                        go("down");
                    }
                    break;
                case 1:
                    if (this.getDirection().equals(new Vector3f(0, 0, -1))) {
                        float z = this.position.z + separationDistance;
                        this.position = new Vector3f(position.x, position.y, z);
                        this.getCharacterNode().setLocalTranslation(position);
                        go("down");
                    } else {
                        float z = this.position.z - separationDistance;
                        this.position = new Vector3f(position.x, position.y, z);
                        this.getCharacterNode().setLocalTranslation(position);
                        go("up");
                    }
                    break;
                case 2:
                    if (this.getDirection().equals(new Vector3f(1, 0, 0))) {
                        float x = this.position.x - separationDistance;
                        this.position = new Vector3f(x, position.y, position.z);
                        this.getCharacterNode().setLocalTranslation(position);
                        go("left");
                    } else {
                        float x = this.position.x + separationDistance;
                        this.position = new Vector3f(x, position.y, position.z);
                        this.getCharacterNode().setLocalTranslation(position);
                        go("right");
                    }
                    break;
                case 3:
                    if (this.getDirection().equals(new Vector3f(-1, 0, 0))) {
                        float x = this.position.x + separationDistance;
                        this.position = new Vector3f(x, position.y, position.z);
                        this.getCharacterNode().setLocalTranslation(position);
                        go("right");
                    } else {
                        float x = this.position.x - separationDistance;
                        this.position = new Vector3f(x, position.y, position.z);
                        this.getCharacterNode().setLocalTranslation(position);
                        go("left");
                    }
                    break;
                case 4:
                    go("backwards");
            }
        }

    }
    
     public void colisionConPortales() {
        if (getCharacterNode().getWorldBound().intersects(portalesNode.getChild(0).getWorldBound())) {
            getCharacterNode().setLocalTranslation(-58, this.getCharacterNode().getLocalTranslation().y, this.getCharacterNode().getLocalTranslation().z);
        } else if (getCharacterNode().getWorldBound().intersects(portalesNode.getChild(1).getWorldBound())) {
            getCharacterNode().setLocalTranslation(58, this.getCharacterNode().getLocalTranslation().y, this.getCharacterNode().getLocalTranslation().z);
        }
    }
}
