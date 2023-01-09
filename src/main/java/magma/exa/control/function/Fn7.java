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

import magma.exa.adt.typelevel.nat.$7;
import magma.exa.base.Force;
import magma.exa.control.adt.function.IFn7;
import magma.exa.value.Unit;
import magma.exa.value.adt.product.Product7;

/**
 * Function that takes 7 arguments and returns a result of type {@code R}.
 *
 * @param <A> type of 1st parameter.
 * @param <B> type of 2nd parameter.
 * @param <C> type of 3rd parameter.
 * @param <D> type of 4th parameter.
 * @param <E> type of 5th parameter.
 * @param <F> type of 6th parameter.
 * @param <G> type of 7th parameter.
 * @param <R> type of function result.
 */
@FunctionalInterface
public interface Fn7<A, B, C, D, E, F, G, R> extends IFn7<$7, A, B, C, D, E, F, G, R> {

    /**
     * Returns the function arity.
     */
    @Override
    default $7 arity() {
        return $7.nat;
    }

    /// LAZY APPLICATION.

    /**
     * Produces a thunk by fixing the arguments and deferring
     * the computation until the result is needed.
     */
    default Fn0<R> thunk(final A a, final B b, final C c, final D d, final E e, final F f, final G g) {
        return () -> onApply(a, b, c, d, e, f, g);
    }

    /// PARTIAL APPLICATION.

    /** Partially applies this function by passing the first 6 arguments. */
    @Override default Fn1<G, R> apply(final A a, final B b, final C c, final D d, final E e, final F f) {
        return g -> onApply(a, b, c, d, e, f, g);
    }

    /** Partially applies this function by passing the first 5 arguments. */
    @Override default Fn2<F, G, R> apply(final A a, final B b, final C c, final D d, final E e) {
        return (f, g) -> onApply(a, b, c, d, e, f, g);
    }

    /** Partially applies this function by passing the first 4 arguments. */
    @Override default Fn3<E, F, G, R> apply(final A a, final B b, final C c, final D d) {
        return (e, f, g) -> onApply(a, b, c, d, e, f, g);
    }

    /** Partially applies this function by passing the first 3 arguments. */
    @Override default Fn4<D, E, F, G, R> apply(final A a, final B b, final C c) {
        return (d, e, f, g) -> onApply(a, b, c, d, e, f, g);
    }

    /** Partially applies this function by passing the first 2 arguments. */
    @Override default Fn5<C, D, E, F, G, R> apply(final A a, final B b) {
        return (c, d, e, f, g) -> onApply(a, b, c, d, e, f, g);
    }

    /** Partially applies this function by passing the first argument. */
    @Override default Fn6<B, C, D, E, F, G, R> apply(final A a) {
        return (b, c, d, e, f, g) -> onApply(a, b, c, d, e, f, g);
    }

    /// UNCURRY ISOMORPHISM.

    /**
     * Produces the tupled function form: instead of 7 arguments,
     * it accepts a single {@link Product7} argument.
     */
    default Fn1<Product7<? extends A, ? extends B, ? extends C, ? extends D, ? extends E, ? extends F, ? extends G>, R> tupled() {
        return p -> onApply(p._1(), p._2(), p._3(), p._4(), p._5(), p._6(), p._7());
    }

    // ----------------------------------------------------------
    //  FN7.PREDICATE
    // ----------------------------------------------------------

    /**
     * A predicate (boolean-valued function) that takes 7 arguments.
     *
     * @param <A> type of 1st parameter.
     * @param <B> type of 2nd parameter.
     * @param <C> type of 3rd parameter.
     * @param <D> type of 4th parameter.
     * @param <E> type of 5th parameter.
     * @param <F> type of 6th parameter.
     * @param <G> type of 7th parameter.
     */
    @FunctionalInterface
    interface Predicate<A, B, C, D, E, F, G> extends Fn7<A, B, C, D, E, F, G, Boolean>, IPredicate<$7, A, B, C, D, E, F, G> {

        /** Predicate that always evaluates to {@code true}. */
        static <A, B, C, D, E, F, G> Predicate<A, B, C, D, E, F, G> True() {
            return Force.cast(Constant.True);
        }

