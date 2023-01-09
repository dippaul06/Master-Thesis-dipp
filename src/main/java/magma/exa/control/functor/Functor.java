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
 * The 'Functor' typeshape declares a generic covariant functorial
 * operation {@link #fmap} over a parameter {@code A}. The Functor
 * abstracts the ability to map over a computational context.
 * <p>
 * Functor instances should obey the following laws:
 * <p><ul>
 * <li>[identity]    fmap id = id
 * <li>[composition] fmap (g ∘ f) = fmap f ∘ fmap g
 * </ul><p>
 *
 * For more information, read about
 * <a href="http://www.cs.ox.ac.uk/people/daniel.james/functor/functor.pdf">
 *     Functional Pearl: F for Functor</a>.
 *
 * ----------------------------------------------------------
 *  FUNCTORS FOR EFFECT MANAGEMENT
 *
 * Types which include the Functor mixin can be viewed as encoding
 * an 'effect' or 'computational context'. Different 'effects' will
 * abstract away different behaviors with respect to fundamental
 * operations like {@link #fmap(Fn1)}.
 *
 * For instance, Option’s effect abstracts away potentially missing
 * values, where fmap applies the function only in the 'Some' case
 * but otherwise threads the 'None' through.
 *
 * From this point of view, we can view Functor as the ability to
 * work with a single effect - we can apply a pure function to a
 * single effective value without having to 'leave' the effect.
 *
 * For more information, read about
 * <a href="https://www.tweag.io/blog/2020-01-16-data-vs-control/">
 *     Control Functors (Linear Types)</a>.
 * ----------------------------------------------------------
 *
 * @param <A> functor parameter.
 * @param <U> unification parameter.
 */
public @Typeshape interface Functor<A, U extends Functor<?, U>> {

    /**
     * Covariantly transmute this functor's parameter using the given
     * mapping function. Generally the result type of this operation
     * is to be specialized to the implementing functor type.
     *
     * @param <B> type of the resulting functor parameter.
     * @param fn  mapping function over the functor parameter.
     * @return functor instance over parameter {@code B}.
     */
    <B> Functor<B, U> fmap(Fn1<? super A, ? extends B> fn);

    /**
     * Narrows the given functor to it's primary type.
     *
     * @param functor to be narrowed.
     * @param <A> functor parameter.
     * @param <U> unification parameter.
     * @param <P> functor's primary type.
     * @return narrowed functor.
     */
    static <A,
            U extends Functor<?, U>,
            P extends Functor<A, U>>
    P narrow(final Functor<A, U> functor) {
        return Narrow.cast(functor);
    }

    // ----------------------------------------------------------
    //  FUNCTOR.IDENTITY
    // ----------------------------------------------------------

    /*
     *
     * @param <A>

    final class Identity<A> implements Functor<A, Identity<?>> {
        private final A value;

        public Identity(final A value) {
            this.value = value;
        }

        @Override
        public <B> Identity<B> fmap(final Fn1<? super A, ? extends B> fn) {
            return new Identity<>(fn.apply(value));
        }
    }*/
}
