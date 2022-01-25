package utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Formatter;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.function.IntConsumer;

import static com.google.common.base.Preconditions.checkState;

// ------------------------------------------------------------
//                       STRING UTILS
// ------------------------------------------------------------
//
public enum StrUtils {
    ;

    // ----------------------------------------------
    //  COLORS.
    // ----------------------------------------------

    public enum Color implements Function<String, String> {
        RESET                     ("\033[0m"),

        BLACK                     ("\033[0;30m"),
        RED                       ("\033[0;31m"),
        GREEN                     ("\033[0;32m"),
        YELLOW                    ("\033[0;33m"),
        BLUE                      ("\033[0;34m"),
        PURPLE                    ("\033[0;35m"),
        CYAN                      ("\033[0;36m"),
        WHITE                     ("\033[0;37m"),
        GREY                      ("\u001b[37;m"),

        BLACK_BOLD                ("\033[1;30m"),
        RED_BOLD                  ("\033[1;31m"),
        GREEN_BOLD                ("\033[1;32m"),
        YELLOW_BOLD               ("\033[1;33m"),
        BLUE_BOLD                 ("\033[1;34m"),
        PURPLE_BOLD               ("\033[1;35m"),
        CYAN_BOLD                 ("\033[1;36m"),
        WHITE_BOLD                ("\033[1;37m"),

        BLACK_UNDERLINED          ("\033[4;30m"),
        RED_UNDERLINED            ("\033[4;31m"),
        GREEN_UNDERLINED          ("\033[4;32m"),
        YELLOW_UNDERLINED         ("\033[4;33m"),
        BLUE_UNDERLINED           ("\033[4;34m"),
        PURPLE_UNDERLINED         ("\033[4;35m"),
        CYAN_UNDERLINED           ("\033[4;36m"),
        WHITE_UNDERLINED          ("\033[4;37m"),

        BLACK_BACKGROUND          ("\033[40m"),
        RED_BACKGROUND            ("\033[41m"),
        GREEN_BACKGROUND          ("\033[42m"),
        YELLOW_BACKGROUND         ("\033[43m"),
        BLUE_BACKGROUND           ("\033[44m"),
        PURPLE_BACKGROUND         ("\033[45m"),
        CYAN_BACKGROUND           ("\033[46m"),
        WHITE_BACKGROUND          ("\033[47m"),

        BLACK_BRIGHT              ("\033[0;90m"),
        RED_BRIGHT                ("\033[0;91m"),
        GREEN_BRIGHT              ("\033[0;92m"),
        YELLOW_BRIGHT             ("\033[0;93m"),
        BLUE_BRIGHT               ("\033[0;94m"),
        PURPLE_BRIGHT             ("\033[0;95m"),
        CYAN_BRIGHT               ("\033[0;96m"),
        WHITE_BRIGHT              ("\033[0;97m"),

        BLACK_BOLD_BRIGHT         ("\033[1;90m"),
        RED_BOLD_BRIGHT           ("\033[1;91m"),
        GREEN_BOLD_BRIGHT         ("\033[1;92m"),
        YELLOW_BOLD_BRIGHT        ("\033[1;93m"),
        BLUE_BOLD_BRIGHT          ("\033[1;94m"),
        PURPLE_BOLD_BRIGHT        ("\033[1;95m"),
        CYAN_BOLD_BRIGHT          ("\033[1;96m"),
        WHITE_BOLD_BRIGHT         ("\033[1;97m"),

        BLACK_BACKGROUND_BRIGHT   ("\033[0;100m"),
        RED_BACKGROUND_BRIGHT     ("\033[0;101m"),
        GREEN_BACKGROUND_BRIGHT   ("\033[0;102m"),
        YELLOW_BACKGROUND_BRIGHT  ("\033[0;103m"),
        BLUE_BACKGROUND_BRIGHT    ("\033[0;104m"),
        PURPLE_BACKGROUND_BRIGHT  ("\033[0;105m"),
        CYAN_BACKGROUND_BRIGHT    ("\033[0;106m"),
        WHITE_BACKGROUND_BRIGHT   ("\033[0;107m")
        ;

        final String code;
        Color(String _code) {
            code = _code;
        }

        public String apply(String s) {
            return RESET.code + code + s + RESET.code;
        }

        // STATIC FUNCTIONS...

