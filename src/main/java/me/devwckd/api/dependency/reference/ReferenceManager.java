package me.devwckd.api.dependency.reference;

import lombok.RequiredArgsConstructor;
import me.devwckd.api.dependency.DependencyManager;
import me.devwckd.api.dependency.export.Export;
import me.devwckd.api.dependency.export.Named;
import me.devwckd.api.dependency.module.Module;
import me.devwckd.api.plugin.ExtendedJavaPlugin;
import me.devwckd.api.util.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

import static me.devwckd.api.util.ReflectionUtils.*;

/**
 * @author devwckd
 */

@RequiredArgsConstructor
public class ReferenceManager implements DependencyManager {

    private final ExtendedJavaPlugin extendedJavaPlugin;

    private final Map<String, Method> methodReferences = new HashMap<>();
    private final Map<String, Object> references = new HashMap<>();

    @Override
    public void init() {
        addReference(extendedJavaPlugin);
    }

    public void addMethodReference(Method method) {
        final Export annotation = method.getAnnotation(Export.class);

        final String name;
        if (annotation.name().trim().isEmpty()) {
            name = method.getReturnType().getSimpleName().toLowerCase();
        } else {
            name = annotation.name().toLowerCase();
        }

        final Class<?> declaringClass = method.getDeclaringClass();
        if (!declaringClass.isAnnotationPresent(Module.class) && isFirstConstructorEmpty(declaringClass)) {

            final Object module = references.get(declaringClass.getSimpleName());
            if (module != null) {
                references.put(name, invokeMethodAndReturnResult(method, module));
                return;
            }

            for (Object reference : references.values()) {
                if (declaringClass.isAssignableFrom(reference.getClass())) {
                    references.put(name, invokeMethodAndReturnResult(method, reference));
                    return;
                }
            }

            if (!isFirstConstructorEmpty(declaringClass)) return;
            references.put(name, invokeMethodAndReturnResult(method, newInstance(getFirstConstructor(declaringClass))));

        } else {
            methodReferences.put(name, method);
        }
    }

    public void addReference(Object object) {
        addReference(object.getClass().getSimpleName(), object);
    }

    public void addReference(String key, Object object) {
        this.references.put(key.toLowerCase(), object);
    }

    public Object resolveReference(String key) {
        key = key.toLowerCase();
        Object reference = references.get(key);
        if (reference != null) return reference;

        Method methodReference = methodReferences.get(key);
        if (methodReference == null) return null;

        reference = resolveMethodReference(methodReference);
        if (reference != null) return reference;

        return null;
    }

    public Object resolveReference(Class<?> referenceClass) {
        Object reference = resolveReference(referenceClass.getSimpleName());
        if (reference != null) return reference;

        final Optional<Object> firstObject = references.values().parallelStream()
          .filter(obj -> obj.getClass().isAssignableFrom(referenceClass))
          .findFirst();
        if (firstObject.isPresent()) return firstObject.get();

        final Method methodReference = methodReferences.get(referenceClass.getSimpleName());
        if (methodReference != null && (reference = resolveMethodReference(methodReference)) != null)
            return reference;

        final Optional<Method> firstMethod = methodReferences.values().parallelStream()
          .filter(method -> method.getDeclaringClass().isAssignableFrom(referenceClass))
          .findFirst();
        if (firstMethod.isPresent() && (reference = resolveMethodReference(firstMethod.get())) != null)
            return reference;

        return null;
    }

    public Object[] resolveMethodParameterReferences(Method method) {
        List<Object> parameterReferences = new LinkedList<>();

        for (Parameter parameter : method.getParameters()) {
            final String namedValue;
            if (parameter.isAnnotationPresent(Named.class) &&
              !(namedValue = parameter.getAnnotation(Named.class).value()).equals("")) {
                parameterReferences.add(resolveReference(namedValue));
            } else {
                parameterReferences.add(resolveReference(parameter.getType()));
            }
        }

        return parameterReferences.toArray();
    }

    private Object resolveMethodReference(Method method) {
        Class<?> declaringClass = method.getDeclaringClass();

        Object declaringClassReference = resolveReference(declaringClass);
        if (declaringClassReference != null) return invokeMethodAndReturnResult(method, declaringClassReference);

        if (!declaringClass.isAnnotationPresent(Module.class) && isFirstConstructorEmpty(declaringClass))
            return invokeMethodAndReturnResult(method, newInstance(getFirstConstructor(declaringClass)));

        return null;
    }

}
