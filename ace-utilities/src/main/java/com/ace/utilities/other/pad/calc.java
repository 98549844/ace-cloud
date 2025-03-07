package com.ace.utilities.other.pad;

import com.ace.utilities.Console;

import java.util.ArrayList;
import java.util.List;

public class calc {

    private static final Integer x = 5;
    private static final Integer y = 6;


    public static void main(String[] args) {
        BallMap ballMap = new BallMap();
        List<List<Ball>> ballmap = ballMap.getBallmap();
        System.out.println("*********************");
        List<List<Ball>> calcBallMap = calc(ballmap);

        for (int i = 0; i < x; i++) {
            List<Ball> rowBall = calcBallMap.get(i);
            for (int j = 0; j < y; j++) {
                if (!rowBall.get(j).isExist()) {
                    Console.print("N  ", Console.RED);

                } else {
                    Console.print("Y  ", Console.BLUE);
                }

            }
            System.out.println();
        }

    }


    public static List<List<Ball>> calc(List<List<Ball>> ballMap) {
        List<List<Ball>> newBallMap = new ArrayList<>();
        for (int i = 0; i < x - 1; i++) {
            List<Ball> newRowBall = new ArrayList<>();
            for (int j = 0; j < y - 1; j++) {
                Ball b = ballMap.get(i).get(j);
                if (ballMap.get(i).get(j).getX() == 0) {
                    b.setLeft(null);
                } else {
                    ballMap.get(i).get(j).setLeft(ballMap.get(i).get(j - 1).getType());
                }
                if (ballMap.get(i).get(j).getX().equals(x)) {
                    ballMap.get(i).get(j).setRight(null);
                } else {
                    ballMap.get(i).get(j).setRight(ballMap.get(i).get(j + 1).getType());
                }

                if (ballMap.get(i).get(j).getY() == 0) {
                    ballMap.get(i).get(j).setUp(null);
                } else {
                    ballMap.get(i).get(j).setUp(ballMap.get(i - 1).get(j).getType());
                }
                if (ballMap.get(i).get(j).getY() == x - 1) {
                    ballMap.get(i).get(j).setDown(null);
                } else {
                    ballMap.get(i).get(j).setDown(ballMap.get(i + 1).get(j).getType());
                }


                if ((b.getType().equals(b.getLeft())) && (b.getType().equals(b.getRight()))) {
                    ballMap.get(i).get(j - 1).setExist(false);
                    ballMap.get(i).get(j).setExist(false);
                    ballMap.get(i).get(j + 1).setExist(false);
                }
                if ((b.getType().equals(b.getUp())) && (b.getType().equals(b.getDown()))) {
                    ballMap.get(i - 1).get(j).setExist(false);
                    ballMap.get(i).get(j).setExist(false);
                    ballMap.get(i + 1).get(j).setExist(false);
                }
                //  newRowBall.add(b);
            }
            // newBallMap.add(newRowBall);
        }
        return ballMap;
    }


}
