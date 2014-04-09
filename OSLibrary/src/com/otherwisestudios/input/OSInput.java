/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.otherwisestudios.input;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;

/**
 *
 * @author Asus
 */
public class OSInput {

    public static void keyEvent(String name, int key, InputManager inputManager, ActionListener ac) {
        inputManager.addMapping(name, new KeyTrigger(key));
        inputManager.addListener(ac, name);
    }

    public static void keyEvent(String name, int key, InputManager inputManager, AnalogListener ac) {
        inputManager.addMapping(name, new KeyTrigger(key));
        inputManager.addListener(ac, name);
    }

    public static void mouseEvent(String name, int key, InputManager inputManager, ActionListener ac) {
        inputManager.addMapping(name, new MouseButtonTrigger(key));
        inputManager.addListener(ac, name);
    }

    public static void mouseEvent(String name, int key, InputManager inputManager, AnalogListener ac) {
        inputManager.addMapping(name, new MouseButtonTrigger(key));
        inputManager.addListener(ac, name);
    }

    public static void mouseAxisEvent(String name, int key, InputManager inputManager, ActionListener ac) {
        inputManager.addMapping(name, new MouseAxisTrigger(key,true));
        inputManager.addListener(ac, name);
    }

    public static void mouseAxisEvent(String name, int key, InputManager inputManager, AnalogListener ac) {
        inputManager.addMapping(name, new MouseAxisTrigger(key,true));
        inputManager.addListener(ac, name);
    }
}
