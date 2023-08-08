package org.souvenirs.controller;


import org.souvenirs.model.Producer;
import org.souvenirs.model.Souvenir;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SouvenirController {
    private static SouvenirController souvenirController;

    private SouvenirController() {
    }

    public static SouvenirController getInstance() {
        if (souvenirController == null) {
            souvenirController = new SouvenirController();
        }
        return souvenirController;
    }

    public List<Souvenir> getSouvenirsFromFile(File souvenirsFile, List<Producer> producers) {
        List<Souvenir> souvenirs = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(souvenirsFile))) {
            String line;
            String[] args;
            while ((line = bufferedReader.readLine()) != null) {
                args = line.split("; ");
                Souvenir souvenir = new Souvenir(args[0], args[1], Double.parseDouble(args[2]));
                for (Producer producer : producers) {
                    if (producer.getName().equals(args[3])) {
                        souvenir.setProducer(producer);
                        producer.getSouvenirs().add(souvenir);
                        break;
                    }
                }
                souvenirs.add(souvenir);
            }
        } catch (ParseException | IOException e) {
            throw new RuntimeException(e);
        }

        return souvenirs;
    }

    public void writeSouvenirsToFile(File file, List<Souvenir> souvenirs) {
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(file))) {
            for (Souvenir souvenir : souvenirs) {
                printWriter.println(souvenir.toWrite());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Souvenir getSouvenirById(int id, List<Souvenir> souvenirs) {
        return souvenirs.stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public boolean updateSouvenir(int id, String name, String releaseDate, double price, List<Souvenir> souvenirs) throws ParseException {
        Souvenir souvenir = getSouvenirById(id, souvenirs);
        if (souvenir != null) {
            if (!name.equals(""))
                souvenir.setName(name);
            if (!releaseDate.equals(""))
                souvenir.setReleaseDate(releaseDate);
            if (price >= 0)
                souvenir.setPrice(price);
            return true;
        }
        return false;
    }

    public List<Souvenir> getSouvenirsByProducerId(int producerId, List<Producer> producers) {
        Producer producer = producers.stream()
                .filter(p -> p.getId() == producerId)
                .findFirst()
                .orElse(null);
        if (producer != null) {
            return producer.getSouvenirs();
        } else {
            return new ArrayList<>();
        }
    }

    public List<Souvenir> getSouvenirsByCountry(String country, List<Producer> producers) {
        return producers.stream()
                .filter(p -> p.getCountry().equals(country))
                .flatMap(p -> p.getSouvenirs().stream())
                .distinct()
                .collect(Collectors.toList());
    }

    public Map<Integer, List<Souvenir>> getSouvenirsByAllYears(List<Souvenir> souvenirs) {
        return souvenirs.stream()
                .collect(Collectors.groupingBy(Souvenir::getYear));
    }

    public void deleteSouvenir(int id, List<Souvenir> souvenirs) {
        Souvenir souvenir = getSouvenirById(id, souvenirs);
        souvenirs.remove(souvenir);
    }
}
























