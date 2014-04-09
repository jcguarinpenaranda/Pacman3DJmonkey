/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.otherwisestudios.pacman;

import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingVolume;
import com.jme3.collision.Collidable;
import com.jme3.collision.CollisionResults;
import com.jme3.collision.UnsupportedCollisionException;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.SceneGraphVisitor;
import com.jme3.scene.Spatial;
import java.util.Queue;

/**
 *
 * @author Asus
 */
public class Portal{

    private Spatial puerta;

    public Portal(AssetManager assetManager, Node portalesNode, int i) {
        /**
         * Load a model. Uses model and texture from jme3-test-data library!
         */
        puerta = assetManager.loadModel("Models/Puertas/puerta"+i+".obj");
        Material mat_default = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat_default.setColor("Color", ColorRGBA.Red);
        
        puerta.setMaterial(mat_default);
        portalesNode.attachChild(puerta);
    }

    public Spatial getPuerta() {
        return puerta;
    }
    
}
