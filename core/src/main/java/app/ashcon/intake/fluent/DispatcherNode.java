/*
 * Intake, a command processing library
 * Copyright (C) sk89q <http://www.sk89q.com>
 * Copyright (C) Intake team and contributors
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package app.ashcon.intake.fluent;

import app.ashcon.intake.dispatcher.Dispatcher;
import app.ashcon.intake.dispatcher.SimpleDispatcher;
import app.ashcon.intake.parametric.ParametricBuilder;
import java.lang.reflect.Method;

/**
 * A collection of commands.
 */
public class DispatcherNode extends AbstractDispatcherNode {

    /**
     * Create a new instance.
     *
     * @param graph      the root fluent graph object
     * @param parent     the getParent node, or null
     * @param dispatcher the dispatcher for this node
     */
    public DispatcherNode(CommandGraph graph, AbstractDispatcherNode parent, Dispatcher dispatcher) {
        super(graph, parent, dispatcher);
    }

    /**
     * Build and register all commands within a class with this dispatcher using
     * the {@link ParametricBuilder} assigned on the root {@link CommandGraph}.
     *
     * @param object the object who's methods are provided to the {@link ParametricBuilder}
     * @return this object
     * @see ParametricBuilder#registerMethodAsCommand(Dispatcher, Method)
     */
    public AbstractDispatcherNode registerCommands(Object object) {
        for(Method method : object.getClass().getDeclaredMethods())
            graph.getBuilder().registerMethodAsCommand(getDispatcher(), object, method);
        return this;
    }

    /**
     * Create a new command that will contain sub-commands.
     *
     * <p>The object returned by this method can be used to add sub-commands. To
     * return to this "getParent" context, use {@link DispatcherNode#graph}.</p>
     *
     * @param aliases the list of aliases, where the first aliases is the primary one
     * @return an object to place sub-commands
     */
    @Override
    public DispatcherNode registerNode(String... aliases) {
        SimpleDispatcher command = new SimpleDispatcher();
        getDispatcher().registerCommand(command, aliases);
        return new DispatcherNode(graph, this, command);
    }

    /**
     * {@inheritDoc}
     */
    public DispatcherNode getParent() {
        return (DispatcherNode) super.getParent();
    }

}
