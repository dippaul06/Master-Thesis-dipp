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

import magma.exa.adt.typelevel.nat.$6;
import magma.exa.base.Force;
import magma.exa.control.adt.function.IFn6;
import magma.exa.value.Unit;
import magma.exa.value.adt.product.Product6;

/**
 * Function that takes 6 arguments and returns a result of type {@code R}.
 *
 * @param <A> type of 1st parameter.
 * @param <B> type of 2nd parameter.
 * @param <C> type of 3rd parameter.
 * @param <D> type of 4th parameter.
 * @param <E> type of 5th parameter.
 * @param <F> type of 6th parameter.
 * @param <R> type of function result.
 */
@FunctionalInterface
public interface Fn6<A, B, C, D, E, F, R> extends IFn6<$6, A, B, C, D, E, F, R> {

    /**
     * Returns the function arity.
     */
    @Override
    default $6 arity() {
        return $6.nat;
    }

    /// LAZY APPLICATION.

    /**
     * Produces a thunk by fixing the arguments and deferring
     * the computation until the result is needed.
     */
    default Fn0<R> thunk(final A a, final B b, final C c, final D d, final E e, final F f) {
        return () -> onApply(a, b, c, d, e, f);
    }

    /// PARTIAL APPLICATION.

    /** Partially applies this function by passing the first 5 arguments. */
    @Override default Fn1<F, R> apply(final A a, final B b, final C c, final D d, final E e) {
        return f -> onApply(a, b, c, d, e, f);
    }

    /** Partially applies this function by passing the first 4 arguments. */
    @Override default Fn2<E, F, R> apply(final A a, final B b, final C c, final D d) {
        return (e, f) -> onApply(a, b, c, d, e, f);
    }

    /** Partially applies this function by passing the first 3 arguments. */
    @Override default Fn3<D, E, F, R> apply(final A a, final B b, final C c) {
        return (d, e, f) -> onApply(a, b, c, d, e, f);
    }

    /** Partially applies this function by passing the first 2 arguments. */
    @Override default Fn4<C, D, E, F, R> apply(final A a, final B b) {
        return (c, d, e, f) -> onApply(a, b, c, d, e, f);
    }

    /** Partially applies this function by passing the first argument. */
    @Override default Fn5<B, C, D, E, F, R> apply(final A a) {
        return (b, c, d, e, f) -> onApply(a, b, c, d, e, f);
    }

    /// UNCURRY ISOMORPHISM.

    /**
     * Produces the tupled function form: instead of 6 arguments,
     * it accepts a single {@link Product6} argument.
     */
    default Fn1<Product6<? extends A, ? extends B, ? extends C, ? extends D, ? extends E, ? extends F>, R> tupled() {
        return p -> onApply(p._1(), p._2(), p._3(), p._4(), p._5(), p._6());
    }

    // ----------------------------------------------------------
    //  FN6.PREDICATE
    // ----------------------------------------------------------

    /**
     * A predicate (boolean-valued function) that takes 6 arguments.
     *
     * @param <A> type of 1st parameter.
     * @param <B> type of 2nd parameter.
     * @param <C> type of 3rd parameter.
     * @param <D> type of 4th parameter.
     * @param <E> type of 5th parameter.
     * @param <F> type of 6th parameter.
     */
    @FunctionalInterface
    interface Predicate<A, B, C, D, E, F> extends Fn6<A, B, C, D, E, F, Boolean>, IPredicate<$6, A, B, C, D, E, F> {

        /** Predicate that always evaluates to {@code true}. */
        static <A, B, C, D, E, F, G> Predicate<A, B, C, D, E, F> True() {
            return Force.cast(Constant.True);
        }

        /** Predicate that always evaluates to {@code false}. */
        static <A, B, C, D, E, F, G> Predicate<A, B, C, D, E, F> False() {
            return Force.cast(Constant.False);
        }

        /// LAZY APPLICATION.

        /**
         * Produces a thunk by fixing the arguments and deferring
         * the computation until the result is needed.
         */
        @Override default Fn0.Predicate thunk(final A a, final B b, final C c, final D d, final E e, final F f) {
            return () -> onEval(a, b, c, d, e, f);
        }

        /// PARTIAL APPLICATION.

        /** Partially applies this predicate by passing the first 5 arguments. */
        @Override default Fn1.Predicate<F> apply(final A a, final B b, final C c, final D d, final E e) {
            return f -> onEval(a, b, c, d, e, f);
        }

        /** Partially applies this predicate by passing the first 4 arguments. */
        @Override default Fn2.Predicate<E, F> apply(final A a, final B b, final C c, final D d) {
            return (e, f) -> onEval(a, b, c, d, e, f);
        }

        /** Partially applies this predicate by passing the first 3 arguments. */
        @Override default Fn3.Predicate<D, E, F> apply(final A a, final B b, final C c) {
            return (d, e, f) -> onEval(a, b, c, d, e, f);
        }

        /** Partially applies this predicate by passing the first 2 arguments. */
        @Override default Fn4.Predicate<C, D, E, F> apply(final A a, final B b) {
            return (c, d, e, f) -> onEval(a, b, c, d, e, f);
        }

        /** Partially applies this predicate by passing the first argument. */
        @Override default Fn5.Predicate<B, C, D, E, F> apply(final A a) {
            return (b, c, d, e, f) -> onEval(a, b, c, d, e, f);
        }

        /// UNCURRY ISOMORPHISM.

