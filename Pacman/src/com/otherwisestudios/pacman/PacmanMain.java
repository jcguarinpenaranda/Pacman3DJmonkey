package com.otherwisestudios.pacman;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.font.BitmapText;
import com.jme3.input.ChaseCamera;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.shadow.PssmShadowRenderer;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * test
 *
 * @author normenhansen
 */
public class PacmanMain extends SimpleApplication {

    Node pisoNode, todoElMundo, personajesNode;
    Node bolitasNode, murosNode, portalesNode, fantasmasNode, poderesNode;
    LinkedList<Vector3f> listaDePuntosDondeCambiaElSentidoElFantasma;
    LinkedList<Spatial> crashList;
    LinkedList<Personaje> listaPersonajes;
    LinkedList<Spatial> listaMuros;
    Node monedasPoderNode;
    private ChaseCamera chaseCam;
    Geometry geomPiso;
    BulletAppState bulletAppState = new BulletAppState();

    public static void main(String[] args) {
        PacmanMain app = new PacmanMain();
        app.start();
    }
    private PersonajePacman personajePacman, personajePacman2;

    @Override
    public void simpleInitApp() {
        stateManager.attach(bulletAppState);
        setDisplayStatView(false);
        setDisplayFps(false);
        inicializacion();
        ponerInterseccionesParaFantasma();
        cargarModeloEscenario();
        crearMonedasPoder();
        crearChaseCam();
        crearControles();
        crearLuces();
        crearPersonajes();
        crearHudTexts();
        viewPort.setBackgroundColor(ColorRGBA.Orange);
    }
    
    private Node noCollidingNode, nodoFantasmas, nodoPacmans;

    private void inicializacion() {

        listaDePuntosDondeCambiaElSentidoElFantasma = new LinkedList<Vector3f>();

        monedasPoderNode = new Node("MonedasPoder");
        rootNode.attachChild(monedasPoderNode);

        portalesNode = new Node("Portales");
        rootNode.attachChild(portalesNode);

        nodoPacmans = new Node("Pacmans");
        rootNode.attachChild(nodoPacmans);

        nodoFantasmas = new Node("Fantasmas");
        rootNode.attachChild(nodoFantasmas);

        murosNode = new Node("muros");
        rootNode.attachChild(murosNode);

        noCollidingNode = new Node("NoColliding");
        rootNode.attachChild(noCollidingNode);

        listaPersonajes = new LinkedList<Personaje>();

    }

    private void crearPersonajes() {
        personajePacman = new PersonajePacman(listaPersonajes, rootNode, assetManager, "Pacman1", "yellow");
        personajePacman2 = new PersonajePacman(listaPersonajes, rootNode, assetManager, "Pacman1", "orange");
        personajePacman.move(-5, 0);
        personajePacman2.move(5, 0);

        for (int i = 1; i < 7; i++) {
            PersonajeFantasma fantasma = new PersonajeFantasma(listaPersonajes, rootNode, assetManager, listaDePuntosDondeCambiaElSentidoElFantasma, "1", "random");
            fantasma.move(8 * i, -20 + 2 * i);
            fantasma.go("up");
        }

    }
    private int totalPuntos = 0;

    private void crearMonedasPoder() {
        Material mat_default = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat_default.setColor("Color", ColorRGBA.White);
        for (int i = 1; i < 14; i++) {
            Spatial monedaPoder = assetManager.loadModel("Models/Moneda/MonedaEsquina/monedaPoder" + i + ".obj");
            monedaPoder.setMaterial(mat_default);
            totalPuntos++;
            monedasPoderNode.attachChild(monedaPoder);
        }
    }

    private void crearChaseCam() {
        chaseCam = new ChaseCamera(cam, rootNode, inputManager);
        chaseCam.setDefaultDistance(300);
        chaseCam.setDefaultHorizontalRotation((float) Math.toRadians(90));
        chaseCam.setDefaultVerticalRotation((float) Math.toRadians(60));
        chaseCam.setLookAtOffset(Vector3f.ZERO);
        chaseCam.setZoomSensitivity(20);
        chaseCam.setMaxDistance(300);
        chaseCam.setMinDistance(80);
        chaseCam.setTrailingRotationInertia(0.5f);
        chaseCam.setSmoothMotion(true);
    }

