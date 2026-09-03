package nl.tudelft.jpacman.npc.ghost;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import com.google.common.collect.Lists;
import java.util.Optional;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.points.PointCalculator;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link Blinky} ghost AI behavior.
 */
@SuppressWarnings({"magicnumber", "PMD.AvoidDuplicateLiterals"})
class BlinkyTest {


    private GhostMapParser parser;

    /**
     * Set up map parser.
     */
    @BeforeEach
    void setUp() {
        PacManSprites sprites = new PacManSprites();
        LevelFactory levelFactory = new LevelFactory(
            sprites,
            new GhostFactory(sprites),
            mock(PointCalculator.class));
        parser = new GhostMapParser(levelFactory, new BoardFactory(sprites),
            new GhostFactory(sprites));
    }

    /**
     * Tests Blinky moving towards player along the shortest path.
     */
    @Test
    void testBlinkyMovesTowardsPlayer() {
        Level level = parser.parseMap(Lists.newArrayList(
            "#######",
            "#B  P #",
            "#######"
        ));
        Player player = new PlayerFactory(new PacManSprites()).createPacMan();
        level.registerPlayer(player);

        Blinky blinky = Navigation.findUnitInBoard(Blinky.class, level.getBoard());
        assertThat(blinky).isNotNull();

        Optional<Direction> move = blinky.nextAiMove();
        assertThat(move).contains(Direction.EAST);
    }

    /**
     * Tests Blinky returns empty when no player is registered.
     */
    @Test
    void testBlinkyNoPlayer() {
        Level level = parser.parseMap(Lists.newArrayList(
            "#####",
            "#B  #",
            "#####"
        ));
        Blinky blinky = Navigation.findUnitInBoard(Blinky.class, level.getBoard());
        assertThat(blinky).isNotNull();

        Optional<Direction> move = blinky.nextAiMove();
        assertThat(move).isEmpty();
    }

    /**
     * Tests Blinky returns empty when player is blocked behind walls.
     */
    @Test
    void testBlinkyUnreachablePlayer() {
        Level level = parser.parseMap(Lists.newArrayList(
            "#######",
            "#B# P #",
            "#######"
        ));
        Player player = new PlayerFactory(new PacManSprites()).createPacMan();
        level.registerPlayer(player);

        Blinky blinky = Navigation.findUnitInBoard(Blinky.class, level.getBoard());
        assertThat(blinky).isNotNull();

        Optional<Direction> move = blinky.nextAiMove();
        assertThat(move).isEmpty();
    }
}
