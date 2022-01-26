package system;

// ------------------------------------------------------------
//                         DISPOSABLE
// ------------------------------------------------------------
// Interface for releasing acquired resources.
//
public interface Disposable extends AutoCloseable {

    default void dispose() {}

    default void close() {
        dispose();
    }
}

