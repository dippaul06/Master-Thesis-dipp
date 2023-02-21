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

import magma.exa.adt.typelevel.nat.$2;
import magma.exa.adt.typelevel.signature.$Fn2;
import magma.exa.control.exception.Throw;
import magma.exa.control.function.Fn1;
import magma.exa.control.function.Fn2;
import magma.exa.value.Unit;

/**
 * Abstract function protocol of arity 2.
 *
 * @param <N> type-level arity.
 * @param <A> type of 1st parameter.
 * @param <B> type of 2nd parameter.
 * @param <R> type of function result.
 */
public interface IFn2<N extends $2, A, B, R>
        extends IFn1<N, A, IFn1<?, B, R>>,
        $Fn2<A, B, R> {

    R onApply(A a, B b) throws Throwable;

    /**
     * Function application by passing 2 arguments.
     *
     * @param a 1st argument.
     * @param b 2nd argument.
     * @return function result.
     */
    default R apply(final A a, final B b) {
        try {
            return onApply(a, b);
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Fn1<B, R> onApply(final A a) {
        return b -> onApply(a, b);
    }

    /**
     * Partial application by passing the first argument.
     *
     * @param a 1st argument.
     * @return function of arity 1.
     */
    @Override
    IFn1<?, B, R> apply(A a);

    // ----------------------------------------------------------
    //  IFN2.PREDICATE
    // ----------------------------------------------------------

    /**
     * Abstract predicate protocol of arity 2.
     *
     * @param <N> type-level arity.
     * @param <A> type of 1st parameter.
     * @param <B> type of 2nd parameter.
     */
    interface IPredicate<N extends $2, A, B> extends IFn2<$2, A, B, Boolean> {

        boolean onEval(A a, B b) throws Throwable;

        /**
         * Predicate evaluation by passing 2 arguments.
         *
         * @param a 1st argument.
         * @param b 2nd argument.
         * @return {@code boolean} value indicating whether the given
         * arguments match the conditions specified by this predicate.
         */
        default boolean eval(final A a, final B b) {
            try {
                return onEval(a, b);
            } catch (final Throwable ex) {
                return Throw.sneaky(ex);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        default Boolean onApply(final A a, final B b) throws Throwable {
            return onEval(a, b);
        }

        /**
         * Constant predicate functions.
         */
        enum Constant implements Fn2.Predicate<Object, Object> {
            True  { public boolean onEval(Object a, Object b) { return true;  } },
            False { public boolean onEval(Object a, Object b) { return false; } }
        }
    }

    // ----------------------------------------------------------
    //  IFN2.CONSUMER
    // ----------------------------------------------------------

    /**
     * Abstract consumer protocol of arity 2.
     *
     * @param <N> type-level arity.
     * @param <A> type of 1st parameter.
     * @param <B> type of 2nd parameter.
     */
    interface IConsumer<N extends $2, A, B> extends IFn2<N, A, B, Unit> {

        void onAccept(A a, B b) throws Throwable;

        /**
         * Consumer invocation by passing 2 arguments.
         *
         * @param a 1st argument.
         * @param b 2nd argument.
         */
        default void accept(final A a, final B b) {
            try {
                onAccept(a, b);
            } catch (final Throwable ex) {
                Throw.sneaky(ex);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        default Unit onApply(final A a, final B b) throws Throwable {
            onAccept(a, b);
            return Unit.value;
        }

        /**
         * Constant consumer functions.
         */
        enum Constant implements Fn2.Consumer<Object, Object> {
            empty { public void onAccept(Object a, Object b) { } }
        }
    }
}
