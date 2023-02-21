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

package magma.exa.data.index;

import magma.exa.adt.mixin.Mixin;
import magma.exa.adt.typelevel.nat.$N;

/**
 * Mixin type that identifies a dimension on type-level
 * within an N-dimensional domain Dim¹ × ... × Dimⁿ.
 *
 * @param <N> type-level dimension.
 */
public @Mixin interface Dim<N extends $N> {

    /**
     * Returns type-level index of the dimension.
     */
    N dim();
}

