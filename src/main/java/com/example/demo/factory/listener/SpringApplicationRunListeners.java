package com.example.demo.factory.listener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public class SpringApplicationRunListeners {
    private final List<SpringApplicationRunListener> listeners;

    public SpringApplicationRunListeners(Collection<? extends SpringApplicationRunListener> listeners) {
        this.listeners = new ArrayList(listeners);
    }

    public void starting(String context){
        this.doWithListeners((listener)-> listener.starting(context));
    }

    private void doWithListeners(Consumer<SpringApplicationRunListener> listenerAction){
        this.listeners.forEach(listenerAction);
    }
}
