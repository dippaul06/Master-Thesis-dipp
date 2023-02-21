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
 * Thrown to indicate that the corresponding
 * code location has not yet been completed.
 */
public final class TODO extends IllegalStateException {

    /**
     * Factory method to create an error with a used-defined message.
     *
     * @param message with detailed description.
     * @return new error.
     */
    public static TODO make(final String message) {
        return new TODO(message);
    }

    /**
     * Factory method for creating an error indicating a missing implementation.
     */
    public static TODO notImplemented() {
        return TODO.make("Not Implemented.");
    }

    /**
     * Internal constructor.
     *
     * @param message with detailed description.
     */
    private TODO(final String message) {
        super(message);
    }

    static final long serialVersionUID = 0;
}
