package nl.tudelft.jpacman;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link PacmanConfigurationException}.
 */
@SuppressWarnings({"magicnumber", "PMD.AvoidDuplicateLiterals"})
class PacmanConfigurationExceptionTest {


    /**
     * Tests constructor with message only.
     */
    @Test
    void testMessageConstructor() {
        PacmanConfigurationException exception =
            new PacmanConfigurationException("Error message");
        assertThat(exception.getMessage()).isEqualTo("Error message");
        assertThat(exception.getCause()).isNull();
    }

    /**
     * Tests constructor with message and cause.
     */
    @Test
    void testMessageAndCauseConstructor() {
        Throwable cause = new IllegalArgumentException("Root cause");
        PacmanConfigurationException exception =
            new PacmanConfigurationException("Error message", cause);
        assertThat(exception.getMessage()).isEqualTo("Error message");
        assertThat(exception.getCause()).isEqualTo(cause);
    }
}
