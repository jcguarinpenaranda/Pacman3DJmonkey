/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.otherwisestudios.pacman.util;

import com.jme3.math.ColorRGBA;

/**
 *
 * @author Asus
 */
public class ColorPicker {

    public static ColorRGBA colorPick(String color) {
        ColorRGBA c = ColorRGBA.White;
        if (color.equals("gray")) {
            c = ColorRGBA.Gray;
        } else if (color.equals("orange")) {
            c = ColorRGBA.Orange;
        } else if (color.equals("blue")) {
            c = ColorRGBA.Blue;
        } else if (color.equals("lightBlue")) {
            c = new ColorRGBA(50, 101, 255, 1f);
        } else if (color.equals("red")) {
            c = ColorRGBA.Red;
        } else if (color.equals("white")) {
            c = ColorRGBA.White;
        } else if (color.equals("black")) {
            c = ColorRGBA.Black;
        } else if (color.equals("green")) {
            c = ColorRGBA.Green;
        } else if (color.equals("yellow")) {
            c = ColorRGBA.Yellow;
        } else if (color.equals("yellow")) {
            c = ColorRGBA.Brown;
        } else if(color.equals("random")){
            c= ColorRGBA.randomColor();
        }
        return c;
    }
}
