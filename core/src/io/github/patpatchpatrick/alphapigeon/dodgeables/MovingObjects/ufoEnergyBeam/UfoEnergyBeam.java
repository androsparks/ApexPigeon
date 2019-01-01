package io.github.patpatchpatrick.alphapigeon.dodgeables.MovingObjects.ufoEnergyBeam;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.World;

import io.github.patpatchpatrick.alphapigeon.AlphaPigeon;
import io.github.patpatchpatrick.alphapigeon.dodgeables.MovingObjects.Dodgeable;

//Class shared by all UFO energy beams

public abstract class UfoEnergyBeam extends Dodgeable {

    protected Sound energyBeamSound;


    public UfoEnergyBeam(World gameWorld, AlphaPigeon game, OrthographicCamera camera) {
        super(gameWorld, game, camera);
    }

    @Override
    public void reset() {
        super.reset();

        //Stop playing energy beam sound when it is no longer active
        this.energyBeamSound.stop();
    }
}
