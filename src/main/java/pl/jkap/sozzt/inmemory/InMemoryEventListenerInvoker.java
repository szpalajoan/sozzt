package pl.jkap.sozzt.inmemory;

import org.springframework.context.event.EventListener;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class InMemoryEventListenerInvoker {

    public static void invokeEvent(Object event) {
        Class<?> eventClass = event.getClass();
        String methodName = "on" + eventClass.getSimpleName();
        List<Class<?>> listenerClasses = findClassesWithMethod(methodName);

        try {
            for(Class<?> listenerClass : listenerClasses) {
                for (Method method : listenerClass.getDeclaredMethods()) {
                    if (method.isAnnotationPresent(EventListener.class) && method.getName().equals(methodName)) {
                        Class<?>[] parameterTypes = method.getParameterTypes();
                        if (parameterTypes.length == 1 && parameterTypes[0].isAssignableFrom(eventClass)) {
                            method.invoke(listenerClass.getConstructor().newInstance(), event);
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

    public static List<Class<?>> findClassesWithMethod(String methodName) {
        List<Class<?>> classesWithMethod = new ArrayList<>();
        for (Class<?> clazz : getAllClasses()) {
            for (Method method : clazz.getMethods()) {
                if (method.getName().equals(methodName)) {
                    classesWithMethod.add(clazz);
                    break;
                }
            }
        }
        return classesWithMethod;
    }

    private static List<Class<?>> getAllClasses() {
        List<Class<?>> classes = new ArrayList<>();
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            assert classLoader != null;
            Class<?>[] classesArray = classLoader
                    .loadClass("pl.jkap.sozzt")
                    .getDeclaredClasses();
            for (Class<?> clazz : classesArray) {
                if (!clazz.isInterface()) {
                    classes.add(clazz);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return classes;
    }
}
