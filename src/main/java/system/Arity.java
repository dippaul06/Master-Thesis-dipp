package system;

// ------------------------------------------------------------
//                            ARITY
// ------------------------------------------------------------
// The arity determines the number of arguments or operands
// can be applied on some entity.
//
public interface Arity {

    int UNARY = 0, BINARY = 1, TERNARY = 2, N_ARY = -1;

    int arity();

    interface Arity0 extends Arity { default int arity() { return 0x0; } }

    interface Arity1 extends Arity { default int arity() { return 0x1; } }

    interface Arity2 extends Arity { default int arity() { return 0x2; } }

    interface Arity3 extends Arity { default int arity() { return 0x3; } }

    interface Arity4 extends Arity { default int arity() { return 0x4; } }

    interface Arity5 extends Arity { default int arity() { return 0x5; } }

    interface Arity6 extends Arity { default int arity() { return 0x6; } }

    interface Arity7 extends Arity { default int arity() { return 0x7; } }

    interface Arity8 extends Arity { default int arity() { return 0x8; } }
}
