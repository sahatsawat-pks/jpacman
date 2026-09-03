package nl.tudelft.jpacman.level;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.EnumMap;
import java.util.Map;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.sprite.AnimatedSprite;
import nl.tudelft.jpacman.sprite.Sprite;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link Player} class.
 */
@SuppressWarnings({"magicnumber", "PMD.AvoidDuplicateLiterals"})
class PlayerTest {


    private Player player;
    private Sprite northSprite;
    private AnimatedSprite deathSprite;

    /**
     * Sets up a player with directional and death sprites.
     */
    @BeforeEach
    void setUp() {
        Map<Direction, Sprite> spriteMap = new EnumMap<>(Direction.class);
        northSprite = mock(Sprite.class);
        Sprite eastSprite = mock(Sprite.class);
        Sprite southSprite = mock(Sprite.class);
        Sprite westSprite = mock(Sprite.class);

        spriteMap.put(Direction.NORTH, northSprite);
        spriteMap.put(Direction.EAST, eastSprite);
        spriteMap.put(Direction.SOUTH, southSprite);
        spriteMap.put(Direction.WEST, westSprite);

        deathSprite = mock(AnimatedSprite.class);
        player = new Player(spriteMap, deathSprite);
    }

    /**
     * Tests initial values of a player.
     */
    @Test
    void testInitialState() {
        assertThat(player.getScore()).isEqualTo(0);
        assertThat(player.isAlive()).isTrue();
        assertThat(player.getKiller()).isNull();
    }

    /**
     * Tests score accumulation.
     */
    @Test
    void testAddPoints() {
        player.addPoints(10);
        player.addPoints(25);
        assertThat(player.getScore()).isEqualTo(35);
    }

    /**
     * Tests player dying and killer assignment.
     */
    @Test
    void testDyingAndKiller() {
        Ghost killer = mock(Ghost.class);
        player.setAlive(false);
        player.setKiller(killer);

        assertThat(player.isAlive()).isFalse();
        assertThat(player.getKiller()).isEqualTo(killer);
        assertThat(player.getSprite()).isEqualTo(deathSprite);
    }

    /**
     * Tests player revived resets killer and returns directional sprite.
     */
    @Test
    void testReviveAndResetKiller() {
        Ghost killer = mock(Ghost.class);
        player.setAlive(false);
        player.setKiller(killer);

        player.setAlive(true);
        player.setDirection(Direction.NORTH);

        assertThat(player.isAlive()).isTrue();
        assertThat(player.getKiller()).isNull();
        assertThat(player.getSprite()).isEqualTo(northSprite);
    }
}

