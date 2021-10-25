package com.example.demo.context;

import java.util.List;

public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {
    private String[] configLocations;
    private List<String> envConfigPath;

    public ClassPathXmlApplicationContext(String configLocations,List<String> envConfigPath) {
        this.configLocations = new String[]{configLocations};
        this.envConfigPath = envConfigPath;
        refresh();
    }

    public ClassPathXmlApplicationContext(String[] configLocations,List<String> envConfigPath) {
        this.configLocations = configLocations;
        this.envConfigPath = envConfigPath;
        refresh();
    }

    protected String[] getConfigLocations() {
        return this.configLocations;
    }

    @Override
    protected List<String> getEnvironmentConfigPath() {
        return this.envConfigPath;
    }
}
