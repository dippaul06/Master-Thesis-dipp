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

import magma.exa.adt.typelevel.nat.$5;
import magma.exa.base.Force;
import magma.exa.control.adt.function.IFn5;
import magma.exa.value.Unit;
import magma.exa.value.adt.product.Product5;

/**
 * Function that takes 5 arguments and returns a result of type {@code R}.
 *
 * @param <A> type of 1st parameter.
 * @param <B> type of 2nd parameter.
 * @param <C> type of 3rd parameter.
 * @param <D> type of 4th parameter.
 * @param <E> type of 5th parameter.
 * @param <R> type of function result.
 */
@FunctionalInterface
public interface Fn5<A, B, C, D, E, R> extends IFn5<$5, A, B, C, D, E, R> {

    /**
     * Returns the function arity.
     */
    @Override
    default $5 arity() {
        return $5.nat;
    }

    /// LAZY APPLICATION.

    /**
     * Produces a thunk by fixing the arguments and deferring
     * the computation until the result is needed.
     */
    default Fn0<R> thunk(final A a, final B b, final C c, final D d, final E e) {
        return () -> onApply(a, b, c, d, e);
    }

    /// PARTIAL APPLICATION.

    /** Partially applies this function by passing the first 4 arguments. */
    @Override default Fn1<E, R> apply(final A a, final B b, final C c, final D d) {
        return e -> onApply(a, b, c, d, e);
    }

    /** Partially applies this function by passing the first 3 arguments. */
    @Override default Fn2<D, E, R> apply(final A a, final B b, final C c) {
        return (d, e) -> onApply(a, b, c, d, e);
    }

    /** Partially applies this function by passing the first 2 arguments. */
    @Override default Fn3<C, D, E, R> apply(final A a, final B b) {
        return (c, d, e) -> onApply(a, b, c, d, e);
    }

    /** Partially applies this function by passing the first 1 arguments. */
    @Override default Fn4<B, C, D, E, R> apply(final A a) {
        return (b, c, d, e) -> onApply(a, b, c, d, e);
    }

    /// UNCURRY ISOMORPHISM.

    /**
     * Produces the tupled function form: instead of 5 arguments,
     * it accepts a single {@link Product5} argument.
     */
    default Fn1<Product5<? extends A, ? extends B, ? extends C, ? extends D, ? extends E>, R> tupled() {
        return p -> onApply(p._1(), p._2(), p._3(), p._4(), p._5());
    }

    // ----------------------------------------------------------
    //  FN5.PREDICATE
    // ----------------------------------------------------------

    /**
     * A predicate (boolean-valued function) that takes 5 arguments.
     *
     * @param <A> type of 1st parameter.
     * @param <B> type of 2nd parameter.
     * @param <C> type of 3rd parameter.
     * @param <D> type of 4th parameter.
     * @param <E> type of 5th parameter.
     */
    @FunctionalInterface
    interface Predicate<A, B, C, D, E> extends Fn5<A, B, C, D, E, Boolean>, IPredicate<$5, A, B, C, D, E> {

        /** Predicate that always evaluates to {@code true}. */
        static <A, B, C, D, E> Predicate<A, B, C, D, E> True() {
            return Force.cast(Constant.True);
        }

        /** Predicate that always evaluates to {@code false}. */
        static <A, B, C, D, E> Predicate<A, B, C, D, E> False() {
            return Force.cast(Constant.False);
        }

        /// LAZY APPLICATION.

        /**
         * Produces a thunk by fixing the arguments and deferring
         * the computation until the result is needed.
         */
        @Override default Fn0.Predicate thunk(final A a, final B b, final C c, final D d, final E e) {
            return () -> onEval(a, b, c, d, e);
        }

        /// PARTIAL APPLICATION.

        /** Partially applies this predicate by passing the first 4 arguments. */
        @Override default Fn1.Predicate<E> apply(final A a, final B b, final C c, final D d) {
            return e -> onEval(a, b, c, d, e);
        }

        /** Partially applies this predicate by passing the first 3 arguments. */
        @Override default Fn2.Predicate<D, E> apply(final A a, final B b, final C c) {
            return (d, e) -> onEval(a, b, c, d, e);
        }

        /** Partially applies this predicate by passing the first 2 arguments. */
        @Override default Fn3.Predicate<C, D, E> apply(final A a, final B b) {
            return (c, d, e) -> onEval(a, b, c, d, e);
        }

        /** Partially applies this predicate by passing the first 1 arguments. */
        @Override default Fn4.Predicate<B, C, D, E> apply(final A a) {
            return (b, c, d, e) -> onEval(a, b, c, d, e);
        }

