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

import magma.exa.adt.typelevel.nat.$3;
import magma.exa.control.function.Fn3;
import magma.exa.value.adt.compound.Compound3;

/**
 * Generalized product of 3 heterogeneous types.
 *
 * @param <A> type of the 1st slot.
 * @param <B> type of the 2nd slot.
 * @param <C> type of the 3rd slot.
 */
public interface Product3<A, B, C> extends Product<$3>,
        Compound3<$3, A, B, C> {

    /**
     * Returns the arity of this product.
     */
    @Override
    default $3 arity() {
        return $3.nat;
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
     * Returns the result of applying the given function to the destructured
     * product, i.e. to the values in the corresponding slots of this product.
     * <p>
     * Dual to uncurry the given function and apply this product to it.
     *
     * @param function to be applied to the destructured product.
     * @param <R>      type of function result.
     * @return result of destructuring.
     */
    default <R> R match(final Fn3<? super A, ? super B, ? super C, ? extends R> function) {
        return function.apply(_1(), _2(), _3());
    }

    // ----------------------------------------------------------
    //  PRODUCT-3.ASSIGNABLE
    // ----------------------------------------------------------

    /**
     * Assignable form of {@link Product3}.
     *
     * @param <A> type of the 1st slot.
     * @param <B> type of the 2nd slot.
     * @param <C> type of the 3rd slot.
     * @param <U> unification parameter.
     */
    interface Assignable<A, B, C, U extends Assignable<A, B, C, ?>>
            extends Product3<A, B, C>, Compound3.Assignable<A, B, C, U> {
    }
}
