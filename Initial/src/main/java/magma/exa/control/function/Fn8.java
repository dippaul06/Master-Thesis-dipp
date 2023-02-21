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

import magma.exa.adt.typelevel.nat.$8;
import magma.exa.base.Force;
import magma.exa.control.adt.function.IFn8;
import magma.exa.value.Unit;
import magma.exa.value.adt.product.Product8;

/**
 * Function that takes 8 arguments and returns a result of type {@code R}.
 *
 * @param <A> type of 1st parameter.
 * @param <B> type of 2nd parameter.
 * @param <C> type of 3rd parameter.
 * @param <D> type of 4th parameter.
 * @param <E> type of 5th parameter.
 * @param <F> type of 6th parameter.
 * @param <G> type of 7th parameter.
 * @param <H> type of 8th parameter.
 * @param <R> type of function result.
 */
@FunctionalInterface
public interface Fn8<A, B, C, D, E, F, G, H, R> extends IFn8<$8, A, B, C, D, E, F, G, H, R> {

    /**
     * Returns the function arity.
     */
    @Override
    default $8 arity() {
        return $8.nat;
    }

    /// LAZY APPLICATION.

    /**
     * Produces a thunk by fixing the arguments and deferring
     * the computation until the result is needed.
     */
    default Fn0<R> thunk(final A a, final B b, final C c, final D d, final E e, final F f, final G g, final H h) {
        return () -> onApply(a, b, c, d, e, f, g, h);
    }

    /// PARTIAL APPLICATION.

    /** Partially applies this function by passing the first 7 arguments. */
    @Override default Fn1<H, R> apply(final A a, final B b, final C c, final D d, final E e, final F f, final G g) {
        return h -> onApply(a, b, c, d, e, f, g, h);
    }

    /** Partially applies this function by passing the first 6 arguments. */
    @Override default Fn2<G, H, R> apply(final A a, final B b, final C c, final D d, final E e, final F f) {
        return (g, h) -> onApply(a, b, c, d, e, f, g, h);
    }

    /** Partially applies this function by passing the first 5 arguments. */
    @Override default Fn3<F, G, H, R> apply(final A a, final B b, final C c, final D d, final E e) {
        return (f, g, h) -> onApply(a, b, c, d, e, f, g, h);
    }

    /** Partially applies this function by passing the first 4 arguments. */
    @Override default Fn4<E, F, G, H, R> apply(final A a, final B b, final C c, final D d) {
        return (e, f, g, h) -> onApply(a, b, c, d, e, f, g, h);
    }

    /** Partially applies this function by passing the first 3 arguments. */
    @Override default Fn5<D, E, F, G, H, R> apply(final A a, final B b, final C c) {
        return (d, e, f, g, h) -> onApply(a, b, c, d, e, f, g, h);
    }

    /** Partially applies this function by passing the first 2 arguments. */
    @Override default Fn6<C, D, E, F, G, H, R> apply(final A a, final B b) {
        return (c, d, e, f, g, h) -> onApply(a, b, c, d, e, f, g, h);
    }

    /** Partially applies this function by passing the first argument. */
    @Override default Fn7<B, C, D, E, F, G, H, R> apply(final A a) {
        return (b, c, d, e, f, g, h) -> onApply(a, b, c, d, e, f, g, h);
    }

    /// UNCURRY ISOMORPHISM.

    /**
     * Produces the tupled function form: instead of 8 arguments,
     * it accepts a single {@link Product8} argument.
     */
    default Fn1<Product8<? extends A, ? extends B, ? extends C, ? extends D, ? extends E, ? extends F, ? extends G, ? extends H>, R> tupled() {
        return p -> onApply(p._1(), p._2(), p._3(), p._4(), p._5(), p._6(), p._7(), p._8());
    }

    // ----------------------------------------------------------
    //  FN8.PREDICATE
    // ----------------------------------------------------------

    /**
     * A predicate (boolean-valued function) that takes 8 arguments.
     *
     * @param <A> type of 1st parameter.
     * @param <B> type of 2nd parameter.
     * @param <C> type of 3rd parameter.
     * @param <D> type of 4th parameter.
     * @param <E> type of 5th parameter.
     * @param <F> type of 6th parameter.
     * @param <G> type of 7th parameter.
     * @param <H> type of 8th parameter.
     */
    @FunctionalInterface
    interface Predicate<A, B, C, D, E, F, G, H> extends Fn8<A, B, C, D, E, F, G, H, Boolean>, IFn8.IPredicate<$8, A, B, C, D, E, F, G, H> {

        /** Predicate that always evaluates to {@code true}. */
        static <A, B, C, D, E, F, G, H> Predicate<A, B, C, D, E, F, G, H> True() {
            return Force.cast(Constant.True);
        }

        /** Predicate that always evaluates to {@code false}. */
        static <A, B, C, D, E, F, G, H> Predicate<A, B, C, D, E, F, G, H> False() {
            return Force.cast(Constant.False);
        }

