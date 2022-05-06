/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P2.turtle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class TurtleSoup {

    /**
     * Draw a square.
     *
     * @param turtle     the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength) {
        turtle.forward(sideLength);
        turtle.turn(90.00);
        turtle.forward(sideLength);
        turtle.turn(90.00);
        turtle.forward(sideLength);
        turtle.turn(90.00);
        turtle.forward(sideLength);
        turtle.turn(90.00);

    }

    /**
     * Determine inside angles of a regular polygon.
     * <p>
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     *
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides) {

        return ((sides - 2) * 180 / (double) sides);
    }

    /**
     * Determine number of sides given the size of interior angles of a regular polygon.
     * <p>
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     *
     * @param angle size of interior angles in degrees, where 0 < angle < 180
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle) {

        return (int) Math.round(360 / (180 - angle));

    }

    /**
     * Given the number of sides, draw a regular polygon.
     * <p>
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to draw.
     *
     * @param turtle     the turtle context
     * @param sides      number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {


        for (int i = 0; i < sides; i++) {
            turtle.forward(sideLength);
            turtle.turn((180 - calculateRegularPolygonAngle(sides)));
        }
    }

    /**
     * Given the current direction, current location, and a target location, calculate the Bearing
     * towards the target point.
     * <p>
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentBearing. The angle must be expressed in
     * degrees, where 0 <= angle < 360.
     * <p>
     * HINT: look at <a href="http://en.wikipedia.org/wiki/Atan2">http://en.wikipedia.org/wiki/Atan2</a> and Java's math libraries
     *
     * @param currentBearing current direction as clockwise from north
     * @param currentX       current location x-coordinate
     * @param currentY       current location y-coordinate
     * @param targetX        target point x-coordinate
     * @param targetY        target point y-coordinate
     * @return adjustment to Bearing (right turn amount) to get to target point,
     * must be 0 <= angle < 360
     */
    public static double calculateBearingToPoint(double currentBearing, int currentX, int currentY,
                                                 int targetX, int targetY) {

        int a = targetX - currentX;
        int b = targetY - currentY;

        double Angle;


        if ((currentX - targetX) == 0 && (currentY - targetY) == 0) {
            return 0;
        }


        Angle = Math.acos((b) / Math.sqrt((a * a + b * b))) * 180 / Math.PI;

        if (a <= 0) {
            Angle = 360 - Angle;
        }

        if (Angle < currentBearing) {
            Angle += 360;
        }
        return Math.round(Angle - currentBearing) == 360 ? 0 : Math.round(Angle - currentBearing);
    }

    /**
     * Given a sequence of points, calculate the Bearing adjustments needed to get from each point
     * to the next.
     * <p>
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0 degrees).
     * For each subsequent point, assumes that the turtle is still facing in the direction it was
     * facing when it moved to the previous point.
     * You should use calculateBearingToPoint() to implement this function.
     *
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of Bearing adjustments between points, of size 0 if (# of points) == 0,
     * otherwise of size (# of points) - 1
     */
    public static List<Double> calculateBearings(List<Integer> xCoords, List<Integer> yCoords) {

        List<Double> list = new ArrayList<>();

        int len = xCoords.size();
        if (xCoords.size() == 0) {
            return null;
        } else {
            list.add(0.0);
            list.add(calculateBearingToPoint(0, xCoords.get(0), yCoords.get(0), xCoords.get(1), yCoords.get(1)));
            for (int i = 0; i < len - 2; i++) {
                list.add(calculateBearingToPoint((list.get(i) + list.get(i + 1)) % 360, xCoords.get(i + 1), yCoords.get(i + 1), xCoords.get(i + 2), yCoords.get(i + 2)));
            }
        }
        list.remove(0);
        return list;
    }

    /**
     * Given a set of points, compute the convex hull, the smallest convex set that contains all the points
     * in a set of input points. The gift-wrapping algorithm is one simple approach to this problem, and
     * there are other algorithms too.
     *
     * @param points a set of points with xCoords and yCoords. It might be empty, contain only 1 point, two points or more.
     * @return minimal subset of the input points that form the vertices of the perimeter of the convex hull
     */
    public static Set<Point> convexHull(Set<Point> points) {

        if (points.size() == 3) {
            return points;
        }
        Set<Point> points_set = new HashSet<>();
        ArrayList<Point> points1 = new ArrayList<>();

        Point leftPoint = new Point(0, 0);
        for (Point point : points) {
            leftPoint = point;
            break;
        }

        double left = 0;
        for (Point point : points) {
            left = point.x();
            break;
        }


        for (Point point : points) {
            points1.add(point);
            if (point.x() < left) {
                left = point.x();
                leftPoint = point;
            }
        }

        Point point1 = leftPoint;

        double Angle = 0;
        for (int i = 0; i < points1.size(); i++) {

            int min = 0;
            double temp = 360;

            for (int j = 0; j < points1.size(); j++) {
                if (temp > calculateBearingToPoint(Angle, (int) point1.x(), (int) point1.y(), (int) points1.get(j).x(), (int) points1.get(j).y())
                        && points1.get(j) != point1) {
                    min = j;
                    temp = calculateBearingToPoint(Angle, (int) point1.x(), (int) point1.y(), (int) points1.get(j).x(), (int) points1.get(j).y());
                } else if (temp == calculateBearingToPoint(Angle, (int) point1.x(), (int) point1.y(), (int) points1.get(j).x(), (int) points1.get(j).y())) {
                    if ((Math.pow(points1.get(j).x() - point1.x(), 2) + Math.pow(points1.get(j).y() - point1.y(), 2))
                            > (Math.pow(points1.get(min).x() - point1.x(), 2) + Math.pow(points1.get(min).y() - point1.y(), 2))) {
                        min = j;
                    }

                }
            }

            point1 = points1.get(min);
            points_set.add(points1.get(min));
            Angle += temp;
            if (point1 == leftPoint) {
                break;
            }
        }

        return points_set;
    }

    /**
     * Draw your personal, custom art.
     * <p>
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something appealing; the complexity can be as little or as much as you want.
     *
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(Turtle turtle) {

        int Size = 90, Step = 3, ColorNum = 6;
        for (int i = 1; i <= Size; i ++) {
            switch (i % ColorNum) {
                case 0:
                    turtle.color(PenColor.BLUE);
                    break;
                case 1:
                    turtle.color(PenColor.GREEN);
                    break;
                case 2:
                    turtle.color(PenColor.YELLOW);
                    break;
                case 3:
                    turtle.color(PenColor.RED);
                    break;
                case 4:
                    turtle.color(PenColor.MAGENTA);
                    break;
                case 5:
                    turtle.color(PenColor.ORANGE);
                    break;
            }
            turtle.forward(Step * i);
            turtle.turn(360 / ColorNum);
        }
    }

    /**
     * Main method.
     * <p>
     * This is the method that runs when you run "java TurtleSoup".
     *
     * @param args unused
     */
    public static void main(String[] args) {
        DrawableTurtle turtle = new DrawableTurtle();



        turtle.draw();
        // draw the window

        drawPersonalArt(turtle);
    }
}
