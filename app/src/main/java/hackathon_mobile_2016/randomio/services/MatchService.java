package hackathon_mobile_2016.randomio.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hackathon_mobile_2016.randomio.enums.GameMode;
import hackathon_mobile_2016.randomio.enums.Team;
import hackathon_mobile_2016.randomio.model.NumberBall;

/**
 * Created by bubu on 29/10/2016.
 */

public class MatchService {

    public static List<NumberBall> generateNewMatch(int maxNumber, GameMode gameMode) {
        List<NumberBall> listNumberBalls = randomMatch(maxNumber, gameMode);

        return listNumberBalls;
    }

    private static List<NumberBall> randomMatch(int maxNumber, GameMode gameMode) {
        Random rand = new Random();
        int sizeX, sizeY;
        if (gameMode == GameMode.EASY) {
            sizeX = 16;
            sizeY = 9;
        } else {
            sizeX = 16;
            sizeY = 18;
        }

        int length = sizeX * sizeY;
        boolean[] a = new boolean[length];
        int[] b = new int[maxNumber];

        // init
        for (int i = 0; i < length; i++) {
            a[i] = false;
        }

        // random
        for (int i = 0; i < maxNumber; i++) {
            int index = rand.nextInt(length);
            while (a[index]) {
                index = rand.nextInt(length);
            }
            a[index] = true;
            b[i] = index;
        }

        List<NumberBall> listNumberBalls = new ArrayList<>();
        for (int i = 0; i < maxNumber; i++) {
            NumberBall newBall = new NumberBall(i + 1);
            newBall.setY(b[i] / sizeX + (rand.nextInt(60) / 100.0) - 0.3);
            newBall.setX(b[i] % sizeX + (rand.nextInt(60) / 100.0) - 0.3);
            listNumberBalls.add(newBall);
        }

        return listNumberBalls;
    }

    public static NumberBall getNumberBallInfo(int number, int matchId) {
        return new NumberBall(number, Team.NO_TEAM);
    }

    public static void chooseNumber(int number, Team chosenTeam) {

    }

}