        /** Predicate that always evaluates to {@code false}. */
        static <A, B, C, D, E, F, G> Predicate<A, B, C, D, E, F, G> False() {
            return Force.cast(Constant.False);
        }

        /// LAZY APPLICATION.

        /**
         * Produces a thunk by fixing the arguments and deferring
         * the computation until the result is needed.
         */
        @Override default Fn0.Predicate thunk(final A a, final B b, final C c, final D d, final E e, final F f, final G g) {
            return () -> onEval(a, b, c, d, e, f, g);
        }

        /// PARTIAL APPLICATION.

        /** Partially applies this predicate by passing the first 6 arguments. */
        @Override default Fn1.Predicate<G> apply(final A a, final B b, final C c, final D d, final E e, final F f) {
            return g -> onEval(a, b, c, d, e, f, g);
        }

        /** Partially applies this predicate by passing the first 5 arguments. */
        @Override default Fn2.Predicate<F, G> apply(final A a, final B b, final C c, final D d, final E e) {
            return (f, g) -> onEval(a, b, c, d, e, f, g);
        }

        /** Partially applies this predicate by passing the first 4 arguments. */
        @Override default Fn3.Predicate<E, F, G> apply(final A a, final B b, final C c, final D d) {
            return (e, f, g) -> onEval(a, b, c, d, e, f, g);
        }

        /** Partially applies this predicate by passing the first 3 arguments. */
        @Override default Fn4.Predicate<D, E, F, G> apply(final A a, final B b, final C c) {
            return (d, e, f, g) -> onEval(a, b, c, d, e, f, g);
        }

        /** Partially applies this predicate by passing the first 2 arguments. */
        @Override default Fn5.Predicate<C, D, E, F, G> apply(final A a, final B b) {
            return (c, d, e, f, g) -> onEval(a, b, c, d, e, f, g);
        }

        /** Partially applies this predicate by passing the first argument. */
        @Override default Fn6.Predicate<B, C, D, E, F, G> apply(final A a) {
            return (b, c, d, e, f, g) -> onEval(a, b, c, d, e, f, g);
        }

        /// UNCURRY ISOMORPHISM.

        /**
         * Produces the tupled predicate form: instead of 7 arguments,
         * it accepts a single {@link Product7} argument.
         */
        default Fn1.Predicate<Product7<? extends A, ? extends B, ? extends C, ? extends D, ? extends E, ? extends F, ? extends G>> tupled() {
            return p -> onEval(p._1(), p._2(), p._3(), p._4(), p._5(), p._6(), p._7());
        }
    }

    // ----------------------------------------------------------
    //  FN7.CONSUMER
    // ----------------------------------------------------------

    /**
     * A side effect operation that accepts 7 arguments.
     *
     * @param <A> type of 1st parameter.
     * @param <B> type of 2nd parameter.
     * @param <C> type of 3rd parameter.
     * @param <D> type of 4th parameter.
     * @param <E> type of 5th parameter.
     * @param <F> type of 6th parameter.
     * @param <G> type of 7th parameter.
     */
    @FunctionalInterface
    interface Consumer<A, B, C, D, E, F, G> extends Fn7<A, B, C, D, E, F, G, Unit>, IConsumer<$7, A, B, C, D, E, F, G> {

        /** Returns an empty consumer. */
        static <A, B, C, D, E, F, G> Consumer<A, B, C, D, E, F, G> Empty() {
            return Force.cast(Constant.empty);
        }

        /// LAZY APPLICATION.

        /**
         * Produces a thunk by fixing the arguments and deferring
         * the computation until the result is needed.
         */
        @Override default Fn0.Consumer thunk(final A a, final B b, final C c, final D d, final E e, final F f, final G g) {
            return () -> onAccept(a, b, c, d, e, f, g);
        }

        /// PARTIAL APPLICATION.

        /** Partially applies this consumer by passing the first 6 arguments. */
        @Override default Fn1.Consumer<G> apply(final A a, final B b, final C c, final D d, final E e, final F f) {
            return g -> onAccept(a, b, c, d, e, f, g);
        }

        /** Partially applies this consumer by passing the first 5 arguments. */
        @Override default Fn2.Consumer<F, G> apply(final A a, final B b, final C c, final D d, final E e) {
            return (f, g) -> onAccept(a, b, c, d, e, f, g);
        }

