package nl.tudelft.jpacman.level;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.Unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Tests for {@link Level} move, pellet count, and observer notifications.
 */
@SuppressWarnings({"magicnumber", "PMD.AvoidDuplicateLiterals"})
class LevelObserverAndMoveTest {

    private Level level;
    private Board board;
    private Square startSquare;
    private Square destinationSquare;
    private CollisionMap collisionMap;

    /**
     * Set up board, squares, and level before each test.
     */
    @BeforeEach
    void setUp() {
        board = mock(Board.class);
        startSquare = mock(Square.class);
        destinationSquare = mock(Square.class);
        collisionMap = mock(CollisionMap.class);

        when(startSquare.getSquareAt(Direction.EAST)).thenReturn(destinationSquare);
        level = new Level(board, new ArrayList<>(), Lists.newArrayList(startSquare), collisionMap);
    }

    /**
     * Tests moving unit when level is not in progress does nothing.
     */
    @Test
    void testMoveWhenNotInProgress() {
        Unit unit = mock(Unit.class);
        when(unit.hasSquare()).thenReturn(true);

        level.move(unit, Direction.EAST);

        verify(unit, never()).occupy(destinationSquare);
    }

    /**
     * Tests moving unit when destination square is accessible.
     */
    @Test
    void testMoveAccessibleDestination() {
        Player player = mock(Player.class);
        when(player.hasSquare()).thenReturn(true);
        when(player.getSquare()).thenReturn(startSquare);
        when(destinationSquare.isAccessibleTo(player)).thenReturn(true);
        when(destinationSquare.getOccupants()).thenReturn(new ArrayList<>());
        when(player.isAlive()).thenReturn(true);

        level.start();
        level.move(player, Direction.EAST);

        verify(player).occupy(destinationSquare);
    }

    /**
     * Tests moving unit when destination is inaccessible (e.g. wall).
     */
    @Test
    void testMoveInaccessibleDestination() {
        Player player = mock(Player.class);
        when(player.hasSquare()).thenReturn(true);
        when(player.getSquare()).thenReturn(startSquare);
        when(destinationSquare.isAccessibleTo(player)).thenReturn(false);
        when(player.isAlive()).thenReturn(true);

        level.start();
        level.move(player, Direction.EAST);

        verify(player, never()).occupy(destinationSquare);
    }

    /**
     * Tests adding and removing level observers.
     */
    @Test
    void testLevelObservers() {
        Level.LevelObserver observer = mock(Level.LevelObserver.class);
        level.addObserver(observer);
        level.removeObserver(observer);

        Player player = mock(Player.class);
        when(player.hasSquare()).thenReturn(true);
        when(player.getSquare()).thenReturn(startSquare);
        when(player.isAlive()).thenReturn(false);

        level.start();
        verify(observer, never()).levelLost();
    }
}
