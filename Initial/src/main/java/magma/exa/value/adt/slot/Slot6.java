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

package magma.exa.value.adt.slot;

import magma.exa.adt.mixin.Mixin;
import magma.exa.adt.typelevel.nat.$6;

/**
 * Declares a parametric slot at position 6 via an index-named accessor.
 * Slots are elementary shapes used to form compounded value structures.
 *
 * @param <N> type-level arity.
 * @param <F> type of slot value.
 */
public @Mixin interface Slot6<N extends $6, F> {

    /**
     * Returns the value of the 6th slot.
     */
    F _6();

    // ----------------------------------------------------------
    //  SLOT-6.ASSIGNABLE
    // ----------------------------------------------------------

    /**
     * Assignable form of {@link Slot6} by declaring
     * an index-named assignment operation.
     *
     * @param <F> type of  value to be assigned.
     * @param <R> type of result to be returned.
     */
    @Mixin interface Assignable<F, R extends Slot6<?, F>> {

        /**
         * Returns the result of assigning the given value to this slot.
         *
         * @param value to be assigned of type {@link F}.
         * @return result of type {@link R}.
         */
        R _6(F value);
    }
}
