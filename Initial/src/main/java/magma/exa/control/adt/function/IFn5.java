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

import magma.exa.adt.typelevel.nat.$5;
import magma.exa.adt.typelevel.signature.$Fn5;
import magma.exa.control.exception.Throw;
import magma.exa.control.function.Fn1;
import magma.exa.control.function.Fn5;
import magma.exa.value.Unit;

/**
 * Abstract function protocol of arity 5.
 *
 * @param <N> type-level arity.
 * @param <A> type of 1st parameter.
 * @param <B> type of 2nd parameter.
 * @param <C> type of 3rd parameter.
 * @param <D> type of 4th parameter.
 * @param <E> type of 5th parameter.
 * @param <R> type of function result.
 */
public interface IFn5<N extends $5, A, B, C, D, E, R>
        extends IFn4<N, A, B, C, D, IFn1<?, E, R>>,
        $Fn5<A, B, C, D, E, R> {

    R onApply(A a, B b, C c, D d, E e) throws Throwable;

    /**
     * Function application by passing 5 arguments.
     *
     * @param a 1st argument.
     * @param b 2nd argument.
     * @param c 3rd argument.
     * @param d 4th argument.
     * @param e 5th argument.
     * @return function result.
     */
    default R apply(final A a, final B b, final C c, final D d, final E e) {
        try {
            return onApply(a, b, c, d, e);
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Fn1<E, R> onApply(final A a, final B b, final C c, final D d) {
        return e -> onApply(a, b, c, d, e);
    }

    /**
     * Partial application by passing the first 4 arguments.
     *
     * @param a 1st argument.
     * @param b 2nd argument.
     * @param c 3rd argument.
     * @param d 4th argument.
     * @return function of arity 1.
     */
    @Override
    IFn1<?, E, R> apply(A a, B b, C c, D d);

    /**
     * Partial application by passing the first 3 arguments.
     *
     * @param a 1st argument.
     * @param b 2nd argument.
     * @param c 3rd argument.
     * @return function of arity 2.
     */
    @Override
    IFn2<?, D, E, R> apply(A a, B b, C c);

    /**
     * Partial application by passing the first 2 arguments.
     *
     * @param a 1st argument.
     * @param b 2nd argument.
     * @return function of arity 3.
     */
    @Override
    IFn3<?, C, D, E, R> apply(A a, B b);

    /**
     * Partial application by passing the first 1 argument.
     *
     * @param a 1st argument.
     * @return function of arity 4.
     */
    @Override
    IFn4<?, B, C, D, E, R> apply(A a);

    // ----------------------------------------------------------
    //  IFN5.PREDICATE
    // ----------------------------------------------------------

    /**
     * Abstract predicate protocol of arity 5.
     *
     * @param <N> type-level arity.
     * @param <A> type of 1st parameter.
     * @param <B> type of 2nd parameter.
     * @param <C> type of 3rd parameter.
     * @param <D> type of 4th parameter.
     * @param <E> type of 5th parameter.
     */
    interface IPredicate<N extends $5, A, B, C, D, E> extends IFn5<$5, A, B, C, D, E, Boolean> {

        boolean onEval(A a, B b, C c, D d, E e) throws Throwable;

        /**
         * Predicate evaluation by passing 5 arguments.
         *
         * @param a 1st argument.
         * @param b 2nd argument.
         * @param c 3rd argument.
         * @param d 4th argument.
         * @param e 5th argument.
         * @return {@code boolean} value indicating whether the given
         * arguments match the conditions specified by this predicate.
         */
        default boolean eval(final A a, final B b, final C c, final D d, final E e) {
            try {
                return onEval(a, b, c, d, e);
            } catch (final Throwable ex) {
                return Throw.sneaky(ex);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        default Boolean onApply(final A a, final B b, final C c, final D d, final E e) throws Throwable {
            return onEval(a, b, c, d, e);
        }

        /**
         * Constant predicate functions.
         */
        enum Constant implements Fn5.Predicate<Object, Object, Object, Object, Object> {
            True  { public boolean onEval(Object a, Object b, Object c, Object d, Object e) { return true;  } },
            False { public boolean onEval(Object a, Object b, Object c, Object d, Object e) { return false; } }
        }
    }

    // ----------------------------------------------------------
    //  IFN5.CONSUMER
    // ----------------------------------------------------------

    /**
     * Abstract consumer protocol of arity 5.
     *
     * @param <N> type-level arity.
     * @param <A> type of 1st parameter.
     * @param <B> type of 2nd parameter.
     * @param <C> type of 3rd parameter.
     * @param <D> type of 4th parameter.
     * @param <E> type of 5th parameter.
     */
    interface IConsumer<N extends $5, A, B, C, D, E> extends IFn5<N, A, B, C, D, E, Unit> {

        void onAccept(A a, B b, C c, D d, E e) throws Throwable;

        /**
         * Consumer invocation by passing 5 arguments.
         *
         * @param a 1st argument.
         * @param b 2nd argument.
         * @param c 3rd argument.
         * @param d 4th argument.
         * @param e 5th argument.
         */
        default void accept(final A a, final B b, final C c, final D d, final E e) {
            try {
                onAccept(a, b, c, d, e);
            } catch (final Throwable ex) {
                Throw.sneaky(ex);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        default Unit onApply(final A a, final B b, final C c, final D d, final E e) throws Throwable {
            onAccept(a, b, c, d, e);
            return Unit.value;
        }

        /**
         * Constant consumer functions.
         */
        enum Constant implements Fn5.Consumer<Object, Object, Object, Object, Object> {
            empty { public void onAccept(Object a, Object b, Object c, Object d, Object e) { } }
        }
    }
}
