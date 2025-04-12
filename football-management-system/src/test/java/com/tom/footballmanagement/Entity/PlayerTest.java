package com.tom.footballmanagement.Entity;

import com.tom.footballmanagement.DTO.PlayerResponseDTO;
import com.tom.footballmanagement.Enum.Position;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayerTest {

    @Test
    public void givenPlayer_whenToResponseDTO_thenReturnsCorrectDTO() {
        Player player = new Player(
                null,
                "Kylian",
                "Mbappé",
                LocalDate.of(1998, 12, 20),
                "French",
                Position.STRIKER,
                null,
                LocalDate.of(2028, 6, 30));

        PlayerResponseDTO responseDTO = player.toResponseDTO();
        assertThat(responseDTO.id()).isEqualTo(player.getId());
        assertThat(responseDTO.first_name()).isEqualTo(player.getFirst_name());
        assertThat(responseDTO.last_name()).isEqualTo(player.getLast_name());
        assertThat(responseDTO.date_of_birth()).isEqualTo(player.getDate_of_birth());
        assertThat(responseDTO.nationality()).isEqualTo(player.getNationality());
        assertThat(responseDTO.position()).isEqualTo(player.getPosition());
        assertThat(responseDTO.team()).isEqualTo(player.getTeam());
        assertThat(responseDTO.contract_end_date()).isEqualTo(player.getContract_end_date());
    }

}
