package com.zing.ledgerposting.api;

import com.zing.ledgerposting.db.entity.LedgerEntryEntity;
import com.zing.ledgerposting.model.LedgerBalance;
import com.zing.ledgerposting.model.Transaction;
import com.zing.ledgerposting.service.LedgerPostingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("lp")
public class LedgerPostingController {

    private LedgerPostingService ledgerPostingService;
    public LedgerPostingController(LedgerPostingService ledgerPostingService) {
        this.ledgerPostingService = ledgerPostingService;

    }

    /**
     * This api helps for ledger posting
     * @return String - posting message
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String postLedgerEntry(@Valid @RequestBody Transaction transaction) {
        return ledgerPostingService.createLedgerEntry(transaction);
    }

}
