package fr.oxal.manager;

import com.google.common.reflect.ClassPath;
import fr.oxal.anotation.FakeBean;
import fr.oxal.anotation.FakeQualifier;
import fr.oxal.anotation.FakeParameter;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Managers {
    private static final Set<Executable> EXEC = new HashSet<>();
    private static final Map<String, Object> PARAMETERS = new HashMap<>();
    private static final Map<Class, Object> INSTANCE = new HashMap<>();
    private final Logger LOGGER = Logger.getLogger(Managers.class.getSimpleName());

    public static <T> T load(Class<T> c) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        return load(c, null);
    }

    public static boolean getExecutable(Executable e, Class type, String name) {
        if (e.getClass().equals(Method.class)) {
            return ((Method) e).getReturnType().equals(type)
                    && (name == null || ((Method) e).getName().equals(name));
        } else if (e.getClass().equals(Constructor.class)) {
            return ((Constructor) e).getDeclaringClass().equals(type) && name == null;
        }
        return false;
    }

    public static Object loadParameter(Parameter parameter) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        for (Annotation annotation : parameter.getAnnotations()) {
            if (FakeParameter.class.equals(annotation.annotationType())) {
                return PARAMETERS.get(((FakeParameter) annotation).value());
            }
            if (FakeQualifier.class.equals(annotation.annotationType())) {
                return load(parameter.getType(), ((FakeQualifier) annotation).value());
            }
        }
        return load(parameter.getType());
    }

    public static <T> T load(Class<T> c, String name) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        List<Executable> executables = new ArrayList<>(EXEC
                .stream()
                .filter(e -> Managers.getExecutable(e, c, name))
                .toList());
        // récupère en priorité les classes si pas de nom
        if (executables.size() > 1 && name == null) {
            executables.removeIf(e -> Method.class.equals(e.getClass()));
        }
        if (executables.size() == 1) {
            Executable e = executables.get(0);
            List<Object> objs = new ArrayList<>();
            for (Parameter p : e.getParameters()) {
                objs.add(loadParameter(p));
            }
            if (e.getClass().equals(Constructor.class)) {

                return ((Constructor<T>) e).newInstance(objs.toArray());
            } else {
                Method method = ((Method) e);
                if (method.getDeclaringClass().equals(c)) {
                    return (T) method.invoke(method.getDeclaringClass().getConstructor().newInstance(), objs.toArray());
                } else {
                    return (T) method.invoke(load(method.getDeclaringClass()), objs.toArray());
                }
            }
        } else {
            throw new RuntimeException("Plusieurs class managées sont présentes sur cette classe");
        }
    }

    public void run() {
        StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
        try {
            String packageName = Class.forName(stElements[stElements.length - 1].getClassName())
                    .getPackageName();
            LOGGER.log(Level.INFO, "nom du package : {}", packageName);
            ClassPath.from(ClassLoader.getSystemClassLoader())
                    .getAllClasses()
                    .stream()
                    .filter(s -> s.getPackageName().contains(packageName))
                    .forEach(this::compute);
            LOGGER.log(Level.INFO, EXEC.toString());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void compute(ClassPath.ClassInfo classInfo) {
        Class c = classInfo.load();
        for (Annotation annotation : c.getAnnotations()) {
            if (FakeBean.class.equals(annotation.annotationType())) {
                List<Constructor> constructorList = List.of(c.getConstructors());
                if (constructorList.size() == 1) {
                    EXEC.add(constructorList.get(0));
                } else {
                    throw new RuntimeException(new NoSuchMethodException());
                }
                break;
            }
        }
        for (Method method : c.getMethods()) {
            for (Annotation annotation : method.getAnnotations()) {
                if (FakeBean.class.equals(annotation.annotationType())) {
                    EXEC.add(method);
                    break;
                }
            }
        }
    }

    public Managers addParameter(String key, Object value) {
        PARAMETERS.put(key, value);
        return this;
    }
}
