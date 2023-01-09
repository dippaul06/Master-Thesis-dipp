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
 * Generalized n-ary functor typeshapes with defined arity from 1 .. 8.
 *
 * @param <U> unification parameter.
 * @see Functor
 */
public interface NFunctor<U extends NFunctor<U>> {

    // ----------------------------------------------------------
    //  NFUNCTOR.Of-1
    // ----------------------------------------------------------

    /**
     * The typeshape 'NFunctor.Of1' declares a generic covariant functorial
     * operation {@link #map(Fn1)} over the given parameter {@code A}.
     *
     * @param <A> type of 1st parameter.
     * @param <U> unification parameter.
     */
    @Typeshape interface Of1<A, U extends Of1<?, U>> extends NFunctor<U> {

        /**
         * Covariantly transmute functor's parameter using the given function.
         *
         * @param f1 mapping function of the parameter.
         * @return functor over the type {@code I}.
         */
        <I> Of1<I, U> map(Fn1<? super A, ? extends I> f1);
    }

    // ----------------------------------------------------------
    //  NFUNCTOR.Of-2
    // ----------------------------------------------------------

    /**
     * The typeshape 'NFunctor.Of2' declares a generic covariant functorial
     * operation {@link #map(Fn1, Fn1)} over the given two parameters
     * {@code A, B} as collective mapping.
     * <p>
     * In addition, a dedicated map operation is defined for each parameter.
     *
     * @param <A> type of 1st parameter.
     * @param <B> type of 2nd parameter.
     * @param <U> unification parameter.
     */
    @Typeshape interface Of2<A, B, U extends Of2<?, ?, U>> extends NFunctor<U> {

        /**
         * Covariantly transmute functor's both parameters using the given functions.
         *
         * @param f1 mapping function of the 1st parameter.
         * @param f2 mapping function of the 2nd parameter.
         * @return functor over the types {@code I, J}.
         */
        <I, J> Of2<I, J, U> map(Fn1<? super A, ? extends I> f1,
                                         Fn1<? super B, ? extends J> f2);

        /**
         * Covariantly transmutes the 1st parameter using the given function.
         *
         * @param f1  mapping function of the 1st parameter.
         * @param <I> type of resulting 1st parameter.
         * @return functor over the types {@code I, B}.
         */
        default <I> Of2<I, B, U> map1(final Fn1<? super A, ? extends I> f1) {
            return map(f1, Fn.identity());
        }

        /**
         * Covariantly transmutes the 2nd parameter using the given function.
         *
         * @param f2  mapping function of the 2nd parameter.
         * @param <J> type of resulting 2nd parameter.
         * @return functor over the types {@code A, J}.
         */
        default <J> Of2<A, J, U> map2(final Fn1<? super B, ? extends J> f2) {
            return map(Fn.identity(), f2);
        }
    }

    // ----------------------------------------------------------
    //  NFUNCTOR.Of-3
    // ----------------------------------------------------------

    /**
     * The typeshape 'NFunctor.Of3' declares a generic covariant functorial
     * operation {@link #map(Fn1, Fn1, Fn1)} over the given three parameters
     * {@code A, B, C} as collective mapping.
     * <p>
     * In addition, a dedicated map operation is defined for each parameter.
     *
     * @param <A> type of 1st parameter.
     * @param <B> type of 2nd parameter.
     * @param <C> type of 3rd parameter.
     * @param <U> unification parameter.
     */
    @Typeshape interface Of3<A, B, C, U extends Of3<?, ?, ?, U>> extends NFunctor<U> {

        /**
         * Covariantly transmute functor's three parameters using the given functions.
         *
         * @param f1 mapping function of the 1st parameter.
         * @param f2 mapping function of the 2nd parameter.
         * @param f3 mapping function of the 3rd parameter.
         * @return functor over the types {@code I, J, K}.
         */
        <I, J, K>
        Of3<I, J, K, U> map(Fn1<? super A, ? extends I> f1,
                            Fn1<? super B, ? extends J> f2,
                            Fn1<? super C, ? extends K> f3);

        /**
         * Covariantly transmutes the 1st parameter using the given function.
         *
         * @param f1  mapping function of the 1st parameter.
         * @param <I> type of resulting 1st parameter.
         * @return functor over the types {@code I, B, C}.
         */
        default <I> Of3<I, B, C, U> map1(final Fn1<? super A, ? extends I> f1) {
            return map(f1, Fn.identity(), Fn.identity());
        }

