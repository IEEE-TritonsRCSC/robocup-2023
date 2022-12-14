package core.util;

import core.ai.GameInfo;
import core.fieldObjects.robot.Robot;

import java.util.ArrayList;
import java.util.List;

import static core.util.ProtobufUtils.*;
import static proto.triton.FilteredObject.Ball;
import static proto.triton.FilteredObject.Robot;
import static proto.vision.MessagesRobocupSslGeometry.SSL_GeometryFieldSize;

public class ObjectHelper {
    
    public static boolean hasOrientation(Robot robot, Vector2d facePos, float angleTolerance) {
        float targetOrientation = (float) Math.atan2(facePos.y - robot.getY(), facePos.x - robot.getX());
        return hasOrientation(robot, targetOrientation, angleTolerance);
    }

    public static boolean hasOrientation(Robot robot, float orientation, float angleTolerance) {
        float angleDifference = Vector2d.angleDifference(orientation, robot.getOrientation());
        return Math.abs(angleDifference) < angleTolerance;
    }

    public static boolean hasPos(Robot robot, Vector2d pos, float distanceTolerance) {
        Vector2d robotPos = new Vector2d(robot.getX(), robot.getY());
        return robotPos.dist(pos) < distanceTolerance;
    }

    public static boolean ballWillArriveAtTarget(Vector2d pos, float delta, float distanceTolerance) {
        return willArriveAtTarget(GameInfo.getBall().getPos(), GameInfo.getBall().getVel(), GameInfo.getBall().getAcc(), pos, delta, distanceTolerance);
    }

    public static boolean willArriveAtTarget(Vector2d pos, Vector2d vel, Vector2d acc, Vector2d target, float delta,
                                             float distanceTolerance) {
        Vector2d predictPos = predictPos(pos, vel, acc, delta);
        return predictPos.dist(target) < distanceTolerance;
    }

    public static Vector2d predictPos(Vector2d pos, Vector2d vel, Vector2d acc, float delta) {
        // TODO ADD ACC PREDICTION
        return pos.add(vel.scale(delta));
    }

    public static boolean willArriveAtTarget(Robot robot, Vector2d target, float delta, float distanceTolerance) {
        return willArriveAtTarget(robot.getPos(), robot.getVel(), robot.getAcc(), target, delta, distanceTolerance);
    }

    public static boolean ballIsMovingTowardTarget(Vector2d pos, float angleTolerance) {
        return isMovingTowardTarget(GameInfo.getBall().getPos(), GameInfo.getBall().getVel(), pos, angleTolerance);
    }

    public static boolean isMovingTowardTarget(Vector2d pos, Vector2d vel, Vector2d target, float angleTolerance) {
        Vector2d towardTarget = target.sub(pos);
        return vel.angle(towardTarget) < angleTolerance;
    }

    public static boolean isMovingTowardTarget(Vector2d target, float speedThreshold, float angleTolerance) {
        return isMovingTowardTarget(GameInfo.getBall().getPos(), GameInfo.getBall().getVel(), target, speedThreshold, angleTolerance);
    }

    public static boolean isMovingTowardTarget(Vector2d pos, Vector2d vel, Vector2d target,
                                               float speedThreshold, float angleTolerance) {
        Vector2d towardTarget = target.sub(pos);
        return vel.mag() > speedThreshold && vel.angle(towardTarget) < angleTolerance;
    }

    public static boolean isMovingTowardTarget(Robot robot, Vector2d pos, float angleTolerance) {
        return isMovingTowardTarget(robot.getPos(), robot.getVel(), pos, angleTolerance);
    }

    public static boolean isMovingTowardTarget(Robot robot, Vector2d pos, float speedThreshold, float angleTolerance) {
        return isMovingTowardTarget(robot.getPos(), robot.getVel(), pos, speedThreshold, angleTolerance);
    }

    public static Vector2d predictRobotPos(Robot robot, float delta) {
        return predictPos(robot.getPos(), robot.getVel(), robot.getAcc(), delta);
    }

