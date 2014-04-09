/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.otherwisestudios.physics;

import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.joints.ConeJoint;
import com.jme3.bullet.joints.HingeJoint;
import com.jme3.bullet.joints.PhysicsJoint;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.util.TangentBinormalGenerator;
import com.otherwisestudios.material.OSMaterial;

/**
 *
 * @author Asus
 */
public class OSPhysics {

    PhysicsSpace physicsSpace;
    AssetManager assetManager;

    public OSPhysics(PhysicsSpace physicsSpace, AssetManager assetManager) {
        this.physicsSpace = physicsSpace;
        this.assetManager = assetManager;
    }

    public static Spatial createRigidBody(Spatial sp, PhysicsSpace physicsSpace, float mass, boolean kinematic) {
        RigidBodyControl r = new RigidBodyControl(mass);
        sp.addControl(r);
        physicsSpace.add(sp);
        if (kinematic) {
            r.setKinematic(kinematic);
        }
        return sp;
    }

    public static Spatial createRigidBody(Spatial sp, PhysicsSpace physicsSpace, float mass, Vector3f location, boolean kinematic) {
        RigidBodyControl r = new RigidBodyControl(mass);
        sp.addControl(r);
        physicsSpace.add(sp);
        if (kinematic) {
            r.setKinematic(kinematic);
            sp.setLocalTranslation(location);
        } else {
            r.setPhysicsLocation(location);
        }
        return sp;
    }

    public static Spatial createBox(Vector3f scale, Vector3f location, AssetManager assetManager) {
        Box b = new Box(scale.x, scale.y, scale.z);
        Geometry geom = new Geometry("Box", b);
        geom.setMaterial(OSMaterial.testMaterial(assetManager));
        geom.move(location);
        return geom;
    }

    public static Spatial createBox(Vector3f scale, Vector3f location) {
        Box b = new Box(scale.x, scale.y, scale.z);
        Geometry geom = new Geometry("Box", b);
        geom.move(location);
        return geom;
    }

    public static PhysicsJoint conejoin(Spatial A, Spatial B, Vector3f connectionPoint) {
        Vector3f pivotA = A.worldToLocal(connectionPoint, new Vector3f());
        Vector3f pivotB = B.worldToLocal(connectionPoint, new Vector3f());
        ConeJoint joint = new ConeJoint(A.getControl(RigidBodyControl.class), B.getControl(RigidBodyControl.class), pivotA, pivotB);
        float m = (float) Math.toRadians(90);
        joint.setLimit(1, 1, 0);
        return joint;
    }

    public static PhysicsJoint hingeJoin(Spatial A, Spatial B, Vector3f connectionPoint){
        /*
         * Se cogen los pivotes del frame y de la puerta
         */
        Vector3f pivotA = A.worldToLocal(connectionPoint, new Vector3f());
        Vector3f pivotB = B.worldToLocal(connectionPoint, new Vector3f());
        /*
         * Se crea un Hinge Joint
         * Éste recibe dos RigidBodies, los 2 pivotes, y los ejes sobre
         * los cuales debe rotar cada cuerpo.
         * En este caso sólo rota la puerta, y ambos ejes deben
         * ser el ejeY, puesto que el eje de rotación es el eje Y.
         */
        HingeJoint joint = new HingeJoint(A.getControl(RigidBodyControl.class), B.getControl(RigidBodyControl.class), pivotA, pivotB, Vector3f.UNIT_Y, Vector3f.UNIT_Y);
        /*
         * Ahora, se crean los limites de la puerta,
         * Esto permite que la puerta no se vaya más allá de
         * un ángulo determinado, que debe estar en radianes.
         */
        float m = (float) Math.toRadians(90);
        joint.setLimit(-m, m);
        /*
         * Luego se retorna el Joint, por si se quiere
         * modificar algo en donde se llama a este método.
         * No es necesario que se reciba la información devuelta.
         */
        return joint;
    }