        /**
         * Covariantly transmutes the 2nd parameter using the given function.
         *
         * @param f2  mapping function of the 2nd parameter.
         * @param <J> type of resulting 2nd parameter.
         * @return functor over the types {@code A, J, C}.
         */
        default <J> Of3<A, J, C, U> map2(final Fn1<? super B, ? extends J> f2) {
            return map(Fn.identity(),f2, Fn.identity());
        }

        /**
         * Covariantly transmutes the 3rd parameter using the given function.
         *
         * @param f3  mapping function of the 3rd parameter.
         * @param <K> type of resulting 3rd parameter.
         * @return functor over the types {@code A, B, K}.
         */
        default <K> Of3<A, B, K, U> map3(final Fn1<? super C, ? extends K> f3) {
            return map(Fn.identity(), Fn.identity(), f3);
        }
    }

    // ----------------------------------------------------------
    //  NFUNCTOR.Of-4
    // ----------------------------------------------------------

    /**
     * The typeshape 'NFunctor.Of4' declares a generic covariant functorial
     * operation {@link #map(Fn1, Fn1, Fn1, Fn1)} over the given four
     * parameters {@code A, B, C, D} as collective mapping.
     * <p>
     * In addition, a dedicated map operation is defined for each parameter.
     *
     * @param <A> type of 1st parameter.
     * @param <B> type of 2nd parameter.
     * @param <C> type of 3rd parameter.
     * @param <D> type of 4th parameter.
     * @param <U> unification parameter.
     */
    @Typeshape interface Of4<A, B, C, D, U extends Of4<?, ?, ?, ?, U>> extends NFunctor<U> {

        /**
         * Covariantly transmute functor's four parameters using the given functions.
         *
         * @param f1 mapping function of the 1st parameter.
         * @param f2 mapping function of the 2nd parameter.
         * @param f3 mapping function of the 3rd parameter.
         * @param f4 mapping function of the 4th parameter.
         * @return functor over the types {@code I, J, K, L}.
         */
        <I, J, K, L>
        Of4<I, J, K, L, U> map(Fn1<? super A, ? extends I> f1,
                               Fn1<? super B, ? extends J> f2,
                               Fn1<? super C, ? extends K> f3,
                               Fn1<? super D, ? extends L> f4);

        /**
         * Covariantly transmutes the 1st parameter using the given function.
         *
         * @param f1  mapping function of the 1st parameter.
         * @param <I> type of resulting 1st parameter.
         * @return functor over the types {@code I, B, C, D}.
         */
        default <I> Of4<I, B, C, D, U> map1(final Fn1<? super A, ? extends I> f1) {
            return map(f1, Fn.identity(), Fn.identity(), Fn.identity());
        }

        /**
         * Covariantly transmutes the 2nd parameter using the given function.
         *
         * @param f2  mapping function of the 2nd parameter.
         * @param <J> type of resulting 2nd parameter.
         * @return functor over the types {@code A, J, C, D}.
         */
        default <J> Of4<A, J, C, D, U> map2(final Fn1<? super B, ? extends J> f2) {
            return map(Fn.identity(), f2, Fn.identity(), Fn.identity());
        }

        /**
         * Covariantly transmutes the 3rd parameter using the given function.
         *
         * @param f3  mapping function of the 3rd parameter.
         * @param <K> type of resulting 3rd parameter.
         * @return functor over the types {@code A, B, K, D}.
         */
        default <K> Of4<A, B, K, D, U> map3(final Fn1<? super C, ? extends K> f3) {
            return map(Fn.identity(), Fn.identity(), f3, Fn.identity());
        }

        /**
         * Covariantly transmutes the 4th parameter using the given function.
         *
         * @param f4  mapping function of the 4th parameter.
         * @param <L> type of resulting 4th parameter.
         * @return functor over the types {@code A, B, C, L}.
         */
        default <L> Of4<A, B, C, L, U> map4(final Fn1<? super D, ? extends L> f4) {
            return map(Fn.identity(), Fn.identity(), Fn.identity(), f4);
        }
    }

