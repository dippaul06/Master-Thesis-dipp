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

package magma.exa.value.adt.coproduct;

import magma.exa.adt.typelevel.shape.Arity;
import magma.exa.adt.typelevel.nat.$N;

/**
 * Common superinterface for all coproduct types.
 *
 * @param <N> type-level arity.
 * @param <T> F-bound parameter.
 */
public interface Coproduct<N extends $N, T extends Coproduct<N, T>> extends Arity<N> {

    /**
     * Returns the arity of a coproduct.
     */
    @Override N arity();
}
