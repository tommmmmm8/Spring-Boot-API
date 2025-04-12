package com.tom.footballmanagement.Service;

import com.tom.footballmanagement.DTO.PlayerResponseDTO;
import com.tom.footballmanagement.Entity.Player;
import com.tom.footballmanagement.Repository.PlayerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PlayerServiceIntegrationTest {


    private final PlayerRepository playerRepository;

    private final PlayerService playerService;

    @Autowired
    PlayerServiceIntegrationTest(PlayerRepository playerRepository, PlayerService playerService) {
        this.playerRepository = playerRepository;
        this.playerService = playerService;
    }

    @Test
    void whenNoPlayersInDb_thenFindAllReturnsEmptyList() {
        List<PlayerResponseDTO> players = playerService.getAllPlayers();

        assertThat(players).isEmpty();
    }

    @Test
    void givenNegativeId_whenGetPlayer_thenThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                playerService.getPlayer(-1L));
    }
}