package com.avanade.card.controllers;

import com.avanade.card.payloads.request.CardRequest;
import com.avanade.card.payloads.response.CardResponse;
import com.avanade.card.services.CardService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("cards")
public class CardController {
    private final CardService service;

    public CardController(CardService service) {
        this.service = service;
    }

    @PostMapping
    public CardResponse create(@RequestBody @Valid CardRequest dto){
        return service.create(dto);
    }

    @GetMapping("/user/{id}")
    public List<CardResponse> getAllByUser(@PathVariable UUID id){
        return service.getAllByUser(id);
    }

    @GetMapping("/{id}")
    public CardResponse getOne(@PathVariable UUID id){
        return service.getOne(id);
    }

    @PutMapping("/{id}")
    public CardResponse edit(@PathVariable UUID id, @RequestBody @Valid CardRequest cartaoRequest){
        return service.update(id, cartaoRequest);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable UUID id){
        return service.delete(id);
    }
}
