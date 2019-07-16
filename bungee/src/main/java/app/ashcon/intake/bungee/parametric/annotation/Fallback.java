package app.ashcon.intake.bungee.parametric.annotation;

import app.ashcon.intake.bungee.parametric.Type;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * Define the fetching behaviour of {@link ProxiedPlayer}s on the local server.
 * <p>
 * If not defined, the default is {@link Type#THROW}.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Fallback {

  Type value();
}
