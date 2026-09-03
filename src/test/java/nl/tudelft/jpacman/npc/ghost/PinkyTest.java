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
 * Tests for the {@link Pinky} ghost AI behavior.
 */
@SuppressWarnings({"magicnumber", "PMD.AvoidDuplicateLiterals"})
class PinkyTest {


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
     * Tests Pinky moving towards destination 4 squares ahead of player.
     */
    @Test
    void testPinkyMovesTowardsTarget() {
        Level level = parser.parseMap(Lists.newArrayList(
            "##############",
            "#K   P       #",
            "##############"
        ));
        Player player = new PlayerFactory(new PacManSprites()).createPacMan();
        level.registerPlayer(player);
        player.setDirection(Direction.EAST);

        Pinky pinky = Navigation.findUnitInBoard(Pinky.class, level.getBoard());
        assertThat(pinky).isNotNull();

        Optional<Direction> move = pinky.nextAiMove();
        assertThat(move).contains(Direction.EAST);
    }

    /**
     * Tests Pinky returns empty when no player is present.
     */
    @Test
    void testPinkyNoPlayer() {
        Level level = parser.parseMap(Lists.newArrayList(
            "#####",
            "#K  #",
            "#####"
        ));
        Pinky pinky = Navigation.findUnitInBoard(Pinky.class, level.getBoard());
        assertThat(pinky).isNotNull();

        Optional<Direction> move = pinky.nextAiMove();
        assertThat(move).isEmpty();
    }

    /**
     * Tests Pinky returns empty when destination is unreachable.
     */
    @Test
    void testPinkyUnreachableTarget() {
        Level level = parser.parseMap(Lists.newArrayList(
            "########",
            "#K## P #",
            "########"
        ));
        Player player = new PlayerFactory(new PacManSprites()).createPacMan();
        level.registerPlayer(player);
        player.setDirection(Direction.EAST);

        Pinky pinky = Navigation.findUnitInBoard(Pinky.class, level.getBoard());
        assertThat(pinky).isNotNull();

        Optional<Direction> move = pinky.nextAiMove();
        assertThat(move).isEmpty();
    }
}
