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

package magma.exa.adt.typelevel.shape;

import magma.exa.adt.mixin.Mixin;
import magma.exa.adt.typelevel.nat.$N;

/**
 * The mixin type 'Rank' declares a type-level cardinal number which
 * specifies the number of dimensions or axes in its structure, or
 * the length of its shape.
 *
 * @param <N> type-level cardinal number.
 */
public @Mixin interface Rank<N extends $N> {

    /**
     * Returns the type-level rank.
     */
    N rank();

    /**
     * Returns the rank as {@code int} value.
     */
    static int of(final Rank<?> type) {
        return type.rank().ordinal();
    }
}
