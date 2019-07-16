package app.ashcon.intake.bungee;

import app.ashcon.intake.CommandException;
import app.ashcon.intake.InvalidUsageException;
import app.ashcon.intake.InvocationCommandException;
import app.ashcon.intake.argument.Namespace;
import app.ashcon.intake.bungee.command.BungeeCommand;
import app.ashcon.intake.dispatcher.Dispatcher;
import app.ashcon.intake.dispatcher.Lockable;
import app.ashcon.intake.exception.TranslatableCommandException;
import app.ashcon.intake.fluent.CommandGraph;
import app.ashcon.intake.util.auth.AuthorizationException;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.stream.Collectors;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;

/**
 * Omnibus API that hooks into a {@link Plugin} and allows implementors to register commands.
 */
public class BungeeIntake {

  private final Plugin plugin;
  private final CommandGraph commandGraph;
  private final List<Command> commands = Lists.newArrayList();

  /**
   * Create a new {@link BungeeIntake} instance
   *
   * @param plugin The plugin's main class
   * @param commandGraph A {@link CommandGraph} instance
   */
  public BungeeIntake(Plugin plugin, CommandGraph commandGraph) {
    Preconditions.checkNotNull(plugin, "Plugin can not be null");
    Preconditions.checkNotNull(commandGraph, "Command graph can not be null");

    this.plugin = plugin;
    this.commandGraph = commandGraph;
  }

  /**
   * Register all of the commands in the command graph
   */
  public void register() {
    Dispatcher dispatcher = getCommandGraph().getRootDispatcherNode().getDispatcher();

    if (!commands.isEmpty()) {
      throw new IllegalStateException("Commands have already been registered!");
    }

    if (dispatcher instanceof Lockable) {
      ((Lockable) dispatcher).lock();
    }

    commands.addAll(dispatcher.getCommands()
        .stream()
        .map(cmd -> new BungeeCommand(plugin, this, cmd))
        .collect(Collectors.toList()));

    commands.forEach(c -> plugin.getProxy().getPluginManager().registerCommand(plugin, c));
  }

  /**
   * Unregister all of the commands in the command graph
   */
  public void unregister() {
    if (commands.isEmpty()) {
      return;
    }

    plugin.getProxy().getPluginManager().unregisterCommands(plugin);

    // Allow for re-registration
    commands.clear();
  }

  public void execute(CommandSender sender, Command command, String[] args) {
    try {
      getCommandGraph().getRootDispatcherNode().getDispatcher()
          .call(getCommand(command, args), getNamespace(sender));
    } catch (TranslatableCommandException e) {
      sender.sendMessage(e.getComponent());
    } catch (AuthorizationException e) {
      sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
    } catch (InvocationCommandException e) {
      sender.sendMessage(ChatColor.RED + "An exception occurred while executing this command!");
      e.getCause().printStackTrace();
    } catch (InvalidUsageException e) {
      if (e.getMessage() != null) {
        sender.sendMessage(ChatColor.RED + e.getMessage());
      }
      if (e.isFullHelpSuggested()) {
        sender.sendMessage(
            ChatColor.RED + "/" + Joiner.on(' ').join(e.getAliasStack()) + " " + e.getCommand()
                .getDescription()
                .getUsage());
      }
    } catch (CommandException e) {
      sender.sendMessage(ChatColor.RED + e.getMessage());
    }
  }

  public List<String> onTabComplete(CommandSender sender, Command command, String[] args) {
    try {
      return getCommandGraph().getRootDispatcherNode().getDispatcher()
          .getSuggestions(getCommand(command, args), getNamespace(sender));
    } catch (CommandException e) {
      return ImmutableList.of();
    }
  }

  public CommandGraph getCommandGraph() {
    return commandGraph;
  }

  protected String getCommand(Command command, String[] args) {
    return Joiner.on(' ').join(Lists.asList(command.getName(), args));
  }

  protected Namespace getNamespace(CommandSender sender) {
    return new Namespace(CommandSender.class, sender);
  }

}
