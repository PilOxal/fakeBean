package fr.dravto;

import fr.dravto.service.CityService;
import fr.dravto.service.UserService;
import fr.dravto.utils.RandomUtils;
import fr.oxal.anotation.FakeBean;
import fr.oxal.anotation.FakeParameter;

import java.util.Date;
import java.util.stream.IntStream;

public class Dravto {

    @FakeBean
    public Dravto run(UserService userService, CityService cityService, @FakeParameter("date") Date date) {
        System.out.println(date);
        System.out.println(userService.getAll());
        System.out.println(cityService.getAll());
        IntStream.range(0, 5)
                .forEach(i -> {
                    userService.add(RandomUtils.fakeUser());
                    cityService.add(RandomUtils.fakeCity());
                });
        System.out.println(userService.getAll());
        System.out.println(cityService.getAll());
        return null;
    }
}
