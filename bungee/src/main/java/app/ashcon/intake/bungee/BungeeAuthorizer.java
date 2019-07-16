package app.ashcon.intake.bungee;

import app.ashcon.intake.argument.Namespace;
import app.ashcon.intake.util.auth.Authorizer;
import net.md_5.bungee.api.CommandSender;

/**
 * An {@link Authorizer} that checks if the {@link CommandSender} has permission to execute the
 * command.
 */
public class BungeeAuthorizer implements Authorizer {

  @Override
  public boolean testPermission(Namespace namespace, String permission) {
    return namespace.need(CommandSender.class).hasPermission(permission);
  }

}
