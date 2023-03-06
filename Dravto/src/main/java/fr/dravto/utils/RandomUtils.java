package fr.dravto.utils;

import fr.dravto.entity.City;
import fr.dravto.entity.User;
import fr.oxal.anotation.FakeBean;
import fr.oxal.anotation.FakeQualifier;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class RandomUtils {

    @FakeBean
    public static String fakeString() {
        byte[] array = new byte[10];
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }

    @FakeBean
    public static Double fakeDouble() {
        return new Random().nextDouble(-25, 50);
    }

    @FakeBean
    public static City fakeCity() {
        City city = new City();
        city.setName(fakeString());
        city.setLongitude(fakeDouble());
        city.setLatitude(fakeDouble());
        return city;
    }

    @FakeBean
    public static User fakeUser() {
        User user = new User();
        user.setPassword(fakeString());
        user.setUsername(fakeString());
        return user;
    }
}
