package fr.lernejo.search.api;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.bytes.BytesArray;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*@RestController
public class GameController {

    private final RestHighLevelClient client;

    public GameController(RestHighLevelClient client) {
        this.client = client;
    }

    @GetMapping("/api/games")
    public List<Map<String, Object>> searchGames(@RequestParam String query, @RequestParam(defaultValue = "10") int size) throws Exception {
        QueryStringQueryBuilder queryBuilder = QueryBuilders.queryStringQuery(query);

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(queryBuilder);
        sourceBuilder.size(size);

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.source(sourceBuilder);

        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        var searchHits = response.getHits().getHits();
        return Arrays.stream(searchHits)
            .map(SearchHit::getSourceAsMap)
            .collect(Collectors.toList());
    }
}*/

// GENERATE A BASIC TEST FOR THE CONTROLLER

@SpringBootTest
public class GameControllerIT {

        private MockMvc mockMvc;

        @Mock
        private RestHighLevelClient client;

        @BeforeEach
        public void setUp() {
            MockitoAnnotations.initMocks(this);
            this.mockMvc = standaloneSetup(new GameController(client)).build();
        }

        @Test
        public void searchGames() throws Exception {
            SearchResponse response = mock(SearchResponse.class);
            SearchHits hits = mock(SearchHits.class);
            SearchHit hit = mock(SearchHit.class);
            when(client.search(any(SearchRequest.class), any(RequestOptions.class))).thenReturn(response);
            when(response.getHits()).thenReturn(hits);
            when(hits.getHits()).thenReturn(new SearchHit[]{hit});
            when(hit.getSourceAsMap()).thenReturn(Map.of("key1", "value1"));

            mockMvc.perform(get("/api/games?query=test"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"key1\":\"value1\"}]"))
                .andDo(MockMvcResultHandlers.print());
        }


}
