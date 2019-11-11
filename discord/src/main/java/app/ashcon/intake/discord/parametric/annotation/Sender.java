package app.ashcon.intake.discord.parametric.annotation;

import app.ashcon.intake.parametric.annotation.Classifier;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Attaches to a provided parameter to signal that the value should be the inferred from the message
 * context.
 */
@Classifier
@Retention(RetentionPolicy.RUNTIME)
public @interface Sender {

}
