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

import magma.exa.adt.typelevel.nat.$0;
import magma.exa.adt.typelevel.shape.Arity;
import magma.exa.adt.typelevel.signature.$Fn0;
import magma.exa.control.exception.Throw;
import magma.exa.value.Unit;

/**
 * Abstract function protocol of arity 0.
 *
 * @param <N> type-level arity.
 * @param <R> type of function result.
 */
public interface IFn0<N extends $0, R>
        extends Arity<N>, $Fn0<R> {

    R onApply() throws Throwable;

    /**
     * Function application (by passing zero arguments).
     *
     * @return function result.
     */
    default R apply() {
        try {
            return onApply();
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
     */
    interface IPredicate<N extends $0> extends IFn0<N, Boolean> {

        boolean onEval() throws Throwable;

        /**
         * Predicate evaluation.
         *
         * @return {@code boolean} value indicating whether the given
         * arguments match the conditions specified by this predicate.
         */
        default boolean eval() {
            try {
                return onEval();
            } catch (final Throwable ex) {
                return Throw.sneaky(ex);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        default Boolean onApply() throws Throwable {
            return onEval();
        }
    }

    // ----------------------------------------------------------
    //  IFN0.CONSUMER
    // ----------------------------------------------------------

    /**
     * Abstract consumer protocol of arity 0.
     *
     * @param <N> type-level arity.
     */
    interface IConsumer<N extends $0> extends IFn0<N, Unit> {

        void onAccept() throws Throwable;

        /**
         * Consumer invocation.
         */
        default void accept() {
            try {
                onAccept();
            } catch (final Throwable ex) {
                Throw.sneaky(ex);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        default Unit onApply() throws Throwable {
            onAccept();
            return Unit.value;
        }
    }
}
