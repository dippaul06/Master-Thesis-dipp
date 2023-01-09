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

import magma.exa.adt.typelevel.nat.$2;
import magma.exa.control.function.Fn2;
import magma.exa.value.adt.compound.Compound2;

/**
 * Generalized product of 2 heterogeneous types.
 *
 * @param <A> type of the 1st slot.
 * @param <B> type of the 2nd slot.
 */
public interface Product2<A, B> extends Product<$2>,
        Compound2<$2, A, B> {

    /**
     * Returns the arity of this product.
     */
    @Override
    default $2 arity() {
        return $2.nat;
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
     * Returns the result of applying the given function to the destructured
     * product, i.e. to the values in the corresponding slots of this product.
     * <p>
     * Dual to uncurry the given function and apply this product to it.
     *
     * @param function to be applied to the destructured product.
     * @param <R>      type of function result.
     * @return result of destructuring.
     */
    default <R> R match(final Fn2<? super A, ? super B, ? extends R> function) {
        return function.apply(_1(), _2());
    }

    // ----------------------------------------------------------
    //  PRODUCT-2.ASSIGNABLE
    // ----------------------------------------------------------

    /**
     * Assignable form of {@link Product2}.
     *
     * @param <A> type of the 1st slot.
     * @param <B> type of the 2nd slot.
     * @param <U> unification parameter.
     */
    interface Assignable<A, B, U extends Assignable<A, B, ?>>
            extends Product2<A, B>, Compound2.Assignable<A, B, U> {
    }
}
