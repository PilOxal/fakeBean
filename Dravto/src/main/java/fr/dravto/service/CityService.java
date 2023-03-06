
package fr.dravto.service;

import fr.dravto.dao.CityDao;
import fr.dravto.dao.UserDao;
import fr.dravto.entity.City;
import fr.dravto.entity.User;
import fr.oxal.anotation.FakeBean;

import java.util.List;

@FakeBean
public class CityService {
    private final CityDao cityDao;

    public CityService(CityDao cityDao) {
        this.cityDao = cityDao;
    }

    public boolean add(City city) {
        return cityDao.add(city);
    }

    public boolean remove(City city) {
        return cityDao.remove(city);
    }

    public City get(int index) {
        return cityDao.get(index);
    }

    public List<City> getAll() {
        return cityDao.getAll();
    }
}
