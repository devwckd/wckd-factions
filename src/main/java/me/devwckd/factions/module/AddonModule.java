package me.devwckd.factions.module;

import lombok.RequiredArgsConstructor;
import me.devwckd.lib.dependency.module.Module;
import me.devwckd.lib.dependency.module.ModuleEnable;
import me.devwckd.factions.util.filter.JarFilter;
import org.reflections8.Reflections;
import org.reflections8.util.ConfigurationBuilder;

import java.io.File;

/**
 * @author devwckd
 */

@Module
@RequiredArgsConstructor
public class AddonModule {

    private final FileModule fileModule;

    private File addonsFolder;

    @ModuleEnable
    public void enable() {
        addonsFolder = fileModule.getAddonsFolder();

        loadAddons();
    }

    private void loadAddons() {
        try {
            for (File jar : addonsFolder.listFiles(JarFilter.getInstance())) {
                final Reflections reflections = new Reflections(
                  new ConfigurationBuilder()
                  .addUrls(jar.toURI().toURL())
                );
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
