package com.glauber.cinema.Ticket.service.impl;

import com.glauber.cinema.Ticket.domain.model.Purchase;
import com.glauber.cinema.Ticket.domain.repository.PurchaseRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PurchaseServiceImplTest {
    @Mock
    private PurchaseRepository purchaseRepository;
    @InjectMocks
    private PurchaseServiceImpl purchaseService;
    @BeforeEach
    public void setUp() {
        Purchase purchase = new Purchase(
                1L,
                1,
                "A",
                1,
                LocalDate.now(),
                LocalTime.now(),
                BigDecimal.valueOf(20.0),
                1);

        Mockito.when(purchaseRepository.findById(1L)).thenReturn(Optional.of(purchase));
    }

    @AfterEach
    public void afterEach() {
        Mockito.reset(purchaseRepository);
    }

    @Test
    @DisplayName("Deve encontrar todas as compras")
    public void itMustFoundAllPurchases(){
        List<Purchase> purchaseList = new ArrayList<>();

        when(purchaseRepository.findAll()).thenReturn(purchaseList);

        List<Purchase> findAllPurchases = purchaseService.findAllPurchases();

        verify(purchaseRepository, times(1)).findAll();

        assertEquals(purchaseList, findAllPurchases);
    }
}