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

package magma.exa.value.adt.product;

import java.util.Comparator;

import magma.exa.adt.typelevel.nat.$N;
import magma.exa.base.Hash;

import static magma.exa.base.Lang.NULL;

/**
 * Basic interface for all products that include at least
 * {@link Product1} to {@link Product8} in the Magma library
 * and thus also their canonical value representations
 * {@code Tuple1} to {@code Tuple8}.
 *
 * @param <N> type-level arity.
 */
public interface Product<N extends $N> extends magma.exa.adt.typelevel.shape.Arity<N> {

    /// HASH-CODE

    /**
     * Returns a hash code value for the given {@link Product1}.
     */
    static int hashCode(final Product1<?> p) {
        return null == p ? 0 : Hash.code(p._1());
    }

    /**
     * Returns a hash code value for the given {@link Product2}.
     */
    static int hashCode(final Product2<?, ?> p) {
        return null == p ? 0 : Hash.code(p._1(), p._2());
    }

    /**
     * Returns a hash code value for the given {@link Product3}.
     */
    static int hashCode(final Product3<?, ?, ?> p) {
        return null == p ? 0 : Hash.code(p._1(), p._2(), p._3());
    }

    /**
     * Returns a hash code value for the given {@link Product4}.
     */
    static int hashCode(final Product4<?, ?, ?, ?> p) {
        return null == p ? 0 : Hash.code(p._1(), p._2(), p._3(), p._4());
    }

    /**
     * Returns a hash code value for the given {@link Product5}.
     */
    static int hashCode(final Product5<?, ?, ?, ?, ?> p) {
        return null == p ? 0 : Hash.code(p._1(), p._2(), p._3(), p._4(), p._5());
    }

    /**
     * Returns a hash code value for the given {@link Product6}.
     */
    static int hashCode(final Product6<?, ?, ?, ?, ?, ?> p) {
        return null == p ? 0 : Hash.code(p._1(), p._2(), p._3(), p._4(), p._5(), p._6());
    }

    /**
     * Returns a hash code value for the given {@link Product7}.
     */
    static int hashCode(final Product7<?, ?, ?, ?, ?, ?, ?> p) {
        return null == p ? 0 : Hash.code(p._1(), p._2(), p._3(), p._4(), p._5(), p._6(), p._7());
    }

    /**
     * Returns a hash code value for the given {@link Product8}.
     */
    static int hashCode(final Product8<?, ?, ?, ?, ?, ?, ?, ?> p) {
        return null == p ? 0 : Hash.code(p._1(), p._2(), p._3(), p._4(), p._5(), p._6(), p._7(), p._8());
    }

    // ---------------------------------------------------------

    /// EQUALS

    /**
     * Determines if the two given {@link Product1} instances are
     * equal by comparing their corresponding components, according
     * to {@link java.util.Objects#equals(Object, Object)}.
     *
     * @param a product to be compared for equality with {@code b}.
     * @param b product to be compared for equality with {@code a}.
     * @return true if the products are equal to each other.
     */
    static boolean equals(final Product1<?> a, final Product1<?> b) {
        return null == a && null == b || null != a && null != b &&
                java.util.Objects.equals(a._1(), b._1());
    }

    /**
     * Determines if the two given {@link Product2} instances are
     * equal by comparing their corresponding components, according
     * to {@link java.util.Objects#equals(Object, Object)}.
     *
     * @param a product to be compared for equality with {@code b}.
     * @param b product to be compared for equality with {@code a}.
     * @return true if the products are equal to each other.
     */
    static boolean equals(final Product2<?, ?> a, final Product2<?, ?> b) {
        return null == a && null == b || null != a && null != b &&
                java.util.Objects.equals(a._1(), b._1()) &&
                java.util.Objects.equals(a._2(), b._2());
    }

    /**
     * Determines if the two given {@link Product3} instances are
     * equal by comparing their corresponding components, according
     * to {@link java.util.Objects#equals(Object, Object)}.
     *
     * @param a product to be compared for equality with {@code b}.
     * @param b product to be compared for equality with {@code a}.
     * @return true if the products are equal to each other.
     */
    static boolean equals(final Product3<?, ?, ?> a, final Product3<?, ?, ?> b) {
        return null == a && null == b || null != a && null != b &&
                java.util.Objects.equals(a._1(), b._1()) &&
                java.util.Objects.equals(a._2(), b._2()) &&
                java.util.Objects.equals(a._3(), b._3());
    }

