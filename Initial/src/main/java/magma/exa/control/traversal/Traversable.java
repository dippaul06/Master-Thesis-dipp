//     _____
//    ╱     ╲┌────╭─╭──────╭─────────╮────╭─╮
//   ╱  ╲ ╱  ╲ ┌──╮ │ ╭──╮ ╮ ┬─╮ ┬─╮ ┌ ┌──╮ │
//  ╱    Y    ╲└──╰ ╵ ╰──╯ │ │ │ │ │ │ └──╰ │
//  ╲____│____╱╰──╰─┴╭───╯ ╰─╰─╯ ╰─╰─┴────╰─┴╲╲
//            ╲╭─────┘─────└────────────────╮╱╱
//
// Copyright (C) esentri.magma - All Rights Reserved.
//
// Unauthorized copying of this file, via any medium
// is strictly prohibited. Proprietary and confidential.

package magma.exa.control.traversal;

import magma.exa.control.adt.traversal.ITraversable;
import magma.exa.control.function.Fn1;

import java.util.Iterator;

/**
 * Exposes a {@link Traverser}, which enables traversal over a source of values.
 *
 * @param <A> type of value.
 */
public interface Traversable<A> extends Iterable<A>,
        ITraversable<Fn1.Consumer<? super A>> {

    /**
     * Returns an traverser.
     */
    Traverser<A> traverser();

    /**
     * {@inheritDoc}
     */
    @Override
    default Iterator<A> iterator() {
        return traverser().iterator();
    }
}
