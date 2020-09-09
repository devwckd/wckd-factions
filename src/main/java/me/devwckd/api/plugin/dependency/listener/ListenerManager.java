package me.devwckd.api.plugin.dependency.listener;

import lombok.RequiredArgsConstructor;
import me.devwckd.api.plugin.dependency.DependencyManager;
import me.devwckd.api.plugin.dependency.module.ModuleManager;
import org.reflections8.Reflections;

/**
 * @author devwckd
 */

@RequiredArgsConstructor
public class ListenerManager implements DependencyManager {

    private final Reflections reflections;
    private final ModuleManager moduleManager;

    @Override
    public void init() { }

    public void registerListeners() { }

}
