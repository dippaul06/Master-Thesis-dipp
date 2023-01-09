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

package magma.exa.base.text;

import magma.exa.base.Narrow;
import magma.exa.base.contract.Require;
import magma.exa.control.function.Fn1;
import magma.exa.data.index.Range;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.StringJoiner;

/**
 *
 */
public interface Text extends CharSequence {

    static Text of() {
        return new BaseText();
    }

    static Text of(final String val) {
        return new BaseText().append(val);
    }

    /**
     * Returns the length.
     */
    @Override int length();


    boolean isPresent();
    boolean isEmpty();
    int last();

    // ----------------------------------------------------------

    /**
     * Returns the index within this string of the first occurrence of
     * the given character. If a character with value occurs in the text,
     * then the index of the first such occurrence is returned.
     *
     * @param ch character.
     * @return index of the first occurrence, otherwise -1.
     */
    int indexOf(int ch);

    /**
     * Returns the index within this string of the first occurrence of the
     * given character. If a character with value occurs in the represented
     * character sequence with an index greater than or equal to the given
     * lower index-bound, then the index of the first such occurrence is
     * returned.
     *
     * @param ch character.
     * @param lo index to start the search from.
     * @return index of the first occurrence, otherwise -1.
     */
    int indexOf(int ch, int lo);

    /**
     * Returns the index within this string of the last occurrence of the
     * given character. If a character with value occurs in the represented
     * character sequence, then the index of the last such occurrence is
     * returned.
     *
     * @param ch character.
     * @return index of the last occurrence, otherwise -1.
     */
    int lastIndexOf(int ch);

    /**
     * Returns the index within this string of the last occurrence of the
     * given character. If a character with value occurs in the represented
     * character sequence with an index greater than or equal to the given
     * lower index-bound, then the index of the last such occurrence is
     * returned.
     *
     * @param ch character.
     * @param lo index to start the search from.
     * @return index of the last occurrence, otherwise -1.
     */
    int lastIndexOf(int ch, int lo);

    // ----------------------------------------------------------

    /// APPEND.

    /**
     * Prepends the string representation of the given {@code boolean} value
     * in this text.
     *
     * @param val value whose string representation is to be inserted.
     * @return 'this' text.
     */
    Text prepend(boolean val);

    /**
     * Prepends the given {@code char} value in this text.
     *
     * @param val value whose string representation is to be inserted.
     * @return 'this' text.
     */
    Text prepend(char val);

    /**
     * Prepends the string representation of the given {@code int} value
     * in this text.
     *
     * @param val value whose string representation is to be inserted.
     * @return 'this' text.
     */
    Text prepend(int val);

    /**
     * Prepends the string representation of the given {@code long} value
     * in this text.
     *
     * @param val value whose string representation is to be inserted.
     * @return 'this' text.
     */
    Text prepend(long val);

    /**
     * Prepends the string representation of the given {@code float} value
     * in this text.
     *
     * @param val whose string representation is to be inserted.
     * @return 'this' text.
     */
    Text prepend(float val);

    /**
     * Prepends the string representation of the given {@code double} value
     * in this text.
     *
     * @param val value whose string representation is to be inserted.
     * @return 'this' text.
     */
    Text prepend(double val);

    // ----------------------------------------------------------

    /**
     * Prepends the string representation of the given {@code boolean[]} array
     * in this text.
     *
     * @param ary array whose string representation is to be inserted.
     * @return 'this' text.
     */
    Text prepend(boolean[] ary);

    /**
     * Prepends the string representation of the given {@code byte[]} array
     * in this text.
     *
     * @param ary array whose string representation is to be inserted.
     * @return 'this' text.
     */
    Text prepend(byte[] ary);

    /**
     * Prepends the string representation of the given {@code short[]} array
     * in this text.
     *
     * @param ary array whose string representation is to be inserted.
     * @return 'this' text.
     */
    Text prepend(short[] ary);

