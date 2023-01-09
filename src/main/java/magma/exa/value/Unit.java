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

package magma.exa.value;

import magma.exa.adt.typelevel.nat.$0;
import magma.exa.value.tuple.Tuple;

/**
 * The Unit type has a single inhabitant, called {@link #value}.
 * It represents a value that carries no computational content.
 *
 * Unit is typically used as a result type of calculations where
 * only the side effects are important and no result is produced,
 * for example {link Fn1.Consumer}.
 *
 * Thus, the intent of Unit is similar to the {@code void} type
 * in Java. The critical difference is that {@code void} denotes
 * the absence of a result type and is therefore incompatible
 * with function composition.
 */
public enum Unit implements Tuple<$0> {
    value {

        /**
         * Unit is a nullary product.
         */
        @Override
        public $0 arity() {
            return $0.nat;
        }
    }
}