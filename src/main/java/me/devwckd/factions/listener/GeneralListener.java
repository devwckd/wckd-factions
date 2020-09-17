package me.devwckd.factions.listener;

import lombok.RequiredArgsConstructor;
import me.devwckd.factions.FactionsPlugin;
import me.devwckd.library.common.dependency.listener.AutoRegisterListener;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;

/**
 * @author devwckd
 */

@AutoRegisterListener
@RequiredArgsConstructor
public class GeneralListener implements Listener {

    private final FactionsPlugin plugin;

    @EventHandler
    private void onPluginEnable(PluginEnableEvent event) {
        Bukkit.getConsoleSender().sendMessage("§c" + plugin.getDescription().getFullName());
    }
}