    /**
     * Prepends the string representation of the given {@code char[]} array
     * in this text.
     *
     * @param ary array whose string representation is to be inserted.
     * @return 'this' text.
     */
    Text prepend(char[] ary);

    /**
     * Prepends the string representation of the given {@code int[]} array
     * in this text.
     *
     * @param ary array whose string representation is to be inserted.
     * @return 'this' text.
     */
    Text prepend(int[] ary);

    /**
     * Prepends the string representation of the given {@code long[]} array
     * in this text.
     *
     * @param ary array whose string representation is to be inserted.
     * @return 'this' text.
     */
    Text prepend(long[] ary);

    /**
     * Prepends the string representation of the given {@code float[]} array
     * in this text.
     *
     * @param ary array whose string representation is to be inserted.
     * @return 'this' text.
     */
    Text prepend(float[] ary);

    /**
     * Prepends the string representation of the given {@code double[]} array
     * in this text.
     *
     * @param ary array whose string representation is to be inserted.
     * @return 'this' text.
     */
    Text prepend(double[] ary);

    /**
     * Prepends the string representation of the given {@code Object[]} array
     * in this text.
     *
     * @param ary array whose string representation is to be inserted.
     * @return 'this' text.
     */
    Text prepend(Object... ary);

    // ----------------------------------------------------------

    /**
     * Prepends the string representation of the given {@link Iterable}
     * in this text.
     *
     * @param itr iterable whose text representation is to be inserted.
     * @return 'this' text.
     */
    Text prepend(Iterable<?> itr);

    /**
     * Prepends the given character sequence in this text.
     *
     * @param csq character sequence to be inserted.
     * @return 'this' text.
     */
    Text prepend(CharSequence csq);

    /**
     * Prepends the formatted string using the specified format string
     * and arguments in this text.
     *
     * @param format A <a href="../util/Formatter.html#syntax">format string</a>
     * @param args   arguments referenced by the format specifiers in the format
     *               string.  If there are more arguments than format specifiers,
     *               the extra arguments are ignored.  The number of arguments is
     *               variable and may be zero.  The maximum number of arguments is
     *               limited by the maximum dimension of a Java array.
     * @return 'this' text.
     */
    Text prepend(String format, Object... args);

    /**
     * Prepends the string representation of the given object
     * in this text.
     *
     * @param obj object whose string representation is to be inserted.
     * @return 'this' text.
     */
    Text prepend(Object obj);

    // ----------------------------------------------------------

    /// APPEND.

    /**
     * Appends the ' ' character.
     *
     * @return 'this' text.
     */
    Text __();

    /**
     * Appends the string representation of the given {@code boolean} value
     * in this text.
     *
     * @param val value whose string representation is to be inserted.
     * @return 'this' text.
     */
    Text append(boolean val);

    /**
     * Appends the given {@code char} value in this text.
     *
     * @param val value whose string representation is to be inserted.
     * @return 'this' text.
     */
    Text append(char val);

    /**
     * Appends the string representation of the given {@code int} value
     * in this text.
     *
     * @param val value whose string representation is to be inserted.
     * @return 'this' text.
     */
    Text append(int val);

    /**
     * Appends the string representation of the given {@code long} value
     * in this text.
     *
     * @param val value whose string representation is to be inserted.
     * @return 'this' text.
     */
    Text append(long val);

    /**
     * Appends the string representation of the given {@code float} value
     * in this text.
     *
     * @param val whose string representation is to be inserted.
     * @return 'this' text.
     */
    Text append(float val);

    /**
     * Appends the string representation of the given {@code double} value
     * in this text.
     *
     * @param val value whose string representation is to be inserted.
     * @return 'this' text.
     */
    Text append(double val);

    // ----------------------------------------------------------

    /**
     * Appends the string representation of the given {@code boolean[]} array
     * in this text.
     *
     * @param ary array whose string representation is to be inserted.
     * @return 'this' text.
     */
    Text append(boolean[] ary);

