package app.ashcon.intake.discord.commands;

import app.ashcon.intake.Command;
import app.ashcon.intake.CommandMapping;
import app.ashcon.intake.dispatcher.Dispatcher;
import java.awt.Color;
import java.util.Set;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed.Field;

public class HelpCommand {

  private final String prefix;
  private final Set<CommandMapping> commands;

  public HelpCommand(String prefix, Set<CommandMapping> commands) {
    this.prefix = prefix;
    this.commands = commands;
  }

  private void generateHelp(EmbedBuilder builder, CommandMapping command, String prefix) {
    if (command.getCallable() instanceof Dispatcher) {
      for (CommandMapping mapping : ((Dispatcher) command.getCallable()).getCommands()) {
        generateHelp(builder, mapping, prefix + " " + command.getPrimaryAlias() + " ");
      }
    } else {
      String desc = command.getDescription().getShortDescription();
      if (desc == null) desc = command.getDescription().getHelp();
      if (desc == null) desc = "N/A";
      builder.addField(new Field(prefix + command.getPrimaryAlias(), desc, false));
    }
  }

  @Command(aliases = "help", desc = "View command help")
  public void help(MessageChannel channel) {
    EmbedBuilder builder = new EmbedBuilder();
    for (CommandMapping command : this.commands) {
      generateHelp(builder, command, prefix);
    }
    channel.sendMessage(builder
        .setColor(Color.CYAN)
        .setTitle("Command Help")
        .build()).queue();
  }
}
