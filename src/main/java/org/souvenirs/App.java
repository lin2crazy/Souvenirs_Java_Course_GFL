package org.souvenirs;


import org.souvenirs.controller.ProducerController;
import org.souvenirs.controller.SouvenirController;
import org.souvenirs.model.Producer;
import org.souvenirs.model.Souvenir;
import org.souvenirs.view.ProducerView;
import org.souvenirs.view.SouvenirView;

import java.io.File;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        try {
            File producersFile = new File("C:\\Users\\Zbarovskyi\\IdeaProjects\\Souvenirs_Java_Course_GFL\\src\\main\\resources\\producers.txt");
            File souvenirsFile = new File("C:\\Users\\Zbarovskyi\\IdeaProjects\\Souvenirs_Java_Course_GFL\\src\\main\\resources\\souvenirs.txt");

            ProducerController producerController = ProducerController.getInstance();
            SouvenirController souvenirController = SouvenirController.getInstance();

            ProducerView producerView = ProducerView.getInstance();
            SouvenirView souvenirView = SouvenirView.getInstance();

            List<Producer> producers = producerController.getProducersFromFile(producersFile);
            List<Souvenir> souvenirs = souvenirController.getSouvenirsFromFile(souvenirsFile, producers);

            System.out.println("Файли успішно зчитано!");

            String operation = "";
            printMenu();
            while (!operation.equals("q")) {
                Scanner scanner = new Scanner(System.in);
                System.out.println();
                System.out.print("Введіть номер операції: ");
                operation = scanner.nextLine();
                switch (operation) {
                    case "0": {
                        System.out.print("Введіть назву виробника: ");
                        String name = scanner.nextLine();
                        System.out.print("Введіть країну виробника: ");
                        String country = scanner.nextLine();
                        producers.add(new Producer(name, country));
                        System.out.println("Виробника створено");
                        break;
                    }
                    case "1": {
                        producerView.printProducers(producers);
                        System.out.print("Введіть id виробника, якого хочете редагувати: ");
                        int id = scanner.nextInt();
                        System.out.println("Для пропуску редагування поля нажміть Enter");
                        scanner = new Scanner(System.in);
                        System.out.print("Введіть нове значення назви: ");
                        String name = scanner.nextLine();
                        System.out.print("Введіть нове значення країни: ");
                        String country = scanner.nextLine();
                        if (producerController.updateProducer(id, name, country, producers))
                            System.out.println("Успішно відредаговано");
                        else
                            System.out.println("Виробника з таким id не існує");
                        break;
                    }
                    case "2": {
                        producerView.printProducers(producers);
                        System.out.print("Введіть id виробника для видалення: ");
                        producerController.deleteProducerWithSouvenirs(scanner.nextInt(), producers, souvenirs);
                        System.out.println("Видалено");
                        break;
                    }
                    case "3": {
                        producerView.printProducers(producers);
                        break;
                    }
                    case "4": {
                        System.out.print("Введіть назву сувеніру: ");
                        String name = scanner.nextLine();
                        System.out.print("Введіть дату у форматі 'dd-MM-yyyy': ");
                        String date = scanner.nextLine();
                        System.out.print("Введіть ціну: ");
                        double price = scanner.nextDouble();
                        try {
                            Souvenir souvenir = new Souvenir(name, date, price);
                            System.out.print("Для сувеніра потрібно обрати існуючого або створити нового виробника\n1 - обрати існуючого\n2 - створити нового\n:");
                            scanner = new Scanner(System.in);
                            switch (scanner.nextLine()) {
                                case "1": {
                                    producerView.printProducers(producers);
                                    System.out.print("Введіть id виробника: ");
                                    Producer producer = producerController.getProducerById(scanner.nextInt(), producers);
                                    if (producer != null) {
                                        souvenir.setProducer(producer);
                                        producer.getSouvenirs().add(souvenir);
                                        souvenirs.add(souvenir);
                                        System.out.println("Виробника успішно обрано");
                                    } else {
                                        System.out.println("Виробника з таким id не існує. Сувенір не створено");
                                    }
                                    break;
                                }
                                case "2": {
                                    System.out.print("Введіть назву виробника: ");
                                    String prodName = scanner.nextLine();
                                    System.out.print("Введіть країну виробника: ");
                                    String country = scanner.nextLine();
                                    Producer producer = new Producer(prodName, country);
                                    souvenir.setProducer(producer);
                                    producer.getSouvenirs().add(souvenir);
                                    producers.add(producer);
                                    souvenirs.add(souvenir);
                                    System.out.println("Виробник успішно створено і обрано");
                                    break;
                                }
                                default: {
                                    System.out.println("Невідома команда! Сувенір не створено");
                                    break;
                                }
                            }
                        } catch (ParseException e) {
                            System.out.println("Сувенір не створено. Неправильно введено дату");
                        }
                        break;
                    }
                    case "5": {
                        souvenirView.printSouvenirs(souvenirs);
                        System.out.print("Введіть id сувеніра, якого хочете редагувати: ");
                        int id = scanner.nextInt();
                        scanner = new Scanner(System.in);
                        System.out.println("Для пропуску редагування поля нажміть Enter, для ціни - поставити від'ємну");
                        System.out.print("Введіть назву сувеніру: ");
                        String name = scanner.nextLine();
                        System.out.print("Введіть дату у форматі 'dd-MM-yyyy': ");
                        String date = scanner.nextLine();
                        System.out.print("Введіть ціну: ");
                        double price = scanner.nextDouble();
                        try {
                            if (souvenirController.updateSouvenir(id, name, date, price, souvenirs))
                                System.out.println("Сувенір успішно відредаговано");
                            else
                                System.out.println("Сувеніру з таким id не існує");
                        } catch (ParseException e) {
                            System.out.println("Сувенір не відредаговано. Неправильно введено дату");
                        }
                        break;
                    }
                    case "6": {
                        souvenirView.printSouvenirs(souvenirs);
                        System.out.print("Введіть id сувеніра для видалення: ");
                        souvenirController.deleteSouvenir(scanner.nextInt(), souvenirs);
                        System.out.println("Видалено");
                        break;
                    }
                    case "7": {
                        souvenirView.printSouvenirs(souvenirs);
                        break;
                    }
                    case "8": {
                        producerView.printProducers(producers);
                        System.out.print("Введіть id виробника: ");
                        producerView.printProducerWithSouvenirs(scanner.nextInt(), producers);
                        break;
                    }
                    case "9": {
                        System.out.print("Введіть назву країни: ");
                        souvenirView.printSouvenirsByCountry(scanner.nextLine(), producers);
                        break;
                    }
                    case "10": {
                        System.out.print("Введіть ціну: ");
                        producerView.printProducersBySouvenirPriceLessThan(scanner.nextDouble(), producers);
                        break;
                    }
                    case "11": {
                        producerView.printProducersWithSouvenirs(producers);
                        break;
                    }
                    case "12": {
                        System.out.print("Введіть назву сувеніру: ");
                        String name = scanner.nextLine();
                        System.out.print("Введіть рік: ");
                        int year = scanner.nextInt();
                        producerView.printProducersBySouvenirNameAndYear(name, year, producers);
                        break;
                    }
                    case "13": {
                        souvenirView.printSouvenirsByAllYears(souvenirs);
                        break;
                    }
                    case "14": {
                        producerController.writeProducersToFile(producersFile, producers);
                        souvenirController.writeSouvenirsToFile(souvenirsFile, souvenirs);
                        System.out.println("Зміни до файлу збережено");
                    }
                    case "q": {
                        operation = "q";
                        break;
                    }
                    default: {
                        System.out.println("Невідома команда!");
                        break;
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Помилка. Файли не зчитано");
        }
    }

    public static void printMenu() {
        System.out.println("Меню:");
        System.out.println("0 - Додати виробника\n1 - Редагувати виробника\n2 - Видалити виробника\n3 - Переглянути всіх виробників");
        System.out.println("---------------------------");
        System.out.println("4 - Додати сувенір\n5 - Редагувати сувенір\n6 - Видалити сувенір\n7 - Переглянути всі сувеніри");
        System.out.println("---------------------------");
        System.out.println("8 - Вивести інформацію про сувеніри заданого виробника");
        System.out.println("9 - Вивести інформацію про сувеніри, виготовлені в заданій країні");
        System.out.println("10 - Вивести інформацію про виробників, чиї ціни на сувеніри менше заданої");
        System.out.println("11 - Вивести інформацію по всіх виробниках та, для кожного виробника вивести інформацію про всі сувеніри, які він виробляє");
        System.out.println("12 - Вивести інформацію про виробників заданого сувеніру, виробленого у заданому року");
        System.out.println("13 - Для кожного року вивести список сувенірів, зроблених цього року");
        System.out.println("14 - Зберегти зміни до файлу");
        System.out.println("q - для виходу");
        System.out.println("---------------------------");
    }
}




















