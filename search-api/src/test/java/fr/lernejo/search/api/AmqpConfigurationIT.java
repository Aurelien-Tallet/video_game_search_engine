package fr.lernejo.search.api;

import org.junit.jupiter.api.Test;

public class AmqpConfigurationIT {
    @Test
    void queueWithGAME_INFO_QUEUE() {
        AmqpConfiguration amqpConfiguration = new AmqpConfiguration();
        amqpConfiguration.queue();
    }
}
