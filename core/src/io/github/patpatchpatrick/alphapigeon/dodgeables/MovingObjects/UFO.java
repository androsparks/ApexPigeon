package io.github.patpatchpatrick.alphapigeon.dodgeables.MovingObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import io.github.patpatchpatrick.alphapigeon.AlphaPigeon;
import io.github.patpatchpatrick.alphapigeon.dodgeables.MovingObjects.ufoEnergyBeam.UfoEnergyBeam;
import io.github.patpatchpatrick.alphapigeon.dodgeables.UFOs;
import io.github.patpatchpatrick.alphapigeon.levels.Gameplay;
import io.github.patpatchpatrick.alphapigeon.resources.BodyEditorLoader;
import io.github.patpatchpatrick.alphapigeon.resources.GameVariables;
import io.github.patpatchpatrick.alphapigeon.resources.SettingsManager;
import io.github.patpatchpatrick.alphapigeon.resources.Sounds;

public class UFO extends Dodgeable {
    private UFOs ufos;

    public final float WIDTH = 15f;
    public final float HEIGHT = WIDTH;
    public final float FORCE_X = -9.0f;
    public final float FORCE_Y = -5.0f;
    public float direction = 0f;
    public boolean energyBallIsSpawned = false;

    // Variables controlling how long ufo should stop in center of screen
    public boolean stopInCenterOfScreen = false;
    public boolean stopInRightCenterOfScreen = false;
    public boolean stopInTopRightCornerOfScreen = false;
    public boolean stopInBottomLeftCornerOfScreen = false;
    public float timeToHold = 0;

    //Sound associated with UFO
    private Sound flyingSound;

    public float spawnTime;
    //Energy balls associated with UFO
    public Array<EnergyBall> energyBalls = new Array<EnergyBall>();
    //Energy beams associated with UFO
    public Array<UfoEnergyBeam> energyBeams = new Array<UfoEnergyBeam>();

