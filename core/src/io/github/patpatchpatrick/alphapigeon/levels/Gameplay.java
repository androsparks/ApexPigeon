package io.github.patpatchpatrick.alphapigeon.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;

import io.github.patpatchpatrick.alphapigeon.dodgeables.AlienMissiles;
import io.github.patpatchpatrick.alphapigeon.dodgeables.Birds;
import io.github.patpatchpatrick.alphapigeon.dodgeables.Dodgeables;
import io.github.patpatchpatrick.alphapigeon.dodgeables.Meteors;
import io.github.patpatchpatrick.alphapigeon.dodgeables.MovingObjects.Dodgeable;
import io.github.patpatchpatrick.alphapigeon.dodgeables.PowerUps;
import io.github.patpatchpatrick.alphapigeon.dodgeables.Rockets;
import io.github.patpatchpatrick.alphapigeon.dodgeables.Teleports;
import io.github.patpatchpatrick.alphapigeon.dodgeables.UFOs;
import io.github.patpatchpatrick.alphapigeon.resources.GameVariables;

public class Gameplay extends Level {

    //Class to control the gameplay of the game.
    //Controls the game levels, and how enemies are spawned as the game time increases

    //Levels
    private LevelOne levelOne;
    private LevelTwo levelTwo;
    private LevelThree levelThree;
    private LevelFour levelFour;
    private LevelFinal levelFinal;

    //GAME TIMES
    private boolean gamePlayInitiated = false;
    private float startTime = 0f; //Used to track when gameplay was initiated
    public static float totalGameTime; //Used to track total game time. Primary time variable used in all levels classes and dodgeables.


    public Gameplay(Dodgeables dodgeables) {
        super(dodgeables);

        levelOne = new LevelOne(dodgeables);
        levelTwo = new LevelTwo(dodgeables);
        levelThree = new LevelThree(dodgeables);
        levelFour = new LevelFour(dodgeables);
        levelFinal = new LevelFinal(dodgeables, levelOne, levelTwo, levelThree, levelFour);
    }


    public void render(SpriteBatch batch) {

        // Render all the dodgeable objects
        // Use the total game time so that the dodgeables stay in sync with the levels (both use totalGameTime)
        this.dodgeables.render(totalGameTime, batch);

    }

    public void update(float stateTime) {

        //Initialize start time for the game to be the current statetime
        if (!gamePlayInitiated) {
            startTime = stateTime;
            gamePlayInitiated = true;
        }

        // Update all levels and gameplay
        // Update the total game time (total game time is always the difference between the stateTime and startTime in seconds)
        totalGameTime = stateTime - startTime;
        updateLevels();

        // Update pigeon speed based on totalGameTime
        // Pigeon min speed is 9 (m/s) and max speed is 300 (m/s) after about 20 minutes (600 seconds)
        if (GameVariables.pigeonSpeed < GameVariables.PIGEON_MAX_SPEED) {
            GameVariables.pigeonSpeed = 9f + (0.45f) * totalGameTime;
        } else {
            GameVariables.pigeonSpeed = GameVariables.PIGEON_MAX_SPEED;
        }

        Gdx.app.log("Pigeon Speed", "" + GameVariables.pigeonSpeed);


        // Update all the dodgeable objects
        // The dodgeable objects are the enemies in the game that must be dodged
        this.dodgeables.update();

    }

    private void updateLevels() {

        // Method to keep track of which level to run based on how much time has passed

        if (totalGameTime > LEVEL_ONE_START_TIME && totalGameTime <= LEVEL_ONE_END_TIME) {

            levelOne.run(totalGameTime);
            spawnRandomPowerUp();
            Gdx.app.log("LEVEL ONE", "PLAYING");

        } else if (totalGameTime > LEVEL_TWO_START_TIME && totalGameTime <= LEVEL_TWO_END_TIME) {

            levelTwo.run(true, NO_WAVE, totalGameTime, false);
            spawnRandomPowerUp();
            Gdx.app.log("LEVEL TWO", "PLAYING");

        } else if (totalGameTime > LEVEL_THREE_START_TIME & totalGameTime <= LEVEL_THREE_END_TIME) {

            levelThree.run(true, NO_WAVE, totalGameTime, false);
            spawnRandomPowerUp();
            Gdx.app.log("LEVEL THREE", "PLAYING");

        } else if (totalGameTime > LEVEL_FOUR_START_TIME & totalGameTime <= LEVEL_FOUR_END_TIME) {

            levelFour.run(true, NO_WAVE, totalGameTime, false);
            spawnRandomPowerUp();
            Gdx.app.log("LEVEL FOUR", "PLAYING");

        } else if (totalGameTime > LEVEL_FINAL_START_TIME) {

            levelFinal.run(totalGameTime);
            spawnRandomPowerUp();
            Gdx.app.log("LEVEL FINAL", "PLAYING");
        }


    }

    private void spawnRandomPowerUp() {

        //Spawn random power up if time between power ups has passed (Power Up Interval)

        if (totalGameTime - PowerUps.lastPowerUpSpawnTime > PowerUps.randomSpawnIntervalTime) {
            powerUps.spawnPowerUp(MathUtils.random(PowerUps.POWER_UP_TYPE_SHIELD, PowerUps.POWER_UP_TYPE_SKULL));
        }
    }
}
