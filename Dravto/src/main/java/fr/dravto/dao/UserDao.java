package fr.dravto.dao;

import fr.dravto.entity.User;
import fr.oxal.anotation.FakeBean;

import java.util.ArrayList;
import java.util.List;

@FakeBean
public class UserDao {
    private static final List<User> users = new ArrayList<>();

    public boolean add(User user) {
        return users.add(user);
    }

    public boolean remove(User user) {
        return users.remove(user);
    }

    public User get(int index) {
        return users.get(index);
    }

    public List<User> getAll() {
        return users;
    }
}