    // ----------------------------------------------------------
    //  NFUNCTOR.Of-5
    // ----------------------------------------------------------

    /**
     * The typeshape 'NFunctor.Of5' declares a generic covariant functorial
     * operation {@link #map(Fn1, Fn1, Fn1, Fn1, Fn1)} over the given five
     * parameters {@code A, B, C, D, E} as collective mapping.
     * <p>
     * In addition, a dedicated map operation is defined for each parameter.
     *
     * @param <A> type of 1st parameter.
     * @param <B> type of 2nd parameter.
     * @param <C> type of 3rd parameter.
     * @param <D> type of 4th parameter.
     * @param <E> type of 5th parameter.
     * @param <U> unification parameter.
     */
    @Typeshape interface Of5<A, B, C, D, E, U extends Of5<?, ?, ?, ?, ?, U>> extends NFunctor<U> {

        /**
         * Covariantly transmute functor's five parameters using the given functions.
         *
         * @param f1 mapping function of the 1st parameter.
         * @param f2 mapping function of the 2nd parameter.
         * @param f3 mapping function of the 3rd parameter.
         * @param f4 mapping function of the 4th parameter.
         * @param f5 mapping function of the 5th parameter.
         * @return functor over the types {@code I, J, K, L, M}.
         */
        <I, J, K, L, M>
        Of5<I, J, K, L, M, U> map(Fn1<? super A, ? extends I> f1,
                                  Fn1<? super B, ? extends J> f2,
                                  Fn1<? super C, ? extends K> f3,
                                  Fn1<? super D, ? extends L> f4,
                                  Fn1<? super E, ? extends M> f5);

        /**
         * Covariantly transmutes the 1st parameter using the given function.
         *
         * @param f1  mapping function of the 1st parameter.
         * @param <I> type of resulting 1st parameter.
         * @return functor over the types {@code I, B, C, D, E}.
         */
        default <I> Of5<I, B, C, D, E, U> map1(final Fn1<? super A, ? extends I> f1) {
            return map(f1, Fn.identity(), Fn.identity(), Fn.identity(), Fn.identity());
        }

        /**
         * Covariantly transmutes the 2nd parameter using the given function.
         *
         * @param f2  mapping function of the 2nd parameter.
         * @param <J> type of resulting 2nd parameter.
         * @return functor over the types {@code A, J, C, D, E}.
         */
        default <J> Of5<A, J, C, D, E, U> map2(final Fn1<? super B, ? extends J> f2) {
            return map(Fn.identity(), f2, Fn.identity(), Fn.identity(), Fn.identity());
        }

        /**
         * Covariantly transmutes the 3rd parameter using the given function.
         *
         * @param f3  mapping function of the 3rd parameter.
         * @param <K> type of resulting 3rd parameter.
         * @return functor over the types {@code A, B, K, D, E}.
         */
        default <K> Of5<A, B, K, D, E, U> map3(final Fn1<? super C, ? extends K> f3) {
            return map(Fn.identity(), Fn.identity(), f3, Fn.identity(), Fn.identity());
        }

        /**
         * Covariantly transmutes the 4th parameter using the given function.
         *
         * @param f4  mapping function of the 4th parameter.
         * @param <L> type of resulting 4th parameter.
         * @return functor over the types {@code A, B, C, L, E}.
         */
        default <L> Of5<A, B, C, L, E, U> map4(final Fn1<? super D, ? extends L> f4) {
            return map(Fn.identity(), Fn.identity(), Fn.identity(), f4, Fn.identity());
        }

        /**
         * Covariantly transmutes the 5th parameter using the given function.
         *
         * @param f5  mapping function of the 5th parameter.
         * @param <M> type of resulting 5th parameter.
         * @return functor over the types {@code A, B, C, D, M}.
         */
        default <M> Of5<A, B, C, D, M, U> map5(final Fn1<? super E, ? extends M> f5) {
            return map(Fn.identity(), Fn.identity(), Fn.identity(), Fn.identity(), f5);
        }
    }

    // ----------------------------------------------------------
    //  NFUNCTOR.Of-6
    // ----------------------------------------------------------

