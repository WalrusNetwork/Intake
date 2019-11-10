package app.ashcon.intake.discord.parametric.provider;

import app.ashcon.intake.argument.CommandArgs;
import app.ashcon.intake.parametric.ProvisionException;
import java.lang.annotation.Annotation;
import java.util.List;
import javax.annotation.Nullable;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

/** Provides the {@link Member} of the command. */
public class MemberProvider implements DiscordProvider<Member> {

  @Override
  public boolean isProvided() {
    return true;
  }

  @Nullable
  @Override
  public Member get(GuildMessageReceivedEvent event, CommandArgs args, List<? extends Annotation> mods)
      throws ProvisionException {
    return event.getMember();
  }
}
