package core.ai.behaviorTree.robotTrees.central;

import core.ai.GameInfo;
import core.ai.GameState;
import core.fieldObjects.robot.Ally;
import core.util.Vector2d;

public class CentralCoordinatorRoot implements Runnable {

    /**
     * Sends appropriate messages to robot trees based on provided pass details
     * Instructs receiver to expect ball at a given pass location
     * Instructs all other robot trees to position based on pass location
     */
    private static void coordinatedPass(Ally receiver, Vector2d passLoc) {
        // TODO
        for (Ally ally : GameInfo.getAllies()) {
            if (ally == receiver) {
                // send message to receiver to expect ball at passLoc
            }
            else {
                // send message to all other robots to position based on passLoc
            }
        }
    }

    /**
     * Check for messages from robots and check if current state is NormalStart
     * When new message received, call correct coordinating method
     * If current state is NormalStart, change current state when ball kicked
     */
    @Override
    public void run() {
        // TODO
        if (GameInfo.getCurrState() == GameState.NORMAL_START) {
            // check if ball has been hit by a robot
            // if so, switch current game state to OPEN_PLAY
        }
        // else check for new message to act upon
    }

}
