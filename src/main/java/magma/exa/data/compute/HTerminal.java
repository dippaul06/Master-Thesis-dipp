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

import magma.exa.data.compute.pipe.*;

/**
 * An operation in a sequence pipeline that takes a lazy sequence
 * as input and produces a result or side effect.
 *
 * @param <P_OUT> type of pipe consumer.
 * @param <R> type of value.
 */
public interface HTerminal<P_OUT, R> {

    /**
     * Returns the outbound consumer.
     */
    P_OUT apply();

    // ----------------------------------------------------------
    //  HTERMINAL.TO
    // ----------------------------------------------------------

    /**
     * A terminal operation that produces a result of type {@code R}.
     *
     * @param <P_OUT> type of pipe consumer.
     * @param <R> type of result.
     */
    interface To<P_OUT, R> extends HTerminal<P_OUT, R> {

        /**
         * Returns the result of type {@code R}.
         */
        R result();
    }

    // ----------------------------------------------------------
    //  HTERMINAL.TO-BOOL
    // ----------------------------------------------------------

    /**
     * A terminal operation that produces a {@code boolean} result.
     *
     * @param <P_OUT> type of pipe consumer.
     */
    interface ToBool<P_OUT> extends HTerminal<P_OUT, Boolean> {

        /**
         * Returns the {@code boolean} result.
         */
        boolean result();
    }

    // ----------------------------------------------------------
    //  HTERMINAL.TO-I32
    // ----------------------------------------------------------

    /**
     * A terminal operation that produces a {@code int} result.
     *
     * <P_OUT> type of pipe consumer.
     */
    interface ToI32<P_OUT> extends HTerminal<P_OUT, Integer> {

        /**
         * Returns the {@code int} result.
         */
        int result();
    }

    // ----------------------------------------------------------
    //  HTERMINAL.TO-I64
    // ----------------------------------------------------------

    /**
     * A terminal operation that produces a {@code long} result.
     *
     * @param <P_OUT> type of pipe consumer.
     */
    interface ToI64<P_OUT> extends HTerminal<P_OUT, Long> {

        /**
         * Returns the {@code long} result.
         */
        long result();
    }

    // ----------------------------------------------------------
    //  HTERMINAL.TO-F32
    // ----------------------------------------------------------

    /**
     * A terminal operation that produces a {@code float} result.
     *
     * @param <P_OUT> type of pipe consumer.
     */
    interface ToF32<P_OUT> extends HTerminal<P_OUT, Float> {

        /**
         * Returns the {@code float} result.
         */
        float result();
    }

    // ----------------------------------------------------------
    //  HTERMINAL.TO-F64
    // ----------------------------------------------------------

    /**
     * A terminal operation that produces a {@code double} result.
     *
     * @param <P_OUT> type of pipe consumer.
     */
    interface ToF64<P_OUT> extends HTerminal<P_OUT, Double> {

        /**
         * Returns the {@code double} result.
         */
        double result();
    }

    // ----------------------------------------------------------
    //  HTERMINAL.FROM-I64
    // ----------------------------------------------------------

    /**
     * Terminal shape that processes a sequence of type {@code A}.
     *
     * @param <A> type of values.
     * @param <R> type of result.
     */
    abstract class From<A, R> implements HTerminal<Pipe<A>, R>, Pipe<A> {

        /**
         * Terminal shape that processes a sequence of type {@code A}
         * to produce a result of type {@code R}.
         *
         * @param <A> type of values.
         * @param <R> type of result.
         */
        public static abstract class To<A, R> extends From<A, R>
                implements HTerminal.To<Pipe<A>, R> {

            /**
             * {@inheritDoc}
             */
            @Override
            public final Pipe<A> apply() {
                return this;
            }
        }

        // ----------------------------------------------------------

        /**
         * Terminal shape that processes a sequence of type {@code A}
         * to produce a {@code boolean} result.
         *
         * @param <A> type of values.
         */
        public static abstract class ToBool<A> extends From<A, Boolean>
                implements HTerminal.ToBool<Pipe<A>> {

            /**
             * {@inheritDoc}
             */
            @Override
            public final Pipe<A> apply() {
                return this;
            }
        }

