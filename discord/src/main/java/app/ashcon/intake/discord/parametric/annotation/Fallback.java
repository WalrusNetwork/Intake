package app.ashcon.intake.discord.parametric.annotation;

import app.ashcon.intake.discord.parametric.Type;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Define the fetching behaviour of provided objects.
 * <p>
 * If not defined, the default is {@link Type#THROW}.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Fallback {

  Type value();
}
