package app.ashcon.intake.bungee.parametric.annotation;

import app.ashcon.intake.parametric.annotation.Classifier;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * Attaches to a {@link ProxiedPlayer} parameter to signal that the {@link
 * net.md_5.bungee.api.CommandSender} should be a {@link ProxiedPlayer}.
 */
@Classifier
@Retention(RetentionPolicy.RUNTIME)
public @interface Sender {

}
