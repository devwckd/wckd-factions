package me.devwckd.api.plugin;

import me.devwckd.api.dependency.command.CommandManager;
import me.devwckd.api.dependency.listener.ListenerManager;
import me.devwckd.api.dependency.module.ModuleManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections8.Reflections;

/**
 * @author devwckd
 */
public class ExtendedJavaPlugin extends JavaPlugin {

    private ModuleManager moduleManager;
    private ListenerManager listenerManager;
    private CommandManager commandManager;

    @Override
    public final void onLoad() {
        final Reflections reflections = new Reflections();

        moduleManager = new ModuleManager(reflections);
        listenerManager = new ListenerManager(reflections, moduleManager);
        commandManager = new CommandManager(reflections, moduleManager);

        moduleManager.init();
        listenerManager.init();
        commandManager.init();

        moduleManager.callLoad();
        load();
    }

    @Override
    public final void onDisable() {
        listenerManager.registerListeners();
        commandManager. registerCommands();

        moduleManager.callEnable();
        enable();
    }

    @Override
    public final void onEnable() {
        disable();
        moduleManager.callDisable();
    }

    public void load() { }
    public void enable() { }
    public void disable() { }
}
