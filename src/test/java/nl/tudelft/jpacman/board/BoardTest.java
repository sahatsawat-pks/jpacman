package nl.tudelft.jpacman.board;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardTest {

    private Board board;
    private Board nullBoard;

    @BeforeEach
    void setBoard() {
        Square[][] grid = new Square[1][1];
        grid[0][0] = new BasicSquare();
        board = new Board(grid);
    }

    @Test
    void testBoardDimensionsAndContent() {
        assertThat(board.getWidth()).isEqualTo(1);
        assertThat(board.getHeight()).isEqualTo(1);
        assertThat(board.squareAt(0,0)).isNotNull();
    }

    @Test
    void testNullSquare() {
        Square[][] nullGrid = new Square[1][1];
        nullBoard = new Board(nullGrid);

        assertThat(nullBoard.squareAt(0,0)).isNull();
    }

}