    /**
     * Appends the string representation of the given {@code byte[]} array
     * in this text.
     *
     * @param ary array whose string representation is to be inserted.
     * @return 'this' text.
     */
    Text append(byte[] ary);

    /**
     * Appends the string representation of the given {@code short[]} array
     * in this text.
     *
     * @param ary array whose string representation is to be inserted.
     * @return 'this' text.
     */
    Text append(short[] ary);

    /**
     * Appends the string representation of the given {@code char[]} array
     * in this text.
     *
     * @param ary array whose string representation is to be inserted.
     * @return 'this' text.
     */
    Text append(char[] ary);

    /**
     * Appends the string representation of the given {@code int[]} array
     * in this text.
     *
     * @param ary array whose string representation is to be inserted.
     * @return 'this' text.
     */
    Text append(int[] ary);

    /**
     * Appends the string representation of the given {@code long[]} array
     * in this text.
     *
     * @param ary array whose string representation is to be inserted.
     * @return 'this' text.
     */
    Text append(long[] ary);

    /**
     * Appends the string representation of the given {@code float[]} array
     * in this text.
     *
     * @param ary array whose string representation is to be inserted.
     * @return 'this' text.
     */
    Text append(float[] ary);

    /**
     * Appends the string representation of the given {@code double[]} array
     * in this text.
     *
     * @param ary array whose string representation is to be inserted.
     * @return 'this' text.
     */
    Text append(double[] ary);

    /**
     * Appends the string representation of the given {@code Object[]} array
     * in this text.
     *
     * @param ary array whose string representation is to be inserted.
     * @return 'this' text.
     */
    Text append(Object... ary);

    // ----------------------------------------------------------

    /**
     * Appends the string representation of the given {@link Iterable}
     * in this text.
     *
     * @param itr iterable whose text representation is to be inserted.
     * @return 'this' text.
     */
    Text append(Iterable<?> itr);

    /**
     * Appends the given character sequence in this text.
     *
     * @param csq character sequence to be inserted.
     * @return 'this' text.
     */
    Text append(CharSequence csq);

    /**
     * Appends the formatted string using the specified format string
     * and arguments in this text.
     *
     * @param format A <a href="../util/Formatter.html#syntax">format string</a>
     * @param args   arguments referenced by the format specifiers in the format
     *               string.  If there are more arguments than format specifiers,
     *               the extra arguments are ignored.  The number of arguments is
     *               variable and may be zero.  The maximum number of arguments is
     *               limited by the maximum dimension of a Java array.
     * @return 'this' text.
     */
    Text append(String format, Object... args);

    /**
     * Appends the string representation of the given object
     * in this text.
     *
     * @param obj object whose string representation is to be inserted.
     * @return 'this' text.
     */
    Text append(Object obj);

    // ----------------------------------------------------------

    /// INSERTION.

    /**
     * Inserts the ' ' character at the given position.
     *
     * @return 'this' text.
     */
    Text __(int pos);

    /**
     * Inserts the string representation of the given {@code boolean} value
     * at the given position in this text.
     *
     * @param pos insertion position of the string representation.
     * @param val value whose string representation is to be inserted.
     * @return 'this' text.
     */
    Text insertAt(long pos, boolean val);

    /**
     * Inserts the given {@code char} value at the given position in this text.
     *
     * @param pos insertion position of the string representation.
     * @param val value whose string representation is to be inserted.
     * @return 'this' text.
     */
    Text insertAt(long pos, char val);

    /**
     * Inserts the string representation of the given {@code int} value
     * at the given position in this text.
     *
     * @param pos insertion position of the string representation.
     * @param val value whose string representation is to be inserted.
     * @return 'this' text.
     */
    Text insertAt(long pos, int val);

    /**
     * Inserts the string representation of the given {@code long} value
     * at the given position in this text.
     *
     * @param pos insertion position of the string representation.
     * @param val value whose string representation is to be inserted.
     * @return 'this' text.
     */
    Text insertAt(long pos, long val);

