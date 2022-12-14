package core.ai.behaviorTree.robotTrees.basicFunctions;

import core.ai.GameInfo;
import core.ai.behaviorTree.nodes.NodeState;
import core.ai.behaviorTree.nodes.taskNodes.TaskNode;
import core.fieldObjects.robot.Ally;

/**
 * Defines task of chasing ball
 */
public class ChaseBallNode extends TaskNode {

    private final MoveToObjectNode moveToObjectNode;

    public ChaseBallNode(Ally ally) {
        super("Chase Ball Node: " + ally.toString(), ally);
        this.moveToObjectNode = new MoveToObjectNode(ally);
    }

    /**
     * Moves robot to move in direction of ball
     */
    @Override
    public NodeState execute() {
        this.moveToObjectNode.execute(GameInfo.getBall());
        return NodeState.SUCCESS;
    }

}
