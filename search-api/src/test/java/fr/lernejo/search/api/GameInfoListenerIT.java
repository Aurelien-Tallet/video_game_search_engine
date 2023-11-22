package fr.lernejo.search.api;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GameInfoListenerIT {

    @Test
    void onMessage() throws Exception {
        RestHighLevelClient mockClient = mock(RestHighLevelClient.class);
        GameInfoListener listener = new GameInfoListener(mockClient);
        MessageProperties properties = new MessageProperties();
        properties.setHeader("game_id", "1");
        Message message = new Message("{\"title\":\"Test Game\"}".getBytes(), properties);
        listener.onMessage(message);
        ArgumentCaptor<IndexRequest> requestCaptor = ArgumentCaptor.forClass(IndexRequest.class);
        verify(mockClient).index(requestCaptor.capture(), eq(RequestOptions.DEFAULT));
        IndexRequest capturedRequest = requestCaptor.getValue();
        assertEquals("1", capturedRequest.id());
        assertEquals("Test Game", capturedRequest.sourceAsMap().get("title"));
    }
}