    /**
     * Determines if the two given {@link Product4} instances are
     * equal by comparing their corresponding components, according
     * to {@link java.util.Objects#equals(Object, Object)}.
     *
     * @param a product to be compared for equality with {@code b}.
     * @param b product to be compared for equality with {@code a}.
     * @return true if the products are equal to each other.
     */
    static boolean equals(final Product4<?, ?, ?, ?> a,
                          final Product4<?, ?, ?, ?> b) {
        return null == a && null == b || null != a && null != b &&
                java.util.Objects.equals(a._1(), b._1()) &&
                java.util.Objects.equals(a._2(), b._2()) &&
                java.util.Objects.equals(a._3(), b._3()) &&
                java.util.Objects.equals(a._4(), b._4());
    }

    /**
     * Determines if the two given {@link Product5} instances are
     * equal by comparing their corresponding components, according
     * to {@link java.util.Objects#equals(Object, Object)}.
     *
     * @param a product to be compared for equality with {@code b}.
     * @param b product to be compared for equality with {@code a}.
     * @return true if the products are equal to each other.
     */
    static boolean equals(final Product5<?, ?, ?, ?, ?> a,
                          final Product5<?, ?, ?, ?, ?> b) {
        return null == a && null == b || null != a && null != b &&
                java.util.Objects.equals(a._1(), b._1()) &&
                java.util.Objects.equals(a._2(), b._2()) &&
                java.util.Objects.equals(a._3(), b._3()) &&
                java.util.Objects.equals(a._4(), b._4()) &&
                java.util.Objects.equals(a._5(), b._5());
    }

    /**
     * Determines if the two given {@link Product6} instances are
     * equal by comparing their corresponding components, according
     * to {@link java.util.Objects#equals(Object, Object)}.
     *
     * @param a product to be compared for equality with {@code b}.
     * @param b product to be compared for equality with {@code a}.
     * @return true if the products are equal to each other.
     */
    static boolean equals(final Product6<?, ?, ?, ?, ?, ?> a,
                          final Product6<?, ?, ?, ?, ?, ?> b) {
        return null == a && null == b || null != a && null != b &&
                java.util.Objects.equals(a._1(), b._1()) &&
                java.util.Objects.equals(a._2(), b._2()) &&
                java.util.Objects.equals(a._3(), b._3()) &&
                java.util.Objects.equals(a._4(), b._4()) &&
                java.util.Objects.equals(a._5(), b._5()) &&
                java.util.Objects.equals(a._6(), b._6());
    }

    /**
     * Determines if the two given {@link Product7} instances are
     * equal by comparing their corresponding components, according
     * to {@link java.util.Objects#equals(Object, Object)}.
     *
     * @param a product to be compared for equality with {@code b}.
     * @param b product to be compared for equality with {@code a}.
     * @return true if the products are equal to each other.
     */
    static boolean equals(final Product7<?, ?, ?, ?, ?, ?, ?> a,
                          final Product7<?, ?, ?, ?, ?, ?, ?> b) {
        return null == a && null == b || null != a && null != b &&
                java.util.Objects.equals(a._1(), b._1()) &&
                java.util.Objects.equals(a._2(), b._2()) &&
                java.util.Objects.equals(a._3(), b._3()) &&
                java.util.Objects.equals(a._4(), b._4()) &&
                java.util.Objects.equals(a._5(), b._5()) &&
                java.util.Objects.equals(a._6(), b._6()) &&
                java.util.Objects.equals(a._7(), b._7());
    }

