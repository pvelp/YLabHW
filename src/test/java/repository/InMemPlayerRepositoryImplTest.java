package repository;

import org.example.entity.Player;
import org.example.repository.PlayerRepository;
import org.example.repository.impl.InMemPlayerRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
class InMemPlayerRepositoryImplTest {
    private PlayerRepository repository;
    private List<Player> players;

    @BeforeEach
    void setUp() {
        players = new ArrayList<>();
        repository = new InMemPlayerRepositoryImpl(players);
    }

    @Test
    public void testFindShouldReturnPlayerWhenPlayerExists() {
        // Arrange
        UUID playerId = UUID.randomUUID();
        Player player = new Player(playerId, "username", "password", 0L, false);
        players.add(player);

        // Act
        Player foundPlayer = repository.find(playerId);

        // Assert
        assertNotNull(foundPlayer);
        assertEquals(player, foundPlayer);
    }

    @Test
    public void testFindShouldReturnNullWhenPlayerDoesNotExist() {
        // Arrange
        UUID playerId = UUID.randomUUID();

        // Act
        Player foundPlayer = repository.find(playerId);

        // Assert
        assertNull(foundPlayer);
    }

    @Test
    public void testFindByUsernameShouldReturnPlayerWhenPlayerExists() {
        // Arrange
        String username = "username";
        Player player = new Player(UUID.randomUUID(), username, "password", 0L, false);
        players.add(player);

        // Act
        Player foundPlayer = repository.findByUsername(username);

        // Assert
        assertNotNull(foundPlayer);
        assertEquals(player, foundPlayer);
    }

    @Test
    public void testFindByUsernameShouldReturnNullWhenPlayerDoesNotExist() {
        // Arrange
        String username = "username";

        // Act
        Player foundPlayer = repository.findByUsername(username);

        // Assert
        assertNull(foundPlayer);
    }

    @Test
    public void testFindAllShouldReturnAllPlayers() {
        // Arrange
        Player player1 = new Player(UUID.randomUUID(), "username1", "password1", 0L, false);
        Player player2 = new Player(UUID.randomUUID(), "username2", "password2", 0L, false);
        players.add(player1);
        players.add(player2);

        // Act
        List<Player> playerList = repository.findAll();

        // Assert
        assertEquals(players.size(), playerList.size());
        assertTrue(playerList.contains(player1));
        assertTrue(playerList.contains(player2));
    }

    @Test
    public void testInsertShouldAddPlayerToList() {
        // Arrange
        Player player = new Player(UUID.randomUUID(), "username", "password", 0L, false);

        // Act
        Player insertedPlayer = repository.insert(player);

        // Assert
        assertTrue(players.contains(player));
        assertEquals(player, insertedPlayer);
    }

    @Test
    public void testDeleteShouldRemovePlayerFromList() {
        // Arrange
        Player player = new Player(UUID.randomUUID(), "username", "password",  0L, false);
        players.add(player);

        // Act
        repository.delete(player.getId());

        // Assert
        assertFalse(players.contains(player));
    }
}