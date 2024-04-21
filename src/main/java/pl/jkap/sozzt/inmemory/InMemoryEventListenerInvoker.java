package pl.jkap.sozzt.inmemory;

import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.context.event.EventListener;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryEventListenerInvoker {

    private final Map<Class<?>, Object> instances = new HashMap<>();

    public InMemoryEventListenerInvoker(@NotNull Object... objects) {
        for (Object obj : objects) {
            instances.put(obj.getClass(), obj);
        }
    }

    public void invokeEvent(Object event) {
        Class<?> eventClass = event.getClass();
        String methodName = "on" + eventClass.getSimpleName();
        List<Class<?>> listenerClasses = findClassesWithMethod(methodName);

        try {
            for(Class<?> listenerClass : listenerClasses) {
                for (Method method : listenerClass.getDeclaredMethods()) {
                    if (method.isAnnotationPresent(EventListener.class) && method.getName().equals(methodName)) {
                        Class<?>[] parameterTypes = method.getParameterTypes();
                        if (parameterTypes.length == 1 && parameterTypes[0].isAssignableFrom(eventClass)) {
                            if (!instances.containsKey(listenerClass)) {
                                throw new RuntimeException("Nie znaleziono instancji klasy " + listenerClass.getName() + "dodaj do konsturktora InMemoryEventListenerInvoker");
                            }
                            method.invoke(instances.get(listenerClass), event);
                            return;
                        }
                    }
                }
            }
            System.out.println("Nie znaleziono metody obsługującej zdarzenie.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Class<?>> findClassesWithMethod(String methodName) {
        List<Class<?>> classesWithMethod = new ArrayList<>();
        for (Class<?> clazz : getClassesInPackage()) {
            for (Method method : clazz.getMethods()) {
                if (method.getName().equals(methodName)) {
                    classesWithMethod.add(clazz);
                    break;
                }
            }
        }
        return classesWithMethod;
    }


    private List<Class<?>> getClassesInPackage() {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage("pl.jkap.sozzt"))
                .setScanners(new SubTypesScanner(false)));

        Set<Class<?>> classes = reflections.getSubTypesOf(Object.class);

        return classes.stream()
                .filter(clazz -> clazz.getPackage().getName().contains("pl.jkap.sozzt"))
                .collect(Collectors.toList());
    }
}
