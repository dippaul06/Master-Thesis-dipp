package system;

import system.Arity.*;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

// ------------------------------------------------------------
//                          CONSUMERS
// ------------------------------------------------------------
//
public enum Consumers {
    ;
    // ----------------------------------------------
    //  CONSUMER.
    // ----------------------------------------------
    //
    public interface IConsumer extends Arity {

    }

    // ----------------------------------------------
    //  CONSUMER 0.
    // ----------------------------------------------
    //
    public interface Consumer0 extends IConsumer, Arity0 {

        void accept();
    }

    // ----------------------------------------------
    //  CONSUMER 1.
    // ----------------------------------------------
    //
    public interface Consumer1<T1> extends IConsumer, Arity1, Consumer<T1> {

        void accept(T1 v1);
    }

    // ----------------------------------------------
    //  CONSUMER 2.
    // ----------------------------------------------
    //
    public interface Consumer2<T1, T2> extends IConsumer, Arity2, BiConsumer<T1, T2> {

        void accept(T1 v1, T2 v2);
    }

    // ----------------------------------------------
    //  CONSUMER 3.
    // ----------------------------------------------
    //
    public interface Consumer3<T1, T2, T3> extends IConsumer, Arity3 {

        void accept(T1 v1, T2 v2, T3 v3);
    }

    // ----------------------------------------------
    //  CONSUMER 4.
    // ----------------------------------------------
    //
    public interface Consumer4<T1, T2, T3, T4> extends IConsumer, Arity4 {

        void accept(T1 v1, T2 v2, T3 v3, T4 v4);
    }

    // ----------------------------------------------
    //  CONSUMER 5.
    // ----------------------------------------------
    //
    public interface Consumer5<T1, T2, T3, T4, T5> extends IConsumer, Arity5 {

        void accept(T1 v1, T2 v2, T3 v3, T4 v4, T5 v5);
    }

    // ----------------------------------------------
    //  CONSUMER 6.
    // ----------------------------------------------
    //
    public interface Consumer6<T1, T2, T3, T4, T5, T6> extends IConsumer, Arity6 {

        void accept(T1 v1, T2 v2, T3 v3, T4 v4, T5 v5, T6 v6);
    }

    // ----------------------------------------------
    //  CONSUMER 7.
    // ----------------------------------------------
    //
    public interface Consumer7<T1, T2, T3, T4, T5, T6, T7> extends IConsumer, Arity7 {

        void accept(T1 v1, T2 v2, T3 v3, T4 v4, T5 v5, T6 v6, T7 v7);
    }

    // ----------------------------------------------
    //  CONSUMER 8.
    // ----------------------------------------------
    //
    public interface Consumer8<T1, T2, T3, T4, T5, T6, T7, T8> extends IConsumer, Arity8 {

        void accept(T1 v1, T2 v2, T3 v3, T4 v4, T5 v5, T6 v6, T7 v7, T8 v8);
    }
}
