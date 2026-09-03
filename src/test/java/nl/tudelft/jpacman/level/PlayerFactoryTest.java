package nl.tudelft.jpacman.level;

import static org.assertj.core.api.Assertions.assertThat;

import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link PlayerFactory}.
 */
@SuppressWarnings({"magicnumber", "PMD.AvoidDuplicateLiterals"})
class PlayerFactoryTest {


    /**
     * Tests creating a PacMan player.
     */
    @Test
    void testCreatePacMan() {
        PacManSprites sprites = new PacManSprites();
        PlayerFactory factory = new PlayerFactory(sprites);

        Player player = factory.createPacMan();

        assertThat(player).isNotNull();
        assertThat(player.isAlive()).isTrue();
        assertThat(factory.getSprites()).isEqualTo(sprites);
    }
}
