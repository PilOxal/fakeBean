package fr.dravto.service;

import fr.dravto.dao.UserDao;
import fr.dravto.entity.User;
import fr.oxal.anotation.FakeBean;

import java.util.List;

@FakeBean
public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }
    public boolean add(User user) {
        return userDao.add(user);
    }

    public boolean remove(User user) {
        return userDao.remove(user);
    }

    public User get(int index) {
        return userDao.get(index);
    }

    public List<User> getAll() {
        return userDao.getAll();
    }
}
