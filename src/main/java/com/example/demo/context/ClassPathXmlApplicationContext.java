package com.example.demo.context;

public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {
    private String[] configLocations;

    public ClassPathXmlApplicationContext(String configLocations) {
        this.configLocations = new String[]{configLocations};
        refresh();
    }

    public ClassPathXmlApplicationContext(String[] configLocations) {
        this.configLocations = configLocations;
        refresh();
    }

    protected String[] getConfigLocations() {
        return this.configLocations;
    }
}
