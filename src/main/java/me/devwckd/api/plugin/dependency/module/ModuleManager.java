package me.devwckd.api.plugin.dependency.module;

import lombok.RequiredArgsConstructor;
import me.devwckd.api.plugin.dependency.DependencyManager;
import org.reflections8.Reflections;

/**
 * @author devwckd
 */

@RequiredArgsConstructor
public class ModuleManager implements DependencyManager {

    private final Reflections reflections;

    @Override
    public void init() { }

    public void callLoad() { }

    public void callEnable() { }

    public void callDisable() { }

}