    /**
     * The typeshape 'NFunctor.Of6' declares a generic covariant functorial
     * operation {@link #map(Fn1, Fn1, Fn1, Fn1, Fn1, Fn1)} over the given
     * six parameters {@code A, B, C, D, E, F} as collective mapping.
     * <p>
     * In addition, a dedicated map operation is defined for each parameter.
     *
     * @param <A> type of 1st parameter.
     * @param <B> type of 2nd parameter.
     * @param <C> type of 3rd parameter.
     * @param <D> type of 4th parameter.
     * @param <E> type of 5th parameter.
     * @param <U> unification parameter.
     */
    @Typeshape interface Of6<A, B, C, D, E, F, U extends Of6<?, ?, ?, ?, ?, ?, U>> extends NFunctor<U> {

        /**
         * Covariantly transmute functor's six parameters using the given functions.
         *
         * @param f1 mapping function of the 1st parameter.
         * @param f2 mapping function of the 2nd parameter.
         * @param f3 mapping function of the 3rd parameter.
         * @param f4 mapping function of the 4th parameter.
         * @param f5 mapping function of the 5th parameter.
         * @param f6 mapping function of the 6th parameter.
         * @return functor over the types {@code I, J, K, L, M, N}.
         */
        <I, J, K, L, M, N>
        Of6<I, J, K, L, M, N, U> map(Fn1<? super A, ? extends I> f1,
                                     Fn1<? super B, ? extends J> f2,
                                     Fn1<? super C, ? extends K> f3,
                                     Fn1<? super D, ? extends L> f4,
                                     Fn1<? super E, ? extends M> f5,
                                     Fn1<? super F, ? extends N> f6);

        /**
         * Covariantly transmutes the 1st parameter using the given function.
         *
         * @param f1  mapping function of the 1st parameter.
         * @param <I> type of resulting 1st parameter.
         * @return functor over the types {@code I, B, C, D, E, F}.
         */
        default <I> Of6<I, B, C, D, E, F, U> map1(final Fn1<? super A, ? extends I> f1) {
            return map(f1, Fn.identity(), Fn.identity(), Fn.identity(), Fn.identity(), Fn.identity());
        }

        /**
         * Covariantly transmutes the 2nd parameter using the given function.
         *
         * @param f2  mapping function of the 2nd parameter.
         * @param <J> type of resulting 2nd parameter.
         * @return functor over the types {@code A, J, C, D, E, F}.
         */
        default <J> Of6<A, J, C, D, E, F, U> map2(final Fn1<? super B, ? extends J> f2) {
            return map(Fn.identity(), f2, Fn.identity(), Fn.identity(), Fn.identity(), Fn.identity());
        }

        /**
         * Covariantly transmutes the 3rd parameter using the given function.
         *
         * @param f3  mapping function of the 3rd parameter.
         * @param <K> type of resulting 3rd parameter.
         * @return functor over the types {@code A, B, K, D, E, F}.
         */
        default <K> Of6<A, B, K, D, E, F, U> map3(final Fn1<? super C, ? extends K> f3) {
            return map(Fn.identity(), Fn.identity(), f3, Fn.identity(), Fn.identity(), Fn.identity());
        }

        /**
         * Covariantly transmutes the 4th parameter using the given function.
         *
         * @param f4  mapping function of the 4th parameter.
         * @param <L> type of resulting 4th parameter.
         * @return functor over the types {@code A, B, C, L, E, F}.
         */
        default <L> Of6<A, B, C, L, E, F, U> map4(final Fn1<? super D, ? extends L> f4) {
            return map(Fn.identity(), Fn.identity(), Fn.identity(), f4, Fn.identity(), Fn.identity());
        }

        /**
         * Covariantly transmutes the 5th parameter using the given function.
         *
         * @param f5  mapping function of the 5th parameter.
         * @param <M> type of resulting 5th parameter.
         * @return functor over the types {@code A, B, C, D, M, F}.
         */
        default <M> Of6<A, B, C, D, M, F, U> map5(final Fn1<? super E, ? extends M> f5) {
            return map(Fn.identity(), Fn.identity(), Fn.identity(), Fn.identity(), f5, Fn.identity());
        }

