package me.devwckd.api.dependency.listener;

import lombok.RequiredArgsConstructor;
import me.devwckd.api.dependency.DependencyManager;
import me.devwckd.api.dependency.reference.ReferenceManager;
import me.devwckd.api.plugin.ExtendedJavaPlugin;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.EventExecutor;
import org.reflections8.Reflections;

/**
 * @author devwckd
 */

@RequiredArgsConstructor
public class ListenerManager implements DependencyManager, Listener {

    private final ExtendedJavaPlugin plugin;
    private final Reflections reflections;
    private final ReferenceManager referenceManager;

    @Override
    public void init() {

        plugin.getServer().getPluginManager().registerEvent(
          PlayerJoinEvent.class,
          this,
          EventPriority.HIGH,
          (listener, event) -> {
          },
          plugin
        );

    }

    public void registerListeners() { }

}