    /**
     * Inserts the string representation of the given {@code float} value
     * at the given position in this text.
     *
     * @param pos insertion position of the string representation.
     * @param val whose string representation is to be inserted.
     * @return 'this' text.
     */
    Text insertAt(long pos, float val);

    /**
     * Inserts the string representation of the given {@code double} value
     * at the given position in this text.
     *
     * @param pos insertion position of the string representation.
     * @param val value whose string representation is to be inserted.
     * @return 'this' text.
     */
    Text insertAt(long pos, double val);

    // ----------------------------------------------------------

    /**
     * Inserts the string representation of the given {@code boolean[]} array
     * at the given position in this text.
     *
     * @param pos insertion position of the string representation.
     * @param ary array whose string representation is to be inserted.
     * @return 'this' text.
     */
    Text insertAt(long pos, boolean[] ary);

    /**
     * Inserts the string representation of the given {@code byte[]} array
     * at the given position in this text.
     *
     * @param pos insertion position of the string representation.
     * @param ary array whose string representation is to be inserted.
     * @return 'this' text.
     */
    Text insertAt(long pos, byte[] ary);

    /**
     * Inserts the string representation of the given {@code short[]} array
     * at the given position in this text.
     *
     * @param pos insertion position of the string representation.
     * @param ary array whose string representation is to be inserted.
     * @return 'this' text.
     */
    Text insertAt(long pos, short[] ary);

    /**
     * Inserts the string representation of the given {@code char[]} array
     * at the given position in this text.
     *
     * @param pos insertion position of the string representation.
     * @param ary array whose string representation is to be inserted.
     * @return 'this' text.
     */
    Text insertAt(long pos, char[] ary);

    /**
     * Inserts the string representation of the given {@code int[]} array
     * at the given position in this text.
     *
     * @param pos insertion position of the string representation.
     * @param ary array whose string representation is to be inserted.
     * @return 'this' text.
     */
    Text insertAt(long pos, int[] ary);

    /**
     * Inserts the string representation of the given {@code long[]} array
     * at the given position in this text.
     *
     * @param pos insertion position of the string representation.
     * @param ary array whose string representation is to be inserted.
     * @return 'this' text.
     */
    Text insertAt(long pos, long[] ary);

    /**
     * Inserts the string representation of the given {@code float[]} array
     * at the given position in this text.
     *
     * @param pos insertion position of the string representation.
     * @param ary array whose string representation is to be inserted.
     * @return 'this' text.
     */
    Text insertAt(long pos, float[] ary);

    /**
     * Inserts the string representation of the given {@code double[]} array
     * at the given position in this text.
     *
     * @param pos insertion position of the string representation.
     * @param ary array whose string representation is to be inserted.
     * @return 'this' text.
     */
    Text insertAt(long pos, double[] ary);

    /**
     * Inserts the string representation of the given {@code Object[]} array
     * at the given position in this text.
     *
     * @param pos insertion position of the string representation.
     * @param ary array whose string representation is to be inserted.
     * @return 'this' text.
     */
    Text insertAt(long pos, Object... ary);

    // ----------------------------------------------------------

    /**
     * Inserts the string representation of the given {@link Iterable}
     * at the given position in this text.
     *
     * @param pos insertion position of the string representation.
     * @param itr iterable whose text representation is to be inserted.
     * @return 'this' text.
     */
    Text insertAt(long pos, Iterable<?> itr);

    /**
     * Inserts the given character sequence at the given position in this text.
     *
     * @param pos insertion position of the string representation.
     * @param csq character sequence to be inserted.
     * @return 'this' text.
     */
    Text insertAt(long pos, CharSequence csq);

    /**
     * Inserts the formatted string using the specified format string
     * and arguments at the given position in this text.
     *
     * @param pos    insertion position of the string representation.
     * @param format A <a href="../util/Formatter.html#syntax">format string</a>
     * @param args   arguments referenced by the format specifiers in the format
     *               string.  If there are more arguments than format specifiers,
     *               the extra arguments are ignored.  The number of arguments is
     *               variable and may be zero.  The maximum number of arguments is
     *               limited by the maximum dimension of a Java array.
     * @return 'this' text.
     */
    Text insertAt(long pos, String format, Object... args);

