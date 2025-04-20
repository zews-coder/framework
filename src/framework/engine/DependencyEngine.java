package framework.engine;

import framework.annotations.Controller;
import framework.annotations.Repository;
import framework.annotations.Service;

import java.util.List;

public class DependencyEngine {
    private static volatile DependencyEngine instance = null;

    private DependencyEngine() {

    }

    public static DependencyEngine getInstance() {
        if (instance == null) {
            instance = new DependencyEngine();
        }
        return instance;
    }

    protected void creteRepository(List<Class<?>> classes){}

    protected void creteService(List<Class<?>> classes){}

    protected void creteController(List<Class<?>> classes){}
}
