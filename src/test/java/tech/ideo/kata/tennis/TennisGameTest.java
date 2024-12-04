package tech.ideo.kata.tennis;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class TennisGameTest {
    private PrintStream originalOut;
    private ByteArrayOutputStream logsContent;

    @Before
    public void setup() {
        redirectConsoleOutput();
    }

    @After
    public void tearDown() {
        restoreConsoleOutput();
    }

    private void redirectConsoleOutput() {
        originalOut = System.out;
        logsContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(logsContent));
    }

    private void restoreConsoleOutput() {
        System.setOut(originalOut);
        logsContent = null;
    }

    @Test
    public void testLogSimpleGame() {
        new TennisGame().log("ABABAA");
        assertThat(logsContent.toString()).isEqualTo(String.join(System.lineSeparator(),
                "Player A : 15 / Player B : 0",
                "Player A : 15 / Player B : 15",
                "Player A : 30 / Player B : 15",
                "Player A : 30 / Player B : 30",
                "Player A : 40 / Player B : 30",
                "Player A wins the game"
        ));
    }

    @Test
    public void testLogGameWithDeuce() {
        new TennisGame().log("ABABABBABB");
        assertThat(logsContent.toString()).isEqualTo(String.join(System.lineSeparator(),
                "Player A : 15 / Player B : 0",
                "Player A : 15 / Player B : 15",
                "Player A : 30 / Player B : 15",
                "Player A : 30 / Player B : 30",
                "Player A : 40 / Player B : 30",
                "Player A : DEUCE / Player B : DEUCE",
                "Player A : 40 / Player B : 40A",
                "Player A : DEUCE / Player B : DEUCE",
                "Player A : 40 / Player B : 40A",
                "Player B wins the game"
        ));
    }

    @Test
    public void testLogUncompletedGame() {
        new TennisGame().log("A");
        assertThat(logsContent.toString()).isEqualTo(String.join(System.lineSeparator(),
                "Player A : 15 / Player B : 0"
        ));
    }

    @Test
    public void testLogWithWrongInput() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new TennisGame().log("WRONG VALUES"))
                .withMessage("Only A and B chars are allowed");
    }

}