package framework.engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for database operations.
 * This class will store instances of classes
 * annotated with @Entity.
 */
public class DatabaseEngine {
    private static volatile DatabaseEngine instance = null;
    private static Map<String, List<Object>> database;

    private DatabaseEngine() {
        database = new HashMap<>();
    }

    public static DatabaseEngine getInstance() {
        if (instance == null) {
            instance = new DatabaseEngine();
        }
        return instance;
    }

    protected void createDatabase(List<Class<?>> classes){

//        for (Class<?> cls : classes) {
//            if (cls.isAnnotationPresent(Entity.class)){
//                System.out.println("Found entity: " + cls.getName());
//            }
//        }
    }
}
