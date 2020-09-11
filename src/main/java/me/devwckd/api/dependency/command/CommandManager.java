package me.devwckd.api.dependency.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.devwckd.api.dependency.DependencyManager;
import me.devwckd.api.dependency.reference.ReferenceManager;
import me.devwckd.api.plugin.ExtendedJavaPlugin;
import me.devwckd.api.util.ReflectionUtils;
import me.saiintbrisson.bukkit.command.BukkitFrame;
import org.reflections8.Reflections;

import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.Set;

import static me.devwckd.api.util.ReflectionUtils.*;

/**
 * @author devwckd
 */

@Getter
@RequiredArgsConstructor
public class CommandManager implements DependencyManager {

    private final ExtendedJavaPlugin plugin;
    private final Reflections reflections;
    private final ReferenceManager referenceManager;
    private final Set<Class<?>> classes = new HashSet<>();

    private BukkitFrame commandFramework;

    @Override
    public void init() {
        classes.addAll(reflections.getTypesAnnotatedWith(AutoRegisterCommand.class));
        classes.addAll(reflections.getTypesAnnotatedWith(AutoRegisterCompleter.class));
    }

    public void registerCommands() {
        commandFramework = new BukkitFrame(plugin);

        for (Class<?> clazz : classes) {
            final Object instance;
            final Constructor<?> firstConstructor = getFirstConstructor(clazz);

            if (isFirstConstructorEmpty(clazz)) {
                instance = newInstance(firstConstructor);
            } else {
                instance = newInstance(firstConstructor,
                  referenceManager.resolveParameterReferences(firstConstructor.getParameters()));
            }

            if (clazz.isAnnotationPresent(AutoRegisterCommand.class) ||
              clazz.isAnnotationPresent(AutoRegisterCompleter.class))
                commandFramework.registerCommands(instance);
        }
    }

}
