package nl.tudelft.jpacman.points;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Pellet;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.npc.Ghost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link DefaultPointCalculator}.
 */
@SuppressWarnings({"magicnumber", "PMD.AvoidDuplicateLiterals"})
class DefaultPointCalculatorTest {


    private DefaultPointCalculator pointCalculator;

    /**
     * Sets up DefaultPointCalculator instance before each test.
     */
    @BeforeEach
    void setUp() {
        pointCalculator = new DefaultPointCalculator();
    }

    /**
     * Tests that consuming a pellet adds its point value to the player.
     */
    @Test
    void testConsumedAPellet() {
        Player player = mock(Player.class);
        Pellet pellet = mock(Pellet.class);
        when(pellet.getValue()).thenReturn(10);

        pointCalculator.consumedAPellet(player, pellet);

        verify(player).addPoints(10);
    }

    /**
     * Tests that colliding with a ghost is a no-op for score.
     */
    @Test
    void testCollidedWithAGhost() {
        Player player = mock(Player.class);
        Ghost ghost = mock(Ghost.class);

        pointCalculator.collidedWithAGhost(player, ghost);

        verifyZeroInteractions(player);
        verifyZeroInteractions(ghost);
    }

    /**
     * Tests that player movement is a no-op for score.
     */
    @Test
    void testPacmanMoved() {
        Player player = mock(Player.class);

        pointCalculator.pacmanMoved(player, Direction.NORTH);

        verifyZeroInteractions(player);
    }
}
