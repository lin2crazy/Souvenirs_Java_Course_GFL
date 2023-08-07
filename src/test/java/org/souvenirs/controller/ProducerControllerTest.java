package org.souvenirs.controller;

import org.junit.BeforeClass;
import org.junit.Test;
import org.souvenirs.model.Producer;
import org.souvenirs.model.Souvenir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.junit.Assert.*;

public class ProducerControllerTest {
    static List<Producer> producers;
    static List<Souvenir> souvenirs;

    File producersFile = new File("C:\\Users\\Zbarovskyi\\IdeaProjects\\Souvenirs_Java_Course_GFL\\src\\test\\resources\\producersTest.txt");
    ProducerController producerController = ProducerController.getInstance();

    @BeforeClass
    public static void setUp() throws ParseException {
        Producer p1 = new Producer("producer1", "country1");
        Producer p2 = new Producer("producer2", "country2");
        Producer p3 = new Producer("producer3", "country3");
        Producer p4 = new Producer("producer4", "country4");

        Souvenir s1 = new Souvenir("souvenir1", "12-04-2022", 200);
        Souvenir s2 = new Souvenir("souvenir2", "21-03-2021", 250);
        s1.setProducer(p1);
        s2.setProducer(p1);
        p1.getSouvenirs().add(s1);
        p1.getSouvenirs().add(s2);

        Souvenir s3 = new Souvenir("souvenir3", "13-05-2023", 300);
        Souvenir s4 = new Souvenir("souvenir4", "14-06-2019", 400);
        Souvenir s5 = new Souvenir("souvenir5", "15-07-2018", 260);
        s3.setProducer(p2);
        s4.setProducer(p2);
        s5.setProducer(p2);
        p2.getSouvenirs().add(s3);
        p2.getSouvenirs().add(s4);
        p2.getSouvenirs().add(s5);

        Souvenir s6 = new Souvenir("souvenir6", "16-08-2017", 600);
        Souvenir s7 = new Souvenir("souvenir7", "17-09-2018", 200);
        s6.setProducer(p3);
        s7.setProducer(p3);
        p3.getSouvenirs().add(s6);
        p3.getSouvenirs().add(s7);

        Souvenir s8 = new Souvenir("souvenir8", "18-10-2019", 800);
        s8.setProducer(p4);
        p4.getSouvenirs().add(s8);

        producers = new ArrayList<>();
        producers.add(p1);
        producers.add(p2);
        producers.add(p3);
        producers.add(p4);

        souvenirs = new ArrayList<>();
        souvenirs.add(s1);
        souvenirs.add(s2);
        souvenirs.add(s3);
        souvenirs.add(s4);
        souvenirs.add(s5);
        souvenirs.add(s6);
        souvenirs.add(s7);
        souvenirs.add(s8);
    }

    @Test
    public void getProducersFromFileTest() {
        List<Producer> actual = producerController.getProducersFromFile(producersFile);
        assertEquals("Lists should be equal", producers, actual);
    }

    @Test
    public void writeProducersToFile() throws IOException {
        PrintWriter printWriter = new PrintWriter(new FileWriter(producersFile));
        printWriter.println("");
        producerController.writeProducersToFile(producersFile, producers);
        List<Producer> actual = producerController.getProducersFromFile(producersFile);
        assertEquals("Lists should be equal", producers, actual);
    }

    @Test
    public void getProducerByIdTest() {
        int id = 0;
        Producer expected = producers.get(id);
        Producer actual = producerController.getProducerById(id, producers);
        assertEquals("Producers should be equal", expected, actual);
    }

    @Test
    public void updateProducerTest() {
        Producer expected = new Producer("edited", "edited");
        Producer actual = producers.get(0);
        producerController.updateProducer(actual.getId(), expected.getName(), expected.getCountry(), producers);
        assertEquals("Producers should be equal", expected, actual);

        producers.get(0).setName("producer1");
        producers.get(0).setCountry("country1");
    }

    @Test
    public void getProducersBySouvenirPriceLessThanTest() {
        double price = 300;
        List<Producer> expected = new ArrayList<>(Collections.singletonList(producers.get(0)));
        List<Producer> actual = producerController.getProducersBySouvenirPriceLessThan(price, producers);
        assertEquals("Lists should be equal", expected, actual);
    }

    @Test
    public void getProducersBySouvenirNameAndYearTest() {
        String souvenirName = "souvenir7";
        int year = 2018;
        List<Producer> expected = new ArrayList<>(Collections.singletonList(producers.get(2)));
        List<Producer> actual = producerController.getProducersBySouvenirNameAndYear(souvenirName, year, producers);
        assertEquals("Lists should be equal", expected, actual);
    }

    @Test
    public void deleteProducerWithSouvenirsTest() {
        int id = 0;
        Producer producer = producerController.getProducerById(id, producers);
        List<Souvenir> prodSouvenirs = producer.getSouvenirs();

        List<Producer> producerList = new ArrayList<>(producers);
        List<Souvenir> souvenirList = new ArrayList<>(souvenirs);

        producerController.deleteProducerWithSouvenirs(id, producerList, souvenirList);
        assertFalse("Deleted producer shouldn't be in the list", producerList.contains(producer));
        assertFalse("Deleted souvenirs shouldn't be in the list", souvenirList.containsAll(prodSouvenirs));
    }
}
