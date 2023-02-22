package com.kevinlago.javaspringapikeysecuredapi.service;

import com.kevinlago.javaspringapikeysecuredapi.dao.Fortune;
import com.kevinlago.javaspringapikeysecuredapi.repo.FortuneRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class FortuneService {
    FortuneRepository fortuneRepository;
    Random random;

    public FortuneService(FortuneRepository fortuneRepository) {
        this.fortuneRepository = fortuneRepository;
        this.random = new Random();
    }

    public Fortune createFortune(String phrase) {
        Fortune fortune = new Fortune(phrase);

        return fortuneRepository.save(fortune);
    }

    public Fortune getFortune(long id) {
        Fortune fortune = fortuneRepository.findById(id).orElseThrow();
        fortune.setLuckyNumbers(generateLuckyNumbers());

        return fortune;
    }

    public Fortune getRandomFortune() {
        // ToDo: This find all approach prevents fetching a deleted record. I.E
        //  Fortune of ID 2 is deleted, but random generates a value of 2
        //  A better/faster approach would be an sql query
        List<Fortune> fortunes = fortuneRepository.findAll();

        return fortunes.get(random.nextInt(0, fortunes.size()));
    }

    public Fortune updateFortune(Fortune fortune) {
        fortuneRepository.update(fortune.getPhrase(), fortune.getId());

        return getFortune(fortune.getId());
    }

    public Fortune deleteFortune(Long id) {
        Fortune fortune = getFortune(id);
        fortuneRepository.delete(fortune);

        return fortune;
    }

    private List<Integer> generateLuckyNumbers() {
        return random.ints(1, 100).distinct().limit(6).boxed().toList();
    }

}
