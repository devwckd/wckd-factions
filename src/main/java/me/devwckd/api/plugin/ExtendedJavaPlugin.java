package me.devwckd.api.plugin;

import me.devwckd.api.dependency.command.CommandManager;
import me.devwckd.api.dependency.export.ExportManager;
import me.devwckd.api.dependency.listener.ListenerManager;
import me.devwckd.api.dependency.module.ModuleManager;
import me.devwckd.api.dependency.reference.ReferenceManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections8.Reflections;
import org.reflections8.scanners.MethodAnnotationsScanner;
import org.reflections8.scanners.SubTypesScanner;
import org.reflections8.scanners.TypeAnnotationsScanner;
import org.reflections8.util.ConfigurationBuilder;

/**
 * @author devwckd
 */
public class ExtendedJavaPlugin extends JavaPlugin {

    private ReferenceManager referenceManager;
    private ExportManager exportManager;
    private ModuleManager moduleManager;
    private ListenerManager listenerManager;
    private CommandManager commandManager;

    @Override
    public final void onLoad() {
        final Reflections reflections = new Reflections(
          new ConfigurationBuilder()
            .addScanners(
              new MethodAnnotationsScanner(),
              new TypeAnnotationsScanner(),
              new SubTypesScanner(false)
            )
          .forPackages(this.getClass().getPackage().getName())
        );

        referenceManager = new ReferenceManager(this);
        exportManager = new ExportManager(reflections, referenceManager);
        moduleManager = new ModuleManager(reflections, referenceManager);
        listenerManager = new ListenerManager(this, reflections, referenceManager);
        commandManager = new CommandManager(reflections, referenceManager);

        referenceManager.init();
        exportManager.init();
        moduleManager.init();
        listenerManager.init();
        commandManager.init();

        moduleManager.callLoad();
        load();
    }

    @Override
    public final void onEnable() {
        listenerManager.registerListeners();
        commandManager. registerCommands();

        moduleManager.callEnable();
        enable();
    }

    @Override
    public final void onDisable() {
        disable();
        moduleManager.callDisable();
    }

    public void load() { }
    public void enable() { }
    public void disable() { }
}