        public static String black(String s)                  { return BLACK.apply(s);  }
        public static String red(String s)                    { return RED.apply(s);    }
        public static String green(String s)                  { return GREEN.apply(s);  }
        public static String yellow(String s)                 { return YELLOW.apply(s); }
        public static String blue(String s)                   { return BLUE.apply(s);   }
        public static String purple(String s)                 { return PURPLE.apply(s); }
        public static String cyan(String s)                   { return CYAN.apply(s);   }
        public static String white(String s)                  { return WHITE.apply(s);  }
        public static String grey(String s)                   { return GREY.apply(s);   }

        public static String boldBlack(String s)              { return BLACK_BOLD.apply(s);  }
        public static String boldRed(String s)                { return RED_BOLD.apply(s);    }
        public static String boldGreen(String s)              { return GREEN_BOLD.apply(s);  }
        public static String boldYellow(String s)             { return YELLOW_BOLD.apply(s); }
        public static String boldBlue(String s)               { return BLUE_BOLD.apply(s);   }
        public static String boldPurple(String s)             { return PURPLE_BOLD.apply(s); }
        public static String boldCyan(String s)               { return CYAN_BOLD.apply(s);   }
        public static String boldWhite(String s)              { return WHITE_BOLD.apply(s);  }

        public static String underlinedBlack(String s)        { return BLACK_UNDERLINED.apply(s);  }
        public static String underlinedRed(String s)          { return RED_UNDERLINED.apply(s);    }
        public static String underlinedGreen(String s)        { return GREEN_UNDERLINED.apply(s);  }
        public static String underlinedYellow(String s)       { return YELLOW_UNDERLINED.apply(s); }
        public static String underlinedBlue(String s)         { return BLUE_UNDERLINED.apply(s);   }
        public static String underlinedPurple(String s)       { return PURPLE_UNDERLINED.apply(s); }
        public static String underlinedCyan(String s)         { return CYAN_UNDERLINED.apply(s);   }
        public static String underlinedWhite(String s)        { return WHITE_UNDERLINED.apply(s);  }

        public static String backgroundBlack(String s)        { return BLACK_BACKGROUND.apply(s);  }
        public static String backgroundRed(String s)          { return RED_BACKGROUND.apply(s);    }
        public static String backgroundGreen(String s)        { return GREEN_BACKGROUND.apply(s);  }
        public static String backgroundYellow(String s)       { return YELLOW_BACKGROUND.apply(s); }
        public static String backgroundBlue(String s)         { return BLUE_BACKGROUND.apply(s);   }
        public static String backgroundPurple(String s)       { return PURPLE_BACKGROUND.apply(s); }
        public static String backgroundCyan(String s)         { return CYAN_BACKGROUND.apply(s);   }
        public static String backgroundWhite(String s)        { return WHITE_BACKGROUND.apply(s);  }

        public static String brightBlack(String s)            { return BLACK_BRIGHT.apply(s);  }
        public static String brightRed(String s)              { return RED_BRIGHT.apply(s);    }
        public static String brightGreen(String s)            { return GREEN_BRIGHT.apply(s);  }
        public static String brightYellow(String s)           { return YELLOW_BRIGHT.apply(s); }
        public static String brightBlue(String s)             { return BLUE_BRIGHT.apply(s);   }
        public static String brightPurple(String s)           { return PURPLE_BRIGHT.apply(s); }
        public static String brightCyan(String s)             { return CYAN_BRIGHT.apply(s);   }
        public static String brightWhite(String s)            { return WHITE_BRIGHT.apply(s);  }

        public static String boldBrightBlack(String s)        { return BLACK_BOLD_BRIGHT.apply(s);  }
        public static String boldBrightRed(String s)          { return RED_BOLD_BRIGHT.apply(s);    }
        public static String boldBrightGreen(String s)        { return GREEN_BOLD_BRIGHT.apply(s);  }
        public static String boldBrightYellow(String s)       { return YELLOW_BOLD_BRIGHT.apply(s); }
        public static String boldBrightBlue(String s)         { return BLUE_BOLD_BRIGHT.apply(s);   }
        public static String boldBrightPurple(String s)       { return PURPLE_BOLD_BRIGHT.apply(s); }
        public static String boldBrightCyan(String s)         { return CYAN_BOLD_BRIGHT.apply(s);   }
        public static String boldBrightWhite(String s)        { return WHITE_BOLD_BRIGHT.apply(s);  }

