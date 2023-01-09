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

package magma.exa.data.compute.pipe;

import magma.exa.control.function.Fn1;
import magma.exa.data.compute.HPipe;

/**
 * Pipe specialized for reference values of type {@code A}.
 *
 * @param <A> type of value.
 */
public interface Pipe<A> extends HPipe<A>, Fn1.Consumer<A> {

    /**
     * Accepts values of type {@code A}.
     */
    @Override
    void onAccept(A value) throws Throwable;

    // ----------------------------------------------------------
    //  PIPE.CHAINED
    // ----------------------------------------------------------

    /**
     * Chaining of a reference-based input pipe with some output-pipe.
     *
     * @param <B> type of output pipe.
     */
    abstract class Chained<A, B extends HPipe<?>>
            extends HPipe.Chained<A, B>
            implements Pipe<A> {

        /**
         * Constructs a chain to the given output pipe.
         *
         * @param out output pipe to be chained.
         */
        protected Chained(final B out) {
            super(out);
        }
    }
}
