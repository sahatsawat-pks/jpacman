package nl.tudelft.jpacman.level;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.npc.Ghost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the {@link CollisionInteractionMap} class.
 */
@SuppressWarnings({"magicnumber", "PMD.AvoidDuplicateLiterals"})
class CollisionInteractionMapTest {


    private CollisionInteractionMap collisionMap;

    /**
     * Set up an empty CollisionInteractionMap before each test.
     */
    @BeforeEach
    void setUp() {
        collisionMap = new CollisionInteractionMap();
    }

    /**
     * Tests a two-way (symmetric) collision handler.
     */
    @Test
    void testSymmetricCollision() {
        Player player = mock(Player.class);
        Ghost ghost = mock(Ghost.class);
        CollisionInteractionMap.CollisionHandler<Player, Ghost> handler =
            mock(CollisionInteractionMap.CollisionHandler.class);

        collisionMap.onCollision(Player.class, Ghost.class, handler);

        collisionMap.collide(player, ghost);
        collisionMap.collide(ghost, player);
        verify(handler, org.mockito.Mockito.times(2)).handleCollision(player, ghost);
    }

    /**
     * Tests an asymmetric (one-way) collision handler.
     */
    @Test
    void testAsymmetricCollision() {
        Player player = mock(Player.class);
        Pellet pellet = mock(Pellet.class);
        CollisionInteractionMap.CollisionHandler<Player, Pellet> handler =
            mock(CollisionInteractionMap.CollisionHandler.class);

        collisionMap.onCollision(Player.class, Pellet.class, false, handler);

        collisionMap.collide(player, pellet);
        collisionMap.collide(pellet, player);
        // Reverse direction should not be handled, total invocations should remain 1
        verify(handler, org.mockito.Mockito.times(1)).handleCollision(player, pellet);
    }

    /**
     * Tests collision when no handler is registered.
     */
    @Test
    void testUnhandledCollision() {
        Player player = mock(Player.class);
        Pellet pellet = mock(Pellet.class);

        // No handlers registered, should return silently
        collisionMap.collide(player, pellet);
        assertThat(player).isNotNull();
    }

    /**
     * Tests collision inheritance matching superclass handlers.
     */
    @Test
    void testInheritanceCollision() {
        Player player = new PlayerFactory(new nl.tudelft.jpacman.sprite.PacManSprites())
            .createPacMan();
        Pellet pellet = new Pellet(10, mock(nl.tudelft.jpacman.sprite.Sprite.class));
        CollisionInteractionMap.CollisionHandler<Unit, Unit> handler =
            mock(CollisionInteractionMap.CollisionHandler.class);

        collisionMap.onCollision(Unit.class, Unit.class, false, handler);

        collisionMap.collide(player, pellet);
        verify(handler).handleCollision(player, pellet);
    }
}


