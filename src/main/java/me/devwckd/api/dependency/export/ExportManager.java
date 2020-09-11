package me.devwckd.api.dependency.export;

import lombok.RequiredArgsConstructor;
import me.devwckd.api.dependency.DependencyManager;
import me.devwckd.api.dependency.reference.ReferenceManager;
import org.reflections8.Reflections;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * @author devwckd
 */
@RequiredArgsConstructor
public class ExportManager implements DependencyManager {

    private final Reflections reflections;
    private final ReferenceManager referenceManager;

    @Override
    public void init() {
        final Set<Method> exportMethods = reflections.getMethodsAnnotatedWith(Export.class);
        exportMethods.forEach(referenceManager::addMethodReference);
    }

}
