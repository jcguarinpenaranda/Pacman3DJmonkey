/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.otherwisestudios.sky;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;

/**
 *
 * @author Asus
 */
public class OSSky {

    public static void loadSky(int i, Node node, AssetManager assetManager) {
        Texture west, east, north, south, upTexture, downTexture;
        Spatial sky = new Node("sky");
        switch (i) {
            case 0:
                //cielo Alpine
                west = assetManager.loadTexture("Textures/Skies/Alpine/west.jpg");
                east = assetManager.loadTexture("Textures/Skies/Alpine/east.jpg");
                north = assetManager.loadTexture("Textures/Skies/Alpine/north.jpg");
                south = assetManager.loadTexture("Textures/Skies/Alpine/south.jpg");
                upTexture = assetManager.loadTexture("Textures/Skies/Alpine/up.jpg");
                downTexture = assetManager.loadTexture("Textures/Skies/Alpine/up.jpg");
                break;
            case 1:
                //cielo calm
                west = assetManager.loadTexture("Textures/Skies/desert_set/east.jpg");
                east = assetManager.loadTexture("Textures/Skies/desert_set/west.jpg");
                north = assetManager.loadTexture("Textures/Skies/desert_set/south.jpg");
                south = assetManager.loadTexture("Textures/Skies/desert_set/north.jpg");
                upTexture = assetManager.loadTexture("Textures/Skies/desert_set/up.jpg");
                downTexture = assetManager.loadTexture("Textures/Skies/desert_set/up.jpg");
                break;
            case 2:
                //cielo BlueCloud
                west = assetManager.loadTexture("Textures/Skies/BlueCloud/west.jpg");
                east = assetManager.loadTexture("Textures/Skies/BlueCloud/east.jpg");
                north = assetManager.loadTexture("Textures/Skies/BlueCloud/north.jpg");
                south = assetManager.loadTexture("Textures/Skies/BlueCloud/south.jpg");
                upTexture = assetManager.loadTexture("Textures/Skies/BlueCloud/up3.jpg");
                downTexture = assetManager.loadTexture("Textures/Skies/BlueCloud/down2.jpg");
                break;
            case 3:
                //cielo calm
                west = assetManager.loadTexture("Textures/Skies/alien/east.jpg");
                east = assetManager.loadTexture("Textures/Skies/alien/west.jpg");
                north = assetManager.loadTexture("Textures/Skies/alien/south.jpg");
                south = assetManager.loadTexture("Textures/Skies/alien/north.jpg");
                upTexture = assetManager.loadTexture("Textures/Skies/alien/up.jpg");
                downTexture = assetManager.loadTexture("Textures/Skies/alien/up.jpg");
                break;
            default:
                //default
                west = assetManager.loadTexture("Textures/Skies/Default/DefaultSky.jpg");
                east = assetManager.loadTexture("Textures/Skies/Default/DefaultSky.jpg");
                north = assetManager.loadTexture("Textures/Skies/Default/DefaultSky.jpg");
                south = assetManager.loadTexture("Textures/Skies/Default/DefaultSky.jpg");
                upTexture = assetManager.loadTexture("Textures/Skies/Default/DefaultSkyTop.jpg");
                downTexture = assetManager.loadTexture("Textures/Skies/Default/DefaultSkyTop.jpg");
        }
        
        if (west != null) {
            sky = SkyFactory.createSky(assetManager, west, east, north, south, upTexture, downTexture);
            sky.rotate(0, (float) Math.toRadians(180), 0);
        }

        node.attachChild(sky);
    }
}
