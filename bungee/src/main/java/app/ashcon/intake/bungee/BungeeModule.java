package app.ashcon.intake.bungee;

import app.ashcon.intake.bungee.parametric.annotation.Sender;
import app.ashcon.intake.bungee.parametric.provider.CommandSenderProvider;
import app.ashcon.intake.bungee.parametric.provider.DynamicPlayerProvider;
import app.ashcon.intake.bungee.parametric.provider.ProvidedPlayerProvider;
import app.ashcon.intake.parametric.AbstractModule;
import app.ashcon.intake.parametric.Provider;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * Default binding list of {@link Provider}s.
 */
public class BungeeModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(CommandSender.class).toProvider(new CommandSenderProvider());
    bind(ProxiedPlayer.class).annotatedWith(Sender.class).toProvider(new ProvidedPlayerProvider());
    bind(ProxiedPlayer.class).toProvider(new DynamicPlayerProvider());
  }

}
