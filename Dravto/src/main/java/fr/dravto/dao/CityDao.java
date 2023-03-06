package fr.dravto.dao;

import fr.dravto.entity.City;
import fr.oxal.anotation.FakeBean;

import java.util.ArrayList;
import java.util.List;

@FakeBean
public class CityDao {
    private static final List<City> cities = new ArrayList<>();

    public boolean add(City city) {
        return cities.add(city);
    }

    public boolean remove(City city) {
        return cities.remove(city);
    }

    public City get(int index) {
        return cities.get(index);
    }

    public List<City> getAll() {
        return cities;
    }
}
