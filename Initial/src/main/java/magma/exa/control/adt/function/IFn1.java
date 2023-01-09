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

import magma.exa.adt.typelevel.nat.$1;
import magma.exa.adt.typelevel.shape.Arity;
import magma.exa.adt.typelevel.signature.$Fn1;
import magma.exa.control.exception.Throw;
import magma.exa.control.function.Fn1;
import magma.exa.value.Unit;

/**
 * Abstract function protocol of arity 1.
 *
 * @param <N> type-level arity.
 * @param <A> type of 1st parameter.
 * @param <R> type of function result.
 */
public interface IFn1<N extends $1, A, R>
        extends Arity<N>, $Fn1<A, R> {

    R onApply(A a) throws Throwable;

    /**
     * Function application by passing 1 argument.
     *
     * @param a 1st argument.
     * @return function result.
     */
    default R apply(final A a) {
        try {
            return onApply(a);
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    // ----------------------------------------------------------
    //  IFN1.PREDICATE
    // ----------------------------------------------------------

    /**
     * Abstract predicate protocol of arity 1.
     *
     * @param <N> type-level arity.
     * @param <A> type of 1st parameter.
     */
    interface IPredicate<N extends $1, A> extends IFn1<$1, A, Boolean> {

        boolean onEval(A a) throws Throwable;

        /**
         * Predicate evaluation by passing a single argument.
         *
         * @param a 1st argument.
         * @return {@code boolean} value indicating whether the given
         * arguments match the conditions specified by this predicate.
         */
        default boolean eval(final A a) {
            try {
                return onEval(a);
            } catch (final Throwable ex) {
                return Throw.sneaky(ex);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        default Boolean onApply(final A a) throws Throwable {
            return onEval(a);
        }

        /**
         * Constant predicate functions.
         */
        enum Constant implements Fn1.Predicate<Object> {
            True  { public boolean onEval(Object a) { return true;  } },
            False { public boolean onEval(Object a) { return false; } }
        }
    }

    // ----------------------------------------------------------
    //  IFN1.CONSUMER
    // ----------------------------------------------------------

    /**
     * Abstract consumer protocol of arity 1.
     *
     * @param <N> type-level arity.
     * @param <A> type of 1st parameter.
     */
    interface IConsumer<N extends $1, A> extends IFn1<N, A, Unit> {

        void onAccept(A a) throws Throwable;

        /**
         * Consumer invocation by passing a single argument.
         *
         * @param a 1st argument.
         */
        default void accept(final A a) {
            try {
                onAccept(a);
            } catch (final Throwable ex) {
                Throw.sneaky(ex);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        default Unit onApply(final A a) throws Throwable {
            onAccept(a);
            return Unit.value;
        }

        /**
         * Constant consumer functions.
         */
        enum Constant implements Fn1.Consumer<Object> {
            empty { public void onAccept(Object a) { } }
        }
    }
}