        /**
         * Covariantly transmutes the 6th parameter using the given function.
         *
         * @param f6  mapping function of the 6th parameter.
         * @param <N> type of resulting 6th parameter.
         * @return functor over the types {@code A, B, C, D, E, N}.
         */
        default <N> Of6<A, B, C, D, E, N, U> map6(final Fn1<? super F, ? extends N> f6) {
            return map(Fn.identity(), Fn.identity(), Fn.identity(), Fn.identity(), Fn.identity(), f6);
        }
    }

    // ----------------------------------------------------------
    //  NFUNCTOR.Of-7
    // ----------------------------------------------------------

    /**
     * The typeshape 'NFunctor.Of7' declares a generic covariant functorial
     * operation {@link #map(Fn1, Fn1, Fn1, Fn1, Fn1, Fn1, Fn1)} over the
     * given seven parameters {@code A, B, C, D, E, F, G} as collective mapping.
     * <p>
     * In addition, a dedicated map operation is defined for each parameter.
     *
     * @param <A> type of 1st parameter.
     * @param <B> type of 2nd parameter.
     * @param <C> type of 3rd parameter.
     * @param <D> type of 4th parameter.
     * @param <E> type of 5th parameter.
     * @param <F> type of 6th parameter.
     * @param <G> type of 7th parameter.
     * @param <U> unification parameter.
     */
    @Typeshape interface Of7<A, B, C, D, E, F, G, U extends Of7<?, ?, ?, ?, ?, ?, ?, U>> extends NFunctor<U> {

        /**
         * Covariantly transmute functor's seven parameters using the given functions.
         *
         * @param f1 mapping function of the 1st parameter.
         * @param f2 mapping function of the 2nd parameter.
         * @param f3 mapping function of the 3rd parameter.
         * @param f4 mapping function of the 4th parameter.
         * @param f5 mapping function of the 5th parameter.
         * @param f6 mapping function of the 6th parameter.
         * @param f7 mapping function of the 7th parameter.
         * @return functor over the types {@code I, J, K, L, M, N, O}.
         */
        <I, J, K, L, M, N, O>
        Of7<I, J, K, L, M, N, O, U> map(Fn1<? super A, ? extends I> f1,
                                                 Fn1<? super B, ? extends J> f2,
                                                 Fn1<? super C, ? extends K> f3,
                                                 Fn1<? super D, ? extends L> f4,
                                                 Fn1<? super E, ? extends M> f5,
                                                 Fn1<? super F, ? extends N> f6,
                                                 Fn1<? super G, ? extends O> f7);

        /**
         * Covariantly transmutes the 1st parameter using the given function.
         *
         * @param f1  mapping function of the 1st parameter.
         * @param <I> type of resulting 1st parameter.
         * @return functor over the types {@code I, B, C, D, E, F, G}.
         */
        default <I> Of7<I, B, C, D, E, F, G, U> map1(final Fn1<? super A, ? extends I> f1) {
            return map(f1, Fn.identity(), Fn.identity(), Fn.identity(), Fn.identity(), Fn.identity(), Fn.identity());
        }

        /**
         * Covariantly transmutes the 2nd parameter using the given function.
         *
         * @param f2  mapping function of the 2nd parameter.
         * @param <J> type of resulting 2nd parameter.
         * @return functor over the types {@code A, J, C, D, E, F, G}.
         */
        default <J> Of7<A, J, C, D, E, F, G, U> map2(final Fn1<? super B, ? extends J> f2) {
            return map(Fn.identity(), f2, Fn.identity(), Fn.identity(), Fn.identity(), Fn.identity(), Fn.identity());
        }

        /**
         * Covariantly transmutes the 3rd parameter using the given function.
         *
         * @param f3  mapping function of the 3rd parameter.
         * @param <K> type of resulting 3rd parameter.
         * @return functor over the types {@code A, B, K, D, E, F, G}.
         */
        default <K> Of7<A, B, K, D, E, F, G, U> map3(final Fn1<? super C, ? extends K> f3) {
            return map(Fn.identity(), Fn.identity(), f3, Fn.identity(), Fn.identity(), Fn.identity(), Fn.identity());
        }

        /**
         * Covariantly transmutes the 4th parameter using the given function.
         *
         * @param f4  mapping function of the 4th parameter.
         * @param <L> type of resulting 4th parameter.
         * @return functor over the types {@code A, B, C, L, E, F, G}.
         */
        default <L> Of7<A, B, C, L, E, F, G, U> map4(final Fn1<? super D, ? extends L> f4) {
            return map(Fn.identity(), Fn.identity(), Fn.identity(), f4, Fn.identity(), Fn.identity(), Fn.identity());
        }