        public static String backgroundBrightBlack(String s)  { return BLACK_BACKGROUND_BRIGHT.apply(s);  }
        public static String backgroundBrightRed(String s)    { return RED_BACKGROUND_BRIGHT.apply(s);    }
        public static String backgroundBrightGreen(String s)  { return GREEN_BACKGROUND_BRIGHT.apply(s);  }
        public static String backgroundBrightYellow(String s) { return YELLOW_BACKGROUND_BRIGHT.apply(s); }
        public static String backgroundBrightBlue(String s)   { return BLUE_BACKGROUND_BRIGHT.apply(s);   }
        public static String backgroundBrightPurple(String s) { return PURPLE_BACKGROUND_BRIGHT.apply(s); }
        public static String backgroundBrightCyan(String s)   { return CYAN_BACKGROUND_BRIGHT.apply(s);   }
        public static String backgroundBrightWhite(String s)  { return WHITE_BACKGROUND_BRIGHT.apply(s);  }


        public static String black(Number n)                  { return BLACK.apply(n.toString());  }
        public static String red(Number n)                    { return RED.apply(n.toString());    }
        public static String green(Number n)                  { return GREEN.apply(n.toString());  }
        public static String yellow(Number n)                 { return YELLOW.apply(n.toString()); }
        public static String blue(Number n)                   { return BLUE.apply(n.toString());   }
        public static String purple(Number n)                 { return PURPLE.apply(n.toString()); }
        public static String cyan(Number n)                   { return CYAN.apply(n.toString());   }
        public static String white(Number n)                  { return WHITE.apply(n.toString());  }
        public static String grey(Number n)                   { return GREY.apply(n.toString());   }

        public static String boldBlack(Number n)              { return BLACK_BOLD.apply(n.toString());  }
        public static String boldRed(Number n)                { return RED_BOLD.apply(n.toString());    }
        public static String boldGreen(Number n)              { return GREEN_BOLD.apply(n.toString());  }
        public static String boldYellow(Number n)             { return YELLOW_BOLD.apply(n.toString()); }
        public static String boldBlue(Number n)               { return BLUE_BOLD.apply(n.toString());   }
        public static String boldPurple(Number n)             { return PURPLE_BOLD.apply(n.toString()); }
        public static String boldCyan(Number n)               { return CYAN_BOLD.apply(n.toString());   }
        public static String boldWhite(Number n)              { return WHITE_BOLD.apply(n.toString());  }

        public static String underlinedBlack(Number n)        { return BLACK_UNDERLINED.apply(n.toString());  }
        public static String underlinedRed(Number n)          { return RED_UNDERLINED.apply(n.toString());    }
        public static String underlinedGreen(Number n)        { return GREEN_UNDERLINED.apply(n.toString());  }
        public static String underlinedYellow(Number n)       { return YELLOW_UNDERLINED.apply(n.toString()); }
        public static String underlinedBlue(Number n)         { return BLUE_UNDERLINED.apply(n.toString());   }
        public static String underlinedPurple(Number n)       { return PURPLE_UNDERLINED.apply(n.toString()); }
        public static String underlinedCyan(Number n)         { return CYAN_UNDERLINED.apply(n.toString());   }
        public static String underlinedWhite(Number n)        { return WHITE_UNDERLINED.apply(n.toString());  }

        public static String backgroundBlack(Number n)        { return BLACK_BACKGROUND.apply(n.toString());  }
        public static String backgroundRed(Number n)          { return RED_BACKGROUND.apply(n.toString());    }
        public static String backgroundGreen(Number n)        { return GREEN_BACKGROUND.apply(n.toString());  }
        public static String backgroundYellow(Number n)       { return YELLOW_BACKGROUND.apply(n.toString()); }
        public static String backgroundBlue(Number n)         { return BLUE_BACKGROUND.apply(n.toString());   }
        public static String backgroundPurple(Number n)       { return PURPLE_BACKGROUND.apply(n.toString()); }
        public static String backgroundCyan(Number n)         { return CYAN_BACKGROUND.apply(n.toString());   }
        public static String backgroundWhite(Number n)        { return WHITE_BACKGROUND.apply(n.toString());  }