    /**
     * Inserts the string representation of the given object
     * at the given position in this text.
     *
     * @param pos insertion position of the string representation.
     * @param obj object whose string representation is to be inserted.
     * @return 'this' text.
     */
    Text insertAt(long pos, Object obj);

    // ----------------------------------------------------------

    /**
     * Surrounds this text with the given open-prefix and close-postfix.
     *
     * @param open prefix used for the front.
     * @param close postfix used for the back.
     * @return 'this' text.
     */
    Text surround(String open, String close);

    // ----------------------------------------------------------

    /**
     * Appends newline.
     */
    Text newline();

    // ----------------------------------------------------------

    /**
     * Removes and returns the first character from this text
     */
    Text removeFirst();

    /**
     * Removes and returns the last character from this text
     */
    Text removeLast();

    /**
     * Removes the character located at the given index.
     *
     * @param idx index of the character to be deleted.
     * @return 'this' text.
     */
    Text removeAt(int idx);

    /**
     * TODO
     */
    Text removeRange(int lo, int hi);

    // ----------------------------------------------------------

    /**
     * Clears this text.
     */
    Text clear();

    // ----------------------------------------------------------

    Text color(final Ansi.Color.Front color);

    Text color(final Ansi.Color.Front color, Range range);

    Text style(final Ansi.Style style);

    Text style(final Ansi.Style style, Range range);

    // ----------------------------------------------------------

    /**
     *
     */
    @SuppressWarnings("NullableProblems")
    final class BaseText implements Text {
        private final StringBuilder sb;
        private final LinkedList<Fn1<String, String>> st;

        BaseText() {
            sb = new StringBuilder();
            st = new LinkedList<>();
        }

        @Override
        public int length() {
            return sb.length();
        }

        @Override
        public boolean isPresent() {
            return sb.length() > 0;
        }

        @Override
        public boolean isEmpty() {
            return sb.length() == 0;
        }

        @Override
        public int last() {
            return Math.max(0, length() - 1);
        }

        @Override
        public char charAt(final int idx) {
            return sb.charAt(idx);
        }

        @Override
        public CharSequence subSequence(final int lo, final int hi) {
            return toString().subSequence(lo, hi);
        }

        // ----------------------------------------------------------

        @Override
        public int indexOf(final int ch) {
            return sb.indexOf(String.valueOf((char) ch));
        }

        @Override
        public int indexOf(final int ch, final int lo) {
            return sb.indexOf(String.valueOf((char) ch), lo);
        }

        @Override
        public int lastIndexOf(final int ch) {
            return sb.lastIndexOf(String.valueOf((char) ch));
        }

        @Override
        public int lastIndexOf(final int ch, final int lo) {
            return sb.lastIndexOf(String.valueOf((char) ch), lo);
        }

        // ----------------------------------------------------------

        @Override public Text prepend(final boolean val)                         { return insertAt(0, val); }

        @Override public Text prepend(final char val)                            { return insertAt(0, val); }

        @Override public Text prepend(final int val)                             { return insertAt(0, val); }

        @Override public Text prepend(final long val)                            { return insertAt(0, val); }

        @Override public Text prepend(final float val)                           { return insertAt(0, val); }

        @Override public Text prepend(final double val)                          { return insertAt(0, val); }

        @Override public Text prepend(final boolean[] ary)                       { return insertAt(0, ary); }

        @Override public Text prepend(final byte[] ary)                          { return insertAt(0, ary); }

        @Override public Text prepend(final short[] ary)                         { return insertAt(0, ary); }

        @Override public Text prepend(final char[] ary)                          { return insertAt(0, ary); }

        @Override public Text prepend(final int[] ary)                           { return insertAt(0, ary); }

