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

import magma.exa.adt.typelevel.nat.$7;
import magma.exa.adt.typelevel.signature.$Fn7;
import magma.exa.control.exception.Throw;
import magma.exa.control.function.Fn1;
import magma.exa.control.function.Fn7;
import magma.exa.value.Unit;

/**
 * Abstract function protocol of arity 7.
 *
 * @param <N> type-level arity.
 * @param <A> type of 1st parameter.
 * @param <B> type of 2nd parameter.
 * @param <C> type of 3rd parameter.
 * @param <D> type of 4th parameter.
 * @param <E> type of 5th parameter.
 * @param <F> type of 6th parameter.
 * @param <G> type of 7th parameter.
 * @param <R> type of function result.
 */
public interface IFn7<N extends $7, A, B, C, D, E, F, G, R>
        extends IFn6<N, A, B, C, D, E, F, IFn1<?, G, R>>,
        $Fn7<A, B, C, D, E, F, G, R> {

    R onApply(A a, B b, C c, D d, E e, F f, G g) throws Throwable;

    /**
     * Function application by passing 7 arguments.
     *
     * @param a 1st argument.
     * @param b 2nd argument.
     * @param c 3rd argument.
     * @param d 4th argument.
     * @param e 5th argument.
     * @param f 6th argument.
     * @param g 7th argument.
     * @return function result.
     */
    default R apply(final A a, final B b, final C c, final D d, final E e, final F f, final G g) {
        try {
            return onApply(a, b, c, d, e, f, g);
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Fn1<G, R> onApply(final A a, final B b, final C c, final D d, final E e, final F f) {
        return g -> onApply(a, b, c, d, e, f, g);
    }

    /**
     * Partial application by passing the first 6 arguments.
     *
     * @param a 1st argument.
     * @param b 2nd argument.
     * @param c 3rd argument.
     * @param d 4th argument.
     * @param e 5th argument.
     * @param f 6th argument.
     * @return function of arity 1.
     */
    @Override
    IFn1<?, G, R> apply(A a, B b, C c, D d, E e, F f);

    /**
     * Partial application by passing the first 5 arguments.
     *
     * @param a 1st argument.
     * @param b 2nd argument.
     * @param c 3rd argument.
     * @param d 4th argument.
     * @param e 5th argument.
     * @return function of arity 2.
     */
    @Override
    IFn2<?, F, G, R> apply(A a, B b, C c, D d, E e);

    /**
     * Partial application by passing the first 4 arguments.
     *
     * @param a 1st argument.
     * @param b 2nd argument.
     * @param c 3rd argument.
     * @param d 4th argument.
     * @return function of arity 3.
     */
    @Override
    IFn3<?, E, F, G, R> apply(A a, B b, C c, D d);

    /**
     * Partial application by passing the first 3 arguments.
     *
     * @param a 1st argument.
     * @param b 2nd argument.
     * @param c 3rd argument.
     * @return function of arity 4.
     */
    @Override
    IFn4<?, D, E, F, G, R> apply(A a, B b, C c);

    /**
     * Partial application by passing the first 2 arguments.
     *
     * @param a 1st argument.
     * @param b 2nd argument.
     * @return function of arity 5.
     */
    @Override
    IFn5<?, C, D, E, F, G, R> apply(A a, B b);

    /**
     * Partial application by passing the first 1 argument.
     *
     * @param a 1st argument.
     * @return function of arity 6.
     */
    @Override
    IFn6<?, B, C, D, E, F, G, R> apply(A a);

    // ----------------------------------------------------------
    //  IFN7.PREDICATE
    // ----------------------------------------------------------

    /**
     * Abstract predicate protocol of arity 7.
     *
     * @param <N> type-level arity.
     * @param <A> type of 1st parameter.
     * @param <B> type of 2nd parameter.
     * @param <C> type of 3rd parameter.
     * @param <D> type of 4th parameter.
     * @param <E> type of 5th parameter.
     * @param <F> type of 6th parameter.
     * @param <G> type of 7th parameter.
     */
    interface IPredicate<N extends $7, A, B, C, D, E, F, G> extends IFn7<$7, A, B, C, D, E, F, G, Boolean> {

        boolean onEval(A a, B b, C c, D d, E e, F f, G g) throws Throwable;

        /**
         * Predicate evaluation by passing 7 arguments.
         *
         * @param a 1st argument.
         * @param b 2nd argument.
         * @param c 3rd argument.
         * @param d 4th argument.
         * @param e 5th argument.
         * @param f 6th argument.
         * @param g 7th argument.
         * @return {@code boolean} value indicating whether the given
         * arguments match the conditions specified by this predicate.
         */
        default boolean eval(final A a, final B b, final C c, final D d, final E e, final F f, final G g) {
            try {
                return onEval(a, b, c, d, e, f, g);
            } catch (final Throwable ex) {
                return Throw.sneaky(ex);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        default Boolean onApply(final A a, final B b, final C c, final D d, final E e, final F f, final G g) throws Throwable {
            return onEval(a, b, c, d, e, f, g);
        }

        /**
         * Constant predicate functions.
         */
        enum Constant implements Fn7.Predicate<Object, Object, Object, Object, Object, Object, Object> {
            True  { public boolean onEval(Object a, Object b, Object c, Object d, Object e, Object f, Object g) { return true;  } },
            False { public boolean onEval(Object a, Object b, Object c, Object d, Object e, Object f, Object g) { return false; } }
        }
    }

    // ----------------------------------------------------------
    //  IFN7.CONSUMER
    // ----------------------------------------------------------

    /**
     * Abstract consumer protocol of arity 7.
     *
     * @param <N> type-level arity.
     * @param <A> type of 1st parameter.
     * @param <B> type of 2nd parameter.
     * @param <C> type of 3rd parameter.
     * @param <D> type of 4th parameter.
     * @param <E> type of 5th parameter.
     * @param <F> type of 6th parameter.
     * @param <G> type of 7th parameter.
     */
    interface IConsumer<N extends $7, A, B, C, D, E, F, G> extends IFn7<N, A, B, C, D, E, F, G, Unit> {

        void onAccept(A a, B b, C c, D d, E e, F f, G g) throws Throwable;

        /**
         * Consumer invocation by passing 7 arguments.
         *
         * @param a 1st argument.
         * @param b 2nd argument.
         * @param c 3rd argument.
         * @param d 4th argument.
         * @param e 5th argument.
         * @param f 6th argument.
         * @param g 7th argument.
         */
        default void accept(final A a, final B b, final C c, final D d, final E e, final F f, final G g) {
            try {
                onAccept(a, b, c, d, e, f, g);
            } catch (final Throwable ex) {
                Throw.sneaky(ex);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        default Unit onApply(final A a, final B b, final C c, final D d, final E e, final F f, final G g) throws Throwable {
            onAccept(a, b, c, d, e, f, g);
            return Unit.value;
        }

        /**
         * Constant consumer functions.
         */
        enum Constant implements Fn7.Consumer<Object, Object, Object, Object, Object, Object, Object> {
            empty { public void onAccept(Object a, Object b, Object c, Object d, Object e, Object f, Object g) { } }
        }
    }
}
