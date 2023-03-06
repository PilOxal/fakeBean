package fr.dravto;

import fr.oxal.manager.Managers;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        new Managers()
                .addParameter("date", new Date())
                .run();
        Managers.load(Dravto.class, "run");
    }
}
