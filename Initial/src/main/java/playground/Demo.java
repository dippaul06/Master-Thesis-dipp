package playground;

import magma.exa.control.exception.Throw;

import java.util.function.BiFunction;
import java.util.function.Function;

public class Demo {

    // Currying vs partial funktion
    // application.
    static void CURRYING() {

        Function<String, Function<Integer, String>> f = a -> b -> a + b;

        // partial function application
        f.apply("a").apply(4);

        // does not give the opportunity
        // to assign parameter iteratively.
        BiFunction<String, Integer, String> g = (a, b) -> a + b;
    }


    // Features:
    //      - not try catch
    //      - function tupled() allows to assign
    //        tuples as parameter.
    static void PARTIAL_FUNCTION_IMPL() {

        @FunctionalInterface
        interface Fn1<A, R> {
            R apply(A a);
        }

        @FunctionalInterface
        interface Fn2<A, B, R> extends Fn1<A, Fn1<B, R>> {
            R apply(A a, B b);
            @Override
            default Fn1<B, R> apply(A a) {
                return b -> apply(a, b);
            }
        }

        @FunctionalInterface
        interface Fn3<A, B, C, R> extends Fn2<A, B, Fn1<C, R>> {
            R onApply(A a, B b, C c) throws Throwable;
            default R apply(A a, B b, C c) {
                try {
                    return onApply(a, b, c);
                }
                catch (Throwable ex) {
                    return Throw.sneaky(ex);
                }
            }
            default Fn2<B, C, R> apply(A a) {
                return (b, c) -> apply(a, b, c);
            }
            default Fn1<C, R> apply(A a, B b) {
                return c -> apply(a, b, c);
            }
        }
        Fn3<String, String, String, String> f = (a, b, c) -> a+b+c;
        f.apply("a", "b").apply("c");
    }

}
