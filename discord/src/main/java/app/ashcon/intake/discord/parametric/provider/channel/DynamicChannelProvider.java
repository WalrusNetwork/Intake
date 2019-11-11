package app.ashcon.intake.discord.parametric.provider.channel;

import app.ashcon.intake.argument.ArgumentException;
import app.ashcon.intake.argument.ArgumentParseException;
import app.ashcon.intake.discord.parametric.provider.DynamicProvider;
import java.util.List;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class DynamicChannelProvider extends DynamicProvider<MessageChannel> {

  @Override
  public MessageChannel search(GuildMessageReceivedEvent event, String query)
      throws ArgumentException {
    List<TextChannel> found = event.getGuild().getTextChannelsByName(query, true);
    if (found.isEmpty()) {
      return null;
    }
    if (found.size() > 1) {
      throw new ArgumentParseException("Provided channel is too vague");
    }

    return found.get(0);
  }

  @Override
  public MessageChannel fallback(GuildMessageReceivedEvent event) {
    return event.getChannel();
  }

  @Override
  public String getName() {
    return "channel";
  }
}
