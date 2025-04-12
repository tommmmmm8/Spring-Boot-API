package com.tom.footballmanagement.Service;

import com.tom.footballmanagement.DTO.CreatePlayerDTO;
import com.tom.footballmanagement.DTO.PlayerResponseDTO;
import com.tom.footballmanagement.Entity.Player;
import com.tom.footballmanagement.Entity.Team;
import com.tom.footballmanagement.Enum.Position;
import com.tom.footballmanagement.Repository.PlayerRepository;
import com.tom.footballmanagement.Repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SpringBootTest
public class PlayerServiceUnitTest {

    @Mock
    PlayerRepository playerRepository;

    @Mock
    TeamRepository teamRepository;

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

    private void assertPlayerResponseDTO(PlayerResponseDTO responseDTO, Player player) {
        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.id()).isEqualTo(player.getId());
        assertThat(responseDTO.first_name()).isEqualTo(player.getFirst_name());
        assertThat(responseDTO.last_name()).isEqualTo(player.getLast_name());
        assertThat(responseDTO.date_of_birth()).isEqualTo(player.getDate_of_birth());
        assertThat(responseDTO.nationality()).isEqualTo(player.getNationality());
        assertThat(responseDTO.position()).isEqualTo(player.getPosition());
        String teamName = player.getTeam() != null ? player.getTeam().getName() : null;
        assertThat(responseDTO.team()).isEqualTo(teamName);
        assertThat(responseDTO.contract_end_date()).isEqualTo(player.getContract_end_date());
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
        Long id = 0L;

        // given - precondition or setup
        given(playerRepository.findById(id)).willReturn(Optional.ofNullable(player));

        // when -  action or the behaviour that we are going test
        PlayerResponseDTO playerResponse = playerService.getPlayer(id);