        /**
         * Covariantly transmutes the 5th parameter using the given function.
         *
         * @param f5  mapping function of the 5th parameter.
         * @param <M> type of resulting 5th parameter.
         * @return functor over the types {@code A, B, C, D, M, F, G}.
         */
        default <M> Of7<A, B, C, D, M, F, G, U> map5(final Fn1<? super E, ? extends M> f5) {
            return map(Fn.identity(), Fn.identity(), Fn.identity(), Fn.identity(), f5, Fn.identity(), Fn.identity());
        }

        /**
         * Covariantly transmutes the 6th parameter using the given function.
         *
         * @param f6  mapping function of the 6th parameter.
         * @param <N> type of resulting 6th parameter.
         * @return functor over the types {@code A, B, C, D, E, N, G}.
         */
        default <N> Of7<A, B, C, D, E, N, G, U> map6(final Fn1<? super F, ? extends N> f6) {
            return map(Fn.identity(), Fn.identity(), Fn.identity(), Fn.identity(), Fn.identity(), f6, Fn.identity());
        }

        /**
         * Covariantly transmutes the 7th parameter using the given function.
         *
         * @param f7  mapping function of the 7th parameter.
         * @param <O> type of resulting 7th parameter.
         * @return functor over the types {@code A, B, C, D, E, F, O}.
         */
        default <O> Of7<A, B, C, D, E, F, O, U> map7(final Fn1<? super G, ? extends O> f7) {
            return map(Fn.identity(), Fn.identity(), Fn.identity(), Fn.identity(), Fn.identity(), Fn.identity(), f7);
        }
    }

    // ----------------------------------------------------------
    //  NFUNCTOR.Of-8
    // ----------------------------------------------------------

    /**
     * The typeshape 'NFunctor.Of8' declares a generic covariant functorial
     * operation {@link #map(Fn1, Fn1, Fn1, Fn1, Fn1, Fn1, Fn1, Fn1)} over
     * the given eight parameters {@code A, B, C, D, E, F, G, H} as collective
     * mapping.
     * <p>
     * In addition, a dedicated map operation is defined for each parameter.
     *
     * @param <A> type of 1st parameter.
     * @param <B> type of 2nd parameter.
     * @param <C> type of 3rd parameter.
     * @param <D> type of 4th parameter.
     * @param <E> type of 5th parameter.
     * @param <F> type of 6th parameter.
     * @param <G> type of 7th parameter.
     * @param <H> type of 8th parameter.
     * @param <U> unification parameter.
     */
    @Typeshape interface Of8<A, B, C, D, E, F, G, H, U extends Of8<?, ?, ?, ?, ?, ?, ?, ?, U>> extends NFunctor<U> {

        /**
         * Covariantly transmute functor's eight parameters using the given functions.
         *
         * @param f1 mapping function of the 1st parameter.
         * @param f2 mapping function of the 2nd parameter.
         * @param f3 mapping function of the 3rd parameter.
         * @param f4 mapping function of the 4th parameter.
         * @param f5 mapping function of the 5th parameter.
         * @param f6 mapping function of the 6th parameter.
         * @param f7 mapping function of the 7th parameter.
         * @param f8 mapping function of the 8th parameter.
         * @return functor over the types {@code I, J, K, L, M, N, O, P}.
         */
        <I, J, K, L, M, N, O, P>
        Of8<I, J, K, L, M, N, O, P, U> map(Fn1<? super A, ? extends I> f1,
                                                    Fn1<? super B, ? extends J> f2,
                                                    Fn1<? super C, ? extends K> f3,
                                                    Fn1<? super D, ? extends L> f4,
                                                    Fn1<? super E, ? extends M> f5,
                                                    Fn1<? super F, ? extends N> f6,
                                                    Fn1<? super G, ? extends O> f7,
                                                    Fn1<? super H, ? extends P> f8);

