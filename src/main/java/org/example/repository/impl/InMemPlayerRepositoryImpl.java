package org.example.repository.impl;

import org.example.entity.Player;
import org.example.repository.PlayerRepository;

import javax.management.RuntimeErrorException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InMemPlayerRepositoryImpl implements PlayerRepository {
    private final List<Player> players;

    public InMemPlayerRepositoryImpl(List<Player> players) {
        this.players = players;
    }

    @Override
    public Player find(UUID id) {
        for (Player player : players) {
            if (player.getId() == id) {
                return player;
            }
        }
        ;
        return null;
    }

    @Override
    public Player insert(Player player) {
        players.add(player);
        return player;
    }

    @Override
    public void update(UUID id, Player player) {
        for (Player pl : players) {
            if (pl.getId() == id) {
                pl.setBalance(player.getBalance());
                pl.setPassword(player.getPassword());
            }
        }
    }

    @Override
    public void delete(UUID id) {
        players.removeIf(player -> player.getId() == id);
    }
}
