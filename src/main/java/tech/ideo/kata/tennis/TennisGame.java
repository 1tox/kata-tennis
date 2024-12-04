package tech.ideo.kata.tennis;

import java.text.MessageFormat;
import java.util.function.BiPredicate;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;

public class TennisGame {
    private int scorePlayer1;
    private int scorePlayer2;

    public void log(String scoreEvolution) {
        scoreEvolution
                .chars()
                .peek(registerPoint())
                .takeWhile(gameNotEnded())
                .forEach(logScore());
        if (gameEnded())
            logWinner();
    }

    private IntConsumer registerPoint() {
        return pointWinner -> {
            if (pointWinner == 'A') {
                scorePlayer1++;
            } else if (pointWinner == 'B') {
                scorePlayer2++;
            } else
                throw new IllegalArgumentException("Only A and B chars are allowed");
        };
    }

    private IntConsumer logScore() {
        return c -> {
            if (scorePlayer1 + scorePlayer2 > 1)
                System.out.println();
            System.out.print(MessageFormat.format("Player A : {0} / Player B : {1}", getDisplayableScore(scorePlayer1, scorePlayer2), getDisplayableScore(scorePlayer2, scorePlayer1)));
        };
    }

    private static String getDisplayableScore(int score1, int score2) {
        return switch (score1) {
            case 0 -> "0";
            case 1 -> "15";
            case 2 -> "30";
            case 3 -> score1 == score2 ? "DEUCE" : "40";
            default -> score1 == score2 ? "DEUCE" : (score1 > score2 ? "40A" : "40");

        };
    }

    private IntPredicate gameNotEnded() {
        return c -> !gameEnded();
    }

    private boolean gameEnded() {
        return player1Wins() || player2Wins();
    }

    private boolean player1Wins() {
        return playerWins().test(scorePlayer1, scorePlayer2);
    }

    private boolean player2Wins() {
        return playerWins().test(scorePlayer2, scorePlayer1);
    }

    private BiPredicate<Integer, Integer> playerWins() {
        return (scorePlayer1, scorePlayer2) -> scorePlayer1 >= 4 && scorePlayer1 > scorePlayer2 + 1;
    }

    private void logWinner() {
        System.out.println();
        System.out.print(MessageFormat.format("Player {0} wins the game", scorePlayer1 > scorePlayer2 ? "A" : "B"));
    }
}
