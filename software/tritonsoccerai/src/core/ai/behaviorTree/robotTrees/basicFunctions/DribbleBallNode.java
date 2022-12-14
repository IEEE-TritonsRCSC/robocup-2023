package core.ai.behaviorTree.robotTrees.basicFunctions;

import core.ai.behaviorTree.nodes.NodeState;
import core.ai.behaviorTree.nodes.taskNodes.TaskNode;
import core.fieldObjects.robot.Ally;
import proto.simulation.SslSimulationRobotControl;


/**
 * Defines tasks to be performed to dribble ball
 */
public class DribbleBallNode extends TaskNode {

    private final Ally ally;

    // TODO Pathfinding algorithms needed
    public DribbleBallNode(Ally ally) {
        // TODO
        super("Dribble Ball Node: " + ally.toString(), ally);
        this.ally = ally;
    }

     /**
      * Move into ball at a speed that keeps the ball close to robot.
      * Find the path to the ball and set dribbler speed.
      */
    @Override
    public NodeState execute() {
        // TODO find path to the ball

        // Set dribbler speed
        SslSimulationRobotControl.RobotCommand.Builder robotCommand = SslSimulationRobotControl.RobotCommand.newBuilder();

        // TODO Not sure how to get the robot id
        robotCommand.setId(actor.getId());
        robotCommand.setDribblerSpeed(1);
        robotCommand.setKickSpeed(0);

        // TODO Not sure how to publish robotcommand

        return null;
    }



}
