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

import magma.exa.adt.mixin.Constant;
import magma.exa.control.function.*;
import magma.exa.data.index.Range;

import java.util.StringJoiner;

/**
 * ANSI ESCAPE SEQUENCES
 * Standard escape codes are prefixed with Escape:
 *
 *  Unicode('\u001b') | OCT('\033') | HEX('\x1B') | DEC('27')
 *
 * Followed by the command, sometimes delimited by opening square
 * bracket ('['), known as a Control Sequence Introducer (CSI),
 * optionally followed by arguments and the command itself.
 * Arguments are delimited by semicolon (;).
 */
public interface Ansi<E extends Enum<E> & Ansi<E>> extends Constant<E> {

    /**
     * Returns ansi code.
     */
    int code();

    // ----------------------------------------------------------
    //  ANSI.COLOR
    // ----------------------------------------------------------

    /**
     * COLOR CODES
     * Most terminals support 8 and 16 colors, as well as 256 (8-bit)
     * colors. These colors are set by the user, but have commonly
     * defined meanings.
     *
     *    ANSI-COLOR | FRONT | BACK
     *       black   |  30   |  40
     *       red	 |  31   |  41
     *       green   |  32   |  42
     *       yellow  |  33   |  43
     *       blue    |  34   |  44
     *       magenta |  35   |  45
     *       cyan    |  36   |  46
     *       white   |  37   |  47
     *       default |  39   |  49
     *       reset   |  0    |  0
     *
     * Note: the Reset color is the reset code that resets all colors
     * and text effects, Use Default color to reset colors only. Most
     * terminals, apart from the basic set of 8 colors, also support
     * the "bright" or "bold" colors. These have their own set of codes,
     * mirroring the normal colors, but with an additional ;1 in their
     * codes:
     */
    interface Color<E extends Enum<E> & Ansi<E>> extends Ansi<E> {

        enum Front implements Color<Front> {
            BLACK, RED, GREEN, YELLOW, BLUE, MAGENTA, CYAN, WHITE, IGNORE
            ;
            @Override public int code() {
                return 30 + ordinal();
            }
        }

        enum Back implements Color<Back> {
            BLACK, RED, GREEN, YELLOW, BLUE, MAGENTA, CYAN, WHITE, IGNORE
            ;
            @Override public int code() {
                return 10 + ordinal();
            }
        }
    }

    // ----------------------------------------------------------
    //  ANSI.STYLE
    // ----------------------------------------------------------

    /**
     * GRAPHIC MODES
     * Some terminals may not support some declared styles.
     *
     *    ANSI-STYLE | CODE | DESCRIPTION
     *      BOLD     |  1   |  bold
     *      ITALIC   |  3   |  italic
     *      ULINE1   |  4   |  underline
     *      ULINE2   |  21  |  double underline
     *      INVERT   |  7   |  inverse/reverse
     *      CROSSED  |  9   |  strikethrough
     *      FRAMED   |  51  |
     */
    enum Style implements Ansi<Style> {
        BOLD(1),
        ITALIC(3),
        ULINE1(4),
        ULINE2(21),
        INVERT(7),
        CROSSED(9),
        FRAMED(51),
        IGNORE(-1)
        ;
        public final int code;
        Style(final int code) {
            this.code = code;
        }
        public int code() {
            return code;
        }
    }



    interface Encoder extends Fn5<Color.Front, Color.Back, Style, Range, String, String> {

    }

    // ----------------------------------------------------------

    static Fn2<String, Range, String> apply(final Style style) {
        return apply(Color.Front.IGNORE, Color.Back.IGNORE, style);
    }

    static Fn2<String, Range, String> apply(final Color.Front front) {
        return apply(front, Color.Back.IGNORE, Style.IGNORE);
    }

    static Fn2<String, Range, String> apply(final Color.Front front, final Style style) {
        return apply(front, Color.Back.IGNORE, style);
    }

    static Fn2<String, Range, String> apply(final Color.Front front, final Color.Back back, final Style style) {
        return (input, range) -> Ansi.encoder().onApply(front, back, style, range, input);
    }

    static Encoder encoder() {
        enum StringEncoder implements Encoder {
            instance;
            @Override
            public String onApply(final Color.Front front,
                                  final Color.Back  back,
                                  final Style       style,
                                  final Range       range,
                                  final String      input) throws Throwable
            {
                final var prefix = new StringJoiner(";", "\033[", "m");

                if (front != Color.Front.IGNORE) {
                    prefix.add(String.valueOf(front.code()));
                }

                if (back != Color.Back.IGNORE) {
                    prefix.add(String.valueOf(back.code()));
                }

                if (style != Style.IGNORE) {
                    prefix.add(String.valueOf(style.code));
                }

                final var lo  = Range.lo(range);
                final var hi  = Math.min(Range.hi(range), input.length());
                final var section = input.substring(lo, hi);
                return input.replace(section, prefix + section + "\033[0m");
            }
        }
        return StringEncoder.instance;
    }
}
