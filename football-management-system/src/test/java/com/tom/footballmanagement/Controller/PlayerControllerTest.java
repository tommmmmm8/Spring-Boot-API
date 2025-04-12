package com.tom.footballmanagement.Controller;

import com.tom.footballmanagement.Entity.Player;
import com.tom.footballmanagement.Entity.Team;
import com.tom.footballmanagement.Enum.Position;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc
public class PlayerControllerTest {

    private MockMvc mockMvc;

    @Autowired
    public PlayerControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void testGetAllPlayers() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/players")
                )
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
}
