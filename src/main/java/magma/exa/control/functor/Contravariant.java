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
import magma.exa.base.Narrow;
import magma.exa.control.function.Fn1;

/**
 * The 'Contravariant' typeshape declares a generic contravariant functorial
 * operation {@link #contraMap} over a parameter {@code A}. A contravariant
 * functor reverses the direction of composition. A functor can be thought
 * of as containing or producing values, then a contravariant functor can be
 * thought of as consuming.
 *
 * Contravariant instances should obey the following laws:
 * <p><ul>
 * <li>[identity]     cmap id = id
 * <li>[composition]  cmap (g ∘ f) = cmap f ∘ cmap g
 * </ul><p>
 *
 * @param <A> contravariant parameter.
 * @param <U> unification parameter.
 */
public @Typeshape interface Contravariant<A, U extends Contravariant<?, U>> {

    /**
     * Contravariantly transmutes this type's parameter using the given
     * mapping function. Generally the result type of this operation is
     * to be specialized to the implementing functor type.
     *
     * @param <B> type of the resulting parameter.
     * @param fn  mapping function over the type's parameter.
     * @return contravariant functor over parameter {@code B}.
     */
    <B> Contravariant<B, U> contraMap(Fn1<? super B, ? extends A> fn);

    /**
     * Returns the given contravariant functor narrowed
     * into a concrete type. Not typesafe.
     */
    static <A,
            U extends Contravariant<?, U>,
            C extends Contravariant<A, U>>
    C narrow(final Contravariant<A, U> cofunctor) {
        return Narrow.cast(cofunctor);
    }
}
