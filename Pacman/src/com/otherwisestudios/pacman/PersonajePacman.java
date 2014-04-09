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
import com.jme3.texture.Texture;
import com.jme3.util.TangentBinormalGenerator;
import com.otherwisestudios.pacman.util.ColorPicker;
import java.util.LinkedList;

/**
 *
 * @author Asus
 */
public class PersonajePacman extends Personaje {

    private Spatial cuerpoPacman, ojoDer, ojoIzq;
    private int puntos;

    public PersonajePacman(LinkedList<Personaje> listaPersonajes, Node rootNode, AssetManager assetManager, String name, String color) {
        super(listaPersonajes, rootNode, name);
        this.setSpeed(1);
        puntos=0;
        cuerpoPacman = assetManager.loadModel("Models/Pacman/PacmanSinOjos.obj");
        Material characterMaterial = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        characterMaterial.setColor("Ambient", ColorPicker.colorPick(color));
        characterMaterial.setColor("Diffuse", ColorPicker.colorPick(color));
        characterMaterial.setColor("Specular", ColorRGBA.White);
        characterMaterial.setBoolean("UseMaterialColors", true);
        characterMaterial.setFloat("Shininess", 50);
        TangentBinormalGenerator.generate(cuerpoPacman);
        cuerpoPacman.setMaterial(characterMaterial);

        ojoDer = assetManager.loadModel("Models/Pacman/OjoDer.obj");
        Material mat_default2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat_default2.setColor("Color", ColorRGBA.Black);
        ojoDer.setMaterial(mat_default2);

        ojoIzq = assetManager.loadModel("Models/Pacman/OjoIzq.obj");
        ojoIzq.setMaterial(mat_default2);

        super.addSpatial(ojoIzq);
        super.addSpatial(ojoDer);
        super.addSpatial(cuerpoPacman);
    }
    private float scale = 1f;

    @Override
    public void update() {
        if (this.isVisible()) {
            this.position = this.getCharacterNode().getLocalTranslation();
            move(this.getVelocity());
            this.colisionar();
        }
    }

    @Override
    public void colisionar() {
        this.colisionarConMonedas();
        this.colisionConMundo();
        //this.colisionConFantasmas();
        this.colisionConPortales();
    }

    public void colisionConMundo() {
        for (Spatial muro : murosNode.getChildren()) {
            if (this.getCharacterNode().getWorldBound().intersects(muro.getWorldBound())) {
                if (this.getDirection().equals(new Vector3f(0, 0, 1))) {
                    float z = this.position.z - 1.5f;
                    this.position = new Vector3f(position.x, position.y, z);
                    this.getCharacterNode().setLocalTranslation(position);
                    go("nowhere");
                }

                if (this.getDirection().equals(new Vector3f(0, 0, -1))) {
                    float z = this.position.z + 1.5f;
                    this.position = new Vector3f(position.x, position.y, z);
                    this.getCharacterNode().setLocalTranslation(position);
                    go("nowhere");
                }

                if (this.getDirection().equals(new Vector3f(1, 0, 0))) {
                    float x = this.position.x - 1.5f;
                    this.position = new Vector3f(x, position.y, position.z);
                    this.getCharacterNode().setLocalTranslation(position);
                    go("nowhere");
                }

                if (this.getDirection().equals(new Vector3f(-1, 0, 0))) {
                    float x = this.position.x + 1.5f;
                    this.position = new Vector3f(x, position.y, position.z);
                    this.getCharacterNode().setLocalTranslation(position);
                    go("nowhere");
                }
            }
        }
    }

    private void colisionConFantasmas() {
        for (Personaje personaje : listaPersonajes) {
            if (this.getCharacterNode().getWorldBound().intersects(personaje.getCharacterNode().getWorldBound())) {
                if (personaje instanceof PersonajeFantasma) {
                    this.setVisible(false);
                    this.getCharacterNode().setLocalScale(0);
                    nodoPacmans.detachChild(this.getCharacterNode());
                }
            }
        }
    }

    private void colisionarConMonedas() {
        for (Spatial moneda : monedasPoderNode.getChildren()) {
            if (this.getCharacterNode().getWorldBound().intersects(moneda.getWorldBound())) {
                monedasPoderNode.detachChild(moneda);
                puntos++;
            }
        }
    }

    public int getPuntos() {
        return puntos;
    }
    
    

   
}
