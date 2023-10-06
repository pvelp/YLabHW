package org.example.repository;

import org.example.entity.Player;

import java.util.UUID;

public interface PlayerRepository {
    Player find(UUID id);
    Player insert(Player player);
    void update(UUID id, Player player);
    void delete(UUID id);

}
