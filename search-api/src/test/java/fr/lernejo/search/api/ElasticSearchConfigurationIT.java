package fr.lernejo.search.api;

import org.junit.jupiter.api.Test;

public class ElasticSearchConfigurationIT {
    // Test the configuration of ElasticSearch
    @Test
    void configuration() {
        ElasticSearchConfiguration elasticSearchConfiguration = new ElasticSearchConfiguration();
        elasticSearchConfiguration.restHighLevelClient(
            "localhost",
            9200,
            "elastic",
            "admin"
        );
    }
}
