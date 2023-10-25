package com.glauber.cinema.Ticket.controller;

import com.glauber.cinema.Ticket.TicketApplication;
import com.glauber.cinema.Ticket.configuration.JacksonConfig;
import com.glauber.cinema.Ticket.controller.converter.TicketConverter;
import com.glauber.cinema.Ticket.controller.request.TicketUpdateRequest;
import com.glauber.cinema.Ticket.domain.model.Ticket;
import com.glauber.cinema.Ticket.domain.repository.PurchaseRepository;
import com.glauber.cinema.Ticket.domain.repository.TicketRepository;
import com.glauber.cinema.Ticket.service.TicketService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = TicketApplication.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TicketControllerTest {

    private final static String Puchase_With_Childrens = "classpath:/db/sql/purchase_with_childrens.sql";
    private static final String TRUNCATE = "classpath:/db/sql/reset.sql";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private TicketConverter converter;
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private JacksonConfig config;
    @Autowired
    private DataSource dataSource;

    @BeforeAll
    void initDB() {
        var databaseSeeder = new ResourceDatabasePopulator();
        databaseSeeder
                .addScript(new ClassPathResource("/db/sql/insert_into_room_and_chair.sql"));
        databaseSeeder.execute(dataSource);
    }

    @AfterEach
    public void cleanUp() {
        ticketRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve listar todas os tickets")
    @Sql({TRUNCATE,Puchase_With_Childrens})
    public void itMustListAllTheTickets() throws Exception {
        //ARRANGE
        //ACT

        mockMvc.perform(get("/tickets")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is2xxSuccessful());

        List<Ticket> allTickets = ticketRepository.findAll();

        //ASSETS
        assertEquals(1, allTickets.size());
    }

    @Test
    @DisplayName("Deve atualizar um ticket")
    @Sql({TRUNCATE,Puchase_With_Childrens})
    public void itMustUpdateTheTicket() throws Exception {
        // Arrange
        var ticketUpdate = new TicketUpdateRequest(BigDecimal.valueOf(40.50));
        ticketService.update(1L, ticketUpdate);
        String body = config.objectMapper().writeValueAsString(ticketUpdate);
        mockMvc.perform(put("/tickets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().is2xxSuccessful());
        Ticket ticket = ticketRepository.findById(1L).orElseThrow();
        Assertions.assertEquals(40.5, ticket.getPrice().setScale(2, RoundingMode.HALF_UP).doubleValue());
    }

    @Test
    @DisplayName("Deve deletar um ticket")
    @Sql({TRUNCATE,Puchase_With_Childrens})
    public void itMustDeleteTheTicket() throws Exception {
        //ARRANGE
        //ACT
        mockMvc.perform(delete("/tickets/1")
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(
                                status().isNoContent()
        );
        //ASSERTS
        List<Ticket> allTickets = ticketRepository.findAll();
        Assertions.assertEquals(0, allTickets.size());
    }
    @Test
    @DisplayName("Deve falhar caso preço na request de update seja menor que 1")
    @Sql({TRUNCATE,Puchase_With_Childrens})
    public void shouldFailIfThePriceInTheUpdateRequestIsLessThanOne() throws Exception {
        // Arrange
        var ticketUpdate = new TicketUpdateRequest(BigDecimal.valueOf(00.00));
        ticketService.update(1L, ticketUpdate);
        String body = config.objectMapper().writeValueAsString(ticketUpdate);
        mockMvc.perform(put("/tickets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().is4xxClientError());
    }
}