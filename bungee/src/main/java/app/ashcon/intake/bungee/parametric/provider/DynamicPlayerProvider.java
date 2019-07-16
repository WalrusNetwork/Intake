package app.ashcon.intake.bungee.parametric.provider;

import app.ashcon.intake.argument.ArgumentException;
import app.ashcon.intake.argument.CommandArgs;
import app.ashcon.intake.argument.Namespace;
import app.ashcon.intake.bungee.parametric.Type;
import app.ashcon.intake.bungee.parametric.annotation.Fallback;
import app.ashcon.intake.parametric.ProvisionException;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * Provides {@link ProxiedPlayer}s from a name query.
 */
public class DynamicPlayerProvider implements BungeeProvider<ProxiedPlayer> {

  @Override
  public String getName() {
    return "player";
  }

  @Nullable
  @Override
  public ProxiedPlayer get(CommandSender sender, CommandArgs args, List<? extends Annotation> mods)
      throws ArgumentException, ProvisionException {
    String query = null;
    if (args.hasNext()) {
      query = args.next();
      final ProxiedPlayer player = ProxyServer.getInstance().getPlayer(query);
      if (player != null) {
        return player;
      }
    }
    final Type type = getType(mods);
    if (type == Type.NULL) {
      return null;
    } else if (type == Type.SELF && sender instanceof ProxiedPlayer) {
      return (ProxiedPlayer) sender;
    } else if (query != null) {
      throw new ArgumentException("Could not find player named '" + query + "'");
    } else {
      throw new ArgumentException("You must provide a player name");
    }
  }

  @Override
  public List<String> getSuggestions(String prefix, CommandSender sender, Namespace namespace,
      List<? extends Annotation> mods) {
    return ProxyServer.getInstance().getPlayers()
        .stream()
        .map(CommandSender::getName)
        .filter(name -> name.toLowerCase().startsWith(prefix.toLowerCase()))
        .sorted()
        .collect(Collectors.toList());
  }

  private Type getType(List<? extends Annotation> mods) {
    for (Annotation mod : mods) {
      if (mod instanceof Fallback) {
        return ((Fallback) mod).value();
      }
    }
    return Type.THROW;
  }

}
