package core.ai.behaviorTree.robotTrees.fielder.fielderRoot;

import core.ai.behaviorTree.nodes.NodeState;
import core.ai.behaviorTree.nodes.compositeNodes.CompositeNode;
import core.constants.ProgramConstants;
import core.fieldObjects.robot.Ally;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Root node of fielder tree
 * If game is in open play, plays offense or defense
 * If referee command, takes appropriate action
 * Runs FielderRootService at defined frequency
 */
public class FielderRootNode extends CompositeNode {

    private final FielderRootService fielderRootService;
    private final ScheduledThreadPoolExecutor executor;

    public FielderRootNode(Ally ally, ScheduledThreadPoolExecutor executor) {
        super("Fielder Root");

        this.fielderRootService = new FielderRootService(ally, executor);
        this.executor = executor;
    }

    /**
     * At a desired frequency, run FielderRootService
     */
    @Override
    public NodeState execute() {
        // TODO
        executor.scheduleAtFixedRate(this.fielderRootService, ProgramConstants.INITIAL_DELAY,
                                    ProgramConstants.LOOP_DELAY, TimeUnit.MILLISECONDS);
        return NodeState.RUNNING;
    }

}
