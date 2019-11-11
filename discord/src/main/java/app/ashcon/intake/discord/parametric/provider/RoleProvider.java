package app.ashcon.intake.discord.parametric.provider;

import app.ashcon.intake.argument.ArgumentException;
import app.ashcon.intake.argument.ArgumentParseException;
import java.util.List;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class RoleProvider extends DynamicProvider<Role> {

  @Override
  public Role search(GuildMessageReceivedEvent event, String query) throws ArgumentException {
    List<Role> found = event.getGuild().getRolesByName(query, true);
    if (found.size() > 1) {
      throw new ArgumentParseException("More than one role matched query");
    }

    if (found.isEmpty()) {
      return null;
    }

    return found.get(0);
  }

  @Override
  public Role fallback(GuildMessageReceivedEvent event) {
    return null;
  }
}
