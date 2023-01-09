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

import magma.exa.base.Force;
import magma.exa.control.function.Fn1;
import magma.exa.data.compute.pipe.*;

/**
 * Common supertype of a unary pipeline operator that uses contravariant
 * composition to form linear, chains of heterogeneous pipes.
 *
 * @param <P_INP>  type of input pipe.
 * @param <P_OUT> type of output pipe.
 */
public interface HOperator<
        P_INP extends HPipe<?>,
        P_OUT extends HPipe<?>
        > extends Fn1<P_OUT, P_INP> {

    /**
     * {@inheritDoc}
     */
    @Override
    P_INP onApply(P_OUT out) throws Throwable;

    /**
     * Contravariant composition of pipeline operators.
     *
     * @param next    operation to be attached to this operator.
     * @param <P_NXT> type of appended pipe.
     * @return composed operator.
     */
    default <P_NXT extends HPipe<?>>
    HOperator<P_INP, P_NXT> contraMap(final HOperator<P_OUT, P_NXT> next) {
        return Fn1.super.contraMap(next)::onApply;
    }

    // ----------------------------------------------------------
    //  OPERATOR.TO-REF
    // ----------------------------------------------------------

    /**
     * Operator that processes values from an upstream pipe to produce
     * reference values that are passed to the downstream pipe.
     *
     * @param <P_IN> type of upstream pipe.
     */
    abstract class To<P_IN extends HPipe<?>, V_OUT>
            implements HOperator<P_IN, Pipe<V_OUT>> {

        /**
         * Operator factory.
         */
        public static <P_IN extends HPipe<?>, V_OUT>
        To<P_IN, V_OUT> make(final To<P_IN, V_OUT> op) {
            return op;
        }
    }

    // ----------------------------------------------------------
    //  OPERATOR.TO-I32
    // ----------------------------------------------------------

    /**
     * Operator that processes values from an upstream pipe to produce
     * {@code int} values that are passed to the downstream pipe.
     *
     * @param <P_IN> type of upstream pipe.
     */
    abstract class ToI32<P_IN extends HPipe<?>>
            implements HOperator<P_IN, I32Pipe> {

        /**
         * Operator factory.
         */
        public static <P_IN extends HPipe<?>>
        HOperator.ToI32<P_IN> make(final HOperator.ToI32<P_IN> op) {
            return op;
        }
    }

    // ----------------------------------------------------------
    //  OPERATOR.TO-I64
    // ----------------------------------------------------------

    /**
     * Operator that processes values from an upstream pipe to produce
     * {@code long} values that are passed to the downstream pipe.
     *
     * @param <P_IN> type of upstream pipe.
     */
    abstract class ToI64<P_IN extends HPipe<?>>
            implements HOperator<P_IN, I64Pipe> {

        /**
         * Operator factory.
         */
        public static <P_IN extends HPipe<?>>
        HOperator.ToI64<P_IN> make(final HOperator.ToI64<P_IN> op) {
            return op;
        }
    }

    // ----------------------------------------------------------
    //  OPERATOR.TO-F32
    // ----------------------------------------------------------

    /**
     * Operator that processes values from an upstream pipe to produce
     * {@code float} values that are passed to the downstream pipe.
     *
     * @param <P_IN> type of upstream pipe.
     */
    abstract class ToF32<P_IN extends HPipe<?>>
            implements HOperator<P_IN, F32Pipe> {

        /**
         * Operator factory.
         */
        public static <P_IN extends HPipe<?>>
        HOperator.ToF32<P_IN> make(final HOperator.ToF32<P_IN> op) {
            return op;
        }
    }

    // ----------------------------------------------------------
    //  OPERATOR.TO-F64
    // ----------------------------------------------------------

    /**
     * Operator that processes values from an upstream pipe to produce
     * {@code double} values that are passed to the downstream pipe.
     *
     * @param <P_IN> type of upstream pipe.
     */
    abstract class ToF64<P_IN extends HPipe<?>>
            implements HOperator<P_IN, F64Pipe> {

        /**
         * Operator factory.
         */
        public static <P_IN extends HPipe<?>>
        HOperator.ToF64<P_IN> make(final HOperator.ToF64<P_IN> op) {
            return op;
        }
    }

    // ----------------------------------------------------------

    /**
     * Identity pipeline operator.
     */
    static <P extends HPipe<?>> HOperator<P, P> identity() {
        enum Identity implements HOperator<HPipe<?>, HPipe<?>> {
            operator {
                @Override
                public final HPipe<?> onApply(final HPipe<?> out) {
                    return out;
                }
            }
        }
        return Force.cast(Identity.operator);
    }
}


