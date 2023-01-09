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
import magma.exa.value.scalar.F32;

/**
 * Pipe specialized for {@code float} values.
 */
public interface F32Pipe extends HPipe<Float>, F32.Consumer {

    /**
     * Accepts {@code float} values.
     */
    @Override
    void onAccept(float value) throws Throwable;

    // ----------------------------------------------------------
    //  F32-PIPE.CHAINED
    // ----------------------------------------------------------

    /**
     * Chaining of a {@code float}-based input pipe with some output pipe.
     *
     * @param <P> type of output pipe.
     */
    abstract class Chained<P extends HPipe<?>>
            extends HPipe.Chained<Float, P>
            implements F32Pipe {

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