        /// UNCURRY ISOMORPHISM.

        /**
         * Produces the tupled predicate form: instead of 5 arguments,
         * it accepts a single {@link Product5} argument.
         */
        default Fn1.Predicate<Product5<? extends A, ? extends B, ? extends C, ? extends D, ? extends E>> tupled() {
            return p -> onEval(p._1(), p._2(), p._3(), p._4(), p._5());
        }
    }

    // ----------------------------------------------------------
    //  FN5.CONSUMER
    // ----------------------------------------------------------

    /**
     * A side effect operation that accepts 5 arguments.
     *
     * @param <A> type of 1st parameter.
     * @param <B> type of 2nd parameter.
     * @param <C> type of 3rd parameter.
     * @param <D> type of 4th parameter.
     * @param <E> type of 5th parameter.
     */
    @FunctionalInterface
    interface Consumer<A, B, C, D, E> extends Fn5<A, B, C, D, E, Unit>, IConsumer<$5, A, B, C, D, E> {

        /** Returns an empty consumer. */
        static <A, B, C, D, E> Consumer<A, B, C, D, E> Empty() {
            return Force.cast(Constant.empty);
        }

        /// LAZY APPLICATION.

        /**
         * Produces a thunk by fixing the arguments and deferring
         * the computation until the result is needed.
         */
        @Override default Fn0.Consumer thunk(final A a, final B b, final C c, final D d, final E e) {
            return () -> onAccept(a, b, c, d, e);
        }

        /// PARTIAL APPLICATION.

        /** Partially applies this consumer by passing the first 4 arguments. */
        @Override default Fn1.Consumer<E> apply(final A a, final B b, final C c, final D d) {
            return e -> onAccept(a, b, c, d, e);
        }

        /** Partially applies this consumer by passing the first 3 arguments. */
        @Override default Fn2.Consumer<D, E> apply(final A a, final B b, final C c) {
            return (d, e) -> onAccept(a, b, c, d, e);
        }

        /** Partially applies this consumer by passing the first 2 arguments. */
        @Override default Fn3.Consumer<C, D, E> apply(final A a, final B b) {
            return (c, d, e) -> onAccept(a, b, c, d, e);
        }

        /** Partially applies this consumer by passing the first 1 arguments. */
        @Override default Fn4.Consumer<B, C, D, E> apply(final A a) {
            return (b, c, d, e) -> onAccept(a, b, c, d, e);
        }

        /// UNCURRY ISOMORPHISM.

        /**
         * Produces the tupled consumer form: instead of 5 arguments,
         * it accepts a single {@link Product5} argument.
         */
        default Fn1.Consumer<Product5<? extends A, ? extends B, ? extends C, ? extends D, ? extends E>> tupled() {
            return p -> onAccept(p._1(), p._2(), p._3(), p._4(), p._5());
        }
    }

    // ----------------------------------------------------------

    /**
     * Constructs a {@link Fn5} function that invariably returns the given value.
     */
    static <A, B, C, D, E, R> Fn5<A, B, C, D, E, R> constant(final R val) {
        return (a, b, c, d, e) -> val;
    }

    /**
     * Constructs a {@link Fn5} function from the given curried {@link Fn1} function.
     */
    static <A, B, C, D, E, R> Fn5<A, B, C, D, E, R> curried(final Fn1<A, Fn4<B, C, D, E, R>> fn) {
        return (a, b, c, d, e) -> fn.onApply(a).onApply(b, c, d, e);
    }

    /**
     * Constructs a {@link Fn5} function from the given curried {@link Fn2} function.
     */
    static <A, B, C, D, E, R> Fn5<A, B, C, D, E, R> curried(final Fn2<A, B, Fn3<C, D, E, R>> fn) {
        return (a, b, c, d, e) -> fn.onApply(a, b).onApply(c, d, e);
    }

    /**
     * Constructs a {@link Fn5} function from the given curried {@link Fn3} function.
     */
    static <A, B, C, D, E, R> Fn5<A, B, C, D, E, R> curried(final Fn3<A, B, C, Fn2<D, E, R>> fn) {
        return (a, b, c, d, e) -> fn.onApply(a, b, c).onApply(d, e);
    }

    /**
     * Constructs a {@link Fn5} function from the given curried {@link Fn4} function.
     */
    static <A, B, C, D, E, R> Fn5<A, B, C, D, E, R> curried(final Fn4<A, B, C, D, Fn1<E, R>> fn) {
        return (a, b, c, d, e) -> fn.onApply(a, b, c, d).onApply(e);
    }
}