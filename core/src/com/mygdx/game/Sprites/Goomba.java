package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.graphics.g3d.particles.influencers.RegionInfluencer;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.PlayScreen;
public class Goomba extends Enemy{

    private float stateTime;

    private Array<TextureRegion> frames;
    public com.badlogic.gdx.graphics.g2d.Animation walkAnimation;


    public Goomba(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        for(int i=0; i<2; i++){
         frames.add(new TextureRegion(screen.getAtlas().findRegion("goomba"),i*16,0, 16, 16));
        walkAnimation = new com.badlogic.gdx.graphics.g2d.Animation<TextureRegion>(0.4f, frames);

        }
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / MyGdxGame.PPM , 32 / MyGdxGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / MyGdxGame.PPM);
        fdef.filter.categoryBits = MyGdxGame.ENEMY_BIT;
        fdef.filter.maskBits = MyGdxGame.COIN_BIT | MyGdxGame.BRICK_BIT | MyGdxGame.ENEMY_BIT | MyGdxGame.OBJECT_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef);

    }
}
