package nl.tudelft.jpacman.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.points.PointCalculator;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link GameFactory}.
 */
@SuppressWarnings({"magicnumber", "PMD.AvoidDuplicateLiterals"})
class GameFactoryTest {


    /**
     * Tests creating a single player game.
     */
    @Test
    void testCreateSinglePlayerGame() {
        PlayerFactory playerFactory = mock(PlayerFactory.class);
        Player player = mock(Player.class);
        when(playerFactory.createPacMan()).thenReturn(player);

        GameFactory gameFactory = new GameFactory(playerFactory);
        Level level = mock(Level.class);
        PointCalculator pointCalculator = mock(PointCalculator.class);

        Game game = gameFactory.createSinglePlayerGame(level, pointCalculator);

        assertThat(game).isNotNull();
        assertThat(gameFactory.getPlayerFactory()).isEqualTo(playerFactory);
        assertThat(game.getPlayers()).containsExactly(player);
    }
}