        public static String brightBlack(Number n)            { return BLACK_BRIGHT.apply(n.toString());  }
        public static String brightRed(Number n)              { return RED_BRIGHT.apply(n.toString());    }
        public static String brightGreen(Number n)            { return GREEN_BRIGHT.apply(n.toString());  }
        public static String brightYellow(Number n)           { return YELLOW_BRIGHT.apply(n.toString()); }
        public static String brightBlue(Number n)             { return BLUE_BRIGHT.apply(n.toString());   }
        public static String brightPurple(Number n)           { return PURPLE_BRIGHT.apply(n.toString()); }
        public static String brightCyan(Number n)             { return CYAN_BRIGHT.apply(n.toString());   }
        public static String brightWhite(Number n)            { return WHITE_BRIGHT.apply(n.toString());  }

        public static String boldBrightBlack(Number n)        { return BLACK_BOLD_BRIGHT.apply(n.toString());  }
        public static String boldBrightRed(Number n)          { return RED_BOLD_BRIGHT.apply(n.toString());    }
        public static String boldBrightGreen(Number n)        { return GREEN_BOLD_BRIGHT.apply(n.toString());  }
        public static String boldBrightYellow(Number n)       { return YELLOW_BOLD_BRIGHT.apply(n.toString()); }
        public static String boldBrightBlue(Number n)         { return BLUE_BOLD_BRIGHT.apply(n.toString());   }
        public static String boldBrightPurple(Number n)       { return PURPLE_BOLD_BRIGHT.apply(n.toString()); }
        public static String boldBrightCyan(Number n)         { return CYAN_BOLD_BRIGHT.apply(n.toString());   }
        public static String boldBrightWhite(Number n)        { return WHITE_BOLD_BRIGHT.apply(n.toString());  }

        public static String backgroundBrightBlack(Number n)  { return BLACK_BACKGROUND_BRIGHT.apply(n.toString());  }
        public static String backgroundBrightRed(Number n)    { return RED_BACKGROUND_BRIGHT.apply(n.toString());    }
        public static String backgroundBrightGreen(Number n)  { return GREEN_BACKGROUND_BRIGHT.apply(n.toString());  }
        public static String backgroundBrightYellow(Number n) { return YELLOW_BACKGROUND_BRIGHT.apply(n.toString()); }
        public static String backgroundBrightBlue(Number n)   { return BLUE_BACKGROUND_BRIGHT.apply(n.toString());   }
        public static String backgroundBrightPurple(Number n) { return PURPLE_BACKGROUND_BRIGHT.apply(n.toString()); }
        public static String backgroundBrightCyan(Number n)   { return CYAN_BACKGROUND_BRIGHT.apply(n.toString());   }
        public static String backgroundBrightWhite(Number n)  { return WHITE_BACKGROUND_BRIGHT.apply(n.toString());  }
    }


    public static final class Str {

        public static Str mkStr() { return new Str(); }

        // ----------------------------------------------

        private StringBuilder sb;
        private String format;
        private int    indent;

        public Str() { this(""); }
        public Str(String s) {
            sb = new StringBuilder();
            sb.append(s);
        }

        // ----------------------------------------------

        public int length() { return sb.length(); }

        public Str length(int len) { sb.setLength(len); return this; }

        public StringBuilder builder() { return sb; }

        public Str clear() { sb.setLength(0); return this; }

        // ----------------------------------------------

        public Str incTab()      { indent++; return this; }

        public Str decTab()      { --indent; return this; }

        public Str incTab(int v) { indent += v; return this; }

        public Str decTab(int v) { indent -= v; return this; }

        public Str newLine() {
            sb.append('\n');
            for (int i = 0; i < indent; ++i)
                sb.append('\t');
            return this;
        }

        // ----------------------------------------------

        public Str delLast()    { return del(sb.length() - 1); }

        public Str del(int pos) { sb.deleteCharAt(pos); return this; }

        // ----------------------------------------------

        public Str add(Collection<?> cs, String sep) {
            for (Object o : cs) {
                add(o.toString()).add(sep);
            }
            final int len = sb.length();
            if (len > sep.length()) {
                sb.delete(len - sep.length(), len);
            }
            return this;
        }

        public Str prepend(String s) {
            return prependStr(s);
        }

        public Str add(String s) {
            return appendStr(s);
        }

        public Str add(String s, Object o) {
            return add(s).add("(").add(o).add(")");
        }

