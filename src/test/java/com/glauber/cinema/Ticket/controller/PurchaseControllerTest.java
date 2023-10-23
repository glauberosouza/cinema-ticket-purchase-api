package com.glauber.cinema.Ticket.controller;

import com.glauber.cinema.Ticket.TicketApplication;
import com.glauber.cinema.Ticket.configuration.JacksonConfig;
import com.glauber.cinema.Ticket.controller.converter.PurchaseConverter;
import com.glauber.cinema.Ticket.controller.request.PurchaseRequest;
import com.glauber.cinema.Ticket.controller.request.PurchaseUpdateRequest;
import com.glauber.cinema.Ticket.domain.model.Chair;
import com.glauber.cinema.Ticket.domain.model.Purchase;
import com.glauber.cinema.Ticket.domain.model.Room;
import com.glauber.cinema.Ticket.domain.model.Ticket;
import com.glauber.cinema.Ticket.domain.repository.PurchaseRepository;
import com.glauber.cinema.Ticket.domain.repository.TicketRepository;
import com.glauber.cinema.Ticket.exception.OccupiedChairException;
import com.glauber.cinema.Ticket.service.PurchaseService;
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
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = TicketApplication.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PurchaseControllerTest {
    private static final String TRUNCATE = "classpath:/db/sql/reset.sql";
    private static final String INSERT_INTO_ROOM_AND_CHAIR = "classpath:/db/sql/insert_into_room_and_chair.sql";
    private static final String Chair_To_Empty = "classpath:db/sql/reset_chair_empty.sql";
    private static final String Update_Chair_To_Occupied = "classpath:db/sql/update_chair_to_occupied.sql";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private PurchaseConverter converter;
    @Autowired
    private JacksonConfig config;
    @Autowired
    private PurchaseService purchaseService;
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
        purchaseRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve registrar a compra de um ingresso com sucesso")
    public void shouldSaveAPurchase() throws Exception {
        //ARRANGE
        PurchaseRequest purchaseRequest = new PurchaseRequest(
                1,
                "A",
                1,
                LocalDate.now(),
                LocalTime.now(),
                BigDecimal.valueOf(20.0), 1);


        //ACT
        String body = config.objectMapper().writeValueAsString(purchaseRequest);
        mockMvc.perform(post("/purchases")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(status().is2xxSuccessful());

        var purchases = purchaseRepository.findAll();
        //ASSETS
        assertEquals(1, purchases.size());
    }

    @Test
    @DisplayName("Não registrar a compra de um ingresso caso poltrona ocupada")
    @Sql(Update_Chair_To_Occupied)
    public void shouldFailIfTheSeatIsOccupied() throws Exception {
        //ARRANGE
        PurchaseRequest purchaseRequest = new PurchaseRequest(
                1,
                "A",
                1,
                LocalDate.now(),
                LocalTime.now(),
                BigDecimal.valueOf(20.0), 1);

        //ACT
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            String body = config.objectMapper().writeValueAsString(purchaseRequest);
            mockMvc.perform(post("/purchases")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body)
            );
        });
        // ASSERT
        if (exception.getCause() instanceof OccupiedChairException) {
            String errorMessage = exception.getCause().getMessage();
            assertEquals("Poltrona já ocupada", errorMessage);
        } else {
            fail("Exceção do tipo OccupiedChairException não está sendo lançada.");
        }
    }

    @Test
    @DisplayName("Deve listar todas as compras")
    public void ShouldListAllPurchases() throws Exception {
        //ARRANGE
        PurchaseRequest purchaseRequest = new PurchaseRequest(
                1,
                "A",
                1,
                LocalDate.now(),
                LocalTime.now(),
                BigDecimal.valueOf(20.0), 1);
        Purchase purchaseEntity = converter.toPurchaseEntity(purchaseRequest);


        //ACT
        String body = config.objectMapper().writeValueAsString(purchaseRequest);
        mockMvc.perform(get("/purchases")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is2xxSuccessful());

        purchaseService.save(purchaseEntity);
        var purchases = purchaseService.findAllPurchases();
        //ASSETS
        assertEquals(1, purchases.size());
    }

    @Test
    @DisplayName("Deve atualizar o preço e quantidade de uma compra")
    @Sql({TRUNCATE, Chair_To_Empty})
    public void shouldUpdatePriceAndQuantityFromPurchase() throws Exception {
        //ARRANGE
        PurchaseUpdateRequest purchaseUpdateRequest = new PurchaseUpdateRequest(BigDecimal.valueOf(60.0), 3);
        PurchaseRequest purchaseRequest = new PurchaseRequest(
                1,
                "A",
                1,
                LocalDate.now(),
                LocalTime.now(),
                BigDecimal.valueOf(20.0), 1);
        Purchase purchaseEntity = converter.toPurchaseEntity(purchaseRequest);
        purchaseEntity.setId(1L);
        purchaseService.save(purchaseEntity);

        var body = config.objectMapper().writeValueAsString(purchaseUpdateRequest);

        //ACT
        mockMvc.perform(put("/purchases" + "/1")
                .contentType(MediaType.APPLICATION_JSON).content(body)
        ).andExpect(status().is2xxSuccessful());

        //ASSETS
        var purchase = purchaseRepository.findById(1L).get();
        assertEquals(3, purchase.getQuantity());
        assertEquals(0, new BigDecimal("60.00").compareTo(purchase.getPrice()));

    }

    @Test
    @DisplayName("Deve deletar uma compra e seus dependentes")
    public void shouldDeletePurchase() throws Exception {
        //ARRANGE
        Purchase purchase = new Purchase(
                1L,
                1,
                "A",
                1,
                LocalDate.now(),
                LocalTime.now(),
                BigDecimal.valueOf(20.0),
                1);
        Room room = new Room();
        Chair chair = new Chair();
        Ticket ticket = new Ticket(
                room,
                LocalDate.now(),
                LocalTime.now(),
                BigDecimal.valueOf(20.0),
                purchase
        );

        ////ACT
        mockMvc.perform(delete("/purchases" + "/1")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is2xxSuccessful());

        var purchases = purchaseRepository.findAll();
        var tickets = ticketRepository.findAll();
        //ASSETS
        assertTrue(purchases.isEmpty());
        assertTrue(tickets.isEmpty());

    }

    @Test
    @DisplayName("Deve falhar caso numero da sala menor que 1")
    public void shouldFailIfTheRoomNumberIsLowerThanOne() throws Exception {
        PurchaseRequest purchaseRequest = new PurchaseRequest(
                0,
                "A",
                1,
                LocalDate.now(),
                LocalTime.now(),
                BigDecimal.valueOf(20.0),
                1);

        String body = config.objectMapper().writeValueAsString(purchaseRequest);
        mockMvc.perform(post("/purchases")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Deve falhar caso numero da sala null")
    public void shouldFailIfTheNumberOfTheRoomIsNull() throws Exception {
        PurchaseRequest purchaseRequest = new PurchaseRequest(
                null,
                "A",
                1,
                LocalDate.now(),
                LocalTime.now(),
                BigDecimal.valueOf(20.0),
                1);

        String body = config.objectMapper().writeValueAsString(purchaseRequest);
        mockMvc.perform(post("/purchases")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("Deve falhar caso fileira seja em branco")
    public void shouldFailIfTheLineIsBlank() throws Exception {
        PurchaseRequest purchaseRequest = new PurchaseRequest(
                1,
                "",
                1,
                LocalDate.now(),
                LocalTime.now(),
                BigDecimal.valueOf(20.0),
                1);

        String body = config.objectMapper().writeValueAsString(purchaseRequest);
        mockMvc.perform(post("/purchases")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Deve falhar caso numero da poltrona seja menor que 1")
    public void shouldFailIfTheNumberOfChairIsLessThanOne() throws Exception {
        PurchaseRequest purchaseRequest = new PurchaseRequest(
                1,
                "A",
                0,
                LocalDate.now(),
                LocalTime.now(),
                BigDecimal.valueOf(20.0),
                1);

        String body = config.objectMapper().writeValueAsString(purchaseRequest);
        mockMvc.perform(post("/purchases")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Deve falhar caso data seja em branco")
    public void shouldFailIfTheDataAreBlank() throws Exception {
        PurchaseRequest purchaseRequest = new PurchaseRequest(
                1,
                "A",
                1,
                null,
                LocalTime.now(),
                BigDecimal.valueOf(20.0),
                1);


        String body = config.objectMapper().writeValueAsString(purchaseRequest);
        mockMvc.perform(post("/purchases")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Deve falhar caso sessão seja em branco")
    public void shouldFailIfTheSessionIsBlank() throws Exception {
        PurchaseRequest purchaseRequest = new PurchaseRequest(
                1,
                "A",
                1,
                LocalDate.now(),
                null,
                BigDecimal.valueOf(20.0),
                1);

        String body = config.objectMapper().writeValueAsString(purchaseRequest);
        mockMvc.perform(post("/purchases")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(status().is4xxClientError());
    }
    @Test
    @DisplayName("Deve falhar caso preço seja menor que 1")
    public void souldFailIfThePriceIsLessThanOne() throws Exception {
        PurchaseRequest purchaseRequest = new PurchaseRequest(
                1,
                "A",
                1,
                LocalDate.now(),
                LocalTime.now(),
                BigDecimal.valueOf(0),
                1);

        String body = config.objectMapper().writeValueAsString(purchaseRequest);
        mockMvc.perform(post("/purchases")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(status().is4xxClientError());
    }
    @Test
    @DisplayName("Deve falhar caso quantidade seja menor que 1")
    public void shouldFailIfTheQuantityIsLessThanOne() throws Exception {
        PurchaseRequest purchaseRequest = new PurchaseRequest(
                1,
                "A",
                1,
                LocalDate.now(),
                LocalTime.now(),
                BigDecimal.valueOf(0),
                0);

        String body = config.objectMapper().writeValueAsString(purchaseRequest);
        mockMvc.perform(post("/purchases")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(status().is4xxClientError());
    }
}