    public static Spatial createDoor(Vector3f dimension, Vector3f location, BulletAppState bulletAppState, AssetManager assetManager, Node node) {
        /*SE CREAN TANTO LA PUERTA COMO EL FRAME EN BASE A LAS DIMENSIONES
         Y A LA POSICION QUE RECIBE EL METODO
         * Este metodo debe recibir el assetmanager
         * para poder crear los materiales
         */
        
        Spatial door = createBox(dimension, location, assetManager);
        Node frame = new Node("frame");
        Spatial frameUp = createBox(new Vector3f(dimension.x, dimension.y * 0.1f, dimension.z),
                new Vector3f(location.x, location.y+getLongitude(door, 'y')/2+0.5f, location.z), assetManager);
        Spatial frameRight = createBox(new Vector3f(dimension.x * 0.1f, dimension.y, dimension.z),
                new Vector3f(getLongitude(door, 'x')/2+0.5f+location.x, location.y, location.z), assetManager);
        Spatial frameLeft = createBox(new Vector3f(dimension.x * 0.1f, dimension.y, dimension.z),
                new Vector3f(door.getLocalTranslation().x-0.5f-getLongitude(door, 'x')/2, location.y, location.z), assetManager);
        frame.attachChild(frameUp);
        frame.attachChild(frameLeft);
        frame.attachChild(frameRight);
        
        /*Aqui se crean los rigidbodys, masa 0 (infinita) para el marco
         * y alguna masa para la puerta
         */
        OSPhysics.createRigidBody(door, bulletAppState.getPhysicsSpace(), 200, false);
        OSPhysics.createRigidBody(frame, bulletAppState.getPhysicsSpace(), 0, false);
        
        
        /*
         *Aqui se crea el Joint entre la puerta y el frame 
         *CTRL + Click para ver funcionamiento.
         */
        OSPhysics.hingeJoin(frame, door, new Vector3f(getLongitude(frame,'x')/2+location.x, location.y, location.z));
        
        /*
         * Aqui se crea un nodo que va a contener tanto
         * al frame como a la puerta, que es distinto a 
         * añadir solo el frame o la puerta al physicsspace,
         * porque este nodo almacena el Joint.
         * Es decir, si se añadiera solo el frame y la puerta
         * al physicsspace no tendriamos el Joint funcionando,
         * es necesario crear un nodo que los contenga a los dos.
         */
        Node laPuerta = new Node("laPuerta");
        laPuerta.attachChild(frame);
        laPuerta.attachChild(door);
        
        /*
         * Aqui se añaden las geometrias al rootNode
         * o nodo en el que van a estar las puertas.
         */
        node.attachChild(laPuerta);
        /*
         * Aqui se añade con addAll() todas las fisicas del nodo
         * que comprenden los rigidbodies y el joint.
         */
        bulletAppState.getPhysicsSpace().addAll(laPuerta);
        
        /*
         * se devuelve el nodo de toda la puerta
         * esto solo por si se quiere mover algo más
         * en donde se llame a este método.
         */
        return laPuerta;
    }

    public static float getLongitude(Spatial sp, char axis) {
        BoundingBox b = (BoundingBox) sp.getWorldBound();
        float logintude = 0;
        switch (axis) {
            case 'x':
                logintude = b.getXExtent() * 2;
                break;
            case 'y':
                logintude = b.getYExtent() * 2;
                break;
            case 'z':
                logintude = b.getZExtent() * 2;
                break;
            default:
                getLongitude(sp, 'x');
        }
        return logintude;
    }

    public static Node createTestLimb(float width, float height, Vector3f location, boolean rotate) {
        int axis = rotate ? PhysicsSpace.AXIS_X : PhysicsSpace.AXIS_Y;
        CapsuleCollisionShape shape = new CapsuleCollisionShape(width, height, axis);
        Node node = new Node("Limb");
        RigidBodyControl rigidBodyControl = new RigidBodyControl(shape, 1);
        node.setLocalTranslation(location);
        node.addControl(rigidBodyControl);
        return node;
    }

    public static void makeCannonBall(BulletAppState bulletAppState, AssetManager assetManager, Camera cam, Node rootNode) {
        /**
         * Create a cannon ball geometry and attach to scene graph.
         */
        Sphere sphere = new Sphere(8, 8, 2, true, false);
        Geometry ball_geo = new Geometry("cannon ball", sphere);
        ball_geo.setMaterial(OSMaterial.testMaterial(assetManager));
        rootNode.attachChild(ball_geo);
        TangentBinormalGenerator.generate(ball_geo);
        ball_geo.setShadowMode(RenderQueue.ShadowMode.Cast);
        /**
         * Position the cannon ball
         */
        ball_geo.setLocalTranslation(cam.getLocation().add(new Vector3f(0, 10, 0)));
        /**
         * Make the ball physcial with a mass > 0.0f
         */
        RigidBodyControl ball_phy = new RigidBodyControl(10f);//Las masas estan en kg

        /**
         * Add physical ball to physics space.
         */
        ball_geo.addControl(ball_phy);
        bulletAppState.getPhysicsSpace().add(ball_phy);

        /**
         * Accelerate the physcial ball to shoot it.
         */
        ball_phy.setLinearVelocity(cam.getDirection().normalize().mult(50));

    }

    public static void makeCannonBall(float speed, float radius, BulletAppState bulletAppState, AssetManager assetManager, Camera cam, Node rootNode) {
        /**
         * Create a cannon ball geometry and attach to scene graph.
         */
        Sphere sphere = new Sphere(8, 8, radius, true, false);
        Geometry ball_geo = new Geometry("cannon ball", sphere);
        ball_geo.setMaterial(OSMaterial.testMaterial(assetManager));
        rootNode.attachChild(ball_geo);
        TangentBinormalGenerator.generate(ball_geo);
        ball_geo.setShadowMode(RenderQueue.ShadowMode.Cast);
        /**
         * Position the cannon ball
         */
        ball_geo.setLocalTranslation(cam.getLocation().add(cam.getDirection().normalize().mult(2)));
        /**
         * Make the ball physcial with a mass > 0.0f
         */
        RigidBodyControl ball_phy = new RigidBodyControl(2);//Las masas estan en kg

        /**
         * Add physical ball to physics space.
         */
        ball_geo.addControl(ball_phy);
        bulletAppState.getPhysicsSpace().add(ball_phy);

        /**
         * Accelerate the physcial ball to shoot it.
         */
        ball_phy.setLinearVelocity(cam.getDirection().normalize().mult(speed));

    }
}