    /**
     * Determines if the two given {@link Product8} instances are
     * equal by comparing their corresponding components, according
     * to {@link java.util.Objects#equals(Object, Object)}.
     *
     * @param a product to be compared for equality with {@code b}.
     * @param b product to be compared for equality with {@code a}.
     * @return true if the products are equal to each other.
     */
    static boolean equals(final Product8<?, ?, ?, ?, ?, ?, ?, ?> a,
                          final Product8<?, ?, ?, ?, ?, ?, ?, ?> b) {
        return null == a && null == b || null != a && null != b &&
                java.util.Objects.equals(a._1(), b._1()) &&
                java.util.Objects.equals(a._2(), b._2()) &&
                java.util.Objects.equals(a._3(), b._3()) &&
                java.util.Objects.equals(a._4(), b._4()) &&
                java.util.Objects.equals(a._5(), b._5()) &&
                java.util.Objects.equals(a._6(), b._6()) &&
                java.util.Objects.equals(a._7(), b._7()) &&
                java.util.Objects.equals(a._8(), b._8());
    }

    // ---------------------------------------------------------

    /**
     * Constructs an {@link Comparator} for {@link Product1}
     * by providing corresponding component comparators.
     *
     * @param c1 comparator for the 1st slot.
     * @return product comparator of arity 1.
     */
    static <A>
    Comparator<Product1<? extends A>>
    comparator(final Comparator<? super A> c1) {
        return (a, b) -> c1.compare(a._1(), b._1());
    }

    /**
     * Constructs an {@link Comparator} for {@link Product2}
     * by providing corresponding component comparators.
     *
     * @param c1 comparator for the 1st slot.
     * @param c2 comparator for the 2nd slot.
     * @return product comparator of arity 2.
     */
    static <A, B>
    Comparator<Product2<? extends A, ? extends B>>
    comparator(final Comparator<? super A> c1,
               final Comparator<? super B> c2) {
        return (a, b) -> {
            int cmp;
            cmp = c1.compare(a._1(), b._1());
            if (cmp != 0) return cmp;
            return c2.compare(a._2(), b._2());
        };
    }

    /**
     * Constructs an {@link Comparator} for {@link Product3}
     * by providing corresponding component comparators.
     *
     * @param c1 comparator for the 1st slot.
     * @param c2 comparator for the 2nd slot.
     * @param c3 comparator for the 3rd slot.
     * @return product comparator of arity 3.
     */
    static <A, B, C>
    Comparator<Product3<? extends A, ? extends B, ? extends C>>
    comparator(final Comparator<? super A> c1,
               final Comparator<? super B> c2,
               final Comparator<? super C> c3) {
        return (a, b) -> {
            int cmp;
            cmp = c1.compare(a._1(), b._1());
            if (cmp != 0) return cmp;
            cmp = c2.compare(a._2(), b._2());
            if (cmp != 0) return cmp;
            return c3.compare(a._3(), b._3());
        };
    }

    /**
     * Constructs an {@link Comparator} for {@link Product4}
     * by providing corresponding component comparators.
     *
     * @param c1 comparator for the 1st slot.
     * @param c2 comparator for the 2nd slot.
     * @param c3 comparator for the 3rd slot.
     * @param c4 comparator for the 4th slot.
     * @return product comparator of arity 4.
     */
    static <A, B, C, D>
    Comparator<Product4<? extends A, ? extends B, ? extends C, ? extends D>>
    comparator(final Comparator<? super A> c1,
               final Comparator<? super B> c2,
               final Comparator<? super C> c3,
               final Comparator<? super D> c4) {
        return (a, b) -> {
            int cmp;
            cmp = c1.compare(a._1(), b._1());
            if (cmp != 0) return cmp;
            cmp = c2.compare(a._2(), b._2());
            if (cmp != 0) return cmp;
            cmp = c3.compare(a._3(), b._3());
            if (cmp != 0) return cmp;
            return c4.compare(a._4(), b._4());
        };
    }

