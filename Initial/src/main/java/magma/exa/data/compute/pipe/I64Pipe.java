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
import magma.exa.value.scalar.I64;

/**
 * Pipe specialized for {@code long} values.
 */
public interface I64Pipe extends HPipe<Long>, I64.Consumer {

    /**
     * Accepts {@code long} values.
     */
    @Override
    void onAccept(long value) throws Throwable;

    // ----------------------------------------------------------
    //  I64-PIPE.CHAINED
    // ----------------------------------------------------------

    /**
     * Chaining of a {@code long}-based input pipe with some output pipe.
     *
     * @param <P> type of output pipe.
     */
    abstract class Chained<P extends HPipe<?>>
            extends HPipe.Chained<Long, P>
            implements I64Pipe {

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