        public Str add(Object o) {
            return appendStr(o != null ? o.toString() : "nil");
        }

        public Str $begin(String fmt) {
            format = fmt;
            return this;
        }

        public Str $end() {
            format = null;
            return this;
        }

        // ----------------------------------------------

        public Str add(byte v) {
            return appendStr(Byte.toString(v));
        }

        public Str add(short v) {
            return appendStr(Short.toString(v));
        }

        public Str add(char v) {
            return appendStr(Character.toString(v));
        }

        public Str add(int v) {
            return appendStr(Integer.toString(v));
        }

        public Str add(long v) {
            return appendStr(Long.toString(v));
        }

        public Str add(boolean v) {
            return appendStr(Boolean.toString(v));
        }

        // ----------------------------------------------

        public Str add(float v) {
            if (Float.isInfinite(v)) sb.append("Float.").append(v > 0 ? "POS_INFINITY" : "NEG_INFINITY");
            else if (Double.isNaN(v)) sb.append("Float.NaN");
            else sb.append(v);
            return this;
        }

        public Str add(double v) {
            if (Double.isInfinite(v)) sb.append("Double.").append(v > 0 ? "POS_INFINITY" : "NEG_INFINITY");
            else if (Double.isNaN(v)) sb.append("Double.NaN");
            else appendStr(Double.toString(v));
            return this;
        }

        // ----------------------------------------------

        public Str asList(Object... vs) {
            return mk(vs.length, "[", "]", ", ", i -> add(vs[i]));
        }

        public Str asList(byte... vs) {
            return mk(vs.length, "[", "]", ", ", i -> add(vs[i]));
        }

        public Str asList(short... vs) {
            return mk(vs.length, "[", "]", ", ", i -> add(vs[i]));
        }

        public Str asList(char... vs) {
            return mk(vs.length, "[", "]", ", ", i -> add(vs[i]));
        }

        public Str asList(int... vs) {
            return mk(vs.length, "[", "]", ", ", i -> add(vs[i]));
        }

        public Str asList(long... vs) {
            return mk(vs.length, "[", "]", ", ", i -> add(vs[i]));
        }

        public Str asList(float... vs) {
            return mk(vs.length, "[", "]", ", ", i -> add(vs[i]));
        }

        public Str asList(double... vs) {
            return mk(vs.length, "[", "]", ", ", i -> add(vs[i]));
        }

        // ----------------------------------------------

        public Str asTuple(Object... vs) {
            return mk(vs.length, "( ", " )", ", ", i -> add(vs[i]));
        }

        public Str asTuple(byte... vs) {
            return mk(vs.length, "( ", " )", ", ", i -> add(vs[i]));
        }

        public Str asTuple(short... vs) {
            return mk(vs.length, "( ", " )", ", ", i -> add(vs[i]));
        }

        public Str asTuple(char... vs) {
            return mk(vs.length, "( ", " )", ", ", i -> add(vs[i]));
        }

        public Str asTuple(int... vs) {
            return mk(vs.length, "( ", " )", ", ", i -> add(vs[i]));
        }

        public Str asTuple(long... vs) {
            return mk(vs.length, "( ", " )", ", ", i -> add(vs[i]));
        }

        public Str asTuple(float... vs) {
            return mk(vs.length, "( ", " )", ", ", i -> add(vs[i]));
        }

        public Str asTuple(double... vs) {
            return mk(vs.length, "( ", " )", ", ", i -> add(vs[i]));
        }


        public Str $black(Object s) {
            return appendStr("\033[0;30m").appendStr(s).appendStr("\033[0m");
        }

        public Str $red(Object s) {
            return appendStr("\033[0;31m").appendStr(s).appendStr("\033[0m");
        }

        public Str $green(Object s) {
            return appendStr("\033[0;32m").appendStr(s).appendStr("\033[0m");
        }

        public Str $yellow(Object s) {
            return appendStr("\033[0;33m").appendStr(s).appendStr("\033[0m");
        }

        public Str $blue(Object s) {
            return appendStr("\033[0;34m").appendStr(s).appendStr("\033[0m");
        }

        public Str $purple(Object s) {
            return appendStr("\033[0;35m").appendStr(s).appendStr("\033[0m");
        }

        public Str $cyan(Object s) {
            return appendStr("\033[0;36m").appendStr(s).appendStr("\033[0m");
        }

