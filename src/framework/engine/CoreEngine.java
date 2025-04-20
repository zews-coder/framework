package framework.engine;

import java.io.File;
import java.util.*;

/**
 * Starts the application.
 * It will create instances of all
 * classes in the engine package,
 * necessary for the framework.
 * Also, will scan for all classes in
 * project which will use framework.
 */
public class CoreEngine {
    private static volatile CoreEngine instance = null;

    protected static DatabaseEngine databaseEngine;
    protected static DependencyEngine dependencyEngine;

    protected static List<Class<?>> classes;            //all classes in a project
    protected static List<Class<?>> entityClasses;      //classes annotated with @Entity
    protected static List<Class<?>> repositoryClasses;  //classes annotated with @Repository
    protected static List<Class<?>> serviceClasses;     //classes annotated with @Service
    protected static List<Class<?>> controllerClasses;  //classes annotated with @Controller

    private static final String PACKAGE_LOCATION = "src/";
    private static final String PACKAGE_NAME = "playground";

    private CoreEngine() {
        classes = new ArrayList<>();
        entityClasses = new ArrayList<>();
        repositoryClasses = new ArrayList<>();
        serviceClasses = new ArrayList<>();
        controllerClasses = new ArrayList<>();

        /* initialization */
        initClasses();
        initDatabase();
        initDependency();
    }

    public static CoreEngine getInstance() {
        if (instance == null) {
            instance = new CoreEngine();
        }
        return instance;
    }

    /**
     * Scans the project for classes..
     */
    private static void initClasses() {
        File directory = new File(PACKAGE_LOCATION + PACKAGE_NAME );

        if (directory.exists()) {
            scanDirectory(directory, PACKAGE_NAME);
        } else {
            System.err.println("Package path not found: " + directory.getAbsolutePath());
        }
    }

    private static void scanDirectory(File directory, String packageName) {
        File[] files = directory.listFiles();
        if (files == null)
            return;

        for (File file : files) {
            if (file.isDirectory()) {
                scanDirectory(file, packageName + "." + file.getName());
            } else if (file.getName().endsWith(".java")) {
                String className = file.getName().substring(0, file.getName().length() - 5); // Remove .java
                System.out.println("Found class: " + packageName + "." + className);

                try {
                    Class<?> clazz = Class.forName(packageName + "." + className);
                    classes.add(Class.forName(packageName + "." + className));

                } catch (ClassNotFoundException e) {
                    System.err.println("Class not found: " + packageName + "." + className);
                }
            }
        }
    }

    private static void initDatabase(){
        databaseEngine = DatabaseEngine.getInstance();
        databaseEngine.createDatabase(entityClasses);
    }

    private static void initDependency(){
        dependencyEngine = DependencyEngine.getInstance();
        dependencyEngine.creteDependency(classes);
    }

    public static List<Class<?>> getClasses() {
        return classes;
    }

    public static void main(String[] args) {
        CoreEngine engine = CoreEngine.getInstance();
        for (Class<?> cls : engine.getClasses()) {
            System.out.println(cls.getName());
        }
    }
}
