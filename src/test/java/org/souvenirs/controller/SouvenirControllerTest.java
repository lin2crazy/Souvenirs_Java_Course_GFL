package org.souvenirs.controller;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.souvenirs.model.Producer;
import org.souvenirs.model.Souvenir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


public class SouvenirControllerTest {
    static List<Producer> producers;
    static List<Souvenir> souvenirs;
    File souvenirsFile = new File("C:\\Users\\Zbarovskyi\\IdeaProjects\\Souvenirs_Java_Course_GFL\\src\\test\\resources\\souvenirsTest.txt");
    SouvenirController souvenirController = SouvenirController.getInstance();


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
    public void getSouvenirsFromFileTest() {
        List<Souvenir> actual = souvenirController.getSouvenirsFromFile(souvenirsFile, producers);
        assertEquals("Lists should be equal", souvenirs, actual);
    }

    @Test
    public void writeSouvenirsToFileTest() throws IOException {
        PrintWriter printWriter = new PrintWriter(new FileWriter(souvenirsFile));
        printWriter.println("");
        souvenirController.writeSouvenirsToFile(souvenirsFile, souvenirs);
        List<Souvenir> actual = souvenirController.getSouvenirsFromFile(souvenirsFile, producers);
        assertEquals("Lists should be equal", souvenirs, actual);
    }

    @Test
    public void getSouvenirByIdTest() {
        int id = 0;
        Souvenir expected = souvenirs.get(id);
        Souvenir actual = souvenirController.getSouvenirById(expected.getId(), souvenirs);
        assertEquals("Souvenirs should be equal", expected, actual);
    }

    @Test
    public void updateSouvenirTest() throws ParseException {
        Souvenir expected = new Souvenir("edited", "22-10-2022", 350);
        Souvenir actual = souvenirs.get(0);
        souvenirController.updateSouvenir(actual.getId(), expected.getName(), "22-10-2022", expected.getPrice(), souvenirs);
        assertEquals("Souvenirs should be equal", expected, actual);

        souvenirs.get(0).setName("souvenir1");
        souvenirs.get(0).setReleaseDate("12-04-2022");
        souvenirs.get(0).setPrice(200);
    }

    @Test
    public void getSouvenirsByProducerIdTest() {
        int id = 0;
        List<Souvenir> expected = producers.get(id).getSouvenirs();
        List<Souvenir> actual = souvenirController.getSouvenirsByProducerId(producers.get(id).getId(), producers);
        assertEquals("Lists should be equal", expected, actual);
    }

    @Test
    public void getSouvenirsByCountryTest() {
        String country = "country1";
        List<Souvenir> expected = new ArrayList<>();
        expected.add(souvenirs.get(0));
        expected.add(souvenirs.get(1));
        List<Souvenir> actual = souvenirController.getSouvenirsByCountry(country, producers);
        assertEquals("Lists should be equal", expected, actual);
    }

    @Test
    public void getSouvenirsByAllYearsTest() {
        Map<Integer, List<Souvenir>> expected = new HashMap<>();
        expected.put(2017, List.of(souvenirs.get(5)));
        expected.put(2018, List.of(souvenirs.get(4), souvenirs.get(6)));
        expected.put(2019, List.of(souvenirs.get(3), souvenirs.get(7)));
        expected.put(2021, List.of(souvenirs.get(1)));
        expected.put(2022, List.of(souvenirs.get(0)));
        expected.put(2023, List.of(souvenirs.get(2)));

        Map<Integer, List<Souvenir>> actual = souvenirController.getSouvenirsByAllYears(souvenirs);
        assertEquals("Maps should be equal", expected, actual);
    }

    @Test
    public void deleteSouvenirTest() {
        int id = 0;
        List<Souvenir> souvenirList = new ArrayList<>(souvenirs);
        Souvenir souvenir = souvenirs.get(id);
        souvenirController.deleteSouvenir(souvenir.getId(), souvenirList);
        assertFalse("Deleted souvenir shouldn't be in the list", souvenirList.contains(souvenir));
    }
}






