        /**
         * Produces the tupled predicate form: instead of 6 arguments,
         * it accepts a single {@link Product6} argument.
         */
        default Fn1.Predicate<Product6<? extends A, ? extends B, ? extends C, ? extends D, ? extends E, ? extends F>> tupled() {
            return p -> onEval(p._1(), p._2(), p._3(), p._4(), p._5(), p._6());
        }
    }

    // ----------------------------------------------------------
    //  FN6.CONSUMER
    // ----------------------------------------------------------

    /**
     * A side effect operation that accepts 6 arguments.
     *
     * @param <A> type of 1st parameter.
     * @param <B> type of 2nd parameter.
     * @param <C> type of 3rd parameter.
     * @param <D> type of 4th parameter.
     * @param <E> type of 5th parameter.
     * @param <F> type of 6th parameter.
     */
    @FunctionalInterface
    interface Consumer<A, B, C, D, E, F> extends Fn6<A, B, C, D, E, F, Unit>, IConsumer<$6, A, B, C, D, E, F> {

        /** Returns an empty consumer. */
        static <A, B, C, D, E, F> Consumer<A, B, C, D, E, F> Empty() {
            return Force.cast(Constant.empty);
        }

        /// LAZY APPLICATION.

        /**
         * Produces a thunk by fixing the arguments and deferring
         * the computation until the result is needed.
         */
        @Override default Fn0.Consumer thunk(final A a, final B b, final C c, final D d, final E e, final F f) {
            return () -> onAccept(a, b, c, d, e, f);
        }

        /// PARTIAL APPLICATION.

        /** Partially applies this consumer by passing the first 5 arguments. */
        @Override default Fn1.Consumer<F> apply(final A a, final B b, final C c, final D d, final E e) {
            return f -> onAccept(a, b, c, d, e, f);
        }

        /** Partially applies this consumer by passing the first 4 arguments. */
        @Override default Fn2.Consumer<E, F> apply(final A a, final B b, final C c, final D d) {
            return (e, f) -> onAccept(a, b, c, d, e, f);
        }

        /** Partially applies this consumer by passing the first 3 arguments. */
        @Override default Fn3.Consumer<D, E, F> apply(final A a, final B b, final C c) {
            return (d, e, f) -> onAccept(a, b, c, d, e, f);
        }

        /** Partially applies this consumer by passing the first 2 arguments. */
        @Override default Fn4.Consumer<C, D, E, F> apply(final A a, final B b) {
            return (c, d, e, f) -> onAccept(a, b, c, d, e, f);
        }

        /** Partially applies this consumer by passing the first argument. */
        @Override default Fn5.Consumer<B, C, D, E, F> apply(final A a) {
            return (b, c, d, e, f) -> onAccept(a, b, c, d, e, f);
        }

        /// UNCURRY ISOMORPHISM.

        /**
         * Produces the tupled consumer form: instead of 6 arguments,
         * it accepts a single {@link Product6} argument.
         */
        default Fn1.Consumer<Product6<? extends A, ? extends B, ? extends C, ? extends D, ? extends E, ? extends F>> tupled() {
            return p -> onAccept(p._1(), p._2(), p._3(), p._4(), p._5(), p._6());
        }
    }

    // ----------------------------------------------------------

    /**
     * Constructs a {@link Fn6} function that invariably returns the given value.
     */
    static <A, B, C, D, E, F, R> Fn6<A, B, C, D, E, F, R> constant(final R val) {
        return (a, b, c, d, e, f) -> val;
    }

    /**
     * Constructs a {@link Fn6} function from the given curried {@link Fn1} function.
     */
    static <A, B, C, D, E, F, R> Fn6<A, B, C, D, E, F, R> curried(final Fn1<A, Fn5<B, C, D, E, F, R>> fn) {
        return (a, b, c, d, e, f) -> fn.onApply(a).onApply(b, c, d, e, f);
    }

    /**
     * Constructs a {@link Fn6} function from the given curried {@link Fn2} function.
     */
    static <A, B, C, D, E, F, R> Fn6<A, B, C, D, E, F, R> curried(final Fn2<A, B, Fn4<C, D, E, F, R>> fn) {
        return (a, b, c, d, e, f) -> fn.onApply(a, b).onApply(c, d, e, f);
    }

    /**
     * Constructs a {@link Fn6} function from the given curried {@link Fn3} function.
     */
    static <A, B, C, D, E, F, R> Fn6<A, B, C, D, E, F, R> curried(final Fn3<A, B, C, Fn3<D, E, F, R>> fn) {
        return (a, b, c, d, e, f) -> fn.onApply(a, b, c).onApply(d, e, f);
    }

    /**
     * Constructs a {@link Fn6} function from the given curried {@link Fn4} function.
     */
    static <A, B, C, D, E, F, R> Fn6<A, B, C, D, E, F, R> curried(final Fn4<A, B, C, D, Fn2<E, F, R>> fn) {
        return (a, b, c, d, e, f) -> fn.onApply(a, b, c, d).onApply(e, f);
    }

    /**
     * Constructs a {@link Fn6} function from the given curried {@link Fn5} function.
     */
    static <A, B, C, D, E, F, R> Fn6<A, B, C, D, E, F, R> curried(final Fn5<A, B, C, D, E, Fn1<F, R>> fn) {
        return (a, b, c, d, e, f) -> fn.onApply(a, b, c, d, e).onApply(f);
    }
}