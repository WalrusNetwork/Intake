package app.ashcon.intake.bungee.parametric;

import app.ashcon.intake.bungee.parametric.annotation.Fallback;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * Various fetching behaviours of {@link ProxiedPlayer}s.
 *
 * @see Fallback
 */
public enum Type {
  SELF,  // Player to sender
  NULL,  // Player to a null value
  THROW; // Player to a thrown exception (default)
}
