package com.mygdx.game.Sprites.TileObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Mario;

public class Brick extends InteractiveTileObject {


    public Brick(PlayScreen screen, MapObject object){
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(MyGdxGame.BRICK_BIT);
    }

    @Override
    public void onHeadHit(Mario mario) {

        if (Mario.marioIsBig) {
            Gdx.app.log("Brick", "Collision");
            setCategoryFilter(MyGdxGame.DESTROYED_BIT);
            getCell().setTile(null);
            Hud.addScore(200);
            MyGdxGame.manager.get("audio/sounds/smb_breakblock.wav", Sound.class).play();
        }
        else
            MyGdxGame.manager.get("audio/sounds/smb_bump.wav", Sound.class).play();
    }
    public TiledMapTileLayer.Cell getCell(){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
        return layer.getCell((int)(body.getPosition().x * MyGdxGame.PPM / 16),
                (int)(body.getPosition().y * MyGdxGame.PPM /16));
    }
}
