package app.ashcon.intake.discord.parametric.provider;

import app.ashcon.intake.argument.ArgumentException;
import app.ashcon.intake.argument.CommandArgs;
import app.ashcon.intake.discord.parametric.Type;
import app.ashcon.intake.discord.parametric.annotation.Fallback;
import app.ashcon.intake.parametric.ProvisionException;
import java.lang.annotation.Annotation;
import java.util.List;
import javax.annotation.Nullable;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public abstract class DynamicProvider<T> implements DiscordProvider<T> {

  public abstract T search(GuildMessageReceivedEvent event, String query) throws ArgumentException;

  public abstract T fallback(GuildMessageReceivedEvent event);

  @Nullable
  @Override
  public T get(GuildMessageReceivedEvent event, CommandArgs args, List<? extends Annotation> mods)
      throws ArgumentException, ProvisionException {
    String query = null;
    if (args.hasNext()) {
      query = args.next();
      final T found = search(event, query);
      if (found != null) {
        return found;
      }
    }
    final Type type = getType(mods);
    if (type == Type.NULL) {
      return null;
    } else if (type == Type.CURRENT) {
      T fallback = fallback(event);
      if (fallback == null) {
        throw new ArgumentException("You must provide a " + getName() + " name");
      }

      return fallback;
    } else if (query != null) {
      throw new ArgumentException("Could not find " + getName() + " named '" + query + "'");
    } else {
      throw new ArgumentException("You must provide a " + getName() + " name");
    }
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
