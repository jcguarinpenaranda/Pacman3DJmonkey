/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.otherwisestudios.material;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.scene.Geometry;

/**
 *
 * @author Asus
 */
public class OSMaterial {

    public static Material testMaterial(AssetManager assetManager) {
        Material m = assetManager.loadMaterial("Materials/testMaterial.j3m");
        m.setColor("Diffuse", ColorRGBA.randomColor());
        m.setColor("Diffuse", ColorRGBA.White.mult(0.3f));
        m.setColor("Specular", ColorRGBA.White);
        m.setFloat("Shininess", 10);
        return m;
    }

    public static void scaleTexture(int x, int y, Geometry geom) {
        geom.getMesh().scaleTextureCoordinates(new Vector2f(x, y));
    }
}
