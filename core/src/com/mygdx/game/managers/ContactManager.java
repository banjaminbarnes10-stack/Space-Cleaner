package com.mygdx.game.managers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.components.BulletObject;
import com.mygdx.game.components.GameObject;
import com.mygdx.game.components.ShipObject;
import com.mygdx.game.components.TrashObject;

public class ContactManager implements ContactListener {
    private final MyGdxGame parent;

    public ContactManager(MyGdxGame parent) {
        this.parent = parent;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        Object dataA = fixtureA.getBody().getUserData();
        Object dataB = fixtureB.getBody().getUserData();

        if (!(dataA instanceof GameObject) || !(dataB instanceof GameObject)) {
            return;
        }

        GameObject objectA = (GameObject) dataA;
        GameObject objectB = (GameObject) dataB;
        int pair = objectA.getcBits() | objectB.getcBits();

        if (pair == (GameSettings.TRASH_BIT | GameSettings.BULLET_BIT)) {
            TrashObject trash = objectA instanceof TrashObject ? (TrashObject) objectA : (TrashObject) objectB;
            BulletObject bullet = objectA instanceof BulletObject ? (BulletObject) objectA : (BulletObject) objectB;
            trash.hitByBullet();
            bullet.hit();
        } else if (pair == (GameSettings.TRASH_BIT | GameSettings.SHIP_BIT)) {
            TrashObject trash = objectA instanceof TrashObject ? (TrashObject) objectA : (TrashObject) objectB;
            ShipObject ship = objectA instanceof ShipObject ? (ShipObject) objectA : (ShipObject) objectB;
            trash.hitByShip();
            ship.hit();
        }
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
