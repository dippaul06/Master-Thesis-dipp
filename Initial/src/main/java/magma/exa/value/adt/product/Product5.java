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

package magma.exa.value.adt.product;

import magma.exa.adt.typelevel.nat.$5;
import magma.exa.control.function.Fn5;
import magma.exa.value.adt.compound.Compound5;

/**
 * Generalized product of 5 heterogeneous types.
 *
 * @param <A> type of the 1st slot.
 * @param <B> type of the 2nd slot.
 * @param <C> type of the 3rd slot.
 * @param <D> type of the 4th slot.
 * @param <E> type of the 5th slot.
 */
public interface Product5<A, B, C, D, E> extends Product<$5>,
        Compound5<$5, A, B, C, D, E> {

    /**
     * Returns the arity of this product.
     */
    @Override
    default $5 arity() {
        return $5.nat;
    }

    /**
     * Returns the projection of the 1st slot.
     */
    @Override A _1();

    /**
     * Returns the projection of the 2nd slot.
     */
    @Override B _2();

    /**
     * Returns the projection of the 3rd slot.
     */
    @Override C _3();

    /**
     * Returns the projection of the 4th slot.
     */
    @Override D _4();

    /**
     * Returns the projection of the 5th slot.
     */
    @Override E _5();

    /**
     * Returns the result of applying the given function to the destructured
     * product, i.e. to the values in the corresponding slots of this product.
     * <p>
     * Dual to uncurry the given function and apply this product to it.
     *
     * @param function to be applied to the destructured product.
     * @param <R>      type of function result.
     * @return result of destructuring.
     */
    default <R> R match(final Fn5<? super A, ? super B, ? super C, ? super D, ? super E, ? extends R> function) {
        return function.apply(_1(), _2(), _3(), _4(), _5());
    }

    // ----------------------------------------------------------
    //  PRODUCT-5.ASSIGNABLE
    // ----------------------------------------------------------

    /**
     * Assignable form of {@link Product5}.
     *
     * @param <A> type of the 1st slot.
     * @param <B> type of the 2nd slot.
     * @param <C> type of the 3rd slot.
     * @param <D> type of the 4th slot.
     * @param <E> type of the 5th slot.
     * @param <U> unification parameter.
     */
    interface Assignable<A, B, C, D, E, U extends Assignable<A, B, C, D, E, ?>>
            extends Product5<A, B, C, D, E>, Compound5.Assignable<A, B, C, D, E, U> {
    }
}
