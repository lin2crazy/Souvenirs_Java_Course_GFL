package org.souvenirs.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Producer {
    private static int counter;
    private final int id;
    private String name;
    private String country;
    private List<Souvenir> souvenirs;

    {
        this.id = counter++;
        souvenirs = new ArrayList<>();
    }

    public Producer() {
    }

    public Producer(String name, String country) {
        this.name = name;
        this.country = country;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Souvenir> getSouvenirs() {
        return souvenirs;
    }

    public void setSouvenirs(List<Souvenir> souvenirs) {
        this.souvenirs = souvenirs;
    }

    public String toWrite() {
        return name + "; " + country;
    }

    @Override
    public String toString() {
        return "id: " + id + ", name: " + name + ", country: " + country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producer producer = (Producer) o;
        return Objects.equals(name, producer.name) && Objects.equals(country, producer.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, country);
    }
}
