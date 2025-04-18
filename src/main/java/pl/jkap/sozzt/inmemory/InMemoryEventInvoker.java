package pl.jkap.sozzt.inmemory;

import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.lang.NonNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class InMemoryEventInvoker implements ApplicationEventPublisher {

    private final Map<Class<?>, Object> instances = new HashMap<>();

    public InMemoryEventInvoker() {
    }

    @Override
    public void publishEvent(@NonNull ApplicationEvent event) {
        invokeEvent(event);
    }

    @Override
    public void publishEvent(@NonNull Object event) {
        invokeEvent(event);
    }

    public void addFacades(@NonNull Object... facades) {
        for (Object facade : facades) {
            instances.put(facade.getClass(), facade);
        }
    }

    private void invokeEvent(Object event) {
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
                                throw new RuntimeException("Not found instance of class " + listenerClass.getName() + " add it to InMemoryEventListenerInvoker");
                            }
                            method.invoke(instances.get(listenerClass), event);
                            return;
                        }
                    }
                }
            }
            log.error("The event handling method was not found: " + eventClass.getName());
        } catch (InvocationTargetException e) {
            log.warn("Handled method threw an exception: " + eventClass.getName());
            Throwable targetException = e.getTargetException();
            if (targetException instanceof RuntimeException) {
                throw (RuntimeException) targetException;
            }
        } catch (Exception e) {
            log.error("Failed to handle event: " + eventClass.getName(), e);
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