    @Override
    public void simpleUpdate(float tpf) {
        try {
            Thread.sleep(1000 / 60);
        } catch (InterruptedException ex) {
            Logger.getLogger(PacmanMain.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (Personaje personaje : listaPersonajes) {
            if (personaje.isVisible()) {
                personaje.update();
            }
        }

        crearHudTexts();

    }

    private void crearControles() {
        inputManager.addMapping("moveForward", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addListener(actionListener, new String[]{"moveForward"});

        inputManager.addMapping("moveForward2", new KeyTrigger(KeyInput.KEY_UP));
        inputManager.addListener(actionListener, new String[]{"moveForward2"});

        inputManager.addMapping("moveBackward", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addListener(actionListener, new String[]{"moveBackward"});

        inputManager.addMapping("moveBackward2", new KeyTrigger(KeyInput.KEY_DOWN));
        inputManager.addListener(actionListener, new String[]{"moveBackward2"});

        inputManager.addMapping("moveRight", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addListener(actionListener, new String[]{"moveRight"});

        inputManager.addMapping("moveRight2", new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addListener(actionListener, new String[]{"moveRight2"});

        inputManager.addMapping("moveLeft", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addListener(actionListener, new String[]{"moveLeft"});

        inputManager.addMapping("moveLeft2", new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addListener(actionListener, new String[]{"moveLeft2"});
    }
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean isPressed, float tpf) {
            if (name.equals("moveForward")) {
                personajePacman.go("up");
            } else if (name.equals("moveBackward")) {
                personajePacman.go("down");
            }
            if (name.equals("moveRight")) {
                personajePacman.go("right");
            } else if (name.equals("moveLeft")) {
                personajePacman.go("left");
            }

            if (name.equals("moveForward2")) {
                personajePacman2.go("up");
            } else if (name.equals("moveBackward2")) {
                personajePacman2.go("down");
            }
            if (name.equals("moveRight2")) {
                personajePacman2.go("right");
            } else if (name.equals("moveLeft2")) {
                personajePacman2.go("left");
            }
        }
    };

    private void crearPiso() {
        /**
         * Load a model. Uses model and texture from jme3-test-data library!
         */
        Spatial pisoModelo = assetManager.loadModel("Models/Mundo/pisoPequeno.obj");

        Material matPiso = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        matPiso.setColor("Ambient", ColorRGBA.DarkGray);
        matPiso.setColor("Diffuse", ColorRGBA.DarkGray);
        matPiso.setColor("Specular", ColorRGBA.White);
        matPiso.setBoolean("UseMaterialColors", true);
        matPiso.setFloat("Shininess", 128);
        pisoModelo.setMaterial(matPiso);

        Spatial pisoGrande = assetManager.loadModel("Models/Mundo/pisoGrande.obj");
        Material matPisoGrande = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        matPisoGrande.setColor("Ambient", ColorRGBA.Orange);
        matPisoGrande.setColor("Diffuse", ColorRGBA.Orange);
        matPisoGrande.setColor("Specular", ColorRGBA.White);
        matPisoGrande.setBoolean("UseMaterialColors", true);
        matPisoGrande.setFloat("Shininess", 50);
        pisoGrande.setMaterial(matPisoGrande);

        noCollidingNode.attachChild(pisoGrande);
        noCollidingNode.attachChild(pisoModelo);

    }

    private void crearLuces() {

        rootNode.setShadowMode(ShadowMode.CastAndReceive);
        rootNode.setCullHint(Spatial.CullHint.Dynamic);


        Vector3f lightDirection = (new Vector3f(0.5f, -0.7f, 0.5f)).normalizeLocal();

        /**
         * A white, directional light source
         */
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(lightDirection);
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);

        /**
         * A white, directional light source
         */
        DirectionalLight sun2 = new DirectionalLight();
        sun2.setDirection((new Vector3f(-0.5f, -0.8f, -0.5f)).normalizeLocal());
        sun2.setColor(ColorRGBA.White);
        rootNode.addLight(sun2);

        /**
         * Advanced shadows for uneven surfaces
         */
        PssmShadowRenderer pssm = new PssmShadowRenderer(assetManager, 2048, 3);
        pssm.setDirection(lightDirection);
        pssm.setShadowIntensity(0.3f);
        viewPort.addProcessor(pssm);

    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    private void cargarModeloEscenario() {

        crearPiso();
        crarTorres();
        crearPortales();

        /**
         * Load a model. Uses model and texture from jme3-test-data library!
         */
        Material matPared = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        matPared.setColor("Ambient", ColorRGBA.Blue);
        matPared.setColor("Diffuse", ColorRGBA.Cyan);
        matPared.setColor("Specular", ColorRGBA.White);
        matPared.setBoolean("UseMaterialColors", true);
        matPared.setFloat("Shininess", 128);
        for (int i = 1; i < 24; i++) {
            Spatial pared = assetManager.loadModel("Models/Laberinto/" + i + ".obj");
            pared.setMaterial(matPared);
            murosNode.attachChild(pared);
        }

    }

    private void crearHudTexts() {
        /**
         * Pacman v3D. Juan Camilo Guarin P.
         */
        guiNode.detachAllChildren();
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText helloText = new BitmapText(guiFont, false);
        helloText.setSize(guiFont.getCharSet().getRenderedSize());
        helloText.setText("Pacman v3D. Juan Camilo Guarin P.");
        helloText.setLocalTranslation(300, helloText.getLineHeight(), 0);
        guiNode.attachChild(helloText);

        /**
         * Puntos Pacman
         */
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText puntosPacmanAmarillo = new BitmapText(guiFont, false);
        puntosPacmanAmarillo.setSize(guiFont.getCharSet().getRenderedSize());

        puntosPacmanAmarillo.setLocalTranslation(0, 2 * helloText.getLineHeight(), 0);
        puntosPacmanAmarillo.setColor(ColorRGBA.Black);


        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText puntosPacmanNaranja = new BitmapText(guiFont, false);
        puntosPacmanNaranja.setSize(guiFont.getCharSet().getRenderedSize());

        puntosPacmanNaranja.setColor(ColorRGBA.Black);
        puntosPacmanNaranja.setLocalTranslation(0, 3 * helloText.getLineHeight(), 0);

        if (personajePacman.getPuntos() > totalPuntos / 2 || !personajePacman2.isVisible()) {
            puntosPacmanAmarillo.setText("Yellow Pacman: Gano!");
        } else {
            puntosPacmanAmarillo.setText("Yellow Pacman: " + personajePacman.getPuntos() + "");
        }

        if (personajePacman2.getPuntos() > totalPuntos / 2 || !personajePacman.isVisible()) {
            puntosPacmanAmarillo.setText("Orange Pacman: Gano!");
        } else {
            puntosPacmanNaranja.setText("Orange Pacman: " + personajePacman2.getPuntos() + "");
        }
        guiNode.attachChild(puntosPacmanNaranja);
        guiNode.attachChild(puntosPacmanAmarillo);
    }

    private void crarTorres() {
        Material torreMaterial = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        torreMaterial.setColor("Ambient", ColorRGBA.Blue);
        torreMaterial.setColor("Diffuse", ColorRGBA.Blue);
        torreMaterial.setColor("Specular", ColorRGBA.White);
        torreMaterial.setBoolean("UseMaterialColors", true);
        torreMaterial.setFloat("Shininess", 120);
        for (int i = 1; i < 5; i++) {
            Spatial torre = assetManager.loadModel("Models/Torres/torre" + i + ".obj");
            torre.setMaterial(torreMaterial);
            noCollidingNode.attachChild(torre);
        }

    }

    private void crearPortales() {
        Portal portalDer = new Portal(assetManager, portalesNode, 1);
        Portal portalIzq = new Portal(assetManager, portalesNode, 2);
    }

    private void ponerInterseccionesParaFantasma() {
        Vector3f punto1 = new Vector3f(-57, 0, -23);
        listaDePuntosDondeCambiaElSentidoElFantasma.add(punto1);

        Vector3f punto2 = new Vector3f(-45, 0, -38);
        listaDePuntosDondeCambiaElSentidoElFantasma.add(punto2);

        Vector3f punto3 = new Vector3f(-57, 0, -38);
        listaDePuntosDondeCambiaElSentidoElFantasma.add(punto3);

        Vector3f punto4 = new Vector3f(-45, 0, -85);
        listaDePuntosDondeCambiaElSentidoElFantasma.add(punto4);

        Vector3f punto5 = new Vector3f(42, 0, -23);
        listaDePuntosDondeCambiaElSentidoElFantasma.add(punto5);

        Vector3f punto6 = new Vector3f(42, 0, -85);
        listaDePuntosDondeCambiaElSentidoElFantasma.add(punto6);

        Vector3f punto7 = new Vector3f(42, 0, -38);
        listaDePuntosDondeCambiaElSentidoElFantasma.add(punto7);

        Material bMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        bMaterial.setColor("Color", ColorRGBA.White);

//        for (Vector3f punto : listaDePuntosDondeCambiaElSentidoElFantasma) {
//            Box b = new Box(2, 2, 2);
//            Geometry g = new Geometry("Box", b);
//            g.setMaterial(bMaterial);
//            g.setLocalTranslation(punto);
//            rootNode.attachChild(g);
//        }


    }
}
