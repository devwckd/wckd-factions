package me.devwckd.factions;

import me.devwckd.api.dependency.export.Export;
import me.devwckd.api.plugin.ExtendedJavaPlugin;

/**
 * @author devwckd
 */
public class FactionsPlugin extends ExtendedJavaPlugin {

    @Export
    private FactionsPlugin plugin() {
        return this;
    }

}