        /**
         * Covariantly transmutes the 1st parameter using the given function.
         *
         * @param f1  mapping function of the 1st parameter.
         * @param <I> type of resulting 1st parameter.
         * @return functor over the types {@code I, B, C, D, E, F, G, H}.
         */
        default <I> Of8<I, B, C, D, E, F, G, H, U> map1(final Fn1<? super A, ? extends I> f1) {
            return map(f1, Fn.identity(), Fn.identity(), Fn.identity(), Fn.identity(), Fn.identity(), Fn.identity(), Fn.identity());
        }

        /**
         * Covariantly transmutes the 2nd parameter using the given function.
         *
         * @param f2  mapping function of the 2nd parameter.
         * @param <J> type of resulting 2nd parameter.
         * @return functor over the types {@code A, J, C, D, E, F, G, H}.
         */
        default <J> Of8<A, J, C, D, E, F, G, H, U> map2(final Fn1<? super B, ? extends J> f2) {
            return map(Fn.identity(), f2, Fn.identity(), Fn.identity(), Fn.identity(), Fn.identity(), Fn.identity(), Fn.identity());
        }

        /**
         * Covariantly transmutes the 3rd parameter using the given function.
         *
         * @param f3  mapping function of the 3rd parameter.
         * @param <K> type of resulting 3rd parameter.
         * @return functor over the types {@code A, B, K, D, E, F, G, H}.
         */
        default <K> Of8<A, B, K, D, E, F, G, H, U> map3(final Fn1<? super C, ? extends K> f3) {
            return map(Fn.identity(), Fn.identity(), f3, Fn.identity(), Fn.identity(), Fn.identity(), Fn.identity(), Fn.identity());
        }

        /**
         * Covariantly transmutes the 4th parameter using the given function.
         *
         * @param f4  mapping function of the 4th parameter.
         * @param <L> type of resulting 4th parameter.
         * @return functor over the types {@code A, B, C, L, E, F, G, H}.
         */
        default <L> Of8<A, B, C, L, E, F, G, H, U> map4(final Fn1<? super D, ? extends L> f4) {
            return map(Fn.identity(), Fn.identity(), Fn.identity(), f4, Fn.identity(), Fn.identity(), Fn.identity(), Fn.identity());
        }

        /**
         * Covariantly transmutes the 5th parameter using the given function.
         *
         * @param f5  mapping function of the 5th parameter.
         * @param <M> type of resulting 5th parameter.
         * @return functor over the types {@code A, B, C, D, M, F, G, H}.
         */
        default <M> Of8<A, B, C, D, M, F, G, H, U> map5(final Fn1<? super E, ? extends M> f5) {
            return map(Fn.identity(), Fn.identity(), Fn.identity(), Fn.identity(), f5, Fn.identity(), Fn.identity(), Fn.identity());
        }

        /**
         * Covariantly transmutes the 6th parameter using the given function.
         *
         * @param f6  mapping function of the 6th parameter.
         * @param <N> type of resulting 6th parameter.
         * @return functor over the types {@code A, B, C, D, E, N, G, H}.
         */
        default <N> Of8<A, B, C, D, E, N, G, H, U> map6(final Fn1<? super F, ? extends N> f6) {
            return map(Fn.identity(), Fn.identity(), Fn.identity(), Fn.identity(), Fn.identity(), f6, Fn.identity(), Fn.identity());
        }

        /**
         * Covariantly transmutes the 7th parameter using the given function.
         *
         * @param f7  mapping function of the 7th parameter.
         * @param <O> type of resulting 7th parameter.
         * @return functor over the types {@code A, B, C, D, E, F, O, H}.
         */
        default <O> Of8<A, B, C, D, E, F, O, H, U> map7(final Fn1<? super G, ? extends O> f7) {
            return map(Fn.identity(), Fn.identity(), Fn.identity(), Fn.identity(), Fn.identity(), Fn.identity(), f7, Fn.identity());
        }

        /**
         * Covariantly transmutes the 8th parameter using the given function.
         *
         * @param f8  mapping function of the 8th parameter.
         * @param <P> type of resulting 8th parameter.
         * @return functor over the types {@code A, B, C, D, E, F, G, P}.
         */
        default <P> Of8<A, B, C, D, E, F, G, P, U> map8(final Fn1<? super H, ? extends P> f8) {
            return map(Fn.identity(), Fn.identity(), Fn.identity(), Fn.identity(), Fn.identity(), Fn.identity(), Fn.identity(), f8);
        }
    }
}