    public static Vector2d predictBallPos(float delta) {
        return predictPos(GameInfo.getBall().getPos(), GameInfo.getBall().getVel(), GameInfo.getBall().getAcc(), delta);
    }

    public static float predictOrientation(Robot robot, float delta) {
        return predictOrientation(robot.getOrientation(), robot.getAngular(), robot.getAccAngular(), delta);
    }

    public static float predictOrientation(float orientation, float angular, float accAngular, float delta) {
        // TODO ADD ACC PREDICTION
        return orientation
                + delta * angular;
    }

    public static Vector2d getAllyGoal(SSL_GeometryFieldSize field) {
        return new Vector2d(0, -field.getFieldLength() / 2f);
    }

    public static Vector2d getFoeGoal(SSL_GeometryFieldSize field) {
        return new Vector2d(0, field.getFieldLength() / 2f);
    }

    public static boolean ballIsInAllyGoal(SSL_GeometryFieldSize field) {
        float goalX = -field.getGoalWidth() / 2f;
        float goalY = -field.getFieldLength() / 2f - field.getGoalDepth();
        float goalWidth = field.getGoalWidth();
        float goalHeight = field.getGoalDepth();
        return GameInfo.getBall().getPos().isInRect(goalX, goalY, goalWidth, goalHeight);
    }

    public static boolean ballIsInFoeGoal(SSL_GeometryFieldSize field) {
        float goalX = -field.getGoalWidth() / 2f;
        float goalY = field.getFieldLength() / 2f;
        float goalWidth = field.getGoalWidth();
        float goalHeight = field.getGoalDepth();
        return GameInfo.getBall().getPos().isInRect(goalX, goalY, goalWidth, goalHeight);
    }

    public static boolean ballIsInBounds(SSL_GeometryFieldSize field) {
        return isInBounds(GameInfo.getBall().getPos(), field);
    }

    public static boolean isInBounds(Vector2d pos, SSL_GeometryFieldSize field) {
        return pos.isInRect(-field.getFieldWidth() / 2f,
                -field.getFieldLength() / 2f,
                field.getFieldWidth(),
                field.getFieldLength());
    }

    public static boolean isInBounds(Robot robot, SSL_GeometryFieldSize field) {
        return isInBounds(robot.getPos(), field);
    }

    public static float distToPath(Vector2d from, Vector2d to, List<Robot> robots) {
        List<Vector2d> points = new ArrayList<>();
        robots.forEach(robot -> points.add(robot.getPos()));
        return Vector2d.distToPath(from, to, points);
    }

    public static boolean checkDistToPath(Vector2d from, Vector2d to, List<Robot> robots, float dist) {
        List<Vector2d> points = new ArrayList<>();
        robots.forEach(robot -> points.add(robot.getPos()));
        return Vector2d.checkDistToPath(from, to, points, dist);
    }

    public static boolean isWithinDist(Vector2d target, List<Robot> robots, float dist) {
        for (Robot robot : robots)
            if (target.dist(robot.getPos()) < dist)
                return true;
        return false;
    }

    public static float getMinDist(Vector2d target, List<Robot> robots) {
        float minDist = Float.MAX_VALUE;
        for (Robot robot : robots)
            minDist = Math.min(minDist, target.dist(robot.getPos()));
        return minDist;
    }

    public static Robot getNearestRobot(Vector2d target, List<Robot> robots) {
        Robot closestRobot = null;
        float minDist = Float.MAX_VALUE;
        for (Robot robot : robots) {
            float dist = target.dist(robot.getPos());
            if (dist < minDist) {
                closestRobot = robot;
                minDist = dist;
            }
        }
        return closestRobot;
    }

    public static List<Robot> getNearRobots(Vector2d target, List<Robot> robots, float distThreshold) {
        List<Robot> nearRobots = new ArrayList<>();
        for (Robot robot : robots) {
            float dist = target.dist(robot.getPos());
            if (dist < distThreshold) {
                nearRobots.add(robot);
            }
        }
        return nearRobots;
    }
    
}