        @Override public Text prepend(final long[] ary)                          { return insertAt(0, ary); }

        @Override public Text prepend(final float[] ary)                         { return insertAt(0, ary); }

        @Override public Text prepend(final double[] ary)                        { return insertAt(0, ary); }

        @Override public Text prepend(final Object... ary)                       { return insertAt(0, ary); }

        @Override public Text prepend(final Iterable<?> itr)                     { return insertAt(0, itr); }

        @Override public Text prepend(final CharSequence csq)                    { return insertAt(0, csq); }

        @Override public Text prepend(final String format, final Object... args) { return insertAt(0, format, args); }

        @Override public Text prepend(final Object obj)                          { return insertAt(0, obj); }

        // ----------------------------------------------------------

        @Override public Text __()                                               { return append(' '); }

        @Override public Text append(final boolean val)                         { return insertAt(length(), val); }

        @Override public Text append(final char val)                            { return insertAt(length(), val); }

        @Override public Text append(final int val)                             { return insertAt(length(), val); }

        @Override public Text append(final long val)                            { return insertAt(length(), val); }

        @Override public Text append(final float val)                           { return insertAt(length(), val); }

        @Override public Text append(final double val)                          { return insertAt(length(), val); }

        @Override public Text append(final boolean[] ary)                       { return insertAt(length(), ary); }

        @Override public Text append(final byte[] ary)                          { return insertAt(length(), ary); }

        @Override public Text append(final short[] ary)                         { return insertAt(length(), ary); }

        @Override public Text append(final char[] ary)                          { return insertAt(length(), ary); }

        @Override public Text append(final int[] ary)                           { return insertAt(length(), ary); }

        @Override public Text append(final long[] ary)                          { return insertAt(length(), ary); }

        @Override public Text append(final float[] ary)                         { return insertAt(length(), ary); }

        @Override public Text append(final double[] ary)                        { return insertAt(length(), ary); }

        @Override public Text append(final Object... ary)                       { return insertAt(length(), ary); }

        @Override public Text append(final Iterable<?> itr)                     { return insertAt(length(), itr); }

        @Override public Text append(final CharSequence csq)                    { return insertAt(length(), csq); }

        @Override public Text append(final String format, final Object... args) { return insertAt(length(), format, args); }

        @Override public Text append(final Object obj)                          { return insertAt(length(), obj); }

        // ----------------------------------------------------------

        @Override
        public Text __(final int pos) {
            insertAt(pos, ' ');
            return this;
        }

        @Override
        public Text insertAt(final long pos, final boolean val) {
            sb.insert(Require.position(Narrow.I32(pos), length()), val);
            return this;
        }

        @Override
        public Text insertAt(final long pos, final char val) {
            sb.insert(Require.position(Narrow.I32(pos), length()), val);
            return this;
        }

        @Override
        public Text insertAt(final long pos, final int val) {
            sb.insert(Require.position(Narrow.I32(pos), length()), val);
            return this;
        }

        @Override
        public Text insertAt(final long pos, final long val) {
            sb.insert(Require.position(Narrow.I32(pos), length()), val);
            return this;
        }

        @Override
        public Text insertAt(final long pos, final float val) {
            sb.insert(Require.position(Narrow.I32(pos), length()), val);
            return this;
        }

        @Override
        public Text insertAt(final long pos, final double val) {
            sb.insert(Require.position(Narrow.I32(pos), length()), val);
            return this;
        }

        // ----------------------------------------------------------

        @Override
        public Text insertAt(final long pos, final boolean[] ary) {
            sb.insert(Require.position(Narrow.I32(pos), length()), Arrays.toString(ary));
            return this;
        }

        @Override
        public Text insertAt(final long pos, final byte[] ary) {
            sb.insert(Require.position(Narrow.I32(pos), length()), Arrays.toString(ary));
            return this;
        }

        @Override
        public Text insertAt(final long pos, final short[] ary) {
            sb.insert(Require.position(Narrow.I32(pos), length()), Arrays.toString(ary));
            return this;
        }