        // ----------------------------------------------------------

        /**
         * Terminal shape that processes a sequence of type {@code A}
         * to produce a {@code int} result.
         *
         * @param <A> type of values.
         */
        public static abstract class ToI32<A> extends From<A, Integer>
                implements HTerminal.ToI32<Pipe<A>> {

            /**
             * {@inheritDoc}
             */
            @Override
            public final Pipe<A> apply() {
                return this;
            }
        }

        // ----------------------------------------------------------

        /**
         * Terminal shape that processes a sequence of type {@code A}
         * to produce a {@code long} result.
         *
         * @param <A> type of values.
         */
        public static abstract class ToI64<A> extends From<A, Long>
                implements HTerminal.ToI64<Pipe<A>> {

            /**
             * {@inheritDoc}
             */
            @Override
            public final Pipe<A> apply() {
                return this;
            }
        }

        // ----------------------------------------------------------

        /**
         * Terminal shape that processes a sequence of type {@code A}
         * to produce a {@code float} result.
         *
         * @param <A> type of values.
         */
        public static abstract class ToF32<A> extends From<A, Float>
                implements HTerminal.ToF32<Pipe<A>> {

            /**
             * {@inheritDoc}
             */
            @Override
            public final Pipe<A> apply() {
                return this;
            }
        }

        // ----------------------------------------------------------

        /**
         * Terminal shape that processes a sequence of type {@code A}
         * to produce a {@code double} result.
         *
         * @param <A> type of values.
         */
        public static abstract class ToF64<A> extends From<A, Double>
                implements HTerminal.ToF64<Pipe<A>> {

            /**
             * {@inheritDoc}
             */
            @Override
            public final Pipe<A> apply() {
                return this;
            }
        }
    }

    // ----------------------------------------------------------
    //  HTERMINAL.FROM-I32
    // ----------------------------------------------------------

    /**
     * Terminal shape that processes a {@code int} sequence.
     *
     * @param <R> type of result.
     */
    abstract class FromI32<R> implements HTerminal<I32Pipe, R>, I32Pipe {

        /**
         * Terminal shape that processes a {@code int} sequence
         * to produce a result of type {@code R}.
         *
         * @param <R> type of result.
         */
        public static abstract class To<R> extends FromI32<R>
                implements HTerminal.To<I32Pipe, R> {

            /**
             * {@inheritDoc}
             */
            @Override
            public final I32Pipe apply() {
                return this;
            }
        }

        // ----------------------------------------------------------

        /**
         * Terminal shape that processes a {@code int} sequence
         * to produce a {@code boolean} result.
         */
        public static abstract class ToBool extends FromI32<Boolean>
                implements HTerminal.ToBool<I32Pipe> {

            /**
             * {@inheritDoc}
             */
            @Override
            public final I32Pipe apply() {
                return this;
            }
        }

        // ----------------------------------------------------------

        /**
         * Terminal shape that processes a {@code int} sequence
         * to produce a {@code int} result.
         */
        public static abstract class ToI32 extends FromI32<Integer>
                implements HTerminal.ToI32<I32Pipe> {

            /**
             * {@inheritDoc}
             */
            @Override
            public final I32Pipe apply() {
                return this;
            }
        }

        // ----------------------------------------------------------

        /**
         * Terminal shape that processes a {@code int} sequence
         * to produce a {@code long} result.
         */
        public static abstract class ToI64 extends FromI32<Long>
                implements HTerminal.ToI64<I32Pipe> {

            /**
             * {@inheritDoc}
             */
            @Override
            public final I32Pipe apply() {
                return this;
            }
        }

        // ----------------------------------------------------------

        /**
         * Terminal shape that processes a {@code int} sequence
         * to produce a {@code float} result.
         */
        public static abstract class ToF32<A> extends FromI32<Float>
                implements HTerminal.ToF32<I32Pipe> {

            /**
             * {@inheritDoc}
             */
            @Override
            public final I32Pipe apply() {
                return this;
            }
        }

        // ----------------------------------------------------------

