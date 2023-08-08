package org.souvenirs.controller;

import org.souvenirs.model.Producer;
import org.souvenirs.model.Souvenir;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProducerController {
    private static ProducerController producerController;

    private ProducerController() {
    }

    public static ProducerController getInstance() {
        if (producerController == null) {
            producerController = new ProducerController();
        }
        return producerController;
    }

    public List<Producer> getProducersFromFile(File file) {
        List<Producer> producers = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            String[] args;
            while ((line = bufferedReader.readLine()) != null) {
                args = line.split("; ");
                producers.add(new Producer(args[0], args[1]));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return producers;
    }

    public void writeProducersToFile(File file, List<Producer> producers) {
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(file))) {
            for (Producer producer : producers) {
                printWriter.println(producer.toWrite());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Producer getProducerById(int id, List<Producer> producers) {
        return producers.stream()
                .filter(p -> p.getId() == id)
                .findFirst().orElse(null);
    }

    public boolean updateProducer(int id, String name, String country, List<Producer> producers) {
        Producer producer = getProducerById(id, producers);
        if (producer != null) {
            if (!name.equals(""))
                producer.setName(name);
            if (!country.equals(""))
                producer.setCountry(country);
            return true;
        }
        return false;
    }

    public List<Producer> getProducersBySouvenirPriceLessThan(double price, List<Producer> producers) {
        return producers.stream()
                .filter(p -> p.getSouvenirs().stream()
                        .allMatch(s -> s.getPrice() < price))
                .collect(Collectors.toList());
    }

    public List<Producer> getProducersBySouvenirNameAndYear(String souvenirName, int year, List<Producer> producers) {
        return producers.stream()
                .filter(p -> p.getSouvenirs().stream()
                        .anyMatch(s -> s.getName().equals(souvenirName) && s.getYear() == year))
                .collect(Collectors.toList());
    }

    public void deleteProducerWithSouvenirs(int id, List<Producer> producers, List<Souvenir> souvenirs) {
        Producer producer = getProducerById(id, producers);
        souvenirs.removeIf(souvenir -> souvenir.getProducer().equals(producer));
        producers.remove(producer);
    }
}
