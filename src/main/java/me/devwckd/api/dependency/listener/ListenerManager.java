package me.devwckd.api.dependency.listener;

import lombok.RequiredArgsConstructor;
import me.devwckd.api.dependency.DependencyManager;
import me.devwckd.api.dependency.reference.ReferenceManager;
import me.devwckd.api.plugin.ExtendedJavaPlugin;
import me.devwckd.api.util.ReflectionUtils;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.reflections8.Reflections;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import static me.devwckd.api.util.ReflectionUtils.*;

/**
 * @author devwckd
 */

@RequiredArgsConstructor
public class ListenerManager implements DependencyManager, Listener {

    private final ExtendedJavaPlugin plugin;
    private final Reflections reflections;
    private final ReferenceManager referenceManager;

    private final List<Class<?>> listenerClasses = new ArrayList<>();

    @Override
    public void init() {
        for (Class<?> listenerClass : reflections.getTypesAnnotatedWith(AutoRegister.class)) {
            if(!Listener.class.isAssignableFrom(listenerClass)) continue;

            listenerClasses.add(listenerClass);
        }
    }

    public void registerListeners() {
        final PluginManager pluginManager = plugin.getServer().getPluginManager();

        for (Class<?> listenerClass : listenerClasses) {
            final Constructor<?> firstConstructor = getFirstConstructor(listenerClass);

            final Object listener;
            if(firstConstructor.getParameters().length < 1) {
                listener = newInstance(firstConstructor);
            } else {
                listener = newInstance(firstConstructor,
                  referenceManager.resolveParameterReferences(firstConstructor.getParameters()));
            }

            pluginManager.registerEvents((Listener) listener, plugin);
        }
    }

}
