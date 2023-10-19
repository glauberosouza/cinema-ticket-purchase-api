package com.glauber.cinema.Ticket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glauber.cinema.Ticket.TicketApplication;
import com.glauber.cinema.Ticket.templates.PurchaseRequestTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = TicketApplication.class)
@Profile("test")
class PurchaseControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Deve registrar a compra de um ingresso com sucesso")
    public void shouldSaveAPurchase() throws Exception {
        //ARRANGE
        var purchase = PurchaseRequestTemplate.creation();
        //ACT
        String body = objectMapper.writeValueAsString(purchase);
        mockMvc.perform(post("/purchases")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(status().is2xxSuccessful());

        var purchases = purchaseRepository.findAll();
        //ASSETS
        assertEquals(1, purchases.size());
    }

}
/*
* @display name -> Deve registrar a compra de um ingresso com suceso.
* */