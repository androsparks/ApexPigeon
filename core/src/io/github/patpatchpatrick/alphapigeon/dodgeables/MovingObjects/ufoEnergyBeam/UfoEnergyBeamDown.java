package io.github.patpatchpatrick.alphapigeon.dodgeables.MovingObjects.ufoEnergyBeam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import io.github.patpatchpatrick.alphapigeon.AlphaPigeon;
import io.github.patpatchpatrick.alphapigeon.dodgeables.MovingObjects.Dodgeable;
import io.github.patpatchpatrick.alphapigeon.dodgeables.MovingObjects.UFO;
import io.github.patpatchpatrick.alphapigeon.resources.BodyData;
import io.github.patpatchpatrick.alphapigeon.resources.BodyEditorLoader;

public class UfoEnergyBeamDown extends UfoEnergyBeam {

    public final float WIDTH = 40f;
    public final float HEIGHT = 80f;
    private final float UFO_WIDTH = 15f;
    private final float UFO_HEIGHT = UFO_WIDTH;

    //UFO associated with beam
    public UFO ufo;

    public UfoEnergyBeamDown(World gameWorld, AlphaPigeon game, OrthographicCamera camera) {
        super(gameWorld, game, camera);

        //spawn a new energybeam
        BodyDef energyBeamBodyDef = new BodyDef();
        energyBeamBodyDef.type = BodyDef.BodyType.DynamicBody;

        //spawn energybeam
        energyBeamBodyDef.position.set(0, 0);
        dodgeableBody = gameWorld.createBody(energyBeamBodyDef);
        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("json/EnergyBeamDown.json"));
        FixtureDef energyBeamFixtureDef = new FixtureDef();
        energyBeamFixtureDef.density = 0.001f;
        energyBeamFixtureDef.friction = 0.5f;
        energyBeamFixtureDef.restitution = 0.3f;
        // set the energybeam filter categories and masks for collisions
        energyBeamFixtureDef.filter.categoryBits = game.CATEGORY_UFO;
        energyBeamFixtureDef.filter.maskBits = game.MASK_UFO;
        loader.attachFixture(dodgeableBody, "EnergyBeamDown", energyBeamFixtureDef, WIDTH);
    }

    public void init(UFO ufo, float energyBeamDirection, Sound energyBeamSound) {

        // Get ufo positions to determine where to spawn the energy beam
        float ufoXPosition = ufo.getPosition().x;
        float ufoYPosition = ufo.getPosition().y + UFO_HEIGHT / 2;
        float energyBeamXPosition = ufoXPosition + UFO_WIDTH / 2 - WIDTH / 2;
        float energyBeamYPosition = ufoYPosition - HEIGHT;

        dodgeableBody.setActive(true);
        dodgeableBody.setTransform(energyBeamXPosition, energyBeamYPosition, dodgeableBody.getAngle());
        dodgeableBody.applyForceToCenter(0, 0, true);
        this.alive = true;

        //Make the energy beam velocity match the UFO velocity
        dodgeableBody.setLinearVelocity(ufo.dodgeableBody.getLinearVelocity());

        // set the ufo linked to the energy beam on the beam so the objects positions can stay in sync
        // via the update method
        // set the energy beam direction so the render method knows how to render the sprite image
        BodyData energyBeamData = new BodyData(false);
        energyBeamData.setUfo(ufo);
        energyBeamData.setEnergyBeamDirection(energyBeamDirection);
        this.direction = energyBeamDirection;
        dodgeableBody.setUserData(energyBeamData);

        this.ufo = ufo;

        //Set and play the energy beam sound
        this.energyBeamSound = energyBeamSound;
        this.energyBeamSound.loop();

    }

}
