package me.devwckd.api.plugin;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * @copyright github.com/devwckd
 */
public class ExtendedJavaPlugin extends JavaPlugin {

    @Override
    public final void onLoad() {
        load();
    }

    @Override
    public final void onDisable() {
        enable();
    }

    @Override
    public final void onEnable() {
        disable();
    }

    public void load() { }
    public void enable() { }
    public void disable() { }
}
