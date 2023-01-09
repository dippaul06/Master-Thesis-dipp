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

package magma.exa.data.compute;

import magma.exa.adt.typelevel.signature.$Fn1;
import magma.exa.base.contract.Require;
import magma.exa.control.function.Fn1;
import magma.exa.data.compute.pipe.*;
import magma.exa.value.Unit;
import magma.exa.value.scalar.F32;
import magma.exa.value.scalar.F64;
import magma.exa.value.scalar.I32;
import magma.exa.value.scalar.I64;

/**
 * A pipe abstracts the data flow and is used to conduct values
 * through the stages of a pipeline according to the First-In-
 * First-Out (FIFO) principle.
 *
 * @param <A> type of value.
 */
public interface HPipe<A> extends $Fn1<A, Unit> {

    /**
     *
     */
    default void open() { }

    /**
     *
     */
    default void close() { }

    // ----------------------------------------------------------
    //  HPIPE.CHAINED
    // ----------------------------------------------------------

    /**
     * Common superclass for all chained pipes.
     *
     * @param <A> type of input values.
     * @param <P_OUT> type of output pipe.
     */
    abstract class Chained<A, P_OUT extends HPipe<?>> implements HPipe<A> {

        /**
         * The output pipe receives processed values of this stage.
         */
        protected final P_OUT out;

        /**
         * Constructs a chaining with the given output pipe.
         *
         * @param out output pipe to be chained.
         */
        protected Chained(final P_OUT out) {
            this.out = Require.notNull(out);
        }

        /**
         * Forwards open-request.
         */
        @Override
        public void open() {
            out.open();
        }

        /**
         * Forwards close-request.
         */
        @Override
        public void close() {
            out.close();
        }
    }

    // ----------------------------------------------------------

    /**
     * Adapts the given consumer to the corresponding pipe.
     *
     * @param <A> type of value.
     * @param consumer to be adapted.
     * @return adapter pipe.
     */
    static <A> Pipe<A> adapt(final Fn1.Consumer<? super A> consumer) {
        return consumer::onAccept;
    }

    /**
     * Adapts the given consumer to the corresponding pipe.
     *
     * @param consumer to be adapted.
     * @return adapter pipe.
     */
    static I32Pipe adapt(final I32.Consumer consumer) {
        return consumer::onAccept;
    }

    /**
     * Adapts the given consumer to the corresponding pipe.
     *
     * @param consumer to be adapted.
     * @return adapter pipe.
     */
    static I64Pipe adapt(final I64.Consumer consumer) {
        return consumer::onAccept;
    }

    /**
     * Adapts the given consumer to the corresponding pipe.
     *
     * @param consumer to be adapted.
     * @return adapter pipe.
     */
    static F32Pipe adapt(final F32.Consumer consumer) {
        return consumer::onAccept;
    }

    /**
     * Adapts the given consumer to the corresponding pipe.
     *
     * @param consumer to be adapted.
     * @return adapter pipe.
     */
    static F64Pipe adapt(final F64.Consumer consumer) {
        return consumer::onAccept;
    }
}

