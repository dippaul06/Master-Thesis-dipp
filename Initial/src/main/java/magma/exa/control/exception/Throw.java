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

package magma.exa.control.exception;

/**
 * Sneaky throw utility. This code will not ignore, wrap,
 * replace, or otherwise modify the thrown checked exception.
 */
public enum Throw {
    ;
    /**
     * Unchecks and re-throws the given exception and strictly preserving
     * the original stack trace. The declared return type of type {@code R}
     * allows using sneaky throw in expression contexts. Invoking this
     * routine always throws an exception and never returns.
     *
     * @param exception to be unchecked and rethrown.
     * @return ⊥
     */
    @SuppressWarnings("unchecked")
    public static <R, E extends Throwable>
    R sneaky(final Throwable exception) throws E {
        throw (E) exception;
    }
}
