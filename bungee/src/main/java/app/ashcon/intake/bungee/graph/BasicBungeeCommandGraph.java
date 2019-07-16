package app.ashcon.intake.bungee.graph;

import app.ashcon.intake.Intake;
import app.ashcon.intake.bungee.BungeeAuthorizer;
import app.ashcon.intake.bungee.BungeeModule;
import app.ashcon.intake.dispatcher.SimpleDispatcher;
import app.ashcon.intake.fluent.CommandGraph;
import app.ashcon.intake.fluent.DispatcherNode;
import app.ashcon.intake.parametric.Module;
import app.ashcon.intake.parametric.ParametricBuilder;
import app.ashcon.intake.parametric.provider.PrimitivesModule;
import java.util.Arrays;

public class BasicBungeeCommandGraph extends CommandGraph<DispatcherNode> {

  /**
   * Create a new command graph with a simple dispatcher node.
   */
  public BasicBungeeCommandGraph(Module... modules) {
    ParametricBuilder builder = new ParametricBuilder(Intake.createInjector());
    builder.setAuthorizer(new BungeeAuthorizer());

    builder.getInjector().install(new BungeeModule());
    builder.getInjector().install(new PrimitivesModule());

    Arrays.stream(modules).forEach(builder.getInjector()::install);

    setBuilder(builder);
    setRootDispatcherNode(new DispatcherNode(this, null, new SimpleDispatcher()));
  }
}
