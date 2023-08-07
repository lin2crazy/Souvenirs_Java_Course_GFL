package org.souvenirs.view;

import org.souvenirs.controller.SouvenirController;
import org.souvenirs.model.Producer;
import org.souvenirs.model.Souvenir;

import java.util.List;
import java.util.Map;

public class SouvenirView {
    private static SouvenirView souvenirView;
    private final SouvenirController souvenirController = SouvenirController.getInstance();

    private SouvenirView() {
    }

    public static SouvenirView getInstance() {
        if (souvenirView == null) {
            souvenirView = new SouvenirView();
        }
        return souvenirView;
    }

    public void printSouvenirs(List<Souvenir> souvenirs) {
        if (!souvenirs.isEmpty()) {
            System.out.println("Сувеніри:");
            for (Souvenir souvenir : souvenirs) {
                System.out.println(souvenir);
            }
        } else {
            System.out.println("Список з сувенірами пустий");
        }
    }

    public void printSouvenirsWithProducer(List<Souvenir> souvenirs) {
        if (!souvenirs.isEmpty()) {
            for (Souvenir souvenir : souvenirs) {
                System.out.println(souvenir + " Producer: " + souvenir.getProducer());
            }
        } else {
            System.out.println("Список з сувенірами пустий");
        }
    }

    public void printSouvenirsByProducerId(int producerId, List<Producer> producers) {
        printSouvenirs(souvenirController.getSouvenirsByProducerId(producerId, producers));
    }

    public void printSouvenirsByCountry(String country, List<Producer> producers) {
        List<Souvenir> souvenirs = souvenirController.getSouvenirsByCountry(country, producers);
        if (!souvenirs.isEmpty()) {
            printSouvenirs(souvenirs);
        }
        System.out.println("Немає сувенірів виготовлених у заданій країні");
    }

    public void printSouvenirsByAllYears(List<Souvenir> souvenirs) {
        Map<Integer, List<Souvenir>> map = souvenirController.getSouvenirsByAllYears(souvenirs);
        if (!map.isEmpty()) {
            for (Map.Entry<Integer, List<Souvenir>> integerListEntry : map.entrySet()) {
                System.out.println(integerListEntry.getKey());
                for (Souvenir souvenir : integerListEntry.getValue()) {
                    System.out.println(souvenir);
                }
                System.out.println();
            }
        } else {
            System.out.println("Список з сувенірами пустий");
        }

    }
}






















