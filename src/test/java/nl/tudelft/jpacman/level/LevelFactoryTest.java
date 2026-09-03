package nl.tudelft.jpacman.level;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.npc.ghost.Blinky;
import nl.tudelft.jpacman.npc.ghost.Clyde;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.npc.ghost.Inky;
import nl.tudelft.jpacman.npc.ghost.Pinky;
import nl.tudelft.jpacman.points.PointCalculator;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link LevelFactory}.
 */
@SuppressWarnings({"magicnumber", "PMD.AvoidDuplicateLiterals"})
class LevelFactoryTest {


    private LevelFactory levelFactory;

    /**
     * Sets up a LevelFactory instance before each test.
     */
    @BeforeEach
    void setUp() {
        PacManSprites sprites = new PacManSprites();
        GhostFactory ghostFactory = new GhostFactory(sprites);
        PointCalculator pointCalculator = mock(PointCalculator.class);
        levelFactory = new LevelFactory(sprites, ghostFactory, pointCalculator);
    }

    /**
     * Tests ghost creation cycling through Blinky, Inky, Pinky, Clyde.
     */
    @Test
    void testCreateGhostCycling() {
        Ghost g1 = levelFactory.createGhost();
        Ghost g2 = levelFactory.createGhost();
        Ghost g3 = levelFactory.createGhost();
        Ghost g4 = levelFactory.createGhost();

        assertThat(g1).isInstanceOf(Blinky.class);
        assertThat(g2).isInstanceOf(Inky.class);
        assertThat(g3).isInstanceOf(Pinky.class);
        assertThat(g4).isInstanceOf(Clyde.class);
    }

    /**
     * Tests pellet creation.
     */
    @Test
    void testCreatePellet() {
        Pellet pellet = levelFactory.createPellet();
        assertThat(pellet).isNotNull();
        assertThat(pellet.getValue()).isEqualTo(10);
    }

    /**
     * Tests level creation with board, ghosts, and start squares.
     */
    @Test
    void testCreateLevel() {
        Board board = mock(Board.class);
        Level level = levelFactory.createLevel(board, new ArrayList<>(), new ArrayList<>());
        assertThat(level).isNotNull();
        assertThat(level.getBoard()).isEqualTo(board);
    }
}