        // then - verify the output
        assertPlayerResponseDTO(playerResponse, player);
    }

    @Test
    public void givenValidIdOfNonExistingPlayer_whenGetPlayer_thenThrowNotFoundException() {
        Long id = 0L;

        // given - precondition or setup
        given(playerRepository.findById(id)).willReturn(Optional.empty());

        // when -  action or the behaviour that we are going test
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> playerService.getPlayer(id));

        // then - verify the output
        System.out.println(exception.getStatusCode());
        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void givenNullId_whenGetPlayer_thenThrowIllegalArgumentException() {
        Long id = null;

        // when -  action or the behaviour that we are going test
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                playerService.getPlayer(id));
    }

    @Test
    public void givenNegativeId_whenGetPlayer_thenThrowIllegalArgumentException() {
        Long id = -1L;
        // when -  action or the behaviour that we are going test
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                playerService.getPlayer(id));
    }

    /* addPlayer method */

    @Test
    public void givenValidPlayer_whenAddPlayer_thenPlayerIsAdded() {
        CreatePlayerDTO createPlayerDTO = new CreatePlayerDTO(
                player.getFirst_name(),
                player.getLast_name(),
                player.getDate_of_birth(),
                player.getNationality(),
                player.getPosition(),
                player.getTeam(),
                player.getContract_end_date()
        );

        //   given - precondition or setup
        given(playerRepository.save(any(Player.class))).willAnswer( invocation -> {
            Player playerToSave = invocation.getArgument(0);
            ReflectionTestUtils.setField(playerToSave, "id", 1L);
            return playerToSave;
        });

        // when -  action or the behaviour that we are going test
        PlayerResponseDTO addedPlayer = playerService.addPlayer(createPlayerDTO);

        // then - verify the output
        assertThat(addedPlayer).isNotNull();
        assertThat(addedPlayer.id()).isNotEqualTo(player.getId()); // The id is going to be generated with Autoincrement
        assertThat(addedPlayer.first_name()).isEqualTo(player.getFirst_name());
        assertThat(addedPlayer.last_name()).isEqualTo(player.getLast_name());
        assertThat(addedPlayer.date_of_birth()).isEqualTo(player.getDate_of_birth());
        assertThat(addedPlayer.nationality()).isEqualTo(player.getNationality());
        assertThat(addedPlayer.position()).isEqualTo(player.getPosition());
        assertThat(addedPlayer.team()).isEqualTo(player.getTeam());
        assertThat(addedPlayer.contract_end_date()).isEqualTo(player.getContract_end_date());
    }

    @Test
    public void givenNullPLayer_whenAddPlayer_thenThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            playerService.addPlayer(null);
        });
    }

    /* modifyPlayer method */

    @Test
    public void givenValidUpdates_whenModifyPlayer_thenUpdatePlayerAndSave() {
        Long id = 1L;
        Map<String, Object> updates = Map.of(
                "first_name", "UpdatedName",
                "last_name", "UpdatedLastName",
                "contract_end_date", LocalDate.of(2025, 6, 30)
        );

        // given - precondition or setup
        given(playerRepository.findById(id)).willReturn(Optional.ofNullable(player));

        player.setFirst_name((String) updates.get("first_name"));
        player.setLast_name((String) updates.get("last_name"));
        player.setContract_end_date((LocalDate) updates.get("contract_end_date"));

        given(playerRepository.save(player)).willReturn(player);

        // when -  action or the behaviour that we are going test
        PlayerResponseDTO responseDTO = playerService.modifyPlayer(id, updates);

        // then - verify the output
        assertPlayerResponseDTO(responseDTO, player);
    }

    @Test
    public void givenValidTeamId_whenModifyPlayer_thenUpdatePlayer() {
        Team team = new Team(10L,"Newcastle United", LocalDate.now(), "St James' Park", null, "Premier League");
        Long playerId = 1L;
        Map<String, Object> updates = Map.of(
                "team", Map.of("id", team.getId())
        );

        // given - precondition or setup
        given(playerRepository.findById(playerId)).willReturn(Optional.ofNullable(player));
        player.setTeam(team);
        given(teamRepository.findById(team.getId())).willReturn(Optional.of(team));
        given(playerRepository.save(player)).willReturn(player);

        // when -  action or the behaviour that we are going test
        PlayerResponseDTO responseDTO = playerService.modifyPlayer(playerId, updates);

        // then - verify the output
        assertPlayerResponseDTO(responseDTO, player);
    }

    @Test
    public void givenValidTeamName_whenModifyPlayer_thenUpdatePlayer() {
        Team team = new Team(10L,"Newcastle United", LocalDate.now(), "St James' Park", null, "Premier League");
        Long playerId = 1L;
        Map<String, Object> updates = Map.of(
                "team", Map.of("name", team.getName())
        );

        // given - precondition or setup
        given(playerRepository.findById(playerId)).willReturn(Optional.ofNullable(player));
        player.setTeam(team);
        given(teamRepository.findByName(team.getName())).willReturn(team);
        given(playerRepository.save(player)).willReturn(player);

        // when -  action or the behaviour that we are going test
        PlayerResponseDTO responseDTO = playerService.modifyPlayer(playerId, updates);

        // then - verify the output
        assertPlayerResponseDTO(responseDTO, player);
    }

    @Test
    public void givenNullTeam_whenModifyPlayer_thenUpdatePlayerTeamToNull() {
        Team team = new Team(10L,"Newcastle United", LocalDate.now(), "St James' Park", null, "Premier League");
        Long id = 1L;
        Map<String, Object> updates = new HashMap<>();
        updates.put("team", null);

        player.setTeam(team);
        given(playerRepository.findById(id)).willReturn(Optional.ofNullable(player));
        given(playerRepository.save(player)).willReturn(player);

        PlayerResponseDTO responseDTO = playerService.modifyPlayer(id, updates);

        assertThat(responseDTO.team()).isNull();
    }

    @Test
    public void givenValidIdOfNonExistingPlayer_whenModifyPlayer_thenThrowNotFoundException() {
        Long id = 100L;
        Map<String, Object> updates = Map.of(
                "first_name", "UpdatedName",
                "last_name", "UpdatedLastName",
                "nationality", "UpdatedNationality"
        );

        given(playerRepository.findById(id)).willReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            playerService.modifyPlayer(id, updates);
        });

        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void givenValidIdOfNonExistingTeam_whenModifyPlayer_thenThrowNotFoundException() {
        Long id = 1L;
        Long teamId = 100L;
        Map<String, Object> updates = Map.of(
                "team", Map.of("id", teamId)
        );

        given(playerRepository.findById(id)).willReturn(Optional.ofNullable(player));
        given(teamRepository.findById(teamId)).willReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            playerService.modifyPlayer(id, updates);
        });

        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void givenNullId_whenModifyPlayer_thenThrowIllegalArgumentException() {
        Long id = null;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            playerService.modifyPlayer(id, Map.of("first_name", "UpdatedName"));
        });
    }

    @Test
    public void givenNegativeId_whenModifyPlayer_thenThrowIllegalArgumentException() {
        Long id = -1L;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            playerService.modifyPlayer(id, Map.of("first_name", "UpdatedName"));
        });
    }

    /* removePlayer method */

    @Test
    public void givenValidId_whenRemovePlayer_thenRemovePlayer() {
        Long id = 1L;

        given(playerRepository.existsById(id)).willReturn(true).willReturn(false);

        ResponseEntity<String> response = playerService.removePlayer(id);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    public void givenInvalidId_whenRemovePlayer_thenThrowNotFoundException() {
        Long id = 1L;

        given(playerRepository.existsById(id)).willReturn(false);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            playerService.removePlayer(id);
        });

        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
