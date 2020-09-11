package me.devwckd.api.dependency.module;

import lombok.RequiredArgsConstructor;
import me.devwckd.api.dependency.DependencyManager;
import me.devwckd.api.dependency.export.Named;
import me.devwckd.api.dependency.reference.ReferenceManager;
import me.devwckd.api.graph.Graph;
import me.devwckd.api.graph.impl.HashGraph;
import org.reflections8.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;

import static me.devwckd.api.util.ReflectionUtils.*;

/**
 * @author devwckd
 */

@RequiredArgsConstructor
public class ModuleManager implements DependencyManager {

    private final Reflections reflections;
    private final ReferenceManager referenceManager;

    private final Graph<Class<?>> moduleGraph = new HashGraph<>();
    private final LinkedHashSet<Object> modules = new LinkedHashSet<>();

    private final LinkedHashMap<Method, Object> loadMethods = new LinkedHashMap<>();
    private final LinkedHashMap<Method, Object> enableMethods = new LinkedHashMap<>();
    private final LinkedHashMap<Method, Object> disableMethods = new LinkedHashMap<>();

    @Override
    public void init() {
        loadModuleGraph();
        loadModules();
    }

    public void callLoad() {
        for (Map.Entry<Method, Object> load : loadMethods.entrySet()) {
            final Method method = load.getKey();
            Object[] parameters = referenceManager.resolveParameterReferences(method.getParameters());
            invokeMethodAndReturnResult(method, load.getValue(), parameters);
        }
    }

    public void callEnable() {
        for (Map.Entry<Method, Object> load : enableMethods.entrySet()) {
            final Method method = load.getKey();
            Object[] parameters = referenceManager.resolveParameterReferences(method.getParameters());
            invokeMethodAndReturnResult(method, load.getValue(), parameters);
        }
    }

    public void callDisable() {
        final List<Map.Entry<Method, Object>> disableMethodsList = Arrays.asList(disableMethods.entrySet().toArray(new Map.Entry[0]));
        Collections.reverse(disableMethodsList);

        for (Map.Entry<Method, Object> load : disableMethodsList) {
            final Method method = load.getKey();
            Object[] parameters = referenceManager.resolveParameterReferences(method.getParameters());
            invokeMethodAndReturnResult(method, load.getValue(), parameters);
        }
    }

    private void loadModuleGraph() {
        final Set<Class<?>> moduleClasses = reflections.getTypesAnnotatedWith(Module.class);

        for (Class<?> moduleClass : moduleClasses) {
            final Constructor<?> firstConstructor = getFirstConstructor(moduleClass);

            final Class<?>[] parameterTypes = getConstructorParameterTypes(firstConstructor);
            if (parameterTypes.length < 1) {
                final Object module = newInstance(firstConstructor);

                modules.add(module);
                referenceManager.addReference(module);

                continue;
            }

            moduleGraph.addVertex(moduleClass, parameterTypes);
        }
    }

    private void loadModules() {
        for (Class<?> vertex : moduleGraph.getVertices()) {
            final LinkedHashSet<Class<?>> edges = moduleGraph.depthFirstTraversal(vertex);

            final List<Class<?>> edgeList = Arrays.asList(edges.toArray(new Class<?>[0]));
            Collections.reverse(edgeList);

            for (Class<?> edge : edgeList) {
                Object module = loadModule(edge);
                loadStateMethods(module);
            }
        }
    }

    private Object loadModule(Class<?> moduleClass) {
        Object reference = referenceManager.resolveReference(moduleClass);
        if (reference != null) return reference;

        final Constructor<?> firstConstructor = getFirstConstructor(moduleClass);

        List<Object> params = new LinkedList<>();
        for (Parameter parameter : getConstructorParameters(firstConstructor)) {

            final String namedValue;
            if (parameter.isAnnotationPresent(Named.class) &&
              !(namedValue = parameter.getAnnotation(Named.class).value()).equalsIgnoreCase("")
            ) {
                reference = referenceManager.resolveReference(namedValue);
            } else {
                reference = referenceManager.resolveReference(parameter.getType());
            }

            if (reference == null)
                throw new NullPointerException("Could not resolve " + moduleClass.getSimpleName() + "'s " +
                  parameter.getName() + " constructor parameter reference.");

            params.add(reference);
        }

        Object module = newInstance(firstConstructor, params.toArray());
        modules.add(module);
        referenceManager.addReference(module);

        return module;
    }

    private void loadStateMethods(Object module) {
        Class<?> moduleClass = module.getClass();

        for (Method declaredMethod : moduleClass.getDeclaredMethods()) {
            if (declaredMethod.isAnnotationPresent(ModuleLoad.class))
                loadMethods.put(declaredMethod, module);

            if (declaredMethod.isAnnotationPresent(ModuleEnable.class))
                enableMethods.put(declaredMethod, module);

            if (declaredMethod.isAnnotationPresent(ModuleDisable.class))
                disableMethods.put(declaredMethod, module);
        }
    }

}