        /** Partially applies this consumer by passing the first 4 arguments. */
        @Override default Fn3.Consumer<E, F, G> apply(final A a, final B b, final C c, final D d) {
            return (e, f, g) -> onAccept(a, b, c, d, e, f, g);
        }

        /** Partially applies this consumer by passing the first 3 arguments. */
        @Override default Fn4.Consumer<D, E, F, G> apply(final A a, final B b, final C c) {
            return (d, e, f, g) -> onAccept(a, b, c, d, e, f, g);
        }

        /** Partially applies this consumer by passing the first 2 arguments. */
        @Override default Fn5.Consumer<C, D, E, F, G> apply(final A a, final B b) {
            return (c, d, e, f, g) -> onAccept(a, b, c, d, e, f, g);
        }

        /** Partially applies this consumer by passing the first argument. */
        @Override default Fn6.Consumer<B, C, D, E, F, G> apply(final A a) {
            return (b, c, d, e, f, g) -> onAccept(a, b, c, d, e, f, g);
        }

        /// UNCURRY ISOMORPHISM.

        /**
         * Produces the tupled consumer form: instead of 7 arguments,
         * it accepts a single {@link Product7} argument.
         */
        default Fn1.Consumer<Product7<? extends A, ? extends B, ? extends C, ? extends D, ? extends E, ? extends F, ? extends G>> tupled() {
            return p -> onAccept(p._1(), p._2(), p._3(), p._4(), p._5(), p._6(), p._7());
        }
    }

    // ----------------------------------------------------------

    /**
     * Constructs a {@link Fn7} function that invariably returns the given value.
     */
    static <A, B, C, D, E, F, G, R> Fn7<A, B, C, D, E, F, G, R> constant(final R val) {
        return (a, b, c, d, e, f, g) -> val;
    }

    /**
     * Constructs a {@link Fn7} function from the given curried {@link Fn1} function.
     */
    static <A, B, C, D, E, F, G, R> Fn7<A, B, C, D, E, F, G, R> curried(final Fn1<A, Fn6<B, C, D, E, F, G, R>> fn) {
        return (a, b, c, d, e, f, g) -> fn.onApply(a).onApply(b, c, d, e, f, g);
    }

    /**
     * Constructs a {@link Fn7} function from the given curried {@link Fn2} function.
     */
    static <A, B, C, D, E, F, G, R> Fn7<A, B, C, D, E, F, G, R> curried(final Fn2<A, B, Fn5<C, D, E, F, G, R>> fn) {
        return (a, b, c, d, e, f, g) -> fn.onApply(a, b).onApply(c, d, e, f, g);
    }

    /**
     * Constructs a {@link Fn7} function from the given curried {@link Fn3} function.
     */
    static <A, B, C, D, E, F, G, R> Fn7<A, B, C, D, E, F, G, R> curried(final Fn3<A, B, C, Fn4<D, E, F, G, R>> fn) {
        return (a, b, c, d, e, f, g) -> fn.onApply(a, b, c).onApply(d, e, f, g);
    }

    /**
     * Constructs a {@link Fn7} function from the given curried {@link Fn4} function.
     */
    static <A, B, C, D, E, F, G, R> Fn7<A, B, C, D, E, F, G, R> curried(final Fn4<A, B, C, D, Fn3<E, F, G, R>> fn) {
        return (a, b, c, d, e, f, g) -> fn.onApply(a, b, c, d).onApply(e, f, g);
    }

    /**
     * Constructs a {@link Fn7} function from the given curried {@link Fn5} function.
     */
    static <A, B, C, D, E, F, G, R> Fn7<A, B, C, D, E, F, G, R> curried(final Fn5<A, B, C, D, E, Fn2<F, G, R>> fn) {
        return (a, b, c, d, e, f, g) -> fn.onApply(a, b, c, d, e).onApply(f, g);
    }

    /**
     * Constructs a {@link Fn7} function from the given curried {@link Fn6} function.
     */
    static <A, B, C, D, E, F, G, R> Fn7<A, B, C, D, E, F, G, R> curried(final Fn6<A, B, C, D, E, F, Fn1<G, R>> fn) {
        return (a, b, c, d, e, f, g) -> fn.onApply(a, b, c, d, e, f).onApply(g);
    }
}
