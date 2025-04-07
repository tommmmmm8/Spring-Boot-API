package com.tom.footballmanagement.Service;

import com.tom.footballmanagement.DTO.PlayerResponseDTO;
import com.tom.footballmanagement.Entity.Player;
import com.tom.footballmanagement.Enum.Position;
import com.tom.footballmanagement.Repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class PlayerServiceUnitTest {

    @Mock
    PlayerRepository playerRepository;

    @InjectMocks
    PlayerService playerService;

    private Player player;

    @BeforeEach
    public void setup(){
        player = new Player(
                null,
                "William",
                "Saliba",
                LocalDate.of(2001, 3, 24),
                "French",
                Position.CENTER_BACK,
                null,
                LocalDate.of(2025, 6, 30));
    }

    // getAllPlayers method
    @Test
    public void givenPlayersList_whenGetAllPlayers_thenReturnPlayersList() {
        Player player1 = new Player(
                null,
                "Kylian",
                "Mbappé",
                LocalDate.of(1998, 12, 20),
                "French",
                Position.STRIKER,
                null,
                LocalDate.of(2028, 6, 30));

        // given - precondition or setup
        given(playerRepository.findAll()).willReturn(List.of(player, player1));

        // when -  action or the behaviour that we are going test
        List<PlayerResponseDTO> playerList = playerService.getAllPlayers();

        // then - verify the output
        System.out.println(playerList);
        assertThat(playerList).isNotNull();
        assertThat(playerList.size()).isGreaterThan(1);
    }

    @Test
    public void givenNoPlayersInDatabase_whenGetAllPlayers_thenReturnEmptyList() {
        // given - precondition or setup
        given(playerRepository.findAll()).willReturn(List.of());

        // when -  action or the behaviour that we are going test
        List<PlayerResponseDTO> playerList = playerService.getAllPlayers();

        // then - verify the output
        System.out.println(playerList);
        assertThat(playerList).isEmpty();
    }

    /* getPlayer method */

    @Test
        public void givenValidId_whenGetPlayer_thenReturnPlayer() {
            Long Id = 0L;

            // given - precondition or setup
            given(playerRepository.findById(Id)).willReturn(Optional.ofNullable(player));

            // when -  action or the behaviour that we are going test
            PlayerResponseDTO playerResponse = playerService.getPlayer(Id);

            // then - verify the output
            assertThat(playerResponse).isNotNull();
            assertThat(playerResponse.id()).isEqualTo(player.getId());
            assertThat(playerResponse.first_name()).isEqualTo(player.getFirst_name());
            assertThat(playerResponse.last_name()).isEqualTo(player.getLast_name());
            assertThat(playerResponse.date_of_birth()).isEqualTo(player.getDate_of_birth());
            assertThat(playerResponse.nationality()).isEqualTo(player.getNationality());
            assertThat(playerResponse.position()).isEqualTo(player.getPosition());
            assertThat(playerResponse.team()).isEqualTo(player.getTeam());
            assertThat(playerResponse.contract_end_date()).isEqualTo(player.getContract_end_date());
        }

        @Test
        public void givenValidId_whenGetPlayer_thenThrowNotFoundException() {
            Long Id = 0L;

            // given - precondition or setup
            given(playerRepository.findById(Id)).willReturn(Optional.empty());

            // when -  action or the behaviour that we are going test
            ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> playerService.getPlayer(Id));

            // then - verify the output
            System.out.println(exception.getStatusCode());
            assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }

        @Test
        public void givenNullId_whenGetPlayer_thenThrowIllegalArgumentException() {
            Long Id = null;

            // when -  action or the behaviour that we are going test
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                    playerService.getPlayer(null));

            // then - verify the output
            assertThat(exception.getMessage()).isEqualTo("Player Id cannot be null");
        }

        @Test
        public void givenNegativeId_whenGetPlayer_thenThrowIllegalArgumentException() {
            Long Id = -1L;

            // when -  action or the behaviour that we are going test
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                    playerService.getPlayer(-1L));

            // then - verify the output
            assertThat(exception.getMessage()).isEqualTo("Player Id cannot be negative");
        }


}
