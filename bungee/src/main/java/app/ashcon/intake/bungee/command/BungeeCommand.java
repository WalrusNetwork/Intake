package app.ashcon.intake.bungee.command;

import app.ashcon.intake.CommandMapping;
import app.ashcon.intake.bungee.BungeeIntake;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import java.util.List;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.TabExecutor;

/**
 * A wrapped {@link Command} that hooks up to a {@link CommandMapping}, and generates all the proper
 * help and usage information.
 */
public class BungeeCommand extends Command implements TabExecutor {

  private final Plugin plugin;
  private final BungeeIntake executor;

  public BungeeCommand(Plugin plugin, BungeeIntake executor, CommandMapping command) {
    super(
        command.getPrimaryAlias(),
        command.getDescription().getShortDescription(),
        Joiner.on(";").join(command.getDescription().getPermissions()));
    this.plugin = plugin;
    this.executor = executor;
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    executor.execute(sender, this, args);
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, String[] args)
      throws IllegalArgumentException {
    List<String> suggestions = executor.onTabComplete(sender, this, args);
    if (suggestions != null) {
      return suggestions;
    }
    return Lists.newArrayList();
  }
}
