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

import magma.exa.adt.typelevel.nat.$3;
import magma.exa.adt.typelevel.signature.$Fn3;
import magma.exa.control.exception.Throw;
import magma.exa.control.function.Fn1;
import magma.exa.control.function.Fn3;
import magma.exa.value.Unit;

/**
 * Abstract function protocol of arity 3.
 *
 * @param <N> type-level arity.
 * @param <A> type of 1st parameter.
 * @param <B> type of 2nd parameter.
 * @param <C> type of 3rd parameter.
 * @param <R> type of function result.
 */
public interface IFn3<N extends $3, A, B, C, R>
        extends IFn2<N, A, B, IFn1<?, C, R>>,
        $Fn3<A, B, C, R> {

    R onApply(A a, B b, C c) throws Throwable;

    /**
     * Function application by passing 3 arguments.
     *
     * @param a 1st argument.
     * @param b 2nd argument.
     * @param c 3rd argument.
     * @return function result.
     */
    default R apply(final A a, final B b, final C c) {
        try {
            return onApply(a, b, c);
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Fn1<C, R> onApply(final A a, final B b) {
        return c -> onApply(a, b, c);
    }

    /**
     * Partial application by passing the first 2 arguments.
     *
     * @param a 1st argument.
     * @param b 2nd argument.
     * @return function of arity 1.
     */
    @Override
    IFn1<?, C, R> apply(A a, B b);

    /**
     * Partial application by passing the first argument.
     *
     * @param a 1st argument.
     * @return function of arity 2.
     */
    @Override
    IFn2<?, B, C, R> apply(A a);

    // ----------------------------------------------------------
    //  IFN3.PREDICATE
    // ----------------------------------------------------------

    /**
     * Abstract predicate protocol of arity 3.
     *
     * @param <N> type-level arity.
     * @param <A> type of 1st parameter.
     * @param <B> type of 2nd parameter.
     * @param <C> type of 3rd parameter.
     */
    interface IPredicate<N extends $3, A, B, C> extends IFn3<$3, A, B, C, Boolean> {

        boolean onEval(A a, B b, C c) throws Throwable;

        /**
         * Predicate evaluation by passing 3 arguments.
         *
         * @param a 1st argument.
         * @param b 2nd argument.
         * @param c 3rd argument.
         * @return {@code boolean} value indicating whether the given
         * arguments match the conditions specified by this predicate.
         */
        default boolean eval(final A a, final B b, final C c) {
            try {
                return onEval(a, b, c);
            } catch (final Throwable ex) {
                return Throw.sneaky(ex);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        default Boolean onApply(final A a, final B b, final C c) throws Throwable {
            return onEval(a, b, c);
        }

        /**
         * Constant predicate functions.
         */
        enum Constant implements Fn3.Predicate<Object, Object, Object> {
            True  { public boolean onEval(Object a, Object b, Object c) { return true;  } },
            False { public boolean onEval(Object a, Object b, Object c) { return false; } }
        }
    }

    // ----------------------------------------------------------
    //  IFN3.CONSUMER
    // ----------------------------------------------------------

    /**
     * Abstract consumer protocol of arity 3.
     *
     * @param <N> type-level arity.
     * @param <A> type of 1st parameter.
     * @param <B> type of 2nd parameter.
     * @param <C> type of 3rd parameter.
     */
    interface IConsumer<N extends $3, A, B, C> extends IFn3<N, A, B, C, Unit> {

        void onAccept(A a, B b, C c) throws Throwable;

        /**
         * Consumer invocation by passing 3 arguments.
         *
         * @param a 1st argument.
         * @param b 2nd argument.
         * @param c 3rd argument.
         */
        default void accept(final A a, final B b, final C c) {
            try {
                onAccept(a, b, c);
            } catch (final Throwable ex) {
                Throw.sneaky(ex);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        default Unit onApply(final A a, final B b, final C c) throws Throwable {
            onAccept(a, b, c);
            return Unit.value;
        }

        /**
         * Constant consumer functions.
         */
        enum Constant implements Fn3.Consumer<Object, Object, Object> {
            empty { public void onAccept(Object a, Object b, Object c) { } }
        }
    }
}
