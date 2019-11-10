package app.ashcon.intake.discord;

import app.ashcon.intake.discord.parametric.provider.ChannelProvider;
import app.ashcon.intake.discord.parametric.provider.MemberProvider;
import app.ashcon.intake.parametric.AbstractModule;
import app.ashcon.intake.parametric.Provider;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;

/** Default binding list of {@link Provider}s. */
public class DiscordModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(Member.class).toProvider(new MemberProvider());
    bind(MessageChannel.class).toProvider(new ChannelProvider());
  }
}
