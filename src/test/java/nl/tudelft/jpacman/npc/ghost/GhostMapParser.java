package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.MapParser;
import nl.tudelft.jpacman.npc.Ghost;

import java.util.List;

/**
 * A test helper utility for writing the ghost unit tests.
 * A useful method for retrieving ghosts from your map would be:
 * findUnitInBoard in the Navigation class.
 */
public final class GhostMapParser extends MapParser {
    private final GhostFactory ghostFactory;

    /**
     * Creates a new enhanced map parser.
     * This map parser allows users to specify which ghost should be placed
     * at an exact location.
     *
     * @param levelFactory The factory providing the NPC objects and the level.
     * @param boardFactory The factory to create board elements.
     * @param ghostFactory the factory to create the ghosts.
     */
    public GhostMapParser(LevelFactory levelFactory, BoardFactory boardFactory,
                          GhostFactory ghostFactory) {
        super(levelFactory, boardFactory);
        this.ghostFactory = ghostFactory;
    }

    @Override
    protected void addSquare(Square[][] grid, List<Ghost> ghosts,
                             List<Square> startPositions, int x, int y, char c) {
        switch (c) {
            case 'C':
                grid[x][y] = makeGhostSquare(ghosts, ghostFactory.createClyde());
                break;
            case 'B':
                grid[x][y] = makeGhostSquare(ghosts, ghostFactory.createBlinky());
                break;
            case 'I':
                grid[x][y] = makeGhostSquare(ghosts, ghostFactory.createInky());
                break;
            case 'K':
                grid[x][y] = makeGhostSquare(ghosts, ghostFactory.createPinky());
                break;
            case 'S':
                Square playerSquare = getBoardCreator().createGround();
                grid[x][y] = playerSquare;
                startPositions.add(playerSquare);
                break;
            default:
                super.addSquare(grid, ghosts, startPositions, x, y, c);
        }
    }
}


