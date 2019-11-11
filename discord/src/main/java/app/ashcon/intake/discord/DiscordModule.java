package app.ashcon.intake.discord;

import app.ashcon.intake.discord.parametric.annotation.Sender;
import app.ashcon.intake.discord.parametric.provider.RoleProvider;
import app.ashcon.intake.discord.parametric.provider.channel.DynamicChannelProvider;
import app.ashcon.intake.discord.parametric.provider.channel.ProvidedChannelProvider;
import app.ashcon.intake.discord.parametric.provider.member.DynamicMemberProvider;
import app.ashcon.intake.discord.parametric.provider.member.ProvidedMemberProvider;
import app.ashcon.intake.parametric.AbstractModule;
import app.ashcon.intake.parametric.Provider;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.Role;

/** Default binding list of {@link Provider}s. */
public class DiscordModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(MessageChannel.class).toProvider(new DynamicChannelProvider());
    bind(MessageChannel.class).annotatedWith(Sender.class).toProvider(new ProvidedChannelProvider());
    bind(Member.class).toProvider(new DynamicMemberProvider());
    bind(Member.class).annotatedWith(Sender.class).toProvider(new ProvidedMemberProvider());
    bind(Role.class).toProvider(new RoleProvider());
  }
}
