package com.example.demo.factory.listener;

import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.List;

public interface SpringApplicationRunListener {
    default void starting(String context){
        this.starting();
    }

    @Deprecated
    default void starting() {
    }

    default void environmentPrepared(List<String> activeEnvironmentPath) {
    }

}
