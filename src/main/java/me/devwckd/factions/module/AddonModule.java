package me.devwckd.factions.module;

import lombok.RequiredArgsConstructor;
import me.devwckd.api.dependency.module.Module;
import me.devwckd.api.dependency.module.ModuleDisable;
import me.devwckd.api.dependency.module.ModuleEnable;
import me.devwckd.api.dependency.module.ModuleLoad;
import me.devwckd.factions.util.filter.JarFilter;
import org.reflections8.Reflections;
import org.reflections8.util.ConfigurationBuilder;

import java.io.File;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

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
