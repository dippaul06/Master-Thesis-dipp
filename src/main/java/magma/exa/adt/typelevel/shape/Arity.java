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
 * The mixin type arity declares a type-level cardinal number which
 * specifies the dimension of a product space. The arity is used for
 * product and function types to express the number of components or
 * arguments in type signatures which allows expressing parametric
 * constraints on the arity of those types.
 *
 * @param <N> type-level number.
 */
public @Mixin interface Arity<N extends $N> {

    /**
     * Returns the type-level arity.
     */
    N arity();

    /**
     * Returns the arity as {@code int} value.
     */
    static int of(final Arity<?> type) {
        return type.arity().ordinal();
    }
}
