package com.ace.utilities.other.pad;



import com.ace.utilities.Console;
import com.ace.utilities.RandomUtil;

import java.util.ArrayList;
import java.util.List;

public class BallMap {
    private static final Integer x = 5;
    private static final Integer y = 6;

    private List<List<Ball>> ballmap;

    public BallMap() {
        ballmap = new ArrayList<>();
    }

    private List<List<Ball>> generateDate() {
        for (int i = 0; i < x; i++) {
            List<Ball> rowBall = new ArrayList<>();
            for (int j = 0; j < y; j++) {
                int color = RandomUtil.getInt(10);
                while (color == 0 || color > 6) {
                    color = RandomUtil.getInt(10);
                }
                Ball ball = new Ball(j, i, color);
                //   System.out.print(ball.getType() + "  ");
                switch (ball.getType()) {
                    case 1:
                        Console.print(ball.getType().toString() + "  ", Console.RED, Console.BOLD);
                        break;
                    case 2:
                        Console.print(ball.getType().toString() + "  ", Console.BLUE, Console.BOLD);

                        break;
                    case 3:
                        Console.print(ball.getType().toString() + "  ", Console.GREEN, Console.BOLD);

                        break;
                    case 4:
                        Console.print(ball.getType().toString() + "  ", Console.FLUORESCENT_PURPLE, Console.BOLD);

                        break;
                    case 5:
                        Console.print(ball.getType().toString() + "  ", Console.YELLOW, Console.BOLD);

                        break;
                    case 6:
                        Console.print(ball.getType().toString() + "  ", Console.ITATIC, Console.BOLD);
                        break;
                    default:
                }
                rowBall.add(ball);
            }
            System.out.println();
            ballmap.add(rowBall);
        }
        return ballmap;
    }


    public List<List<Ball>> getBallmap() {
        return generateDate();
    }

    public void setBallmap(List<List<Ball>> ballmap) {
        this.ballmap = ballmap;
    }
}
