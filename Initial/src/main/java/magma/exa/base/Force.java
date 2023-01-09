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
 * Force reference conversion treats expressions of a reference type
 * {@code S} as expressions of a different reference type {@code T},
 * where S » IS NOT A SUPER-/SUBTYPE « of T.
 *
 * @param <S> source type.
 * @param <T> target type.
 */
public final class Force<S, T> {

    /**
     * Applies operator to the given value of type {@code S} to return the value
     * forced into type {@code T}.
     *
     * @param s operand of type {@code S},
     * @return operand forced into type {@code T}.
     */
    @SuppressWarnings("unchecked")
    public T apply(final S s) {
        return (T) s;
    }

    /**
     * Return the given value of type {@code S} forced into type {@code T}.
     *
     * @param s value of type {@code S},
     * @param <T> target type.
     * @return value forced into type {@code T}.
     */
    public static <T> T cast(final Object s) {
        return Force.<Object, T> cast().apply(s);
    }

    /**
     * Returns force-cast operator as first-class.
     *
     * @param <S> supertype of {@code T}.
     * @param <T>   subtype if {@code S}.
     * @return cast operator.
     */
    @SuppressWarnings("unchecked")
    public static <S, T> Force<S, T> cast() {
        return (Force<S, T>) operator;
    }

    /** Singleton. */
    private Force() { }
    static { operator = new Force<>(); }
    private static final Force<?, ?> operator;
}
