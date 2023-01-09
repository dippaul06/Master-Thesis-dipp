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

package magma.exa.base;

/**
 * Language level constants and utilities.
 */
public enum Lang {
    ;
    /**
     * Constant that defines the string {@literal null}.
     */
    public static final String NULL = "null";

    /**
     * Returns the {@link Class#getSimpleName()} of the given object,
     * or 'null' if the given object is null.
     *
     * @param obj object whose simple typename is to be returned.
     * @return simple name or 'null'.
     */
    public static String className(final Object obj) {
        return null == obj ? Lang.NULL : className(obj.getClass());
    }

    /**
     * Returns the {@link Class#getSimpleName()} of the given class object,
     * or 'null' if the given class object is null.
     *
     * @param type class object whose simple name is to be returned.
     * @return simple name or 'null'.
     */
    public static String className(final Class<?> type) {
        return null == type ? Lang.NULL : type.getSimpleName();
    }
}
