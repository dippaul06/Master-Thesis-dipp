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

import magma.exa.data.compute.HPipe;
import magma.exa.value.scalar.F64;

/**
 * Pipe specialized for {@code double} values.
 */
public interface F64Pipe extends HPipe<Double>, F64.Consumer {

    /**
     * Accepts {@code double} values.
     */
    @Override
    void onAccept(double value) throws Throwable;

    // ----------------------------------------------------------
    //  F64-PIPE.CHAINED
    // ----------------------------------------------------------

    /**
     * Chaining of a {@code double}-based input pipe with some output pipe.
     *
     * @param <P> type of output pipe.
     */
    abstract class Chained<P extends HPipe<?>>
            extends HPipe.Chained<Double, P>
            implements F64Pipe {

        /**
         * Constructs a chain to the given output pipe.
         *
         * @param out output pipe to be chained.
         */
        protected Chained(final P out) {
            super(out);
        }
    }
}
