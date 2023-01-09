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

package magma.exa.control.function;

import magma.exa.adt.typelevel.nat.$2;
import magma.exa.base.Force;
import magma.exa.control.adt.function.IFn2;
import magma.exa.value.Unit;
import magma.exa.value.adt.product.Product2;

/**
 * Function that takes 2 arguments and returns a result of type {@code R}.
 *
 * @param <A> type of 1st parameter.
 * @param <B> type of 2nd parameter.
 * @param <R> type of function result.
 */
@FunctionalInterface
public interface Fn2<A, B, R> extends IFn2<$2, A, B, R> {

    /**
     * Returns the function arity.
     */
    @Override
    default $2 arity() {
        return $2.nat;
    }

    /// LAZY APPLICATION.

    /**
     * Produces a thunk by fixing the arguments and deferring
     * the computation until the result is needed.
     */
    default Fn0<R> thunk(final A a, final B b) {
        return () -> onApply(a, b);
    }

    /// PARTIAL APPLICATION.

    /** Partially applies this function by passing the first argument. */
    @Override default Fn1<B, R> apply(final A a) {
        return b -> onApply(a, b);
    }

    /// UNCURRY ISOMORPHISM.

    /**
     * Produces the tupled function form: instead of 2 arguments,
     * it accepts a single {@link Product2} argument.
     */
    default Fn1<Product2<? extends A, ? extends B>, R> tupled() {
        return p -> onApply(p._1(), p._2());
    }

    // ----------------------------------------------------------
    //  FN2.PREDICATE
    // ----------------------------------------------------------

    /**
     * A predicate (boolean-valued function) that takes 2 arguments.
     *
     * @param <A> type of 1st parameter.
     * @param <B> type of 2nd parameter.
     */
    @FunctionalInterface
    interface Predicate<A, B> extends Fn2<A, B, Boolean>, IPredicate<$2, A, B> {

        /** Predicate that always evaluates to {@code true}. */
        static <A, B> Predicate<A, B> True() {
            return Force.cast(Constant.True);
        }

        /** Predicate that always evaluates to {@code false}. */
        static <A, B> Predicate<A, B> False() {
            return Force.cast(Constant.False);
        }

        /// LAZY APPLICATION.

        /**
         * Produces a thunk by fixing the arguments and deferring
         * the computation until the result is needed.
         */
        @Override default Fn0.Predicate thunk(final A a, final B b) {
            return () -> onEval(a, b);
        }

        /// PARTIAL APPLICATION.

        /** Partially applies this predicate by passing the first argument. */
        @Override default Fn1.Predicate<B> apply(final A a) {
            return b -> onEval(a, b);
        }

        /// UNCURRY ISOMORPHISM.

        /**
         * Produces the tupled predicate form: instead of 2 arguments,
         * it accepts a single {@link Product2} argument.
         */
        default Fn1.Predicate<Product2<? extends A, ? extends B>> tupled() {
            return p -> onEval(p._1(), p._2());
        }
    }

    // ----------------------------------------------------------
    //  FN2.CONSUMER
    // ----------------------------------------------------------

    /**
     * A side effect operation that accepts 2 arguments.
     *
     * @param <A> type of 1st parameter.
     * @param <B> type of 2nd parameter.
     */
    @FunctionalInterface
    interface Consumer<A, B> extends Fn2<A, B, Unit>, IConsumer<$2, A, B> {

        /** Returns an empty consumer. */
        static <A, B> Consumer<A, B> Empty() {
            return Force.cast(Constant.empty);
        }

        /// LAZY APPLICATION.

        /**
         * Produces a thunk by fixing the arguments and deferring
         * the computation until the result is needed.
         */
        @Override default Fn0.Consumer thunk(final A a, final B b) {
            return () -> onAccept(a, b);
        }

        /// PARTIAL APPLICATION.

        /** Partially applies this consumer by passing the first argument. */
        @Override default Fn1.Consumer<B> apply(final A a) {
            return b -> onAccept(a, b);
        }

        /// UNCURRY ISOMORPHISM.

        /**
         * Produces the tupled consumer form: instead of 2 arguments,
         * it accepts a single {@link Product2} argument.
         */
        default Fn1.Consumer<Product2<? extends A, ? extends B>> tupled() {
            return p -> onAccept(p._1(), p._2());
        }
    }

    // ----------------------------------------------------------

    /**
     * Constructs a {@link Fn2} function that invariably returns the given value.
     */
    static <A, B, R> Fn2<A, B, R> constant(final R val) {
        return (a, b) -> val;
    }

    /**
     * Constructs a {@link Fn2} function from the given curried {@link Fn1} function.
     */
    static <A, B, R> Fn2<A, B, R> curried(final Fn1<A, Fn1<B, R>> fn) {
        return (a, b) -> fn.onApply(a).onApply(b);
    }
}









