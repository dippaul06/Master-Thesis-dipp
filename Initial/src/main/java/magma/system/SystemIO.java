package magma.system;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.function.Function;

// ------------------------------------------------------------
//                     SYSTEM STANDARD I/O
// ------------------------------------------------------------
//
public enum SystemIO {
    ;
    // The "standard" output stream. This stream is
    // already open and ready to accept output data.
    public static final ConsoleOutput out = new ConsoleOutput();


    // ----------------------------------------------
    //  CONSOLE OUTPUT.
    // ----------------------------------------------
    //
    public static final class ConsoleOutput extends PrintStream {
        static final ThreadLocal<Font> contexts = new ThreadLocal<>();

        // CONSTRUCTOR.
        ConsoleOutput() {
            super(System.out);
        }

        public void print(boolean x)   { super.print(applyFont(String.valueOf(x))); }
        public void print(char x)      { super.print(applyFont(String.valueOf(x))); }
        public void print(int x)       { super.print(applyFont(String.valueOf(x))); }
        public void print(long x)      { super.print(applyFont(String.valueOf(x))); }
        public void print(float x)     { super.print(applyFont(String.valueOf(x))); }
        public void print(double x)    { super.print(applyFont(String.valueOf(x))); }
        public void print(char[] x)    { super.print(applyFont(String.valueOf(x))); }
        public void print(String x)    { super.print(applyFont(String.valueOf(x))); }
        public void print(Object x)    { super.print(applyFont(String.valueOf(x))); }

        public void println(boolean x) { super.println(applyFont(String.valueOf(x))); }
        public void println(char x)    { super.println(applyFont(String.valueOf(x))); }
        public void println(int x)     { super.println(applyFont(String.valueOf(x))); }
        public void println(long x)    { super.println(applyFont(String.valueOf(x))); }
        public void println(float x)   { super.println(applyFont(String.valueOf(x))); }
        public void println(double x)  { super.println(applyFont(String.valueOf(x))); }
        public void println(char[] x)  { super.println(applyFont(String.valueOf(x))); }
        public void println(String x)  { super.println(applyFont(String.valueOf(x))); }
        public void println(Object x)  { super.println(applyFont(String.valueOf(x))); }

        public ConsoleOutput reset() { return set(null); }
        public ConsoleOutput set(Font font) {
            contexts.set(font);
            return this;
        }

        private String applyFont(final String val) {
            final Font font = contexts.get();
            return (font != null) ? font.apply(val) : val;
        }
    }

    // ----------------------------------------------
    //  CONSOLE INPUT.
    // ----------------------------------------------
    // The "standard" input stream, which is already
    // open and ready to supply input data. Typically
    // this stream corresponds to keyboard input or
    // another input source specified by the host
    // environment or user.
    //
    public static final class ConsoleInput extends InputStream {

        // CONSTRUCTOR.
        private ConsoleInput() {
        }

        public int read() {
            return 0;
        }
    }

    // ----------------------------------------------
    //  ANSI FONT STYLING.
    // ----------------------------------------------

    public enum Style {
        BOLD,
        UNDERLINE,
        INVERSE
    }

    public enum Color {
        BLACK,
        RED,
        GREEN,
        YELLOW,
        BLUE,
        PURPLE,
        CYAN,
        GRAY,

        LIGHT_BLACK,
        LIGHT_RED,
        LIGHT_GREEN,
        LIGHT_YELLOW,
        LIGHT_BLUE,
        LIGHT_PURPLE,
        LIGHT_CYAN,
        LIGHT_GRAY
    }

    public static final class Font implements Function<String, String> {

        public static Font of(Color color, Style...styles) {
            return new Font(color, styles);
        }

        public static Font of(Style...styles) {
            return new Font(Color.BLACK, styles);
        }

        private final Color color;
        private final Style[] styles;

        Font(Color _color, Style[] _styles) {
            color  = _color;
            styles = _styles;
        }

        public String apply(final String val) {
            final Text sb = Text.mkText();
            if (styles.length > 0) {
                for (int i = 0; i < styles.length; ++i) {
                    switch (styles[i]) {
                        case BOLD:      sb.add("1"); break;
                        case UNDERLINE: sb.add("4"); break;
                        case INVERSE:   sb.add("7"); break;
                        default: {
                            throw new UnsupportedOperationException();
                        }
                    }
                    if (i < styles.length - 1) {
                        sb.add(';');
                    }
                }
                sb.add(';');
            }
            switch (color) {
                case BLACK:         sb.add("30"); break;
                case RED:           sb.add("31"); break;
                case GREEN:         sb.add("32"); break;
                case YELLOW:        sb.add("33"); break;
                case BLUE:          sb.add("34"); break;
                case PURPLE:        sb.add("35"); break;
                case CYAN:          sb.add("36"); break;
                case GRAY:          sb.add("37"); break;
                case LIGHT_BLACK:   sb.add("90"); break;
                case LIGHT_RED:     sb.add("91"); break;
                case LIGHT_GREEN:   sb.add("92"); break;
                case LIGHT_YELLOW:  sb.add("93"); break;
                case LIGHT_BLUE:    sb.add("94"); break;
                case LIGHT_PURPLE:  sb.add("95"); break;
                case LIGHT_CYAN:    sb.add("96"); break;
                case LIGHT_GRAY:    sb.add("97"); break;
                default: {
                    throw new UnsupportedOperationException();
                }
            }
            return "\033[" + sb.toString() + "m" + val + "\033[0m";
        }
    }
}