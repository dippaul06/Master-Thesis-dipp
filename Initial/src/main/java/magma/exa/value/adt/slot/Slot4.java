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
import magma.exa.adt.typelevel.nat.$4;

/**
 * Declares a parametric slot at position 4 via an index-named accessor.
 * Slots are elementary shapes used to form compounded value structures.
 *
 * @param <N> type-level arity.
 * @param <D> type of slot value.
 */
public @Mixin interface Slot4<N extends $4, D> {

    /**
     * Returns the value of the 4th slot.
     */
    D _4();

    // ----------------------------------------------------------
    //  SLOT-4.ASSIGNABLE
    // ----------------------------------------------------------

    /**
     * Assignable form of {@link Slot4} by declaring
     * an index-named assignment operation.
     *
     * @param <D> type of  value to be assigned.
     * @param <R> type of result to be returned.
     */
    @Mixin interface Assignable<D, R extends Slot4<?, D>> {

        /**
         * Returns the result of assigning the given value to this slot.
         *
         * @param value to be assigned of type {@link D}.
         * @return result of type {@link R}.
         */
        R _4(D value);
    }
}