        /// LAZY APPLICATION.

        /**
         * Produces a thunk by fixing the arguments and deferring
         * the computation until the result is needed.
         */
        @Override default Fn0.Predicate thunk(final A a, final B b, final C c, final D d, final E e, final F f, final G g, final H h) {
            return () -> onEval(a, b, c, d, e, f, g, h);
        }

        /// PARTIAL APPLICATION.

        /** Partially applies this predicate by passing the first 7 arguments. */
        @Override default Fn1.Predicate<H> apply(final A a, final B b, final C c, final D d, final E e, final F f, final G g) {
            return h -> onEval(a, b, c, d, e, f, g, h);
        }

        /** Partially applies this predicate by passing the first 6 arguments. */
        @Override default Fn2.Predicate<G, H> apply(final A a, final B b, final C c, final D d, final E e, final F f) {
            return (g, h) -> onEval(a, b, c, d, e, f, g, h);
        }

        /** Partially applies this predicate by passing the first 5 arguments. */
        @Override default Fn3.Predicate<F, G, H> apply(final A a, final B b, final C c, final D d, final E e) {
            return (f, g, h) -> onEval(a, b, c, d, e, f, g, h);
        }

        /** Partially applies this predicate by passing the first 4 arguments. */
        @Override default Fn4.Predicate<E, F, G, H> apply(final A a, final B b, final C c, final D d) {
            return (e, f, g, h) -> onEval(a, b, c, d, e, f, g, h);
        }

        /** Partially applies this predicate by passing the first 5 arguments. */
        @Override default Fn5.Predicate<D, E, F, G, H> apply(final A a, final B b, final C c) {
            return (d, e, f, g, h) -> onEval(a, b, c, d, e, f, g, h);
        }

        /** Partially applies this predicate by passing the first 6 arguments. */
        @Override default Fn6.Predicate<C, D, E, F, G, H> apply(final A a, final B b) {
            return (c, d, e, f, g, h) -> onEval(a, b, c, d, e, f, g, h);
        }

        /** Partially applies this predicate by passing the first 7 arguments. */
        @Override default Fn7.Predicate<B, C, D, E, F, G, H> apply(final A a) {
            return (b, c, d, e, f, g, h) -> onEval(a, b, c, d, e, f, g, h);
        }

        /// UNCURRY ISOMORPHISM.

        /**
         * Produces the tupled predicate form: instead of 8 arguments,
         * it accepts a single {@link Product8} argument.
         */
        default Fn1.Predicate<Product8<? extends A, ? extends B, ? extends C, ? extends D, ? extends E, ? extends F, ? extends G, ? extends H>> tupled() {
            return p -> onEval(p._1(), p._2(), p._3(), p._4(), p._5(), p._6(), p._7(), p._8());
        }
    }

    // ----------------------------------------------------------
    //  FN8.CONSUMER
    // ----------------------------------------------------------

    /**
     * A side effect operation that accepts 8 arguments.
     *
     * @param <A> type of 1st parameter.
     * @param <B> type of 2nd parameter.
     * @param <C> type of 3rd parameter.
     * @param <D> type of 4th parameter.
     * @param <E> type of 5th parameter.
     * @param <F> type of 6th parameter.
     * @param <G> type of 7th parameter.
     * @param <H> type of 8th parameter.
     */
    @FunctionalInterface
    interface Consumer<A, B, C, D, E, F, G, H> extends Fn8<A, B, C, D, E, F, G, H, Unit>, IFn8.IConsumer<$8, A, B, C, D, E, F, G, H> {

        /** Returns an empty consumer. */
        static <A, B, C, D, E, F, G, H> Consumer<A, B, C, D, E, F, G, H> Empty() {
            return Force.cast(Constant.empty);
        }

        /// LAZY APPLICATION.

        /**
         * Produces a thunk by fixing the arguments and deferring
         * the computation until the result is needed.
         */
        @Override default Fn0.Consumer thunk(final A a, final B b, final C c, final D d, final E e, final F f, final G g, final H h) {
            return () -> onAccept(a, b, c, d, e, f, g, h);
        }

        /// PARTIAL APPLICATION.

        /** Partially applies this consumer by passing the first 7 arguments. */
        @Override default Fn1.Consumer<H> apply(final A a, final B b, final C c, final D d, final E e, final F f, final G g) {
            return h -> onAccept(a, b, c, d, e, f, g, h);
        }

        /** Partially applies this consumer by passing the first 6 arguments. */
        @Override default Fn2.Consumer<G, H> apply(final A a, final B b, final C c, final D d, final E e, final F f) {
            return (g, h) -> onAccept(a, b, c, d, e, f, g, h);
        }

        /** Partially applies this consumer by passing the first 5 arguments. */
        @Override default Fn3.Consumer<F, G, H> apply(final A a, final B b, final C c, final D d, final E e) {
            return (f, g, h) -> onAccept(a, b, c, d, e, f, g, h);
        }

