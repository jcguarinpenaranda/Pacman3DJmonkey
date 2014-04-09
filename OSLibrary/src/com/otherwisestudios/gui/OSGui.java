/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.otherwisestudios.gui;

import com.jme3.bullet.control.VehicleControl;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;

/**
 *
 * @author Asus
 */
public class OSGui {

    public static BitmapText crearTexto(String text, BitmapFont guiFont, Node guiNode, AppSettings settings) {
        BitmapText bMText = new BitmapText(guiFont, false);
//        bMText.setSize(guiFont.getCharSet().getRenderedSize());
        if (settings.getWidth() > 1200) {
            bMText.setSize(24);      // font size
        } else {
            bMText.setSize(16);      // font size
        }
        bMText.setColor(ColorRGBA.White);                             // font color
        bMText.setText(text);             // the text
        bMText.setLocalTranslation(10, bMText.getLineHeight(), 0); // position
        guiNode.attachChild(bMText);
        return bMText;
    }
}
