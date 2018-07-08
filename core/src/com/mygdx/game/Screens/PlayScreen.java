package com.mygdx.game.Screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Scenes.Hud;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Sprites.Enemies.Enemy;
import com.mygdx.game.Sprites.Items.Item;
import com.mygdx.game.Sprites.Items.ItemDef;
import com.mygdx.game.Sprites.Items.Mushroom;
import com.mygdx.game.Sprites.Mario;
import com.mygdx.game.Tools.B2WorldCreator;
import com.mygdx.game.Tools.WorldContactListener;

import java.util.PriorityQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class PlayScreen implements Screen{
   private MyGdxGame game;
    private TextureAtlas atlas;
    private Hud hud;
    private OrthographicCamera gameCam;
    private Viewport gamePort;

    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private Mario player;

    //Box2d variables

    private World world;

    private Box2DDebugRenderer b2dr;
    public static Music music;
    private Array<Item> items;
    private LinkedBlockingQueue<ItemDef> itemsToSpawn;
    private B2WorldCreator creator;


    public PlayScreen(MyGdxGame game){
        atlas = new TextureAtlas("Hero_and_others.pack");
        this.game = game;
            //create cam used to follow mario through cam world
        gameCam = new OrthographicCamera();
            //aspect fit could use strectch or others
            //create a FitViewport to maintain virtual aspect ratio despite screen size
        gamePort = new FitViewport(MyGdxGame.V_WIDTH / MyGdxGame.PPM,  MyGdxGame.V_HEIGHT / MyGdxGame.PPM, gameCam);
            //create our game HUD for scores/timers/level info
        hud = new Hud(game.batch);
            //Load our map and setup our map renderer
        maploader = new TmxMapLoader();
        map = maploader.load("map1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1/MyGdxGame.PPM );
            //initially set our gamcam to be centered correctly at the start of of map
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
            //create our Box2D world, setting no gravity in X, -10 gravity in Y, and allow bodies to sleep
        world = new World(new Vector2(0, -10), true);
            //allows for debug lines of our box2d world.
        b2dr = new Box2DDebugRenderer();

       creator = new B2WorldCreator(this);

       player = new Mario( this);

       world.setContactListener(new WorldContactListener());

       music = MyGdxGame.manager.get("audio/music/mario_music.ogg", Music.class);
       music.setLooping(true);
       music.play();

       items = new Array<Item>();
        itemsToSpawn = new LinkedBlockingQueue<ItemDef>();
        }

       public void spawnItem(ItemDef idef){
           itemsToSpawn.add(idef);
    }
    public void handleSpawningItems(){
        if(!itemsToSpawn.isEmpty()){
        ItemDef idef = itemsToSpawn.poll();
        if(idef.type == Mushroom.class){
           items.add(new Mushroom(this, idef.position.x, idef.position.y));
          }
        }
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    @Override
    public void show() {

    }
    public void handleInput(float dt){
    if(Gdx.input.isKeyJustPressed(Input.Keys.UP))
        player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2)
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2)
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
/*        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            player.fire();*/

    }




    public void update(float dt) {
        handleInput(dt);
        handleSpawningItems();
        world.step(1/60f, 6, 2);
        player.update(dt);
        for(Enemy enemy : creator.getGoombas()) {
            enemy.update(dt);
            ///Unfreeze enemies when mario is near
            if(enemy.getX() < player.getX() + 224 / MyGdxGame.PPM)
                enemy.b2body.setActive(true);
        }

        for(Item item : items)
            item.update(dt);
        hud.update(dt);
        gameCam.position.x = player.b2body.getPosition().x;
        gameCam.update();
        renderer.setView(gameCam);
    }

    @Override
    public void render(float delta) {

        update(delta);

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        b2dr.render(world, gameCam.combined);

        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();

        ///Bring back Mario
        player.draw(game.batch);


        for(Enemy enemy : creator.getGoombas())
            enemy.draw(game.batch);
        for(Item item : items)
            item.draw(game.batch);
        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    public TiledMap getMap(){
        return map;
    }

    public World getWorld(){
        return world;
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
