package com.example.cofeeshop;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CoffeeController.class)
public class CoffeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Coffee coffee1;
    private Coffee coffee2;

    @BeforeEach
    void setUp() {
        coffee1 = new Coffee("Lungnnnnno");
        coffee2 = new Coffee("Nescafee");
    }

    @Test
    void testGetAllCoffees() throws Exception {
        mockMvc.perform(get("/coffees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(coffee1.getName())))
                .andExpect(jsonPath("$[1].name", is(coffee2.getName())));
    }

    @Test
    void testPostCoffee() throws Exception {
        Coffee newCoffee = new Coffee("Espresso");

        mockMvc.perform(post("/coffees/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCoffee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(newCoffee.getName())));
    }

    @Test
    void testPutCoffee() throws Exception {
        Coffee updatedCoffee = new Coffee("UpdatedCoffee");

        mockMvc.perform(put("/coffees/{id}", coffee1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCoffee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(updatedCoffee.getName())));
    }

    @Test
    void testDeleteCoffee() throws Exception {
        mockMvc.perform(delete("/coffees/{id}", coffee1.getId()))
                .andExpect(status().isOk());
    }
}