    /**
     * Constructs an {@link Comparator} for {@link Product5}
     * by providing corresponding component comparators.
     *
     * @param c1 comparator for the 1st slot.
     * @param c2 comparator for the 2nd slot.
     * @param c3 comparator for the 3rd slot.
     * @param c4 comparator for the 4th slot.
     * @param c5 comparator for the 5th slot.
     * @return product comparator of arity 5.
     */
    static <A, B, C, D, E>
    Comparator<Product5<? extends A, ? extends B, ? extends C, ? extends D, ? extends E>>
    comparator(final Comparator<? super A> c1,
               final Comparator<? super B> c2,
               final Comparator<? super C> c3,
               final Comparator<? super D> c4,
               final Comparator<? super E> c5) {
        return (a, b) -> {
            int cmp;
            cmp = c1.compare(a._1(), b._1());
            if (cmp != 0) return cmp;
            cmp = c2.compare(a._2(), b._2());
            if (cmp != 0) return cmp;
            cmp = c3.compare(a._3(), b._3());
            if (cmp != 0) return cmp;
            cmp = c4.compare(a._4(), b._4());
            if (cmp != 0) return cmp;
            return c5.compare(a._5(), b._5());
        };
    }

    /**
     * Constructs an {@link Comparator} for {@link Product6}
     * by providing corresponding component comparators.
     *
     * @param c1 comparator for the 1st slot.
     * @param c2 comparator for the 2nd slot.
     * @param c3 comparator for the 3rd slot.
     * @param c4 comparator for the 4th slot.
     * @param c5 comparator for the 5th slot.
     * @param c6 comparator for the 6th slot.
     * @return product comparator of arity 6.
     */
    static <A, B, C, D, E, F>
    Comparator<Product6<? extends A, ? extends B, ? extends C, ? extends D, ? extends E, ? extends F>>
    comparator(final Comparator<? super A> c1,
               final Comparator<? super B> c2,
               final Comparator<? super C> c3,
               final Comparator<? super D> c4,
               final Comparator<? super E> c5,
               final Comparator<? super F> c6) {
        return (a, b) -> {
            int cmp;
            cmp = c1.compare(a._1(), b._1());
            if (cmp != 0) return cmp;
            cmp = c2.compare(a._2(), b._2());
            if (cmp != 0) return cmp;
            cmp = c3.compare(a._3(), b._3());
            if (cmp != 0) return cmp;
            cmp = c4.compare(a._4(), b._4());
            if (cmp != 0) return cmp;
            cmp = c5.compare(a._5(), b._5());
            if (cmp != 0) return cmp;
            return c6.compare(a._6(), b._6());
        };
    }

    /**
     * Constructs an {@link Comparator} for {@link Product7}
     * by providing corresponding component comparators.
     *
     * @param c1 comparator for the 1st slot.
     * @param c2 comparator for the 2nd slot.
     * @param c3 comparator for the 3rd slot.
     * @param c4 comparator for the 4th slot.
     * @param c5 comparator for the 5th slot.
     * @param c6 comparator for the 6th slot.
     * @param c7 comparator for the 7th slot.
     * @return product comparator of arity 7.
     */
    static <A, B, C, D, E, F, G>
    Comparator<Product7<? extends A, ? extends B, ? extends C, ? extends D, ? extends E, ? extends F, ? extends G>>
    comparator(final Comparator<? super A> c1,
               final Comparator<? super B> c2,
               final Comparator<? super C> c3,
               final Comparator<? super D> c4,
               final Comparator<? super E> c5,
               final Comparator<? super F> c6,
               final Comparator<? super G> c7) {
        return (a, b) -> {
            int cmp;
            cmp = c1.compare(a._1(), b._1());
            if (cmp != 0) return cmp;
            cmp = c2.compare(a._2(), b._2());
            if (cmp != 0) return cmp;
            cmp = c3.compare(a._3(), b._3());
            if (cmp != 0) return cmp;
            cmp = c4.compare(a._4(), b._4());
            if (cmp != 0) return cmp;
            cmp = c5.compare(a._5(), b._5());
            if (cmp != 0) return cmp;
            cmp = c6.compare(a._6(), b._6());
            if (cmp != 0) return cmp;
            return c7.compare(a._7(), b._7());
        };
    }

