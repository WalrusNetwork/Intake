package app.ashcon.intake.discord;

import app.ashcon.intake.CommandMapping;
import app.ashcon.intake.InvalidUsageException;
import app.ashcon.intake.InvocationCommandException;
import app.ashcon.intake.argument.Namespace;
import app.ashcon.intake.discord.commands.HelpCommand;
import app.ashcon.intake.discord.graph.BasicDiscordCommandGraph;
import app.ashcon.intake.dispatcher.Dispatcher;
import app.ashcon.intake.dispatcher.Lockable;
import app.ashcon.intake.fluent.CommandGraph;
import app.ashcon.intake.util.auth.AuthorizationException;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import java.awt.Color;
import java.util.Collection;
import java.util.List;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

/** Omnibus API that allows implementors to register commands. */
public class DiscordIntake {

  private final String prefix;
  private final CommandGraph commandGraph;
  private final Multimap<Long, String> permissions;
  private final List<CommandMapping> commands = Lists.newArrayList();

  /**
   * Create a new {@link DiscordIntake} instance
   *
   * @param commandGraph A {@link CommandGraph} instance
   */
  public DiscordIntake(String prefix, BasicDiscordCommandGraph commandGraph, Multimap<Long, String> permissions) {
    Preconditions.checkNotNull(commandGraph, "Command graph can not be null");

    this.prefix = prefix;
    commandGraph
        .getBuilder()
        .setAuthorizer(
            (namespace, permission) -> checkPermission(
                namespace.need(GuildMessageReceivedEvent.class).getMember(), permission));
    commandGraph.getRootDispatcherNode().registerCommands(new HelpCommand(prefix, commandGraph.getRootDispatcherNode().getDispatcher().getCommands()));
    this.commandGraph = commandGraph;
    this.permissions = permissions;
  }

  /** Register all of the commands in the command graph */
  public void register() {
    Dispatcher dispatcher = getCommandGraph().getRootDispatcherNode().getDispatcher();

    if (!commands.isEmpty()) {
      throw new IllegalStateException("Commands have already been registered!");
    }

    if (dispatcher instanceof Lockable) {
      ((Lockable) dispatcher).lock();
    }

    commands.addAll(dispatcher.getCommands());
  }

  /** Unregister all of the commands in the command graph */
  public void unregister() {
    if (commands.isEmpty()) {
      return;
    }

    // Allow for re-registration
    commands.clear();
  }

  public void execute(GuildMessageReceivedEvent event) {
    if (!event.getMessage().getContentRaw().startsWith(this.prefix))
      return;
    try {
      getCommandGraph()
          .getRootDispatcherNode()
          .getDispatcher()
          .call(event.getMessage().getContentRaw().substring(1), getNamespace(event));
    } catch (AuthorizationException e) {
      event.getChannel().sendMessage(
          new EmbedBuilder()
              .setColor(Color.RED)
              .setDescription("You do not have permission to use this command!")
              .build()).queue();
    } catch (InvocationCommandException e) {
      event.getChannel().sendMessage(
          new EmbedBuilder()
              .setColor(Color.RED)
              .setDescription("An exception occurred while executing this command!")
              .build()).queue();
      e.getCause().printStackTrace();
    } catch (InvalidUsageException e) {
      if (e.getMessage() != null) {
        event.getChannel().sendMessage(
            new EmbedBuilder().setColor(Color.RED).setDescription(e.getMessage()).build()).queue();
      }
      if (e.isFullHelpSuggested()) {
        event.getChannel().sendMessage(
            new EmbedBuilder()
                .setColor(Color.RED)
                .setDescription(
                    this.prefix + Joiner.on(' ').join(e.getAliasStack()) + " "
                        + e.getCommand().getDescription().getUsage())
                .build()).queue();
      }
    } catch (Exception e) {
      event.getChannel().sendMessage(
          new EmbedBuilder().setColor(Color.RED).setDescription(e.getMessage()).build()).queue();
      e.printStackTrace();
    }
  }

  private boolean checkPermission(Member sender, String needed) {
    for (Role role : sender.getRoles()) {
      Collection<String> perms = permissions.get(role.getIdLong());
      if (perms == null) continue;

      if (perms.contains("*") || perms.contains(needed)) return true;
    }

    return false;
  }

  public CommandGraph getCommandGraph() {
    return commandGraph;
  }

  protected Namespace getNamespace(GuildMessageReceivedEvent event) {
    return new Namespace(GuildMessageReceivedEvent.class, event);
  }
}
