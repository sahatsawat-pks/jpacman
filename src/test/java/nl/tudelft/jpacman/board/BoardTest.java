package nl.tudelft.jpacman.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link Board} class.
 */
public class BoardTest {

    private Board board;

    /**
     * Sets up a simple 1x1 board before each test.
     */
    @BeforeEach
    void setBoard() {
        Square[][] grid = new Square[1][1];
        grid[0][0] = new BasicSquare();
        board = new Board(grid);
    }

    /**
     * Tests that width, height, and square content are correctly retrieved.
     */
    @Test
    void testBoardDimensionsAndContent() {
        assertThat(board.getWidth()).isEqualTo(1);
        assertThat(board.getHeight()).isEqualTo(1);
        assertThat(board.squareAt(0, 0)).isNotNull();
    }

    /**
     * Tests that creating a board with a null square throws an AssertionError.
     */
    @Test
    void testNullSquare() {
        Square[][] nullGrid = new Square[1][1];
        assertThatThrownBy(() -> new Board(nullGrid))
            .isInstanceOf(AssertionError.class);
    }

    /**
     * Tests withinBorders with coordinates within and outside the board.
     */
    @Test
    void testWithinBorders() {
        assertThat(board.withinBorders(0, 0)).isTrue();
        assertThat(board.withinBorders(-1, 0)).isFalse();
        assertThat(board.withinBorders(0, -1)).isFalse();
        assertThat(board.withinBorders(1, 0)).isFalse();
        assertThat(board.withinBorders(0, 1)).isFalse();
    }
}

