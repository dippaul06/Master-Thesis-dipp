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

import magma.exa.adt.typelevel.nat.$1;
import magma.exa.control.function.Fn1;
import magma.exa.value.adt.compound.Compound1;

/**
 * Generalized product of 1 heterogeneous types.
 *
 * @param <A> type of the 1st slot.
 */
public interface Product1<A> extends Product<$1>,
        Compound1<$1, A> {

    /**
     * Returns the arity of this product.
     */
    @Override
    default $1 arity() {
        return $1.nat;
    }

    /**
     * Returns the projection of the 1st slot.
     */
    @Override A _1();

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
    default <R> R match(final Fn1<? super A, ? extends R> function) {
        return function.apply(_1());
    }

    // ----------------------------------------------------------
    //  PRODUCT-1.ASSIGNABLE
    // ----------------------------------------------------------

    /**
     * Assignable form of {@link Product1}.
     *
     * @param <A> type of the 1st slot.
     * @param <U> unification parameter.
     */
    interface Assignable<A, U extends Assignable<A, ?>>
            extends Product1<A>, Compound1.Assignable<A, U> {
    }
}
