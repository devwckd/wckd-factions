package me.devwckd.factions.module;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.devwckd.lib.dependency.module.Module;
import me.devwckd.lib.dependency.module.ModuleEnable;
import me.devwckd.factions.FactionsPlugin;

import java.io.File;

/**
 * @author devwckd
 */

@Module
@Getter
@RequiredArgsConstructor
public class FileModule {

    private final FactionsPlugin plugin;

    private File dataFolder;
    private File addonsFolder;

    @ModuleEnable
    private void enable() {
        checkDataFolder();
        checkAddonsFolder();
    }

    private void checkDataFolder() {
        dataFolder = plugin.getDataFolder();
        if(!dataFolder.exists())
            dataFolder.mkdirs();
    }

    private void checkAddonsFolder() {
        addonsFolder = new File(dataFolder, "/addons/");
        if(!addonsFolder.exists())
            addonsFolder.mkdirs();
    }

}