        /** Partially applies this consumer by passing the first 4 arguments. */
        @Override default Fn4.Consumer<E, F, G, H> apply(final A a, final B b, final C c, final D d) {
            return (e, f, g, h) -> onAccept(a, b, c, d, e, f, g, h);
        }

        /** Partially applies this consumer by passing the first 5 arguments. */
        @Override default Fn5.Consumer<D, E, F, G, H> apply(final A a, final B b, final C c) {
            return (d, e, f, g, h) -> onAccept(a, b, c, d, e, f, g, h);
        }

        /** Partially applies this consumer by passing the first 6 arguments. */
        @Override default Fn6.Consumer<C, D, E, F, G, H> apply(final A a, final B b) {
            return (c, d, e, f, g, h) -> onAccept(a, b, c, d, e, f, g, h);
        }

        /** Partially applies this consumer by passing the first 7 arguments. */
        @Override default Fn7.Consumer<B, C, D, E, F, G, H> apply(final A a) {
            return (b, c, d, e, f, g, h) -> onAccept(a, b, c, d, e, f, g, h);
        }

        /// UNCURRY ISOMORPHISM.

        /**
         * Produces the tupled consumer form: instead of 8 arguments,
         * it accepts a single {@link Product8} argument.
         */
        default Fn1.Consumer<Product8<? extends A, ? extends B, ? extends C, ? extends D, ? extends E, ? extends F, ? extends G, ? extends H>> tupled() {
            return p -> onAccept(p._1(), p._2(), p._3(), p._4(), p._5(), p._6(), p._7(), p._8());
        }
    }

    // ----------------------------------------------------------

    /**
     * Constructs a {@link Fn8} function that invariably returns the given value.
     */
    static <A, B, C, D, E, F, G, H, R> Fn8<A, B, C, D, E, F, G, H, R> constant(final R val) {
        return (a, b, c, d, e, f, g, h) -> val;
    }

    /**
     * Constructs a {@link Fn8} function from the given curried {@link Fn1} function.
     */
    static <A, B, C, D, E, F, G, H, R> Fn8<A, B, C, D, E, F, G, H, R> curried(final Fn1<A, Fn7<B, C, D, E, F, G, H, R>> fn) {
        return (a, b, c, d, e, f, g, h) -> fn.onApply(a).onApply(b, c, d, e, f, g, h);
    }

    /**
     * Constructs a {@link Fn8} function from the given curried {@link Fn2} function.
     */
    static <A, B, C, D, E, F, G, H, R> Fn8<A, B, C, D, E, F, G, H, R> curried(final Fn2<A, B, Fn6<C, D, E, F, G, H, R>> fn) {
        return (a, b, c, d, e, f, g, h) -> fn.onApply(a, b).onApply(c, d, e, f, g, h);
    }

    /**
     * Constructs a {@link Fn8} function from the given curried {@link Fn3} function.
     */
    static <A, B, C, D, E, F, G, H, R> Fn8<A, B, C, D, E, F, G, H, R> curried(final Fn3<A, B, C, Fn5<D, E, F, G, H, R>> fn) {
        return (a, b, c, d, e, f, g, h) -> fn.onApply(a, b, c).onApply(d, e, f, g, h);
    }

    /**
     * Constructs a {@link Fn8} function from the given curried {@link Fn4} function.
     */
    static <A, B, C, D, E, F, G, H, R> Fn8<A, B, C, D, E, F, G, H, R> curried(final Fn4<A, B, C, D, Fn4<E, F, G, H, R>> fn) {
        return (a, b, c, d, e, f, g, h) -> fn.onApply(a, b, c, d).onApply(e, f, g, h);
    }

    /**
     * Constructs a {@link Fn8} function from the given curried {@link Fn5} function.
     */
    static <A, B, C, D, E, F, G, H, R> Fn8<A, B, C, D, E, F, G, H, R> curried(final Fn5<A, B, C, D, E, Fn3<F, G, H, R>> fn) {
        return (a, b, c, d, e, f, g, h) -> fn.onApply(a, b, c, d, e).onApply(f, g, h);
    }

    /**
     * Constructs a {@link Fn8} function from the given curried {@link Fn6} function.
     */
    static <A, B, C, D, E, F, G, H, R> Fn8<A, B, C, D, E, F, G, H, R> curried(final Fn6<A, B, C, D, E, F, Fn2<G, H, R>> fn) {
        return (a, b, c, d, e, f, g, h) -> fn.onApply(a, b, c, d, e, f).onApply(g, h);
    }

    /**
     * Constructs a {@link Fn8} function from the given curried {@link Fn7} function.
     */
    static <A, B, C, D, E, F, G, H, R> Fn8<A, B, C, D, E, F, G, H, R> curried(final Fn7<A, B, C, D, E, F, G, Fn1<H, R>> fn) {
        return (a, b, c, d, e, f, g, h) -> fn.onApply(a, b, c, d, e, f, g).onApply(h);
    }
}