package me.devwckd.factions.command;

import me.devwckd.lib.dependency.command.AutoRegisterCommand;
import me.devwckd.lib.dependency.command.AutoRegisterCompleter;
import me.saiintbrisson.bukkit.command.command.BukkitContext;
import me.saiintbrisson.minecraft.command.annotation.Command;

/**
 * @author devwckd
 */

@AutoRegisterCompleter
@AutoRegisterCommand
public class FactionsCommand {

    static final String LABEL = " §e/factions §7- §fOpens the player GUI.";

    @Command(
      name = "factions",
      aliases = {"faction", "fac", "f"}
    )
    private void onFactionsCommand(BukkitContext bukkitContext) {
        // TODO: show player info GUI.
    }

}
