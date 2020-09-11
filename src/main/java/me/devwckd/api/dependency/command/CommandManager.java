package me.devwckd.api.dependency.command;

import lombok.RequiredArgsConstructor;
import me.devwckd.api.dependency.DependencyManager;
import me.devwckd.api.dependency.reference.ReferenceManager;
import org.reflections8.Reflections;

/**
 * @author devwckd
 */

@RequiredArgsConstructor
public class CommandManager implements DependencyManager {

    private final Reflections reflections;
    private final ReferenceManager referenceManager;

    @Override
    public void init() { }

    public void registerCommands() { }

}
