package nl.tudelft.jpacman.points;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link PointCalculatorLoader}.
 */
@SuppressWarnings({"magicnumber", "PMD.AvoidDuplicateLiterals"})
class PointCalculatorLoaderTest {


    /**
     * Tests loading default point calculator strategy.
     */
    @Test
    void testLoadDefault() {
        PointCalculatorLoader loader = new PointCalculatorLoader();
        PointCalculator calculator = loader.load();

        assertThat(calculator).isNotNull();
    }
}
