package org.souvenirs.view;

import org.souvenirs.controller.ProducerController;
import org.souvenirs.model.Producer;
import org.souvenirs.model.Souvenir;

import java.util.List;

public class ProducerView {
    private static ProducerView producerView;
    private final ProducerController producerController = ProducerController.getInstance();

    private ProducerView() {
    }

    public static ProducerView getInstance() {
        if (producerView == null) {
            producerView = new ProducerView();
        }
        return producerView;
    }

    public void printProducers(List<Producer> producers) {
        if (!producers.isEmpty()) {
            System.out.println("Виробники:");
            for (Producer producer : producers) {
                System.out.println(producer);
            }
        } else {
            System.out.println("Список з виробниками пустий");
        }
    }

    public void printProducersWithSouvenirs(List<Producer> producers) {
        if (!producers.isEmpty()) {
            for (Producer producer : producers) {
                System.out.println("Виробник:");
                System.out.println(producer);
                System.out.println("Сувеніри:");
                for (Souvenir souvenir : producer.getSouvenirs()) {
                    System.out.println(souvenir);
                }
                System.out.println();
            }
        } else {
            System.out.println("Список з виробниками пустий");
        }
    }

    public void printProducerWithSouvenirs(int id, List<Producer> producers) {
        Producer producer = producerController.getProducerById(id, producers);
        if (producer != null) {
            System.out.println("Виробник:");
            System.out.println(producer);
            System.out.println("Сувеніри:");
            for (Souvenir souvenir : producer.getSouvenirs()) {
                System.out.println(souvenir);
            }
        } else {
            System.out.println("Виробника з таким id не існує");
        }

    }

    public void printProducersBySouvenirPriceLessThan(double price, List<Producer> producers) {
        List<Producer> producerList = producerController.getProducersBySouvenirPriceLessThan(price, producers);
        if(!producerList.isEmpty()) {
            printProducers(producerList);
        } else {
            System.out.println("Немає виробників з ціною сувенірів менше за задану");
        }
    }

    public void printProducersBySouvenirNameAndYear(String souvenirName, int year, List<Producer> producers) {
        List<Producer> producerList = producerController.getProducersBySouvenirNameAndYear(souvenirName, year, producers);
        if(!producerList.isEmpty()) {
            printProducers(producerList);
        } else {
            System.out.println("Немає виробників з заданими параметрами");
        }
    }
}















