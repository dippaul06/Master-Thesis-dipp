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

import magma.exa.adt.typelevel.nat.$6;
import magma.exa.adt.typelevel.signature.$Fn6;
import magma.exa.control.exception.Throw;
import magma.exa.control.function.Fn1;
import magma.exa.control.function.Fn6;
import magma.exa.value.Unit;

/**
 * Abstract function protocol of arity 6.
 *
 * @param <N> type-level arity.
 * @param <A> type of 1st parameter.
 * @param <B> type of 2nd parameter.
 * @param <C> type of 3rd parameter.
 * @param <D> type of 4th parameter.
 * @param <E> type of 5th parameter.
 * @param <F> type of 6th parameter.
 * @param <R> type of function result.
 */
public interface IFn6<N extends $6, A, B, C, D, E, F, R>
        extends IFn5<N, A, B, C, D, E, IFn1<?, F, R>>,
        $Fn6<A, B, C, D, E, F, R> {

    R onApply(A a, B b, C c, D d, E e, F f) throws Throwable;

    /**
     * Function application by passing 6 arguments.
     *
     * @param a 1st argument.
     * @param b 2nd argument.
     * @param c 3rd argument.
     * @param d 4th argument.
     * @param e 5th argument.
     * @param f 6th argument.
     * @return function result.
     */
    default R apply(final A a, final B b, final C c, final D d, final E e, final F f) {
        try {
            return onApply(a, b, c, d, e, f);
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Fn1<F, R> onApply(final A a, final B b, final C c, final D d, final E e) {
        return f -> onApply(a, b, c, d, e, f);
    }

    /**
     * Partial application by passing the first 5 arguments.
     *
     * @param a 1st argument.
     * @param b 2nd argument.
     * @param c 3rd argument.
     * @param d 4th argument.
     * @param e 5th argument.
     * @return function of arity 1.
     */
    @Override
    IFn1<?, F, R> apply(A a, B b, C c, D d, E e);

    /**
     * Partial application by passing the first 4 arguments.
     *
     * @param a 1st argument.
     * @param b 2nd argument.
     * @param c 3rd argument.
     * @param d 4th argument.
     * @return function of arity 2.
     */
    @Override
    IFn2<?, E, F, R> apply(A a, B b, C c, D d);

    /**
     * Partial application by passing the first 3 arguments.
     *
     * @param a 1st argument.
     * @param b 2nd argument.
     * @param c 3rd argument.
     * @return function of arity 3.
     */
    @Override
    IFn3<?, D, E, F, R> apply(A a, B b, C c);

    /**
     * Partial application by passing the first 2 arguments.
     *
     * @param a 1st argument.
     * @param b 2nd argument.
     * @return function of arity 2.
     */
    @Override
    IFn4<?, C, D, E, F, R> apply(A a, B b);

    /**
     * Partial application by passing the first 1 arguments.
     *
     * @param a 1st argument.
     * @return function of arity 1.
     */
    @Override
    IFn5<?, B, C, D, E, F, R> apply(A a);

    // ----------------------------------------------------------
    //  IFN6.PREDICATE
    // ----------------------------------------------------------

    /**
     * Abstract predicate protocol of arity 6.
     *
     * @param <N> type-level arity.
     * @param <A> type of 1st parameter.
     * @param <B> type of 2nd parameter.
     * @param <C> type of 3rd parameter.
     * @param <D> type of 4th parameter.
     * @param <E> type of 5th parameter.
     * @param <F> type of 6th parameter.
     */
    interface IPredicate<N extends $6, A, B, C, D, E, F> extends IFn6<$6, A, B, C, D, E, F, Boolean> {

        boolean onEval(A a, B b, C c, D d, E e, F f) throws Throwable;

        /**
         * Predicate evaluation by passing 6 arguments.
         *
         * @param a 1st argument.
         * @param b 2nd argument.
         * @param c 3rd argument.
         * @param d 4th argument.
         * @param e 5th argument.
         * @param f 6th argument.
         * @return {@code boolean} value indicating whether the given
         * arguments match the conditions specified by this predicate.
         */
        default boolean eval(final A a, final B b, final C c, final D d, final E e, final F f) {
            try {
                return onEval(a, b, c, d, e, f);
            } catch (final Throwable ex) {
                return Throw.sneaky(ex);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        default Boolean onApply(final A a, final B b, final C c, final D d, final E e, final F f) throws Throwable {
            return onEval(a, b, c, d, e, f);
        }

        /**
         * Constant predicate functions.
         */
        enum Constant implements Fn6.Predicate<Object, Object, Object, Object, Object, Object> {
            True  { public boolean onEval(Object a, Object b, Object c, Object d, Object e, Object f) { return true;  } },
            False { public boolean onEval(Object a, Object b, Object c, Object d, Object e, Object f) { return false; } }
        }
    }

    // ----------------------------------------------------------
    //  IFN6.CONSUMER
    // ----------------------------------------------------------

    /**
     * Abstract consumer protocol of arity 6.
     *
     * @param <N> type-level arity.
     * @param <A> type of 1st parameter.
     * @param <B> type of 2nd parameter.
     * @param <C> type of 3rd parameter.
     * @param <D> type of 4th parameter.
     * @param <E> type of 5th parameter.
     * @param <F> type of 6th parameter.
     */
    interface IConsumer<N extends $6, A, B, C, D, E, F> extends IFn6<N, A, B, C, D, E, F, Unit> {

        void onAccept(A a, B b, C c, D d, E e, F f) throws Throwable;

        /**
         * Consumer invocation by passing 6 arguments.
         *
         * @param a 1st argument.
         * @param b 2nd argument.
         * @param c 3rd argument.
         * @param d 4th argument.
         * @param e 5th argument.
         * @param f 6th argument.
         */
        default void accept(final A a, final B b, final C c, final D d, final E e, final F f) {
            try {
                onAccept(a, b, c, d, e, f);
            } catch (final Throwable ex) {
                Throw.sneaky(ex);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        default Unit onApply(final A a, final B b, final C c, final D d, final E e, final F f) throws Throwable {
            onAccept(a, b, c, d, e, f);
            return Unit.value;
        }

        /**
         * Constant consumer functions.
         */
        enum Constant implements Fn6.Consumer<Object, Object, Object, Object, Object, Object> {
            empty { public void onAccept(Object a, Object b, Object c, Object d, Object e, Object f) { } }
        }
    }
}
