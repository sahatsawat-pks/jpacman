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
 * Tests for the {@link Inky} ghost AI behavior.
 */
@SuppressWarnings({"magicnumber", "PMD.AvoidDuplicateLiterals"})
class InkyTest {


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
     * Tests Inky navigating when both Blinky and Player exist.
     */
    @Test
    void testInkyMovesTowardsTarget() {
        Level level = parser.parseMap(Lists.newArrayList(
            "########################",
            "#B I  P                #",
            "########################"
        ));
        Player player = new PlayerFactory(new PacManSprites()).createPacMan();
        level.registerPlayer(player);
        player.setDirection(Direction.EAST);

        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertThat(inky).isNotNull();

        Optional<Direction> move = inky.nextAiMove();
        assertThat(move).contains(Direction.EAST);
    }


    /**
     * Tests Inky returns empty when Blinky is not on board.
     */
    @Test
    void testInkyNoBlinky() {
        Level level = parser.parseMap(Lists.newArrayList(
            "########",
            "#I   P #",
            "########"
        ));
        Player player = new PlayerFactory(new PacManSprites()).createPacMan();
        level.registerPlayer(player);

        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertThat(inky).isNotNull();

        Optional<Direction> move = inky.nextAiMove();
        assertThat(move).isEmpty();
    }

    /**
     * Tests Inky returns empty when Player is not on board.
     */
    @Test
    void testInkyNoPlayer() {
        Level level = parser.parseMap(Lists.newArrayList(
            "########",
            "#B  I  #",
            "########"
        ));
        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertThat(inky).isNotNull();

        Optional<Direction> move = inky.nextAiMove();
        assertThat(move).isEmpty();
    }

    /**
     * Tests Inky returns empty when target is unreachable.
     */
    @Test
    void testInkyUnreachableTarget() {
        Level level = parser.parseMap(Lists.newArrayList(
            "############",
            "#B# I  # P #",
            "############"
        ));
        Player player = new PlayerFactory(new PacManSprites()).createPacMan();
        level.registerPlayer(player);

        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertThat(inky).isNotNull();

        Optional<Direction> move = inky.nextAiMove();
        assertThat(move).isEmpty();
    }
}
