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

package magma.exa.control.adt.function;

import magma.exa.adt.typelevel.nat.$8;
import magma.exa.adt.typelevel.signature.$Fn8;
import magma.exa.control.exception.Throw;
import magma.exa.control.function.Fn1;
import magma.exa.control.function.Fn8;
import magma.exa.value.Unit;

/**
 * Abstract function protocol of arity 8.
 *
 * @param <N> type-level arity.
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
public interface IFn8<N extends $8, A, B, C, D, E, F, G, H, R>
        extends IFn7<N, A, B, C, D, E, F, G, IFn1<?, H, R>>,
        $Fn8<A, B, C, D, E, F, G, H, R> {

    R onApply(A a, B b, C c, D d, E e, F f, G g, H h) throws Throwable;

    /**
     * Function application by passing 8 arguments.
     *
     * @param a 1st argument.
     * @param b 2nd argument.
     * @param c 3rd argument.
     * @param d 4th argument.
     * @param e 5th argument.
     * @param f 6th argument.
     * @param g 7th argument.
     * @param h 8th argument.
     * @return function result.
     */
    default R apply(final A a, final B b, final C c, final D d, final E e, final F f, final G g, final H h) {
        try {
            return onApply(a, b, c, d, e, f, g, h);
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Fn1<H, R> onApply(final A a, final B b, final C c, final D d, final E e, final F f, final G g) {
        return h -> onApply(a, b, c, d, e, f, g, h);
    }

    /**
     * Partial application by passing the first 7 arguments.
     *
     * @param a 1st argument.
     * @param b 2nd argument.
     * @param c 3rd argument.
     * @param d 4th argument.
     * @param e 5th argument.
     * @param f 6th argument.
     * @param g 7th argument.
     * @return function of arity 1.
     */
    @Override
    IFn1<?, H, R> apply(A a, B b, C c, D d, E e, F f, G g);

    /**
     * Partial application by passing the first 6 arguments.
     *
     * @param a 1st argument.
     * @param b 2nd argument.
     * @param c 3rd argument.
     * @param d 4th argument.
     * @param e 5th argument.
     * @param f 6th argument.
     * @return function of arity 2.
     */
    @Override
    IFn2<?, G, H, R> apply(A a, B b, C c, D d, E e, F f);

    /**
     * Partial application by passing the first 5 arguments.
     *
     * @param a 1st argument.
     * @param b 2nd argument.
     * @param c 3rd argument.
     * @param d 4th argument.
     * @param e 5th argument.
     * @return function of arity 3.
     */
    @Override
    IFn3<?, F, G, H, R> apply(A a, B b, C c, D d, E e);

    /**
     * Partial application by passing the first 4 arguments.
     *
     * @param a 1st argument.
     * @param b 2nd argument.
     * @param c 3rd argument.
     * @param d 4th argument.
     * @return function of arity 4.
     */
    @Override
    IFn4<?, E, F, G, H, R> apply(A a, B b, C c, D d);

    /**
     * Partial application by passing the first 3 arguments.
     *
     * @param a 1st argument.
     * @param b 2nd argument.
     * @param c 3rd argument.
     * @return function of arity 5.
     */
    @Override
    IFn5<?, D, E, F, G, H, R> apply(A a, B b, C c);

    /**
     * Partial application by passing the first 2 arguments.
     *
     * @param a 1st argument.
     * @param b 2nd argument.
     * @return function of arity 6.
     */
    @Override
    IFn6<?, C, D, E, F, G, H, R> apply(A a, B b);

    /**
     * Partial application by passing the first argument.
     *
     * @param a 1st argument.
     * @return function of arity 7.
     */
    @Override
    IFn7<?, B, C, D, E, F, G, H, R> apply(A a);

    // ----------------------------------------------------------
    //  IFN8.PREDICATE
    // ----------------------------------------------------------

    /**
     * Abstract predicate protocol of arity 8.
     *
     * @param <N> type-level arity.
     * @param <A> type of 1st parameter.
     * @param <B> type of 2nd parameter.
     * @param <C> type of 3rd parameter.
     * @param <D> type of 4th parameter.
     * @param <E> type of 5th parameter.
     * @param <F> type of 6th parameter.
     * @param <G> type of 7th parameter.
     * @param <H> type of 8th parameter.
     */
    interface IPredicate<N extends $8, A, B, C, D, E, F, G, H> extends IFn8<N, A, B, C, D, E, F, G, H, Boolean> {

        boolean onEval(A a, B b, C c, D d, E e, F f, G g, H h) throws Throwable;

        /**
         * Predicate evaluation by passing 8 arguments.
         *
         * @param a 1st argument.
         * @param b 2nd argument.
         * @param c 3rd argument.
         * @param d 4th argument.
         * @param e 5th argument.
         * @param f 6th argument.
         * @param g 7th argument.
         * @param h 8th argument.
         * @return {@code boolean} value indicating whether the given
         * arguments match the conditions specified by this predicate.
         */
        default boolean eval(final A a, final B b, final C c, final D d, final E e, final F f, final G g, final H h) {
            try {
                return onEval(a, b, c, d, e, f, g, h);
            } catch (final Throwable ex) {
                return Throw.sneaky(ex);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        default Boolean onApply(final A a, final B b, final C c, final D d, final E e, final F f, final G g, final H h) throws Throwable {
            return onEval(a, b, c, d, e, f, g, h);
        }

        /**
         * Constant predicate functions.
         */
        enum Constant implements Fn8.Predicate<Object, Object, Object, Object, Object, Object, Object, Object> {
            True  { public boolean onEval(Object a, Object b, Object c, Object d, Object e, Object f, Object g, Object h) { return true;  } },
            False { public boolean onEval(Object a, Object b, Object c, Object d, Object e, Object f, Object g, Object h) { return false; } }
        }
    }

    // ----------------------------------------------------------
    //  IFN8.CONSUMER
    // ----------------------------------------------------------

    /**
     * Abstract consumer protocol of arity 8.
     *
     * @param <N> type-level arity.
     * @param <A> type of 1st parameter.
     * @param <B> type of 2nd parameter.
     * @param <C> type of 3rd parameter.
     * @param <D> type of 4th parameter.
     * @param <E> type of 5th parameter.
     * @param <F> type of 6th parameter.
     * @param <G> type of 7th parameter.
     * @param <H> type of 8th parameter.
     */
    interface IConsumer<N extends $8, A, B, C, D, E, F, G, H> extends IFn8<N, A, B, C, D, E, F, G, H, Unit> {

        void onAccept(A a, B b, C c, D d, E e, F f, G g, H h) throws Throwable;

        /**
         * Consumer invocation by passing 8 arguments.
         *
         * @param a 1st argument.
         * @param b 2nd argument.
         * @param c 3rd argument.
         * @param d 4th argument.
         * @param e 5th argument.
         * @param f 6th argument.
         * @param g 7th argument.
         * @param h 8th argument.
         */
        default void accept(final A a, final B b, final C c, final D d, final E e, final F f, final G g, final H h) {
            try {
                onAccept(a, b, c, d, e, f, g, h);
            } catch (final Throwable ex) {
                Throw.sneaky(ex);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        default Unit onApply(final A a, final B b, final C c, final D d, final E e, final F f, final G g, final H h) throws Throwable {
            onAccept(a, b, c, d, e, f, g, h);
            return Unit.value;
        }

        /**
         * Constant consumer functions.
         */
        enum Constant implements Fn8.Consumer<Object, Object, Object, Object, Object, Object, Object, Object> {
            empty { public void onAccept(Object a, Object b, Object c, Object d, Object e, Object f, Object g, Object h) { } }
        }
    }
}
