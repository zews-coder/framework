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

    protected void creteDependency(List<Class<?>> classes){
        for (Class<?> cls : classes) {
            if (cls.isAnnotationPresent(Repository.class)){
                return;
            }
            if (cls.isAnnotationPresent(Service.class)){
                return;
            }
            if (cls.isAnnotationPresent(Controller.class)){
                return;
            }
        }

    }
}
