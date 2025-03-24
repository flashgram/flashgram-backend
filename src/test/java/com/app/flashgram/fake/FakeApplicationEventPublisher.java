package com.app.flashgram.fake;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import org.springframework.context.ApplicationEventPublisher;

public class FakeApplicationEventPublisher implements ApplicationEventPublisher {

    private final Map<Class<?>, Consumer<?>> listeners = new HashMap<>();

    @Override
    public void publishEvent(Object event) {
        if (listeners.containsKey(event.getClass())) {

            Consumer consumer = listeners.get(event.getClass());
            consumer.accept(event);
        }
    }

    public <T> void registerListener(Class<T> eventType, Consumer<T> listener) {
        listeners.put(eventType, listener);
    }
}