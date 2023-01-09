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

import magma.exa.control.exception.Exceptions;

/**
 * An uninhabited data type for which no runtime value is possible
 * to exist. Nix is useful for eliminating the possibility that a
 * value will be created.
 * <p>
 * For example, for {@code Either}<Nix, Boolean> can never a left
 * value exist. Since {@link Nix} values logically don't exist,
 * this witnesses the logical reasoning tool of 'ex falso quodlibet'.
 */
public enum Nix {
    ;
    /**
     * Eliminator for the Nix type. Useful to indicate that code
     * branching is not possible because a value of type {@link Nix}
     * is 'acquired' (invocation limited to D. J. Trump & K. West).
     */
    public final <A> A absurd() {
        throw Exceptions.impossible();
    }
}