        public Str $white(Object s) {
            return appendStr("\033[0;37m").appendStr(s).appendStr("\033[0m");
        }

        public Str $grey(Object s) {
            return appendStr("\u001b[37;m").appendStr(s).appendStr("\033[0m");
        }


        // ----------------------------------------------

        public Str done() {
            return appendStr("\033[0m");
        }

        public Str black() {
            return appendStr("\033[0;30m");
        }

        public Str red() {
            return appendStr("\033[0;31m");
        }

        public Str green() {
            return appendStr("\033[0;32m");
        }

        public Str yellow() {
            return appendStr("\033[0;33m");
        }

        public Str blue() {
            return appendStr("\033[0;34m");
        }

        public Str purple() {
            return appendStr("\033[0;35m");
        }

        public Str cyan() {
            return appendStr("\033[0;36m");
        }

        public Str white() {
            return appendStr("\033[0;37m");
        }

        public Str grey() {
            return appendStr("\u001b[37;m");
        }


        // ----------------------------------------------

        public String toString() {
            return sb.toString();
        }

        // ----------------------------------------------

        private interface F {
            void f();
        }

        private Str appendStr(String s) {
            sb.append(s);
            return this;
        }

        private Str appendStr(Object s) {
            sb.append(s.toString());
            return this;
        }

        private Str prependStr(String s) {
            sb.insert(0, s);
            return this;
        }


        private Str mk(int len, String sep, String beg, String end, IntConsumer cn) {
            appendStr(beg);
            for (int i = 0; i < len; ++i) {
                cn.accept(i);
                if (i < len - 1) {
                    appendStr(sep);
                }
            }
            return appendStr(end);
        }

        // ----------------------------------------------

        public static String Entry(String name, Object val) {
            return Entry(name, val, Color.BLUE_BRIGHT);
        }

        public static String Entry(String name, Object val, Color c) {
            return !"".equals(val) ? (Color.underlinedBlack(name)
                    + '(' + c.apply(val.toString()) + ')') : "()";
        }

        public static String Entry(String name, Object... vals) {
            final StringJoiner sj = new StringJoiner(", ");
            for (Object val : vals) sj.add(Color.brightBlue(val.toString()));
            return !(vals.length == 0) ? (Color.underlinedBlack(name)
                    + '(' + sj.toString() + ')') : "()";
        }
    }

    public static final class TableStr {
        private static final int SPACING = 2;
        private final ArrayList<String[]> rows;
        private final String[] header;
        private final int[] maxCol;
        private int maxLen = 0;
        private int spacing;

        public static TableStr of(String...cols) {
            return new TableStr(cols);
        }
        private TableStr(String...cols) {
            maxCol = new int[cols.length];
            rows = new ArrayList<>();
            spacing = SPACING;
            header = cols;
        }

        public TableStr spacing(int length) {
            spacing = length;
            return this;
        }

        public TableStr append(String...es) {
            checkState(es.length == header.length);
            rows.add(es);
            int len = es.length * spacing;
            for (int i = 0; i < es.length; ++i) {
                final int l = es[i].length();
                maxCol[i] = Math.max(l, Math.max(maxCol[i], header[i].length()));
                len += maxCol[i];
            }
            maxLen = Math.max(len, maxLen);
            return this;
        }

        public TableStr append(Object...os) {
            final String[] es = new String[os.length];
            for (int i = 0; i < os.length; ++i) {
                es[i] = os[i].toString();
            }
            return append(es);
        }

        public String toString() {
            final Str sb = Str.mkStr();
            //sb.add(org.apache.commons.lang3.StringUtils.repeat('-', maxLen)).newLine();
            final StringJoiner fb = new StringJoiner(" ");
            for (int i = 0; i < header.length; ++i) {
                fb.add("%" + (i + 1) + "$-" + (maxCol[i] + SPACING) + "s");
            }
            fb.add("\n");
            final String format = fb.toString();
            final Formatter fm = new Formatter(sb.builder());
            fm.format("# " + format, (Object[]) header);
            //sb.add(org.apache.commons.lang3.StringUtils.repeat('-', maxLen)).newLine();
            for (int i = 0; i < rows.size(); ++i) {
                fm.format("# " + format, (Object[]) rows.get(i));
            }
            return sb.toString();
        }
    }
}
