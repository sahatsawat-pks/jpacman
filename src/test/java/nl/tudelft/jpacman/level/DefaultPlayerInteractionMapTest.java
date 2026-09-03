package nl.tudelft.jpacman.level;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.points.PointCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the {@link DefaultPlayerInteractionMap} class.
 */
class DefaultPlayerInteractionMapTest {

    private PointCalculator pointCalculator;
    private DefaultPlayerInteractionMap interactionMap;

    /**
     * Set up the interaction map with a mocked point calculator.
     */
    @BeforeEach
    void setUp() {
        pointCalculator = mock(PointCalculator.class);
        interactionMap = new DefaultPlayerInteractionMap(pointCalculator);
    }

    /**
     * Tests player colliding with ghost kills the player and sets killer.
     */
    @Test
    void testPlayerCollidesWithGhost() {
        Player player = new PlayerFactory(new nl.tudelft.jpacman.sprite.PacManSprites())
            .createPacMan();
        Ghost ghost = mock(Ghost.class);

        interactionMap.collide(player, ghost);

        assertThat(player.isAlive()).isFalse();
        assertThat(player.getKiller()).isEqualTo(ghost);
        verify(pointCalculator).collidedWithAGhost(player, ghost);
    }

    /**
     * Tests ghost colliding with player (symmetric collision).
     */
    @Test
    void testGhostCollidesWithPlayer() {
        Player player = new PlayerFactory(new nl.tudelft.jpacman.sprite.PacManSprites())
            .createPacMan();
        Ghost ghost = mock(Ghost.class);

        interactionMap.collide(ghost, player);

        assertThat(player.isAlive()).isFalse();
        assertThat(player.getKiller()).isEqualTo(ghost);
        verify(pointCalculator).collidedWithAGhost(player, ghost);
    }


    /**
     * Tests player colliding with pellet consumes pellet.
     */
    @Test
    void testPlayerCollidesWithPellet() {
        Player player = mock(Player.class);
        Pellet pellet = mock(Pellet.class);

        interactionMap.collide(player, pellet);

        verify(pointCalculator).consumedAPellet(player, pellet);
        verify(pellet).leaveSquare();
    }

    /**
     * Tests pellet colliding with player (symmetric collision).
     */
    @Test
    void testPelletCollidesWithPlayer() {
        Player player = mock(Player.class);
        Pellet pellet = mock(Pellet.class);

        interactionMap.collide(pellet, player);

        verify(pointCalculator).consumedAPellet(player, pellet);
        verify(pellet).leaveSquare();
    }
}
