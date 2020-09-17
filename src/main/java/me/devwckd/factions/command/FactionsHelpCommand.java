package me.devwckd.factions.command;

import me.devwckd.library.common.dependency.command.AutoRegisterCommand;
import me.devwckd.library.common.dependency.command.AutoRegisterCompleter;
import me.saiintbrisson.bukkit.command.command.BukkitContext;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.annotation.Optional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author devwckd
 */

@AutoRegisterCompleter
@AutoRegisterCommand
public class FactionsHelpCommand {

    static final String LABEL = " §e/factions help (page) §7- §fShows the plugin commands.";
    static final List<List<String>> pages = new ArrayList<List<String>>() {{
        add(new ArrayList<String>() {{
            add(FactionsCommand.LABEL);
            add(FactionsHelpCommand.LABEL);
        }});
    }};

    @Command(
      name = "factions.help",
      aliases = {"h"}
    )
    private void onFactionsCommand(
      BukkitContext bukkitContext,
      @Optional(def = "1") int page
    ) {
        page = page - 1;
        if (pages.size() >= page) page = pages.size() - 1;

        bukkitContext.sendMessage(
          new String[]{
            " ",
            " §c§lFactions §8- §7Help (page " + page + ")"
          }
        );

        for (String item : pages.get(page)) {
            bukkitContext.sendMessage(item);
        }

        bukkitContext.sendMessage(" ");
    }

}
