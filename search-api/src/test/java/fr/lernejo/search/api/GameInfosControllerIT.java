package fr.lernejo.search.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class GameInfosControllerIT {
    @Test
    void getGameInfoValidURI(@Autowired MockMvc mockMvc) {
        assertAll(() -> mockMvc
            .perform(MockMvcRequestBuilders.get("/api/games?query=developer:CD Projekt Red"))
            .andExpect(MockMvcResultMatchers.status().isOk()));
    }
    @Test
     void getBadEndpoint(@Autowired MockMvc mockMvc) throws Exception {
        mockMvc
            .perform(MockMvcRequestBuilders.get("/api/failed"))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
