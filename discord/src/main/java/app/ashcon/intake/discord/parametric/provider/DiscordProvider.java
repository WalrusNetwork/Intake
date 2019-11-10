package app.ashcon.intake.discord.parametric.provider;

import app.ashcon.intake.argument.ArgumentException;
import app.ashcon.intake.argument.CommandArgs;
import app.ashcon.intake.argument.Namespace;
import app.ashcon.intake.parametric.Provider;
import app.ashcon.intake.parametric.ProvisionException;
import com.google.common.collect.ImmutableList;
import java.lang.annotation.Annotation;
import java.util.List;
import javax.annotation.Nullable;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

/** A {@link Provider} where the {@link GuildMessageReceivedEvent} is always present. */
public interface DiscordProvider<T> extends Provider<T> {

  @Nullable
  @Override
  default T get(CommandArgs arguments, List<? extends Annotation> modifiers)
      throws ArgumentException, ProvisionException {
    return get(arguments.getNamespace().need(GuildMessageReceivedEvent.class), arguments, modifiers);
  }

  @Nullable
  T get(GuildMessageReceivedEvent event, CommandArgs args, List<? extends Annotation> mods)
      throws ArgumentException, ProvisionException;

  @Override
  default List<String> getSuggestions(
      String prefix, Namespace namespace, List<? extends Annotation> modifiers) {
    return getSuggestions(prefix, namespace.need(GuildMessageReceivedEvent.class), namespace, modifiers);
  }

  default List<String> getSuggestions(
      String prefix, GuildMessageReceivedEvent event, Namespace namespace, List<? extends Annotation> mods) {
    return ImmutableList.of();
  }
}
