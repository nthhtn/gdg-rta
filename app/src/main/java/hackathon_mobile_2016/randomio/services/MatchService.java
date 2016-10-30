package hackathon_mobile_2016.randomio.services;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hackathon_mobile_2016.randomio.model.Game;
import hackathon_mobile_2016.randomio.model.NumberBall;

/**
 * Created by bubu on 29/10/2016.
 */

public class MatchService {

    public static List<NumberBall> generateNewMatch(int maxNumber, int gameMode) {
        List<NumberBall> listNumberBalls = randomMatch(maxNumber, gameMode);

        return listNumberBalls;
    }

    private static List<NumberBall> suffle(int ignore, List<NumberBall> lstBall) {
        int size = lstBall.size(), j;
        int[] index = new int[size];
        for (int i = 0; i < size; i++) index[i] = i;

        Random ran = new Random();
        for (int i = 0; i < size; i++) {
            j = ran.nextInt(size);
            if (i == ignore || j == ignore) continue;
            index[i] = j;
            index[j] = i;
        }

        List<NumberBall> result = new ArrayList<NumberBall>();
        NumberBall ball;
        for (int i = 0; i < size; i++) {
            ball = lstBall.get(i);
            ball.setX(lstBall.get(index[i]).getX());
            ball.setY(lstBall.get(index[i]).getY());
            result.add(ball);
        }

        return result;
    }

    private static List<NumberBall> randomMatch(int maxNumber, int gameMode) {
        Random rand = new Random();
        int sizeX, sizeY;
        if (gameMode == 0) {
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
            while (a[index] || index%sizeX<2) {
                index = rand.nextInt(length);
            }
            a[index] = true;
            b[i] = index;
        }

        List<NumberBall> listNumberBalls = new ArrayList<>();
        for (int i = 0; i < maxNumber; i++) {
            NumberBall newBall = new NumberBall(i + 1);
            //newBall.setY(b[i] / sizeX + (rand.nextInt(60) / 100.0) - 0.3);
            //newBall.setX(b[i] % sizeX + (rand.nextInt(60) / 100.0) - 0.3);
            if (b[i] / sizeX == 0) {
                newBall.setY(b[i] / sizeX + (rand.nextInt(30) / 100.0));
            } else if (b[i] / sizeX == sizeY - 1) {
                newBall.setY(b[i] / sizeX - (rand.nextInt(30) / 100.0));
            } else {
                newBall.setY(b[i] / sizeX + (rand.nextInt(60) / 100.0) - 0.3);
            }

            if (b[i] % sizeX == sizeX - 1) {
                newBall.setX(b[i] % sizeX + (rand.nextInt(30) / 100.0));
            } else {
                newBall.setX(b[i] % sizeX + (rand.nextInt(60) / 100.0) - 0.3);
            }
            listNumberBalls.add(newBall);
        }

        return listNumberBalls;
    }

    public static NumberBall getNumberBallInfo(int number, int matchId) {
        return new NumberBall(number, 0);
    }

    public static void chooseNumber(String roomId, int number, int chosenTeam) {
        choseNumber(roomId, number, chosenTeam);
    }

    public static void serverStartMatch(List<NumberBall> points, String roomId, GameRender gameMatchLoading) {

        Game gameMatch = new Game();
        gameMatch.current = 1;
        gameMatch.points = (ArrayList<NumberBall>)points;
        Network.serverStartMatch(roomId, gameMatch, gameMatchLoading);

        //Update room status
        DatabaseReference roomManager = Network.firebaseDatabase.getReference("room/" + roomId);
        roomManager.child("status").setValue(2);

    }

    public static void clientStartMatch(String roomId,  GameRender gameMatchLoading) {
        Network.clientStartMatch(roomId, gameMatchLoading);
    }

    public static void choseNumber(String roomId, int number, int team) {
        Network.choseNumber(roomId, number, team);
    }

    public interface GameRender {
        void success(List<NumberBall> numberBalls);
        void renderChoosenNumber(int number);
    }


}
