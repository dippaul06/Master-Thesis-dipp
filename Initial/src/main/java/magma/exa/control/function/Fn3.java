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

import magma.exa.adt.typelevel.nat.$3;
import magma.exa.base.Force;
import magma.exa.control.adt.function.IFn3;
import magma.exa.value.Unit;
import magma.exa.value.adt.product.Product3;

/**
 * Function that takes 3 arguments and returns a result of type {@code R}.
 *
 * @param <A> type of 1st parameter.
 * @param <B> type of 2nd parameter.
 * @param <C> type of 3rd parameter.
 * @param <R> type of function result.
 */
@FunctionalInterface
public interface Fn3<A, B, C, R> extends IFn3<$3, A, B, C, R> {

    /**
     * Returns the function arity.
     */
    @Override
    default $3 arity() {
        return $3.nat;
    }

    /// LAZY APPLICATION.

    /**
     * Produces a thunk by fixing the arguments and deferring
     * the computation until the result is needed.
     */
    default Fn0<R> thunk(final A a, final B b, final C c) {
        return () -> onApply(a, b, c);
    }

    /// PARTIAL APPLICATION.

    /** Partially applies this function by passing the first 2 arguments. */
    @Override default Fn1<C, R> apply(final A a, final B b) {
        return c -> onApply(a, b, c);
    }

    /** Partially applies this function by passing the first argument. */
    @Override default Fn2<B, C, R> apply(final A a) {
        return (b, c) -> onApply(a, b, c);
    }

    /// UNCURRY ISOMORPHISM.

    /**
     * Produces the tupled function form: instead of 3 arguments,
     * it accepts a single {@link Product3} argument.
     */
    default Fn1<Product3<? extends A, ? extends B, ? extends C>, R> tupled() {
        return p -> onApply(p._1(), p._2(), p._3());
    }

    // ----------------------------------------------------------
    //  FN3.PREDICATE
    // ----------------------------------------------------------

    /**
     * A predicate (boolean-valued function) that takes 3 arguments.
     *
     * @param <A> type of 1st parameter.
     * @param <B> type of 2nd parameter.
     * @param <C> type of 3rd parameter.
     */
    @FunctionalInterface
    interface Predicate<A, B, C> extends Fn3<A, B, C, Boolean>, IPredicate<$3, A, B, C> {

        /** Predicate that always evaluates to {@code true}. */
        static <A, B, C> Predicate<A, B, C> True() {
            return Force.cast(Constant.True);
        }

        /** Predicate that always evaluates to {@code false}. */
        static <A, B, C> Predicate<A, B, C> False() {
            return Force.cast(Constant.False);
        }

        /// LAZY APPLICATION.

        /**
         * Produces a thunk by fixing the arguments and deferring
         * the computation until the result is needed.
         */
        @Override default Fn0.Predicate thunk(final A a, final B b, final C c) {
            return () -> onEval(a, b, c);
        }

        /// PARTIAL APPLICATION.

        /** Partially applies this predicate by passing the first 2 arguments. */
        @Override default Fn1.Predicate<C> apply(final A a, final B b) {
            return c -> onEval(a, b, c);
        }

        /** Partially applies this predicate by passing the first argument. */
        @Override default Fn2.Predicate<B, C> apply(final A a) {
            return (b, c) -> onEval(a, b, c);
        }

        /// UNCURRY ISOMORPHISM.

        /**
         * Produces the tupled predicate form: instead of 3 arguments,
         * it accepts a single {@link Product3} argument.
         */
        default Fn1.Predicate<Product3<? extends A, ? extends B, ? extends C>> tupled() {
            return p -> onApply(p._1(), p._2(), p._3());
        }
    }

    // ----------------------------------------------------------
    //  FN3.CONSUMER
    // ----------------------------------------------------------

    /**
     * A side effect operation that accepts 3 arguments.
     *
     * @param <A> type of 1st parameter.
     * @param <B> type of 2nd parameter.
     * @param <C> type of 3rd parameter.
     */
    @FunctionalInterface
    interface Consumer<A, B, C> extends Fn3<A, B, C, Unit>, IConsumer<$3, A, B, C> {

        /** Returns an empty consumer. */
        static <A, B, C> Consumer<A, B, C> Empty() {
            return Force.cast(Constant.empty);
        }

        /// LAZY APPLICATION.

        /**
         * Produces a thunk by fixing the arguments and deferring
         * the computation until the result is needed.
         */
        @Override default Fn0.Consumer thunk(final A a, final B b, final C c) {
            return () -> onAccept(a, b, c);
        }

        /// PARTIAL APPLICATION.

        /** Partially applies this consumer by passing the first 2 arguments. */
        @Override default Fn1.Consumer<C> apply(final A a, final B b) {
            return c -> onAccept(a, b, c);
        }

        /** Partially applies this consumer by passing the first argument. */
        @Override default Fn2.Consumer<B, C> apply(final A a) {
            return (b, c) -> onAccept(a, b, c);
        }

        /// UNCURRY ISOMORPHISM.

        /**
         * Produces the tupled consumer form: instead of 3 arguments,
         * it accepts a single {@link Product3} argument.
         */
        default Fn1.Consumer<Product3<? extends A, ? extends B, ? extends C>> tupled() {
            return p -> onAccept(p._1(), p._2(), p._3());
        }
    }

    // ----------------------------------------------------------

    /**
     * Constructs a {@link Fn3} function that invariably returns the given value.
     */
    static <A, B, C, R> Fn3<A, B, C, R> constant(final R val) {
        return (a, b, c) -> val;
    }

    /**
     * Constructs a {@link Fn3} function from the given curried {@link Fn1} function.
     */
    static <A, B, C, R> Fn3<A, B, C, R> curried(final Fn1<A, Fn2<B, C, R>> fn) {
        return (a, b, c) -> fn.onApply(a).onApply(b, c);
    }

    /**
     * Constructs a {@link Fn3} function from the given curried {@link Fn2} function.
     */
    static <A, B, C, R> Fn3<A, B, C, R> curried(final Fn2<A, B, Fn1<C, R>> fn) {
        return (a, b, c) -> fn.onApply(a, b).onApply(c);
    }
}