        /**
         * Terminal shape that processes a {@code int} sequence
         * to produce a {@code double} result.
         */
        public static abstract class ToF64<A> extends FromI32<Double>
                implements HTerminal.ToF64<I32Pipe> {

            /**
             * {@inheritDoc}
             */
            @Override
            public final I32Pipe apply() {
                return this;
            }
        }
    }

    // ----------------------------------------------------------
    //  HTERMINAL.FROM-I64
    // ----------------------------------------------------------

    /**
     * Terminal shape that processes a {@code long} sequence.
     *
     * @param <R> type of result.
     */
    abstract class FromI64<R> implements HTerminal<I64Pipe, R>, I64Pipe {

        /**
         * Terminal shape that processes a {@code long} sequence
         * to produce a result of type {@code R}.
         *
         * @param <R> type of result.
         */
        public static abstract class To<R> extends FromI64<R>
                implements HTerminal.To<I64Pipe, R> {

            /**
             * {@inheritDoc}
             */
            @Override
            public final I64Pipe apply() {
                return this;
            }
        }

        // ----------------------------------------------------------

        /**
         * Terminal shape that processes a {@code long} sequence
         * to produce a {@code boolean} result.
         */
        public static abstract class ToBool extends FromI64<Boolean>
                implements HTerminal.ToBool<I64Pipe> {

            /**
             * {@inheritDoc}
             */
            @Override
            public final I64Pipe apply() {
                return this;
            }
        }

        // ----------------------------------------------------------

        /**
         * Terminal shape that processes a {@code long} sequence
         * to produce a {@code int} result.
         */
        public static abstract class ToI32 extends FromI64<Integer>
                implements HTerminal.ToI32<I64Pipe> {

            /**
             * {@inheritDoc}
             */
            @Override
            public final I64Pipe apply() {
                return this;
            }
        }

        // ----------------------------------------------------------

        /**
         * Terminal shape that processes a {@code long} sequence
         * to produce a {@code long} result.
         */
        public static abstract class ToI64 extends FromI64<Long>
                implements HTerminal.ToI64<I64Pipe> {

            /**
             * {@inheritDoc}
             */
            @Override
            public final I64Pipe apply() {
                return this;
            }
        }

        // ----------------------------------------------------------

        /**
         * Terminal shape that processes a {@code long} sequence
         * to produce a {@code float} result.
         */
        public static abstract class ToF32 extends FromI64<Float>
                implements HTerminal.ToF32<I64Pipe> {

            /**
             * {@inheritDoc}
             */
            @Override
            public final I64Pipe apply() {
                return this;
            }
        }

        // ----------------------------------------------------------

        /**
         * Terminal shape that processes a {@code long} sequence
         * to produce a {@code double} result.
         */
        public static abstract class ToF64 extends FromI64<Double>
                implements HTerminal.ToF64<I64Pipe> {

            /**
             * {@inheritDoc}
             */
            @Override
            public final I64Pipe apply() {
                return this;
            }
        }
    }

    // ----------------------------------------------------------
    //  HTERMINAL.FROM-F32
    // ----------------------------------------------------------

    /**
     * Terminal shape that processes a {@code float} sequence.
     *
     * @param <R> type of result.
     */
    abstract class FromF32<R> implements HTerminal<F32Pipe, R>, F32Pipe {

        /**
         * Terminal shape that processes a {@code float} sequence
         * to produce a result of type {@code R}.
         *
         * @param <R> type of result.
         */
        public static abstract class To<R> extends FromF32<R>
                implements HTerminal.To<F32Pipe, R> {

            /**
             * {@inheritDoc}
             */
            @Override
            public final F32Pipe apply() {
                return this;
            }
        }

        // ----------------------------------------------------------

        /**
         * Terminal shape that processes a {@code float} sequence
         * to produce a {@code boolean} result.
         */
        public static abstract class ToBool extends FromF32<Boolean>
                implements HTerminal.ToBool<F32Pipe> {

            /**
             * {@inheritDoc}
             */
            @Override
            public final F32Pipe apply() {
                return this;
            }
        }

        // ----------------------------------------------------------

