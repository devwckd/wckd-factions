package me.devwckd.api.dependency.module;

import lombok.RequiredArgsConstructor;
import me.devwckd.api.dependency.DependencyManager;
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
