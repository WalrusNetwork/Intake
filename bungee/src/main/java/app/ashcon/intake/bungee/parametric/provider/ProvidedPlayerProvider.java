package app.ashcon.intake.bungee.parametric.provider;

import app.ashcon.intake.argument.ArgumentException;
import app.ashcon.intake.argument.CommandArgs;
import app.ashcon.intake.bungee.parametric.annotation.Sender;
import app.ashcon.intake.parametric.ProvisionException;
import java.lang.annotation.Annotation;
import java.util.List;
import javax.annotation.Nullable;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * Provides the {@link ProxiedPlayer} who sent the command, annotated with {@link Sender}.
 */
public class ProvidedPlayerProvider implements BungeeProvider<ProxiedPlayer> {

  @Override
  public boolean isProvided() {
    return true;
  }

  @Nullable
  @Override
  public ProxiedPlayer get(CommandSender sender, CommandArgs args, List<? extends Annotation> mods)
      throws ArgumentException, ProvisionException {
    if (sender instanceof ProxiedPlayer) {
      return (ProxiedPlayer) sender;
    }
    throw new ArgumentException("You must be a player to use this command");
  }

}
