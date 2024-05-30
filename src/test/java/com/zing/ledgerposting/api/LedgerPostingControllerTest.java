package com.zing.ledgerposting.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zing.ledgerposting.config.LocalDateTimeAdapter;
import com.zing.ledgerposting.enums.TransactionType;
import com.zing.ledgerposting.exception.InvalidLedgerException;
import com.zing.ledgerposting.exception.LedgerNotFoundException;
import com.zing.ledgerposting.exception.LedgerPostingExceptionHandler;
import com.zing.ledgerposting.model.LedgerBalance;
import com.zing.ledgerposting.model.Transaction;
import com.zing.ledgerposting.service.LedgerPostingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@AutoConfigureMockMvc
public class LedgerPostingControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @InjectMocks
    private LedgerPostingController ledgerPostingController;
    Gson gson;

    @Mock
    private LedgerPostingService ledgerPostingService;

    @BeforeEach
    public void setUp() {
        this.mockMvc = standaloneSetup(ledgerPostingController)
                .setControllerAdvice(new LedgerPostingExceptionHandler()).build();
        MockitoAnnotations.initMocks(this);
        gson = new GsonBuilder()

                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
    }

    @Test
    public void createLedgerPosting_whenValidLedgerGiven_thenReturnStatus201()
            throws Exception {

        //given

        when(ledgerPostingService.createLedgerEntry(any()))
                .thenReturn(new String("created successfully."));
        //when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.post("/lp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountId\": \"33854564664654\",\"amount\": 1000,\"timestamp\": \"2024-05-17T22:33:44\",\"type\": \"dr\"}"))
                        .andExpect(status().isCreated());

        //thentransaction
        verify(ledgerPostingService, times(1)).createLedgerEntry(any());
    }

    @Test
    public void createLedgerPosting_whenInvalidLedgerGiven_thenReturnStatus400BadRequest()
            throws Exception {

        //given
       when(ledgerPostingService.createLedgerEntry(any()))
                .thenThrow(new InvalidLedgerException("Invalid entrty"));

        //when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.post("/lp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountId\": \"33854564664654\",\"amount\": 1000,\"timestamp\": \"2024-05-28T22:33:44\",\"type\": \"sgsgw\"}"))
                .andExpect(status().isBadRequest());
        //then
        verify(ledgerPostingService, times(0)).createLedgerEntry(any());
    }



}
