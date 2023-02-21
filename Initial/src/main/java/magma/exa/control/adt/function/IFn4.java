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

import magma.exa.adt.typelevel.nat.$4;
import magma.exa.adt.typelevel.signature.$Fn4;
import magma.exa.control.exception.Throw;
import magma.exa.control.function.Fn1;
import magma.exa.control.function.Fn4;
import magma.exa.value.Unit;

/**
 * Abstract function protocol of arity 4.
 *
 * @param <N> type-level arity.
 * @param <A> type of 1st parameter.
 * @param <B> type of 2nd parameter.
 * @param <C> type of 3rd parameter.
 * @param <D> type of 4th parameter.
 * @param <R> type of function result.
 */
public interface IFn4<N extends $4, A, B, C, D, R>
        extends IFn3<N, A, B, C, IFn1<?, D, R>>,
        $Fn4<A, B, C, D, R> {

    R onApply(A a, B b, C c, D d) throws Throwable;

    /**
     * Function application by passing 4 arguments.
     *
     * @param a 1st argument.
     * @param b 2nd argument.
     * @param c 3rd argument.
     * @param d 4th argument.
     * @return function result.
     */
    default R apply(final A a, final B b, final C c, final D d) {
        try {
            return onApply(a, b, c, d);
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Fn1<D, R> onApply(final A a, final B b, final C c) {
        return d -> onApply(a, b, c, d);
    }

    /**
     * Partial application by passing the first 3 arguments.
     *
     * @param a 1st argument.
     * @param b 2nd argument.
     * @param c 3rd argument.
     * @return function of arity 3.
     */
    @Override
    IFn1<?, D, R> apply(A a, B b, C c);

    /**
     * Partial application by passing the first 2 arguments.
     *
     * @param a 1st argument.
     * @param b 2nd argument.
     * @return function of arity 2.
     */
    @Override
    IFn2<?, C, D, R> apply(A a, B b);

    /**
     * Partial application by passing the first argument.
     *
     * @param a 1st argument.
     * @return function of arity 3.
     */
    @Override
    IFn3<?, B, C, D, R> apply(A a);

    // ----------------------------------------------------------
    //  IFN4.PREDICATE
    // ----------------------------------------------------------

    /**
     * Abstract predicate protocol of arity 4.
     *
     * @param <N> type-level arity.
     * @param <A> type of 1st parameter.
     * @param <B> type of 2nd parameter.
     * @param <C> type of 3rd parameter.
     * @param <D> type of 4th parameter.
     */
    interface IPredicate<N extends $4, A, B, C, D> extends IFn4<$4, A, B, C, D, Boolean> {

        boolean onEval(A a, B b, C c, D d) throws Throwable;

        /**
         * Predicate evaluation by passing 4 arguments.
         *
         * @param a 1st argument.
         * @param b 2nd argument.
         * @param c 3rd argument.
         * @param d 4th argument.
         * @return {@code boolean} value indicating whether the given
         * arguments match the conditions specified by this predicate.
         */
        default boolean eval(final A a, final B b, final C c, final D d) {
            try {
                return onEval(a, b, c, d);
            } catch (final Throwable ex) {
                return Throw.sneaky(ex);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        default Boolean onApply(final A a, final B b, final C c, final D d) throws Throwable {
            return onEval(a, b, c, d);
        }

        /**
         * Constant predicate functions.
         */
        enum Constant implements Fn4.Predicate<Object, Object, Object, Object> {
            True  { public boolean onEval(Object a, Object b, Object c, Object d) { return true;  } },
            False { public boolean onEval(Object a, Object b, Object c, Object d) { return false; } }
        }
    }

    // ----------------------------------------------------------
    //  IFN4.CONSUMER
    // ----------------------------------------------------------

    /**
     * Abstract consumer protocol of arity 4.
     *
     * @param <N> type-level arity.
     * @param <A> type of 1st parameter.
     * @param <B> type of 2nd parameter.
     * @param <C> type of 3rd parameter.
     * @param <D> type of 4th parameter.
     */
    interface IConsumer<N extends $4, A, B, C, D> extends IFn4<N, A, B, C, D, Unit> {

        void onAccept(A a, B b, C c, D d) throws Throwable;

        /**
         * Consumer invocation by passing 4 arguments.
         *
         * @param a 1st argument.
         * @param b 2nd argument.
         * @param c 3rd argument.
         * @param d 4th argument.
         */
        default void accept(final A a, final B b, final C c, final D d) {
            try {
                onAccept(a, b, c, d);
            } catch (final Throwable ex) {
                Throw.sneaky(ex);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        default Unit onApply(final A a, final B b, final C c, final D d) throws Throwable {
            onAccept(a, b, c, d);
            return Unit.value;
        }

        /**
         * Constant consumer functions.
         */
        enum Constant implements Fn4.Consumer<Object, Object, Object, Object> {
            empty { public void onAccept(Object a, Object b, Object c, Object d) { } }
        }
    }
}
