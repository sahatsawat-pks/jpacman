package nl.tudelft.jpacman.sprite;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.awt.Graphics;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link EmptySprite}.
 */
@SuppressWarnings({"magicnumber", "PMD.AvoidDuplicateLiterals"})
class EmptySpriteTest {


    /**
     * Tests empty sprite dimensions and split/draw behaviors.
     */
    @Test
    void testEmptySprite() {
        EmptySprite emptySprite = new EmptySprite();

        assertThat(emptySprite.getWidth()).isEqualTo(0);
        assertThat(emptySprite.getHeight()).isEqualTo(0);

        Sprite splitSprite = emptySprite.split(0, 0, 10, 10);
        assertThat(splitSprite).isInstanceOf(EmptySprite.class);

        Graphics graphics = mock(Graphics.class);
        emptySprite.draw(graphics, 0, 0, 10, 10);
    }
}
