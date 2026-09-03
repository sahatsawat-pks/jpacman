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
 * Tests for the {@link Clyde} ghost AI behavior.
 */
@SuppressWarnings({"magicnumber", "PMD.AvoidDuplicateLiterals"})
class ClydeTest {


    private GhostMapParser parser;

    /**
     * Set up map parser with sprite factory.
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
     * Tests Clyde moving towards player when distance is greater than 8 spaces.
     */
    @Test
    void testClydeFarFromPlayer() {
        Level level = parser.parseMap(Lists.newArrayList(
            "##############",
            "#C         P #",
            "##############"
        ));
        Player player = new PlayerFactory(new PacManSprites()).createPacMan();
        level.registerPlayer(player);

        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertThat(clyde).isNotNull();

        Optional<Direction> move = clyde.nextAiMove();
        assertThat(move).contains(Direction.EAST);
    }

    /**
     * Tests Clyde retreating from player when distance is less than or equal to 8 spaces.
     */
    @Test
    void testClydeNearPlayer() {
        Level level = parser.parseMap(Lists.newArrayList(
            "##########",
            "# C   P  #",
            "##########"
        ));
        Player player = new PlayerFactory(new PacManSprites()).createPacMan();
        level.registerPlayer(player);

        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertThat(clyde).isNotNull();

        Optional<Direction> move = clyde.nextAiMove();
        // Facing player East, Clyde is near (distance <= 8), so moves West (away)
        assertThat(move).contains(Direction.WEST);
    }

    /**
     * Tests Clyde returns empty when there is no player on board.
     */
    @Test
    void testClydeNoPlayer() {
        Level level = parser.parseMap(Lists.newArrayList(
            "####",
            "#C #",
            "####"
        ));
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertThat(clyde).isNotNull();

        Optional<Direction> move = clyde.nextAiMove();
        assertThat(move).isEmpty();
    }

    /**
     * Tests Clyde returns empty when player is unreachable.
     */
    @Test
    void testClydeUnreachablePlayer() {
        Level level = parser.parseMap(Lists.newArrayList(
            "#######",
            "#C# P #",
            "#######"
        ));
        Player player = new PlayerFactory(new PacManSprites()).createPacMan();
        level.registerPlayer(player);

        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertThat(clyde).isNotNull();

        Optional<Direction> move = clyde.nextAiMove();
        assertThat(move).isEmpty();
    }
}