        @Override
        public Text insertAt(final long pos, final char[] ary) {
            sb.insert(Require.position(Narrow.I32(pos), length()), Arrays.toString(ary));
            return this;
        }

        @Override
        public Text insertAt(final long pos, final int[] ary) {
            sb.insert(Require.position(Narrow.I32(pos), length()), Arrays.toString(ary));
            return this;
        }

        @Override
        public Text insertAt(final long pos, final long[] ary) {
            sb.insert(Require.position(Narrow.I32(pos), length()), Arrays.toString(ary));
            return this;
        }

        @Override
        public Text insertAt(final long pos, final float[] ary) {
            sb.insert(Require.position(Narrow.I32(pos), length()), Arrays.toString(ary));
            return this;
        }

        @Override
        public Text insertAt(final long pos, final double[] ary) {
            sb.insert(Require.position(Narrow.I32(pos), length()), Arrays.toString(ary));
            return this;
        }

        @Override
        public Text insertAt(final long pos, final Object... ary) {
            sb.insert(Require.position(Narrow.I32(pos), length()), Arrays.toString(ary));
            return this;
        }

        // ----------------------------------------------------------

        @Override
        public Text insertAt(final long pos, final Iterable<?> itr) {
            final var tmp = new StringJoiner(", ", "(", ")");
            itr.forEach(x -> tmp.add(String.valueOf(x)));
            sb.insert(Require.position(Narrow.I32(pos), length()), tmp);
            return this;
        }

        @Override
        public Text insertAt(final long pos, final CharSequence csq) {
            sb.insert(Require.position(Narrow.I32(pos), length()), csq);
            return this;
        }

        @Override
        public Text insertAt(final long pos, final String format, final Object... args) {
            sb.insert(Require.position(Narrow.I32(pos), length()), String.format(format, args));
            return this;
        }

        @Override
        public Text insertAt(final long pos, final Object obj) {
            sb.insert(Require.position(Narrow.I32(pos), length()), obj);
            return this;
        }

        // ----------------------------------------------------------

        @Override
        public Text surround(final String open, final String close) {
            return prepend(open).append(close);
        }

        // ----------------------------------------------------------

        @Override
        public Text newline() {
            sb.append('\n');
            return this;
        }

        // ----------------------------------------------------------

        @Override
        public Text removeFirst() {
            if (isPresent()) {
                sb.deleteCharAt(0);
            }
            return this;
        }

        @Override
        public Text removeLast() {
            if (isPresent()) {
                sb.deleteCharAt(last());
            }
            return this;
        }

        @Override
        public Text removeAt(int idx) {
            sb.deleteCharAt(idx);
            return this;
        }

        @Override
        public Text removeRange(int lo, int hi) {
            sb.delete(lo, hi);
            return this;
        }

        @Override
        public Text clear() {
            sb.setLength(0);
            return this;
        }

        // ----------------------------------------------------------

        @Override
        public Text color(final Ansi.Color.Front color) {
            return color(color, Range.of(0, sb.length()));
        }

        @Override
        public Text color(final Ansi.Color.Front color, final Range range) {
            st.push(Ansi.encoder().apply(color, Ansi.Color.Back.IGNORE, Ansi.Style.IGNORE, range));
            return this;
        }

        @Override
        public Text style(final Ansi.Style style) {
            return style(style, Range.of(0, sb.length()));
        }

        @Override
        public Text style(final Ansi.Style style, final Range range) {
            st.push(Ansi.encoder().apply(Ansi.Color.Front.IGNORE, Ansi.Color.Back.IGNORE, style, range));
            return this;
        }

        // ----------------------------------------------------------

        @Override
        public String toString() {
            String txt = sb.toString();
            for (var coder : st) {
                txt = coder.apply(txt);
            }
            return txt;
        }
    }

    // ----------------------------------------------------------
    //
    // ----------------------------------------------------------

    interface Formatter<T> extends Fn1<T, Text> {

    }
}