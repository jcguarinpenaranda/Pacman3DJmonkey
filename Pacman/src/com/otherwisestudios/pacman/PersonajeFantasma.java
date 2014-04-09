/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.otherwisestudios.pacman;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.otherwisestudios.pacman.util.ColorPicker;
import java.util.LinkedList;

/**
 *
 * @author Asus
 */
public class PersonajeFantasma extends Personaje {

    private Spatial cuerpoFantasma, ojoDer, ojoIzq, pupDer, pupIzq;
    private LinkedList<Vector3f> listaDePuntosDondeCambiaElSentidoElFantasma;
    private int number;
    

    public int getNumber() {
        return number;
    }

    public PersonajeFantasma(LinkedList<Personaje> listaPersonajes, Node rootNode, AssetManager assetManager, LinkedList<Vector3f> listaDePuntosDondeCambiaElSentidoElFantasma, String name, String color) {
        super(listaPersonajes, rootNode, name);
        this.setSpeed(0.95f);

        Material characterMaterial = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        characterMaterial.setColor("Ambient", ColorPicker.colorPick(color));
        characterMaterial.setColor("Diffuse", ColorPicker.colorPick(color));
        characterMaterial.setColor("Specular", ColorRGBA.White);
        characterMaterial.setBoolean("UseMaterialColors", true);
        characterMaterial.setFloat("Shininess", 50);

        this.listaDePuntosDondeCambiaElSentidoElFantasma = listaDePuntosDondeCambiaElSentidoElFantasma;
        cuerpoFantasma = assetManager.loadModel("Models/Fantasma/fantasmaCuerpoObj.obj");
        cuerpoFantasma.setMaterial(characterMaterial);

        ojoDer = assetManager.loadModel("Models/Fantasma/fantasmaOjoDerObj.obj");
        Material mat_default2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat_default2.setColor("Color", ColorRGBA.White);
        ojoDer.setMaterial(mat_default2);

        ojoIzq = assetManager.loadModel("Models/Fantasma/fantasmaOjoIzqObj.obj");
        ojoIzq.setMaterial(mat_default2);

        pupDer = assetManager.loadModel("Models/Fantasma/fantasmaPupilaIzqObj.obj");
        Material mat_pupila = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat_pupila.setColor("Color", ColorRGBA.Black);
        pupDer.setMaterial(mat_pupila);

        pupIzq = assetManager.loadModel("Models/Fantasma/fantasmaPupilaDerObj.obj");
        pupIzq.setMaterial(mat_pupila);

        super.addSpatial(ojoDer);
        super.addSpatial(ojoIzq);
        super.addSpatial(cuerpoFantasma);
        super.addSpatial(pupDer);
        super.addSpatial(pupIzq);
    }

    @Override
    public void update() {
        this.position = this.getCharacterNode().getLocalTranslation();
        move(this.getVelocity());
        this.colisionar();
    }

    @Override
    public void colisionar() {
        changeDirection();
        colisionConMundo();
        colisionConPortales();
//        serAutonomo();
    }

    private void changeDirection() {
        float distance = 8f;
        if (this.getDirection().x > 0) {
            futurePosition = new Vector3f(position.x + distance, position.y, position.z);
        } else if (this.getDirection().x < 0) {
            futurePosition = new Vector3f(position.x - distance, position.y, position.z);
        } else if (this.getDirection().z > 0) {
            futurePosition = new Vector3f(position.x, position.y, position.z + distance);
        } else if (this.getDirection().z < 0) {
            futurePosition = new Vector3f(position.x, position.y, position.z - distance);
        }

        for (Spatial muro : murosNode.getChildren()) {
            if (muro.getWorldBound().intersects(futurePosition)) {
                go("random");
            }
        }
        
        colisionarConPuntosEnElMapa();

    }
    
    private float separationDistance = 3f;

    public void colisionConMundo() {
        for (Spatial muro : murosNode.getChildren()) {
            if (this.getCharacterNode().getWorldBound().intersects(muro.getWorldBound())) {
                if (this.getDirection().equals(new Vector3f(0, 0, 1))) {
                    float z = this.position.z - separationDistance;
                    this.position = new Vector3f(position.x, position.y, z);
                    this.getCharacterNode().setLocalTranslation(position);
                    go("backwards");
                }
                if (this.getDirection().equals(new Vector3f(0, 0, -1))) {
                    float z = this.position.z + separationDistance;
                    this.position = new Vector3f(position.x, position.y, z);
                    this.getCharacterNode().setLocalTranslation(position);
                    go("backwards");
                }
                if (this.getDirection().equals(new Vector3f(1, 0, 0))) {
                    float x = this.position.x - separationDistance;
                    this.position = new Vector3f(x, position.y, position.z);
                    this.getCharacterNode().setLocalTranslation(position);
                    go("backwards");
                }

                if (this.getDirection().equals(new Vector3f(-1, 0, 0))) {
                    float x = this.position.x + separationDistance;
                    this.position = new Vector3f(x, position.y, position.z);
                    this.getCharacterNode().setLocalTranslation(position);
                    go("backwards");
                }
            }
        }
    }

    private void colisionarConPuntosEnElMapa() {
        for (Vector3f punto : listaDePuntosDondeCambiaElSentidoElFantasma) {
            if (this.getCharacterNode().getWorldBound().intersects(punto)) {
                this.go("random");
            }
        }
    }
}
