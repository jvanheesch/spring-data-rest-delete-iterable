package com.github.jvanheesch;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootConfiguration
@EnableAutoConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookResourceTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void givenARepositoryWithDeleteIterable_whenSendHttpDeleteToItemResource_ThenResponseStatusCodeShouldBeSuccessful() {
        String json = "{\"title\":\"abc\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        RequestEntity<String> createBookRequest = new RequestEntity<>(json, headers, HttpMethod.POST, URI.create("http://localhost:" + port + "/books"));
        ResponseEntity<Book> createBookResponse = restTemplate.exchange(createBookRequest, Book.class);

        assertThat(createBookResponse.getStatusCode())
                .isEqualTo(HttpStatus.CREATED);

        RequestEntity<String> deleteBookRequest = new RequestEntity<>(HttpMethod.DELETE, createBookResponse.getHeaders().getLocation());
        ResponseEntity<?> deleteBookResponse = restTemplate.exchange(deleteBookRequest, Void.class);

        assertThat(deleteBookResponse.getStatusCode())
                .matches(HttpStatus::is2xxSuccessful, "is 2xx");
    }
}
