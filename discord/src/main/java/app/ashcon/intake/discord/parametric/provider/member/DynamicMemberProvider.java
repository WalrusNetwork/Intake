package app.ashcon.intake.discord.parametric.provider.member;

import app.ashcon.intake.argument.ArgumentException;
import app.ashcon.intake.argument.ArgumentParseException;
import app.ashcon.intake.discord.parametric.provider.DynamicProvider;
import java.util.List;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class DynamicMemberProvider extends DynamicProvider<Member> {

  @Override
  public Member search(GuildMessageReceivedEvent event, String query) throws ArgumentException {
    Member found = event.getGuild().getMemberByTag(query);
    if (found != null) {
      return found;
    }

    found = checkList(event.getGuild().getMembersByNickname(query, true));
    if (found != null) {
      return found;
    }

    found = checkList(event.getGuild().getMembersByName(query, true));
    if (found != null) {
      return found;
    }

    found = checkList(event.getGuild().getMembersByEffectiveName(query, true));
    if (found != null) {
      return found;
    }

    return null;
  }

  private Member checkList(List<Member> members) throws ArgumentException {
    if (members.size() > 1) {
      throw new ArgumentParseException("More than one member matched query");
    }
    if (members.isEmpty()) {
      return null;
    }

    return members.get(0);
  }

  @Override
  public Member fallback(GuildMessageReceivedEvent event) {
    return event.getMember();
  }

  @Override
  public String getName() {
    return "member";
  }
}
