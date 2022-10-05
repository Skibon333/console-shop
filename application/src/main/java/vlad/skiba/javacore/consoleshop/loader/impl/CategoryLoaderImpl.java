package vlad.skiba.javacore.consoleshop.loader.impl;

import vlad.skiba.javacore.consoleshop.exception.ApplicationGenericException;
import vlad.skiba.javacore.consoleshop.model.Category;
import vlad.skiba.javacore.consoleshop.loader.CategoryLoader;

import java.util.stream.Collectors;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Arrays;
import java.util.List;
import java.io.File;
import java.net.URL;

/**
 * Copyright Vlad Skiba (c) 2021.
 */
public class CategoryLoaderImpl implements CategoryLoader {

    private static final Path PATH_TO_XML_FILE = Paths.get("application/src/main/resources/sorting-criteria.xml");

    @Override
    public List<Category> loadFromPackage(String packageForCategory) {
        File[] files = Optional.ofNullable(packageForCategory)
                .map(this::getPathToPackage)
                .map(URL::getFile)
                .map(File::new)
                .map(File::listFiles)
                .filter(foundFiles -> foundFiles.length != 0)
                .orElseThrow(() -> new ApplicationGenericException("Incorrect package name for category files! "));
        return Arrays.stream(files)
                .map(File::getName)
                .map(fileName -> fileName.replace(".class", ""))
                .map(simpleClassName -> packageForCategory.concat(".").concat(simpleClassName))
                .map(this::loadClass)
                .filter(Category.class::isAssignableFrom)
                .map(this::createInstance)
                .collect(Collectors.toList());
    }

    private Category createInstance(Class categoryClass) {
        try {
            return (Category) categoryClass.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new ApplicationGenericException("Object is not created: ", ex);
        }
    }

    private Class<?> loadClass(String nameOfClass) {
        try {
            return Class.forName(nameOfClass);
        } catch (ClassNotFoundException ex) {
            throw new ApplicationGenericException("Class not found: ", ex);
        }
    }

    private URL getPathToPackage(String packageName) {
        return Thread.currentThread().getContextClassLoader().getResource(packageName.replace(".", "/"));
    }

}