        /**
         * Terminal shape that processes a {@code float} sequence
         * to produce a {@code int} result.
         */
        public static abstract class ToI32 extends FromF32<Integer>
                implements HTerminal.ToI32<F32Pipe> {

            /**
             * {@inheritDoc}
             */
            @Override
            public final F32Pipe apply() {
                return this;
            }
        }

        // ----------------------------------------------------------

        /**
         * Terminal shape that processes a {@code float} sequence
         * to produce a {@code long} result.
         */
        public static abstract class ToI64 extends FromF32<Long>
                implements HTerminal.ToI64<F32Pipe> {

            /**
             * {@inheritDoc}
             */
            @Override
            public final F32Pipe apply() {
                return this;
            }
        }

        // ----------------------------------------------------------

        /**
         * Terminal shape that processes a {@code float} sequence
         * to produce a {@code float} result.
         */
        public static abstract class ToF32 extends FromF32<Float>
                implements HTerminal.ToF32<F32Pipe> {

            /**
             * {@inheritDoc}
             */
            @Override
            public final F32Pipe apply() {
                return this;
            }
        }

        // ----------------------------------------------------------

        /**
         * Terminal shape that processes a {@code float} sequence
         * to produce a {@code double} result.
         */
        public static abstract class ToF64 extends FromF32<Double>
                implements HTerminal.ToF64<F32Pipe> {

            /**
             * {@inheritDoc}
             */
            @Override
            public final F32Pipe apply() {
                return this;
            }
        }
    }

    // ----------------------------------------------------------
    //  HTERMINAL.FROM-F64
    // ----------------------------------------------------------

    /**
     * Terminal shape that processes a {@code double} sequence.
     *
     * @param <R> type of result.
     */
    abstract class FromF64<R> implements HTerminal<F64Pipe, R>, F64Pipe {

        /**
         * Terminal shape that processes a {@code double} sequence
         * to produce a result of type {@code R}.
         *
         * @param <R> type of result.
         */
        public static abstract class To<R> extends FromF64<R>
                implements HTerminal.To<F64Pipe, R> {

            /**
             * {@inheritDoc}
             */
            @Override
            public final F64Pipe apply() {
                return this;
            }
        }

        // ----------------------------------------------------------

        /**
         * Terminal shape that processes a {@code double} sequence
         * to produce a {@code boolean} result.
         */
        public static abstract class ToBool extends FromF64<Boolean>
                implements HTerminal.ToBool<F64Pipe> {

            /**
             * {@inheritDoc}
             */
            @Override
            public final F64Pipe apply() {
                return this;
            }
        }

        // ----------------------------------------------------------

        /**
         * Terminal shape that processes a {@code double} sequence
         * to produce a {@code int} result.
         */
        public static abstract class ToI32 extends FromF64<Integer>
                implements HTerminal.ToI32<F64Pipe> {

            /**
             * {@inheritDoc}
             */
            @Override
            public final F64Pipe apply() {
                return this;
            }
        }

        // ----------------------------------------------------------

        /**
         * Terminal shape that processes a {@code double} sequence
         * to produce a {@code long} result.
         */
        public static abstract class ToI64 extends FromF64<Long>
                implements HTerminal.ToI64<F64Pipe> {

            /**
             * {@inheritDoc}
             */
            @Override
            public final F64Pipe apply() {
                return this;
            }
        }

        // ----------------------------------------------------------

        /**
         * Terminal shape that processes a {@code double} sequence
         * to produce a {@code float} result.
         */
        public static abstract class ToF32 extends FromF64<Float>
                implements HTerminal.ToF32<F64Pipe> {

            /**
             * {@inheritDoc}
             */
            @Override
            public final F64Pipe apply() {
                return this;
            }
        }

        // ----------------------------------------------------------

        /**
         * Terminal shape that processes a {@code double} sequence
         * to produce a {@code double} result.
         */
        public static abstract class ToF64 extends FromF64<Double>
                implements HTerminal.ToF64<F64Pipe> {

            /**
             * {@inheritDoc}
             */
            @Override
            public final F64Pipe apply() {
                return this;
            }
        }
    }
}


