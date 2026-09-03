package nl.tudelft.jpacman.ui;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link PacKeyListener}.
 */
@SuppressWarnings({"magicnumber", "PMD.AvoidDuplicateLiterals"})
class PacKeyListenerTest {


    private Action upAction;
    private PacKeyListener keyListener;
    private Component source;

    /**
     * Set up key listener with mapped UP key.
     */
    @BeforeEach
    void setUp() {
        upAction = mock(Action.class);
        Map<Integer, Action> keyMappings = new HashMap<>();
        keyMappings.put(KeyEvent.VK_UP, upAction);

        keyListener = new PacKeyListener(keyMappings);
        source = mock(Component.class);
    }

    /**
     * Tests pressing a mapped key triggers the action.
     */
    @Test
    void testMappedKeyPressed() {
        KeyEvent event = new KeyEvent(source, KeyEvent.KEY_PRESSED,
            System.currentTimeMillis(), 0, KeyEvent.VK_UP, ' ');

        keyListener.keyPressed(event);

        verify(upAction).doAction();
    }

    /**
     * Tests pressing an unmapped key does not trigger action.
     */
    @Test
    void testUnmappedKeyPressed() {
        KeyEvent event = new KeyEvent(source, KeyEvent.KEY_PRESSED,
            System.currentTimeMillis(), 0, KeyEvent.VK_DOWN, ' ');

        keyListener.keyPressed(event);

        verify(upAction, never()).doAction();
    }

    /**
     * Tests keyTyped and keyReleased events.
     */
    @Test
    void testKeyTypedAndReleased() {
        KeyEvent event = new KeyEvent(source, KeyEvent.KEY_TYPED,
            System.currentTimeMillis(), 0, KeyEvent.VK_UP, ' ');

        keyListener.keyTyped(event);
        keyListener.keyReleased(event);

        verify(upAction, never()).doAction();
    }
}
