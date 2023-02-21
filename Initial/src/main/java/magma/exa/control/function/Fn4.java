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

import magma.exa.adt.typelevel.nat.$4;
import magma.exa.base.Force;
import magma.exa.control.adt.function.IFn4;
import magma.exa.value.Unit;
import magma.exa.value.adt.product.Product4;

/**
 * Function that takes 4 arguments and returns a result of type {@code R}.
 *
 * @param <A> type of 1st parameter.
 * @param <B> type of 2nd parameter.
 * @param <C> type of 3rd parameter.
 * @param <D> type of 4th parameter.
 * @param <R> type of function result.
 */
@FunctionalInterface
public interface Fn4<A, B, C, D, R> extends IFn4<$4, A, B, C, D, R> {

    /**
     * Returns the function arity.
     */
    @Override
    default $4 arity() {
        return $4.nat;
    }

    /// LAZY APPLICATION.

    /**
     * Produces a thunk by fixing the arguments and deferring
     * the computation until the result is needed.
     */
    default Fn0<R> thunk(final A a, final B b, final C c, final D d) {
        return () -> onApply(a, b, c, d);
    }

    /// PARTIAL APPLICATION.

    /** Partially applies this function by passing the first 3 arguments. */
    @Override default Fn1<D, R> apply(final A a, final B b, final C c) {
        return d -> onApply(a, b, c, d);
    }

    /** Partially applies this function by passing the first 2 arguments. */
    @Override default Fn2<C, D, R> apply(final A a, final B b) {
        return (c, d) -> onApply(a, b, c, d);
    }

    /** Partially applies this function by passing the first argument. */
    @Override default Fn3<B, C, D, R> apply(final A a) {
        return (b, c, d) -> onApply(a, b, c, d);
    }

    /// UNCURRY ISOMORPHISM.

    /**
     * Produces the tupled function form: instead of 4 arguments,
     * it accepts a single {@link Product4} argument.
     */
    default Fn1<Product4<? extends A, ? extends B, ? extends C, ? extends D>, R> tupled() {
        return p -> onApply(p._1(), p._2(), p._3(), p._4());
    }

    // ----------------------------------------------------------
    //  FN4.PREDICATE
    // ----------------------------------------------------------

    /**
     * A predicate (boolean-valued function) that takes 4 arguments.
     *
     * @param <A> type of 1st parameter.
     * @param <B> type of 2nd parameter.
     * @param <C> type of 3rd parameter.
     * @param <D> type of 4th parameter.
     */
    @FunctionalInterface
    interface Predicate<A, B, C, D> extends Fn4<A, B, C, D, Boolean>, IPredicate<$4, A, B, C, D> {

        /** Predicate that always evaluates to {@code true}. */
        static <A, B, C, D> Predicate<A, B, C, D> True() {
            return Force.cast(Constant.True);
        }

        /** Predicate that always evaluates to {@code false}. */
        static <A, B, C, D> Predicate<A, B, C, D> False() {
            return Force.cast(Constant.False);
        }

        /// LAZY APPLICATION.

        /**
         * Produces a thunk by fixing the arguments and deferring
         * the computation until the result is needed.
         */
        @Override default Fn0.Predicate thunk(final A a, final B b, final C c, final D d) {
            return () -> onEval(a, b, c, d);
        }

        /// PARTIAL APPLICATION.

        /** Partially applies this predicate by passing the first 3 arguments. */
        @Override default Fn1.Predicate<D> apply(final A a, final B b, final C c) {
            return d -> onEval(a, b, c, d);
        }

        /** Partially applies this predicate by passing the first 2 arguments. */
        @Override default Fn2.Predicate<C, D> apply(final A a, final B b) {
            return (c, d) -> onEval(a, b, c, d);
        }

        /** Partially applies this predicate by passing the first argument. */
        @Override default Fn3.Predicate<B, C, D> apply(final A a) {
            return (b, c, d) -> onEval(a, b, c, d);
        }

        /// UNCURRY ISOMORPHISM.

        /**
         * Produces the tupled predicate form: instead of 4 arguments,
         * it accepts a single {@link Product4} argument.
         */
        default Fn1.Predicate<Product4<? extends A, ? extends B, ? extends C, ? extends D>> tupled() {
            return p -> onApply(p._1(), p._2(), p._3(), p._4());
        }
    }

    // ----------------------------------------------------------
    //  FN4.CONSUMER
    // ----------------------------------------------------------

    /**
     * A side effect operation that accepts 4 arguments.
     *
     * @param <A> type of 1st parameter.
     * @param <B> type of 2nd parameter.
     * @param <C> type of 3rd parameter.
     * @param <D> type of 4th parameter.
     */
    @FunctionalInterface
    interface Consumer<A, B, C, D> extends Fn4<A, B, C, D, Unit>, IConsumer<$4, A, B, C, D> {

        /** Returns an empty consumer. */
        static <A, B, C, D> Consumer<A, B, C, D> Empty() {
            return Force.cast(Constant.empty);
        }

        /// LAZY APPLICATION.

        /**
         * Produces a thunk by fixing the arguments and deferring
         * the computation until the result is needed.
         */
        @Override default Fn0.Consumer thunk(final A a, final B b, final C c, final D d) {
            return () -> onAccept(a, b, c, d);
        }

        /// PARTIAL APPLICATION.

        /** Partially applies this consumer by passing the first 3 arguments. */
        @Override default Fn1.Consumer<D> apply(final A a, final B b, final C c) {
            return d -> onAccept(a, b, c, d);
        }

        /** Partially applies this consumer by passing the first 2 arguments. */
        @Override default Fn2.Consumer<C, D> apply(final A a, final B b) {
            return (c, d) -> onAccept(a, b, c, d);
        }

        /** Partially applies this consumer by passing the first argument. */
        @Override default Fn3.Consumer<B, C, D> apply(final A a) {
            return (b, c, d) -> onAccept(a, b, c, d);
        }

        /// UNCURRY ISOMORPHISM.

        /**
         * Produces the tupled consumer form: instead of 4 arguments,
         * it accepts a single {@link Product4} argument.
         */
        default Fn1.Consumer<Product4<? extends A, ? extends B, ? extends C, ? extends D>> tupled() {
            return p -> onAccept(p._1(), p._2(), p._3(), p._4());
        }
    }

    // ----------------------------------------------------------

    /**
     * Constructs a {@link Fn4} function that invariably returns the given value.
     */
    static <A, B, C, D, R> Fn4<A, B, C, D, R> constant(final R val) {
        return (a, b, c, d) -> val;
    }

    /**
     * Constructs a {@link Fn4} function from the given curried {@link Fn1} function.
     */
    static <A, B, C, D, R> Fn4<A, B, C, D, R> curried(final Fn1<A, Fn3<B, C, D, R>> fn) {
        return (a, b, c, d) -> fn.onApply(a).onApply(b, c, d);
    }

    /**
     * Constructs a {@link Fn4} function from the given curried {@link Fn2} function.
     */
    static <A, B, C, D, R> Fn4<A, B, C, D, R> curried(final Fn2<A, B, Fn2<C, D, R>> fn) {
        return (a, b, c, d) -> fn.onApply(a, b).onApply(c, d);
    }

    /**
     * Constructs a {@link Fn4} function from the given curried {@link Fn3} function.
     */
    static <A, B, C, D, R> Fn4<A, B, C, D, R> curried(final Fn3<A, B, C, Fn1<D, R>> fn) {
        return (a, b, c, d) -> fn.onApply(a, b, c).onApply(d);
    }
}