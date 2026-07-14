package com.mygdx.game.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.GameSettings;

public class GameObject implements Disposable {
    private float x;
    private float y;
    int width;
    int height;
    World world;
    Body body;
    private final short cBits;
    private final Texture texture;
    private final Sprite sprite;

    GameObject(String texturePath, int x, int y, int width, int height, World world, short cBits) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.world = world;
        this.cBits = cBits;
        texture = new Texture(texturePath);
        sprite = new Sprite(texture);
        sprite.setSize(width, height);
        body = createBody(x, y, world);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
        if (body != null) {
            body.setTransform(x * GameSettings.SCALE, body.getPosition().y, body.getAngle());
        }
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
        if (body != null) {
            body.setTransform(body.getPosition().x, y * GameSettings.SCALE, body.getAngle());
        }
    }

    public void draw(SpriteBatch batch) {
        float left = getX() - width / 2f;
        float bottom = getY() - height / 2f;
        sprite.setOriginCenter();
        sprite.setPosition(left, bottom);
        sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
        sprite.draw(batch);
    }

    public void hit() {
    }

    private Body createBody(float x, float y, World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x * GameSettings.SCALE, y * GameSettings.SCALE);
        bodyDef.fixedRotation = cBits != GameSettings.TRASH_BIT;

        Body createdBody = world.createBody(bodyDef);
        createdBody.setSleepingAllowed(false);

        CircleShape shape = new CircleShape();
        float diameter = Math.min(width, height) * 0.82f;
        shape.setRadius(diameter * GameSettings.SCALE / 2f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 0f;
        fixtureDef.filter.categoryBits = cBits;

        if (cBits == GameSettings.TRASH_BIT) {
            fixtureDef.filter.maskBits = GameSettings.BULLET_BIT | GameSettings.SHIP_BIT;
        } else if (cBits == GameSettings.BULLET_BIT) {
            fixtureDef.filter.maskBits = GameSettings.TRASH_BIT;
        } else if (cBits == GameSettings.SHIP_BIT) {
            fixtureDef.filter.maskBits = GameSettings.TRASH_BIT;
        }

        createdBody.createFixture(fixtureDef);
        createdBody.setUserData(this);
        shape.dispose();
        return createdBody;
    }

    public void update() {
        x = body.getPosition().x / GameSettings.SCALE;
        y = body.getPosition().y / GameSettings.SCALE;
    }

    public Body getBody() {
        return body;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public short getcBits() {
        return cBits;
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
