package com.example.cofeeshop;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CoffeeControllerRestTemplateTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void postCoffee() {
        // Given
        Coffee newCoffee = new Coffee( "Latte");

        // When
        ResponseEntity<Coffee> postResponse = restTemplate.postForEntity("/coffees/new", newCoffee, Coffee.class);
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());

        // Then
        ResponseEntity<Coffee[]> getResponse = restTemplate.getForEntity("/coffees", Coffee[].class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        Coffee[] coffees = getResponse.getBody();
        assertNotNull(coffees);

        boolean found = Arrays.stream(coffees).anyMatch(coffee -> coffee.getName().equals(newCoffee.getName()) && coffee.getId().equals(newCoffee.getId()));
        assertTrue(found, "The new coffee should be present in the list of coffees");
    }
}