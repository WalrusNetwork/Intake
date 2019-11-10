package app.ashcon.intake.discord.commands;

import app.ashcon.intake.Command;
import app.ashcon.intake.CommandMapping;
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

  @Command(aliases = "help", desc = "View command help")
  public void help(MessageChannel channel) {
    EmbedBuilder builder = new EmbedBuilder();
    for (CommandMapping command : this.commands) {
      builder.addField(new Field(prefix + command.getPrimaryAlias(),
          command.getDescription().getShortDescription(), false));
    }
    channel.sendMessage(builder
        .setColor(Color.CYAN)
        .setTitle("Command Help")
        .build()).queue();
  }
}
