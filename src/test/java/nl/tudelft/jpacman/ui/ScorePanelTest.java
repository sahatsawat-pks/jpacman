package nl.tudelft.jpacman.ui;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.common.collect.Lists;
import nl.tudelft.jpacman.level.Player;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link ScorePanel}.
 */
@SuppressWarnings({"magicnumber", "PMD.AvoidDuplicateLiterals"})
class ScorePanelTest {


    /**
     * Tests refreshing score for alive and dead players.
     */
    @Test
    void testRefreshAliveAndDeadPlayer() {
        Player player1 = mock(Player.class);
        when(player1.getScore()).thenReturn(100);
        when(player1.isAlive()).thenReturn(true);

        Player player2 = mock(Player.class);
        when(player2.getScore()).thenReturn(50);
        when(player2.isAlive()).thenReturn(false);

        ScorePanel panel = new ScorePanel(Lists.newArrayList(player1, player2));
        panel.refresh();

        // Custom formatter test
        panel.setScoreFormatter((p) -> "PTS: " + p.getScore());
        panel.refresh();

        assertThat(panel.getComponentCount()).isEqualTo(4);
    }
}
