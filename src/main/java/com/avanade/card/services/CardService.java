package com.avanade.card.services;

import com.avanade.card.entities.Card;
import com.avanade.card.enums.CardType;
import com.avanade.card.exceptions.DatabaseException;
import com.avanade.card.exceptions.ResourceNotFoundException;
import com.avanade.card.exceptions.UserFetchException;
import com.avanade.card.httpcliente.UserHttpClient;
import com.avanade.card.payloads.request.CardRequest;
import com.avanade.card.payloads.response.CardResponse;
import com.avanade.card.repositories.CardRepository;
import feign.FeignException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class CardService {

    private static Random random;
    private final CardRepository repository;
    private UserHttpClient userHttpClient;

    public CardService(CardRepository repository, UserHttpClient userHttpClient) {
        this.repository = repository;
        this.userHttpClient = userHttpClient;
    }

    public CardResponse create(CardRequest requestCard) {

        checkIfUserExists(requestCard.userId());

        String accountNumber = prepareRandomNumber(5);
        String securityCode = prepareRandomNumber(4);
        String cardNumber = prepareRandomNumber(14);
        CardType cardType = generateRandomCardType();
        LocalDate cardExpirationDate = LocalDate.now().plusYears(5);

        Card card = new Card();
        card.setHolderName(requestCard.holderName());
        card.setExpiry(cardExpirationDate);
        card.setSecurityCode(securityCode);
        card.setBankAccountId(accountNumber);
        card.setNumber(cardNumber);
        card.setType(cardType);
        card.setIsDependent(requestCard.isDependent());
        card.setClosingDay(requestCard.closingDay());
        card.setUserId(requestCard.userId());
        saveOrFail(card);

        return card.toResponse();
    }

    public List<CardResponse> getAllByUser(UUID id) {
        return repository.findByUserId(id)
                .stream()
                .map(Card::toResponse).collect(Collectors.toList());
    }

    public CardResponse getOne(UUID id) {
        return findOrFailById(id).toResponse();
    }

    public CardResponse update(UUID id, CardRequest requestCard) {
        Card card = findOrFailById(id);

        card.setUserId(requestCard.userId());
        card.setHolderName(requestCard.holderName());
        card.setIsDependent(requestCard.isDependent());
        card.setExpiry(requestCard.expirationDate());

        saveOrFail(card);

        return card.toResponse();
    }

    public String delete(UUID id) {
        repository.deleteById(id);
        return "Cartão " + id + " deletado com sucesso";
    }

    private Card findOrFailById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Card could not be found"));
    }

    private void saveOrFail(Card card) {
        try {
            repository.saveAndFlush(card);
        } catch (Exception e) {
            throw new DatabaseException("Error while saving card");
        }
    }

    private static Random getRandom() {
        if (Objects.isNull(random)) {
            random = new Random();
        }
        return random;
    }

    private String prepareRandomNumber(int length) {
        final int size = length <= 0 ? 1 : length;
        IntStream stream = getRandom().ints(size, 0, 9);
        StringBuilder builder = new StringBuilder();
        stream.forEachOrdered(builder::append);
        return builder.toString();
    }

    private CardType generateRandomCardType() {
        CardType[] cardTypes = CardType.values();
        random = getRandom();
        int index = random.nextInt(cardTypes.length);
        return cardTypes[index];
    }

    private void checkIfUserExists(UUID userId){
        try{
            userHttpClient.getUserById(userId);
        }catch (FeignException e){
            throw new UserFetchException("User not found.");
        }
    }



}
