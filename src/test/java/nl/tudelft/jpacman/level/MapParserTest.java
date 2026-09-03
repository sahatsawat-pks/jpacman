package nl.tudelft.jpacman.level;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

import com.google.common.collect.Lists;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import nl.tudelft.jpacman.PacmanConfigurationException;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.points.PointCalculator;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link MapParser}.
 */
@SuppressWarnings({"magicnumber", "PMD.AvoidDuplicateLiterals"})
class MapParserTest {


    private MapParser parser;

    /**
     * Set up parser with real factories.
     */
    @BeforeEach
    void setUp() {
        PacManSprites sprites = new PacManSprites();
        LevelFactory levelFactory = new LevelFactory(
            sprites,
            new GhostFactory(sprites),
            mock(PointCalculator.class));
        BoardFactory boardFactory = new BoardFactory(sprites);
        parser = new MapParser(levelFactory, boardFactory);
    }

    /**
     * Tests parsing a valid map containing all supported characters.
     */
    @Test
    void testValidMap() {
        Level level = parser.parseMap(Lists.newArrayList(
            "#####",
            "# . #",
            "# G #",
            "# P #",
            "#####"
        ));
        assertThat(level).isNotNull();
        assertThat(level.getBoard().getWidth()).isEqualTo(5);
        assertThat(level.getBoard().getHeight()).isEqualTo(5);
        assertThat(level.remainingPellets()).isEqualTo(1);
    }

    /**
     * Tests parsing map from InputStream.
     *
     * @throws IOException when reading stream fails.
     */
    @Test
    void testParseFromInputStream() throws IOException {
        String mapString = "###\n#P#\n###";
        try (InputStream stream = new ByteArrayInputStream(
            mapString.getBytes(StandardCharsets.UTF_8))) {
            Level level = parser.parseMap(stream);
            assertThat(level).isNotNull();
            assertThat(level.getBoard().getWidth()).isEqualTo(3);
        }
    }

    /**
     * Tests null input text throws PacmanConfigurationException.
     */
    @Test
    void testNullMap() {
        assertThatThrownBy(() -> parser.parseMap((List<String>) null))
            .isInstanceOf(PacmanConfigurationException.class)
            .hasMessage("Input text cannot be null.");
    }

    /**
     * Tests empty list of rows throws PacmanConfigurationException.
     */
    @Test
    void testEmptyMap() {
        assertThatThrownBy(() -> parser.parseMap(Collections.emptyList()))
            .isInstanceOf(PacmanConfigurationException.class)
            .hasMessage("Input text must consist of at least 1 row.");
    }

    /**
     * Tests empty line in map throws PacmanConfigurationException.
     */
    @Test
    void testEmptyLineInMap() {
        assertThatThrownBy(() -> parser.parseMap(Lists.newArrayList("")))
            .isInstanceOf(PacmanConfigurationException.class)
            .hasMessage("Input text lines cannot be empty.");
    }

    /**
     * Tests unequal row widths throw PacmanConfigurationException.
     */
    @Test
    void testUnequalWidths() {
        assertThatThrownBy(() -> parser.parseMap(Lists.newArrayList("###", "#")))
            .isInstanceOf(PacmanConfigurationException.class)
            .hasMessage("Input text lines are not of equal width.");
    }

    /**
     * Tests invalid character throws PacmanConfigurationException.
     */
    @Test
    void testInvalidCharacter() {
        assertThatThrownBy(() -> parser.parseMap(Lists.newArrayList("X")))
            .isInstanceOf(PacmanConfigurationException.class)
            .hasMessageContaining("Invalid character");
    }

    /**
     * Tests non-existent resource name throws PacmanConfigurationException.
     */
    @Test
    void testNonExistentResource() {
        assertThatThrownBy(() -> parser.parseMap("/nonexistent_board.txt"))
            .isInstanceOf(PacmanConfigurationException.class)
            .hasMessageContaining("Could not get resource for");
    }
}
