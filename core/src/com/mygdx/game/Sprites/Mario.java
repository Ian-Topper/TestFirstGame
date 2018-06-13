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

    public enum State { FALLING, JUMPING, STANDING, RUNNING };
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private TextureRegion marioStand;
    private Animation<TextureRegion> marioRun;
    private Animation<TextureRegion> marioJump;
    private float stateTimer;
    private boolean runningRight;

    public Mario(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("little_mario"));
        this.world = world;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        for(int i =1; i < 4; i++)
            frames.add(new TextureRegion(getTexture(), i * 16, 0, 16, 16));
        marioRun = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        for (int i = 4; i< 6; i++)
            frames.add(new TextureRegion(getTexture(), i * 16, 0, 16, 16));
        marioJump = new Animation<TextureRegion>(0.1f, frames);


        defineMario();
        marioStand = new TextureRegion(getTexture(),0,0,16,16);
        setBounds(0,0,16 / MyGdxGame.PPM, 16 / MyGdxGame.PPM);
        setRegion(marioStand);
    }

    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt){
        currentState = getState();

        TextureRegion region;
        switch(currentState){
            case JUMPING:
                region = marioJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = marioRun.getKeyFrame(stateTimer, true);
            case FALLING:
            case STANDING:
                default:
                    region = marioStand;
                    break;
        }
        //if mario is running left and the texture isnt facing left... flip it.
        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        }

        //if mario is running right and the texture isnt facing right... flip it.
        else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }

        //if the current state is the same as the previous state increase the state timer.
        //otherwise the state has changed and we need to reset timer.
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        //update previous state
        previousState = currentState;
        //return our final adjusted frame
        return region;
        }
    public State getState(){
        if(b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.JUMPING;
            //if negative in Y-Axis mario is falling
        else if(b2body.getLinearVelocity().x > 0)
            return State.RUNNING;
        else if(b2body.getLinearVelocity().y < 0)
            return State.FALLING;
            //if mario is positive or negative in the X axis he is running

            //if none of these return then he must be standing
        else
            return State.STANDING;
    }

    public void defineMario(){

        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / MyGdxGame.PPM , 32 / MyGdxGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / MyGdxGame.PPM);

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