    public UFO(World gameWorld, AlphaPigeon game, OrthographicCamera camera) {
        super(gameWorld, game, camera);
        this.ufos = ufos;

        //spawn a new ufo
        BodyDef ufoBodyDef = new BodyDef();
        ufoBodyDef.type = BodyDef.BodyType.DynamicBody;

        //spawn ufo at random height
        ufoBodyDef.position.set(-100, -100);
        dodgeableBody = gameWorld.createBody(ufoBodyDef);
        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("json/Ufo.json"));
        FixtureDef ufoFixtureDef = new FixtureDef();
        ufoFixtureDef.density = 0.001f;
        ufoFixtureDef.friction = 0.5f;
        ufoFixtureDef.restitution = 0.3f;
        // set the ufo filter categories and masks for collisions
        ufoFixtureDef.filter.categoryBits = GameVariables.CATEGORY_UFO;
        ufoFixtureDef.filter.maskBits = GameVariables.MASK_UFO;
        loader.attachFixture(dodgeableBody, "Ufo", ufoFixtureDef, HEIGHT);


    }

    public void init(float direction) {

        //Set the direction which the energy beams associated with the UFO should fire
        this.direction = direction;

        dodgeableBody.setActive(true);
        dodgeableBody.setTransform(camera.viewportWidth, MathUtils.random(0, camera.viewportHeight - HEIGHT), dodgeableBody.getAngle());
        dodgeableBody.applyForceToCenter(FORCE_X, 0, true);
        this.alive = true;

        //keep track of time the ufo was spawned
        spawnTime = Gameplay.totalGameTime;

        //Play UFO Sound
        this.flyingSound = Sounds.ufoFlyingSound;
        this.flyingSound.loop(SettingsManager.gameVolume);
        Sounds.activeSounds.add(this.flyingSound);


    }

    public void initHorizontal(float direction) {

        //Set the direction which the energy beams associated with the UFO should fire
        this.direction = direction;

        dodgeableBody.setActive(true);
        dodgeableBody.setTransform(camera.viewportWidth, (camera.viewportHeight - HEIGHT) / 2, dodgeableBody.getAngle());
        dodgeableBody.applyForceToCenter(FORCE_X, 0, true);
        this.alive = true;

        //keep track of time the ufo was spawned
        spawnTime = Gameplay.totalGameTime;

        //Play UFO Sound
        this.flyingSound = Sounds.ufoFlyingSound;
        this.flyingSound.loop(SettingsManager.gameVolume);
        Sounds.activeSounds.add(this.flyingSound);

    }

    public void initVertical(float direction){
        //Set the direction which the energy beams associated with the UFO should fire
        this.direction = direction;

        dodgeableBody.setActive(true);
        dodgeableBody.setTransform(camera.viewportWidth/2 - WIDTH/2, camera.viewportHeight, dodgeableBody.getAngle());
        dodgeableBody.applyForceToCenter(0, FORCE_Y, true);
        this.alive = true;

        //keep track of time the ufo was spawned
        spawnTime = Gameplay.totalGameTime;

        //Play UFO Sound
        this.flyingSound = Sounds.ufoFlyingSound;
        this.flyingSound.loop(SettingsManager.gameVolume);
        Sounds.activeSounds.add(this.flyingSound);

    }

    public void initStopInCenter(float direction, float timeToHoldInCenter) {

        //This version of the UFO will stop in the center of the screen for a specified period of time

        //Set the direction which the energy beams associated with the UFO should fire
        this.direction = direction;

        dodgeableBody.setActive(true);
        dodgeableBody.setTransform(camera.viewportWidth, (camera.viewportHeight - HEIGHT) / 2, dodgeableBody.getAngle());
        dodgeableBody.applyForceToCenter(FORCE_X, 0, true);
        this.alive = true;

        //keep track of time the ufo was spawned
        spawnTime = Gameplay.totalGameTime;

        this.stopInCenterOfScreen = true;
        this.timeToHold = timeToHoldInCenter;

        //Play UFO Sound
        this.flyingSound = Sounds.ufoFlyingSound;
        this.flyingSound.loop(SettingsManager.gameVolume);
        Sounds.activeSounds.add(this.flyingSound);

    }

    public void initStopInRightCenter(float direction, float timeToHoldInCenter) {

        //This version of the UFO will stop in the center of the screen for a specified period of time

        //Set the direction which the energy beams associated with the UFO should fire
        this.direction = direction;

        dodgeableBody.setActive(true);
        dodgeableBody.setTransform(camera.viewportWidth, (camera.viewportHeight - HEIGHT) / 2, dodgeableBody.getAngle());
        dodgeableBody.applyForceToCenter(FORCE_X, 0, true);
        this.alive = true;

        //keep track of time the ufo was spawned
        spawnTime = Gameplay.totalGameTime;

        this.stopInRightCenterOfScreen = true;
        this.timeToHold = timeToHoldInCenter;

        //Play UFO Sound
        this.flyingSound = Sounds.ufoFlyingSound;
        this.flyingSound.loop(SettingsManager.gameVolume);
        Sounds.activeSounds.add(this.flyingSound);

    }

    public void initStopInTopRightCorner(float direction, float timeToHold){
        //This version of the UFO will stop in the right corner of the screen for a specified period of time

        //Set the direction which the energy beams associated with the UFO should fire
        this.direction = direction;

        dodgeableBody.setActive(true);
        dodgeableBody.setTransform(camera.viewportWidth, camera.viewportHeight - HEIGHT, dodgeableBody.getAngle());
        dodgeableBody.applyForceToCenter(FORCE_X, 0, true);
        this.alive = true;

        //keep track of time the ufo was spawned
        spawnTime = Gameplay.totalGameTime;

        this.stopInTopRightCornerOfScreen = true;
        this.timeToHold = timeToHold;

        //Play UFO Sound
        this.flyingSound = Sounds.ufoFlyingSound;
        this.flyingSound.loop(SettingsManager.gameVolume);
        Sounds.activeSounds.add(this.flyingSound);
    }

    public void initStopInBottomLeftCorner(float direction, float timeToHold){
        //This version of the UFO will stop in the bottom left corner of the screen for a specified period of time

        //Set the direction which the energy beams associated with the UFO should fire
        this.direction = direction;

        dodgeableBody.setActive(true);
        dodgeableBody.setTransform(0-WIDTH, 0 - HEIGHT/3, dodgeableBody.getAngle());
        dodgeableBody.applyForceToCenter(-FORCE_X, 0, true);
        this.alive = true;

        //keep track of time the ufo was spawned
        spawnTime = Gameplay.totalGameTime;

        this.stopInBottomLeftCornerOfScreen = true;
        this.timeToHold = timeToHold;

        //Play UFO Sound
        this.flyingSound = Sounds.ufoFlyingSound;
        this.flyingSound.loop(SettingsManager.gameVolume);
        Sounds.activeSounds.add(this.flyingSound);
    }

    @Override
    public void checkIfCanBeUnheld() {
        super.checkIfCanBeUnheld();

        float currentTime = Gameplay.totalGameTime;
        if (this.timeHoldWillBeReleased <= currentTime){
            this.stopInCenterOfScreen = false;
            this.stopInRightCenterOfScreen = false;
            this.stopInTopRightCornerOfScreen = false;
            this.stopInBottomLeftCornerOfScreen = false;
            //Kill all energy beams when UFO is unheld
            for(UfoEnergyBeam energyBeam : this.energyBeams){
                energyBeam.flagForDeletion = true;
            }
        }
    }

    @Override
    public void reset() {
        super.reset();

        //Clear all values set on previous UFOs
        this.energyBallIsSpawned = false;
        for (EnergyBall energyBall : this.energyBalls){
            energyBall.reset();
        }
        this.energyBalls.clear();
        for (UfoEnergyBeam energyBeam : this.energyBeams){
            energyBeam.reset();
        }
        this.energyBeams.clear();
        this.spawnTime = 0;
        this.stopInCenterOfScreen = false;
        this.stopInRightCenterOfScreen = false;
        this.stopInTopRightCornerOfScreen = false;
        this.stopInBottomLeftCornerOfScreen = false;
        this.timeToHold = 0;

        this.flyingSound.stop();
        Sounds.activeSounds.remove(this.flyingSound);

    }
}
