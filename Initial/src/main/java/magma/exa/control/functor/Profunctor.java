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

package magma.exa.control.functor;

import magma.exa.adt.mixin.Typeshape;
import magma.exa.control.function.Fn;
import magma.exa.control.function.Fn1;

/**
 * The 'Profunctor' typeshape declares a generic functional operation
 * {@link #dimap(Fn1, Fn1)} over two parameters {@code A, B}. The 1st
 * argument is a mapping function that contravariantly transmutes the
 * left parameter {@code A} and the 2nd argument is a mapping function
 * that covariantly transmutes the right parameter {@code B}.
 *
 * Profunctor instances should obey the following laws:
 * <p><ul>
 * <li>Identity:    dimap id = id
 * <li>Composition: dimap (g ∘ f) = dimap f ∘ dimap g
 * </ul><p>
 *
 * @param <A> type of 1st parameter (contravariant).
 * @param <B> type of 2nd parameter (covariant).
 * @param <U> unification parameter.
 */
public @Typeshape interface Profunctor<A, B, U extends Profunctor<?, ?, U>>
        extends Contravariant<A, Profunctor<?, B, U>>
{
    /**
     * Simultaneous mapping of both parameters {@code A, B} by contra-
     * variantly transmuting the left parameter using the 1st mapping
     * function {@code A -> L} and covariantly transmuting the right
     * parameter using the 2nd mapping function {@code B -> R}.
     *
     * @param <L> type of the mapped left  parameter.
     * @param <R> type of the mapped right parameter.
     * @param fl  1st mapping function over left  parameter.
     * @param fr  2nd mapping function over right parameter.
     * @return profunctor instance over parameter {@code L, R}.
     */
    <L, R> Profunctor<L, R, U> dimap(Fn1<? super L, ? extends A> fl,
                                     Fn1<? super B, ? extends R> fr);

    /**
     * Contravariantly transmuting the left parameter by applying
     * the given mapping function.
     *
     * @param fl  mapping function over the left parameter.
     * @param <L> type of the mapped left parameter.
     * @return profunctor instance over parameter {@code L, B}.
     */
    default <L> Profunctor<L, B, U> dimapL(final Fn1<? super L, ? extends A> fl) {
        return dimap(fl, Fn.identity());
    }

    /**
     * Contravariantly transmuting the right parameter by applying
     * the given mapping function.
     *
     * @param fr  mapping function over the right parameter.
     * @param <R> type of the mapped right parameter.
     * @return profunctor instance over parameter {@code A, R}.
     */
    default <R> Profunctor<A, R, U> dimapR(final Fn1<? super B, ? extends R> fr) {
        return dimap(Fn.identity(), fr);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default <L> Profunctor<L, B, U> contraMap(final Fn1<? super L, ? extends A> fl) {
        return dimapL(fl);
    }
}
