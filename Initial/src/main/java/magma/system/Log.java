package magma.system;

import magma.system.SystemIO.Color;
import magma.system.SystemIO.ConsoleOutput;
import magma.system.SystemIO.Font;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiConsumer;

import static com.google.common.base.Preconditions.checkNotNull;
import static magma.exa.base.contract.Require.isTrue;


// ------------------------------------------------------------
//                         LOG SYSTEM
// ------------------------------------------------------------
// TODO: IMPROVE EXCEPTION LOG WITH STACK-WALKING API
//
public enum Log {

    get("log.out"); // DEFAULT LOG-FILE !

    // ----------------------------------------------

    private static final Font ERROR_STYLE = Font.of(Color.RED, SystemIO.Style.BOLD);

    private static final Font INFO_STYLE = Font.of(Color.GRAY);

    private static final Font WARN_STYLE = Font.of(Color.YELLOW, SystemIO.Style.BOLD);

    private static final Font DEBUG_STYLE = Font.of(Color.PURPLE);

    private static final Font SYSTEM_STYLE = Font.of(Color.BLACK);

    // ----------------------------------------------

    private final LogContext context;

    private LogHandler level;

    // ----------------------------------------------

    Log(final String filePath) {
        context = new LogContext(filePath);
        level = LogHandler.WARN;
    }

    // ----------------------------------------------

    public static void system(final String msg) {
        LogHandler.SYSTEM.accept(msg, null);
    }

    // ----------------------------------------------

    public static void error(final Throwable ex) {
        LogHandler.ERROR.accept("", ex);
    }

    public static void error(final String msg, Object... params) {
        var fmt = String.format(msg, params);
        LogHandler.ERROR.accept(fmt, null);
    }

    public static void error(final String msg) {
        LogHandler.ERROR.accept(msg, null);
    }

    public static void error(final String msg, final Throwable ex) {
        LogHandler.ERROR.accept(msg, ex);
    }

    // ----------------------------------------------
    public static void warn(final String msg, Object... params) {
        var fmt = String.format(msg, params);
        LogHandler.WARN.accept(fmt, null);
    }

    public static void warn(final String msg) {
        LogHandler.WARN.accept(msg, null);
    }

    public static void warn(final Object ref) {
        LogHandler.WARN.accept(Objects.toString(ref), null);
    }

    public static void warn(final String msg, Throwable ex) {
        LogHandler.WARN.accept(msg, ex);
    }

    // ----------------------------------------------
    public static void info(final String msg, Object... params) {
        var fmt = String.format(msg, params);
        LogHandler.INFO.accept(fmt, null);
    }

    public static void info(final String msg) {
        LogHandler.INFO.accept(msg, null);
    }

    public static void info(final Object ref) {
        if (ref != null && ref.getClass().isArray()
                && !ref.getClass().getComponentType().isPrimitive()) {
            final Object[] array = (Object[]) ref;
            LogHandler.INFO.accept(Arrays.toString(array), null);
        } else {
            LogHandler.INFO.accept(Objects.toString(ref), null);
        }
    }

    public static void info(final String msg, Throwable ex) {
        LogHandler.INFO.accept(msg, ex);
    }

    // ----------------------------------------------

    public static void debug(final String msg) {
        LogHandler.DEBUG.accept(msg, null);
    }

    public static void debug(final Object ref) {
        LogHandler.DEBUG.accept(Objects.toString(ref), null);
    }

    public static void debug(final String msg, Throwable ex) {
        LogHandler.DEBUG.accept(msg, ex);
    }

    // ----------------------------------------------

    enum LogHandler implements BiConsumer<String, Throwable> {
        ERROR {
            // Critical errors.
            public void accept(String msg, Throwable ex) {
                if (get.level.ordinal() < ordinal())
                    return;

                final LogWriter wr = get.context.logger();
                wr.log(SYSTEM_STYLE, toString() + " ");
                wr.logln(ERROR_STYLE, msg);
                if (ex != null) {
                    wr.logThrowable(ex);
                }
            }
        },
        WARN {
            // Important warnings.
            public void accept(String msg, Throwable ex) {
                //if (get.level.ordinal() >= ordinal())
                //    return;
                final LogWriter wr = get.context.logger();
                wr.logln(WARN_STYLE, msg);
                if (ex != null) {
                    wr.logln(WARN_STYLE, ex.getMessage());
                }
            }
        },
        INFO {
            // Informative messages.
            public void accept(String msg, Throwable ex) {
                if (get.level.ordinal() >= ordinal())
                    return;
                final LogWriter wr = get.context.logger();
                wr.logln(INFO_STYLE, msg);
                if (ex != null) {
                    wr.logln(INFO_STYLE, ex.getMessage());
                }
            }
        },
        DEBUG {
            // Debug messages.
            public void accept(String msg, Throwable ex) {
                if (get.level.ordinal() >= ordinal())
                    return;
                final LogWriter wr = get.context.logger();
                wr.logln(DEBUG_STYLE, msg);
                if (ex != null) {
                    wr.logln(DEBUG_STYLE, ex.getMessage());
                }
            }
        },
        SYSTEM {
            // System messages.
            public void accept(String msg, Throwable ex) {
                final LogWriter wr = get.context.logger();
                wr.println(SYSTEM_STYLE, "# " + msg);
            }
        },
        NONE {
            public void accept(String msg, Throwable ex) {
            }
        };

        public String toString() {
            return '[' + name() + ']';
        }
    }

    // ----------------------------------------------

    static final class LogWriter {
        private final ConsoleOutput cos;
        private final PrintStream fos;
        private final Thread thread;
        private Font font;

        private LogWriter(final ConsoleOutput _cos,
                          final FileOutputStream _fos) {
            thread = Thread.currentThread();
            cos = checkNotNull(_cos);
            fos = (_fos != null)
                    ? new PrintStream(_fos)
                    : null;
        }

        public void println(Font font, String msg) {
            cos.set(font).println(msg);
            cos.reset();
            cos.flush();
            if (fos != null) {
                fos.println(msg);
                fos.flush();
            }
        }

        public void log(Font font, String msg) {
            msg = "Thread[" + (thread.getName() + ','
                    + thread.getId() + "] ") + msg;
            cos.set(font).print(msg);
            cos.reset();
            cos.flush();
            if (fos != null) {
                fos.print(msg);
                fos.flush();
            }
        }

        public void logln(Font font, String msg) {
            msg = "Thread[" + (thread.getName() + ','
                    + thread.getId() + "] ") + msg;
            cos.set(font).println(msg);
            cos.reset();
            cos.flush();
            if (fos != null) {
                fos.println(msg);
                fos.flush();
            }
        }

        public void logThrowable(Throwable th) {
            th.printStackTrace(cos);
            cos.flush();
            if (fos != null) {
                th.printStackTrace(fos);
                fos.flush();
            }
        }
    }

    // ----------------------------------------------

    static final class LogContext {
        private final ThreadLocal<LogWriter> writer;
        private final Path path;

        LogContext(final String filePath) {
            final FileOutputStream fos;
            if (filePath != null) {
                path = Paths.get(filePath);
                final File logFile = this.path.toFile();
                if (logFile.exists()) {
                    isTrue(logFile.delete());
                }
                try {
                    isTrue(logFile.createNewFile());
                    fos = new FileOutputStream(logFile);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                path = Paths.get("");
                fos = null;
            }
            writer = ThreadLocal.withInitial(() ->
                    new LogWriter(SystemIO.out, fos));
        }

        final LogWriter logger() {
            return writer.get();
        }
    }
}
