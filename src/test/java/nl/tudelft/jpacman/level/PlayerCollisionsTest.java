package nl.tudelft.jpacman.level;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.points.PointCalculator;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link PlayerCollisions}.
 */
@SuppressWarnings({"magicnumber", "PMD.AvoidDuplicateLiterals"})
class PlayerCollisionsTest {


    private PointCalculator pointCalculator;
    private PlayerCollisions playerCollisions;

    /**
     * Set up mocked point calculator and player collisions handler.
     */
    @BeforeEach
    void setUp() {
        pointCalculator = mock(PointCalculator.class);
        playerCollisions = new PlayerCollisions(pointCalculator);
    }

    /**
     * Tests player colliding with a ghost kills the player and sets killer.
     */
    @Test
    void testPlayerCollidesWithGhost() {
        Player player = new PlayerFactory(new PacManSprites()).createPacMan();
        Ghost ghost = mock(Ghost.class);

        playerCollisions.collide(player, ghost);

        assertThat(player.isAlive()).isFalse();
        assertThat(player.getKiller()).isEqualTo(ghost);
        verify(pointCalculator).collidedWithAGhost(player, ghost);
    }

    /**
     * Tests ghost colliding with a player kills the player.
     */
    @Test
    void testGhostCollidesWithPlayer() {
        Player player = new PlayerFactory(new PacManSprites()).createPacMan();
        Ghost ghost = mock(Ghost.class);

        playerCollisions.collide(ghost, player);

        assertThat(player.isAlive()).isFalse();
        assertThat(player.getKiller()).isEqualTo(ghost);
        verify(pointCalculator).collidedWithAGhost(player, ghost);
    }


    /**
     * Tests player colliding with a pellet consumes the pellet.
     */
    @Test
    void testPlayerCollidesWithPellet() {
        Player player = mock(Player.class);
        Pellet pellet = mock(Pellet.class);

        playerCollisions.collide(player, pellet);

        verify(pointCalculator).consumedAPellet(player, pellet);
        verify(pellet).leaveSquare();
    }

    /**
     * Tests pellet colliding with player consumes the pellet.
     */
    @Test
    void testPelletCollidesWithPlayer() {
        Player player = mock(Player.class);
        Pellet pellet = mock(Pellet.class);

        playerCollisions.collide(pellet, player);

        verify(pointCalculator).consumedAPellet(player, pellet);
        verify(pellet).leaveSquare();
    }

    /**
     * Tests unhandled collisions such as pellet colliding with ghost.
     */
    @Test
    void testPelletCollidesWithGhost() {
        Pellet pellet = mock(Pellet.class);
        Ghost ghost = mock(Ghost.class);

        playerCollisions.collide(pellet, ghost);

        verifyZeroInteractions(pointCalculator);
        verifyZeroInteractions(pellet);
    }

    /**
     * Tests unhandled unit colliding with player.
     */
    @Test
    void testUnhandledUnitCollidesWithPlayer() {
        Unit customUnit = mock(Unit.class);
        Player player = mock(Player.class);

        playerCollisions.collide(customUnit, player);

        verifyZeroInteractions(pointCalculator);
    }
}
