package nl.tudelft.jpacman.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.points.PointCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link SinglePlayerGame} and {@link Game} lifecycle.
 */
@SuppressWarnings({"magicnumber", "PMD.AvoidDuplicateLiterals"})
class SinglePlayerGameTest {


    private SinglePlayerGame game;
    private Player player;
    private Level level;
    private PointCalculator pointCalculator;

    /**
     * Sets up the single player game with mocked dependencies.
     */
    @BeforeEach
    void setUp() {
        player = mock(Player.class);
        level = mock(Level.class);
        pointCalculator = mock(PointCalculator.class);
        game = new SinglePlayerGame(player, level, pointCalculator);
    }

    /**
     * Tests starting the game when player is alive and pellets remain.
     */
    @Test
    void testStartGameSuccess() {
        when(level.isAnyPlayerAlive()).thenReturn(true);
        when(level.remainingPellets()).thenReturn(10);

        game.start();

        assertThat(game.isInProgress()).isTrue();
        verify(level).addObserver(game);
        verify(level).start();
    }

    /**
     * Tests that game will not start if no players are alive.
     */
    @Test
    void testStartGamePlayerDead() {
        when(level.isAnyPlayerAlive()).thenReturn(false);
        when(level.remainingPellets()).thenReturn(10);

        game.start();

        assertThat(game.isInProgress()).isFalse();
        verify(level, never()).start();
    }

    /**
     * Tests that game will not start if no pellets remain.
     */
    @Test
    void testStartGameNoPellets() {
        when(level.isAnyPlayerAlive()).thenReturn(true);
        when(level.remainingPellets()).thenReturn(0);

        game.start();

        assertThat(game.isInProgress()).isFalse();
        verify(level, never()).start();
    }

    /**
     * Tests pausing / stopping the game.
     */
    @Test
    void testStopGame() {
        when(level.isAnyPlayerAlive()).thenReturn(true);
        when(level.remainingPellets()).thenReturn(5);

        game.start();
        assertThat(game.isInProgress()).isTrue();

        game.stop();
        assertThat(game.isInProgress()).isFalse();
        verify(level).stop();
    }

    /**
     * Tests moving player when game is in progress.
     */
    @Test
    void testMovePlayerWhileInProgress() {
        when(level.isAnyPlayerAlive()).thenReturn(true);
        when(level.remainingPellets()).thenReturn(5);

        game.start();
        game.move(player, Direction.NORTH);

        verify(level).move(player, Direction.NORTH);
        verify(pointCalculator).pacmanMoved(player, Direction.NORTH);
    }

    /**
     * Tests moving player when game is not in progress does nothing.
     */
    @Test
    void testMovePlayerWhileNotInProgress() {
        game.move(player, Direction.NORTH);

        verify(level, never()).move(player, Direction.NORTH);
        verify(pointCalculator, never()).pacmanMoved(player, Direction.NORTH);
    }

    /**
     * Tests winning or losing level stops the game.
     */
    @Test
    void testLevelWonAndLost() {
        when(level.isAnyPlayerAlive()).thenReturn(true);
        when(level.remainingPellets()).thenReturn(5);

        game.start();
        game.levelWon();
        assertThat(game.isInProgress()).isFalse();

        game.start();
        game.levelLost();
        assertThat(game.isInProgress()).isFalse();
    }

    /**
     * Tests player and level getters.
     */
    @Test
    void testGetters() {
        assertThat(game.getPlayers()).containsExactly(player);
        assertThat(game.getLevel()).isEqualTo(level);
    }
}
