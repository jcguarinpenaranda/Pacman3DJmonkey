/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.otherwisestudios.light;

import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 *
 * @author Asus
 */
public class OSLight {

    public static void ambientLight(Node node) {
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White);
        node.addLight(ambient);
    }

    public static void dirLight(Vector3f lightDir, Node node) {
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection((lightDir).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        node.addLight(sun);
    }
}
