package app.ashcon.intake.discord.parametric.provider.channel;

import app.ashcon.intake.argument.CommandArgs;
import app.ashcon.intake.discord.parametric.provider.DiscordProvider;
import app.ashcon.intake.parametric.ProvisionException;
import java.lang.annotation.Annotation;
import java.util.List;
import javax.annotation.Nullable;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

/**
 * Provides the {@link MessageChannel} of the command.
 */
public class ProvidedChannelProvider implements DiscordProvider<MessageChannel> {

  @Override
  public boolean isProvided() {
    return true;
  }

  @Nullable
  @Override
  public MessageChannel get(GuildMessageReceivedEvent event, CommandArgs args,
      List<? extends Annotation> mods)
      throws ProvisionException {
    return event.getChannel();
  }
}
