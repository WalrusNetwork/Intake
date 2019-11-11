package app.ashcon.intake.discord.parametric;

/**
 * Various fetching behaviours for provided objects.
 *
 * @see app.ashcon.intake.discord.parametric.annotation.Fallback
 */
public enum Type {
  CURRENT,  // Object to current value
  NULL,     // Object to a null value
  THROW    // Object to a thrown exception (default)
}