    /**
     * Constructs an {@link Comparator} for {@link Product8}
     * by providing corresponding component comparators.
     *
     * @param c1 comparator for the 1st slot.
     * @param c2 comparator for the 2nd slot.
     * @param c3 comparator for the 3rd slot.
     * @param c4 comparator for the 4th slot.
     * @param c5 comparator for the 5th slot.
     * @param c6 comparator for the 6th slot.
     * @param c7 comparator for the 7th slot.
     * @param c8 comparator for the 8th slot.
     * @return product comparator of arity 8.
     */
    static <A, B, C, D, E, F, G, H>
    Comparator<Product8<? extends A, ? extends B, ? extends C, ? extends D, ? extends E, ? extends F, ? extends G, ? extends H>>
    comparator(final Comparator<? super A> c1,
               final Comparator<? super B> c2,
               final Comparator<? super C> c3,
               final Comparator<? super D> c4,
               final Comparator<? super E> c5,
               final Comparator<? super F> c6,
               final Comparator<? super G> c7,
               final Comparator<? super H> c8) {
        return (a, b) -> {
            int cmp;
            cmp = c1.compare(a._1(), b._1());
            if (cmp != 0) return cmp;
            cmp = c2.compare(a._2(), b._2());
            if (cmp != 0) return cmp;
            cmp = c3.compare(a._3(), b._3());
            if (cmp != 0) return cmp;
            cmp = c4.compare(a._4(), b._4());
            if (cmp != 0) return cmp;
            cmp = c5.compare(a._5(), b._5());
            if (cmp != 0) return cmp;
            cmp = c6.compare(a._6(), b._6());
            if (cmp != 0) return cmp;
            cmp = c7.compare(a._7(), b._7());
            if (cmp != 0) return cmp;
            return c8.compare(a._8(), b._8());
        };
    }

    // ---------------------------------------------------------

    /**
     * Returns a string representation of the given {@link Product1}.
     */
    static String toString(final Product1<?> p) {
        return null == p ? NULL : String.format(FORMAT_1, p._1());
    }

    /**
     * Returns a string representation of the given {@link Product2}.
     */
    static String toString(final Product2<?, ?> p) {
        return null == p ? NULL : String.format(FORMAT_2, p._1(), p._2());
    }

    /**
     * Returns a string representation of the given {@link Product3}.
     */
    static String toString(final Product3<?, ?, ?> p) {
        return null == p ? NULL : String.format(FORMAT_3, p._1(), p._2(), p._3());
    }

    /**
     * Returns a string representation of the given {@link Product4}.
     */
    static String toString(final Product4<?, ?, ?, ?> p) {
        return null == p ? NULL : String.format(FORMAT_4, p._1(), p._2(), p._3(), p._4());
    }

    /**
     * Returns a string representation of the given {@link Product5}.
     */
    static String toString(final Product5<?, ?, ?, ?, ?> p) {
        return null == p ? NULL : String.format(FORMAT_5, p._1(), p._2(), p._3(), p._4(), p._5());
    }

    /**
     * Returns a string representation of the given {@link Product6}.
     */
    static String toString(final Product6<?, ?, ?, ?, ?, ?> p) {
        return null == p ? NULL : String.format(FORMAT_6, p._1(), p._2(), p._3(), p._4(), p._5(), p._6());
    }

    /**
     * Returns a string representation of the given {@link Product7}.
     */
    static String toString(final Product7<?, ?, ?, ?, ?, ?, ?> p) {
        return null == p ? NULL : String.format(FORMAT_7, p._1(), p._2(), p._3(), p._4(), p._5(), p._6(), p._7());
    }

    /**
     * Returns a string representation of the given {@link Product8}.
     */
    static String toString(final Product8<?, ?, ?, ?, ?, ?, ?, ?> p) {
        return null == p ? NULL : String.format(FORMAT_8, p._1(), p._2(), p._3(), p._4(), p._5(), p._6(), p._7(), p._8());
    }

    // ---------------------------------------------------------

    /**
     * Format specifier used for string representations.
     */
    String FORMAT_1 = "(%s)",
            FORMAT_2 = "(%s, %s)",
            FORMAT_3 = "(%s, %s, %s)",
            FORMAT_4 = "(%s, %s, %s, %s)",
            FORMAT_5 = "(%s, %s, %s, %s, %s)",
            FORMAT_6 = "(%s, %s, %s, %s, %s, %s)",
            FORMAT_7 = "(%s, %s, %s, %s, %s, %s, %s)",
            FORMAT_8 = "(%s, %s, %s, %s, %s, %s, %s, %s)";
}
