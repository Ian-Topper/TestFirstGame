package com.mygdx.game.Sprites;


import com.badlogic.gdx.audio.Music;

import com.badlogic.gdx.audio.Sound;

import com.badlogic.gdx.graphics.g2d.Animation;

import com.badlogic.gdx.graphics.g2d.Batch;

import com.badlogic.gdx.graphics.g2d.Sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.physics.box2d.Body;

import com.badlogic.gdx.physics.box2d.BodyDef;

import com.badlogic.gdx.physics.box2d.CircleShape;

import com.badlogic.gdx.physics.box2d.EdgeShape;

import com.badlogic.gdx.physics.box2d.Filter;

import com.badlogic.gdx.physics.box2d.Fixture;

import com.badlogic.gdx.physics.box2d.FixtureDef;

import com.badlogic.gdx.physics.box2d.World;

import com.badlogic.gdx.utils.Array;

import com.mygdx.game.MyGdxGame;

import com.mygdx.game.Screens.PlayScreen;

//import com.mygdx.game.Sprites.Other.FireBall;

//import com.mygdx.game.Sprites.Enemies.*;

public class Mario extends Sprite {


    public World world;
    public Body b2body;

    public Mario(World world){
        this.world = world;
        defineMario();
    }
    public void defineMario(){

        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / MyGdxGame.PPM , 32 / MyGdxGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5 / MyGdxGame.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);


      /*  fdef.filter.maskBits = MarioBros.GROUND_BIT |

                MarioBros.COIN_BIT |

                MarioBros.BRICK_BIT |

                MarioBros.ENEMY_BIT |

                MarioBros.OBJECT_BIT |

                MarioBros.ENEMY_HEAD_BIT |

                MarioBros.ITEM_BIT;



        fdef.shape = shape;

        b2body.createFixture(fdef).setUserData(this);



        EdgeShape head = new EdgeShape();

        head.set(new Vector2(-2 / MarioBros.PPM, 6 / MarioBros.PPM), new Vector2(2 / MarioBros.PPM, 6 / MarioBros.PPM));

        fdef.filter.categoryBits = MarioBros.MARIO_HEAD_BIT;

        fdef.shape = head;

        fdef.isSensor = true;



        b2body.createFixture(fdef).setUserData(this);*/

    }
}

