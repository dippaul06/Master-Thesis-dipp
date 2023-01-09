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

package magma.exa.value.adt;

import magma.exa.adt.mixin.Mixin;

/**
 * Declares a parametric value via an accessor operation.
 *
 * @param <A> type of value.
 */
public @Mixin interface Value<A> {

    /**
     * Returns the value.
     */
    A value();

    // ----------------------------------------------------------
    //  VALUE.ASSIGN
    // ----------------------------------------------------------

    /**
     * Declares value assignment via a assign operation.
     *
     * @param <A> type of  value to be assigned.
     * @param <R> type of result to be returned.
     */
    @Mixin interface Assignable<A, R extends Value<A>> {

        /**
         * Returns the result of assigning the given value to this slot.
         *
         * @param a value to be assigned of type {@link A}.
         * @return result of assignment of type {@link R}.
         */
        R value(A a);
    }
}
