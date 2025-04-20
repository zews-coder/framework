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
    protected DatabaseEngine databaseEngine;
    protected DependencyContainerEngine dependencyContainerEngine;
    protected DependencyInjectionEngine dependencyInjectionEngine;

    protected static Set<Class<?>> classes;    //all classes in project
    protected static Map<String, Class<?>> classMap;    //full package name to class map

    private static volatile CoreEngine instance = null;

    private static final String PACKAGE_LOCATION = "src/";
    private static final String PACKAGE_NAME = "playground";

    private CoreEngine() {
        classes = new HashSet<>();
        classMap = new HashMap<>();

        databaseEngine = new DatabaseEngine();
        dependencyContainerEngine = new DependencyContainerEngine();
        dependencyInjectionEngine = new DependencyInjectionEngine();

        findClassesInPackage();
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
    private static void findClassesInPackage() {
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
                    classMap.put(packageName + "." + className, clazz);
                } catch (ClassNotFoundException e) {
                    System.err.println("Class not found: " + packageName + "." + className);
                }
            }
        }
    }

    public static Set<Class<?>> getClasses() {
        return classes;
    }

    public static Map<String, Class<?>> getClassMap() {
        return classMap;
    }

    public static void main(String[] args) {
        CoreEngine engine = CoreEngine.getInstance();
        for (Class<?> cls : engine.getClasses()) {
            System.out.println(cls.getName());
        }
    }
}
