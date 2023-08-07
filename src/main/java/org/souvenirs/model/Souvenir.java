package org.souvenirs.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Souvenir {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
    private static int count;
    private final int id;
    private String name;
    private Date releaseDate;
    private double price;
    private Producer producer;

    {
        this.id = count++;
    }
    public Souvenir() {
    }

    public Souvenir(String name, String releaseDate, double price) throws ParseException {
        this.name = name;
        this.releaseDate = DATE_FORMAT.parse(releaseDate);
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) throws ParseException {
        this.releaseDate = DATE_FORMAT.parse(releaseDate);
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public String toWrite() {
        return name + "; " + DATE_FORMAT.format(releaseDate) + "; " + price + "; " + producer.getName();
    }

    public int getYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(releaseDate);
        return calendar.get(Calendar.YEAR);
    }

    @Override
    public String toString() {
        return "id: " + id + ", name: " + name + ", date: " + DATE_FORMAT.format(releaseDate) + ", price: " + price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Souvenir souvenir = (Souvenir) o;
        return Double.compare(souvenir.price, price) == 0 && Objects.equals(name, souvenir.name) && Objects.equals(releaseDate, souvenir.releaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, releaseDate, price);
    }
}
