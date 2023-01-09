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

package magma.exa.control.adt.traversal;

import magma.exa.adt.typelevel.signature.$Fn1;
import magma.exa.control.exception.Throw;
import magma.exa.data.index.Ix1D;
import magma.exa.value.Unit;

/**
 *
 */
@SuppressWarnings("StatementWithEmptyBody")
public interface ITraverser<A, F extends $Fn1<? super A, Unit>>

        extends ICursor.Simplex<ICursor.Control, F>,

        Iterable<A>
{
    // ----------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    boolean tryNext(F action);

    // ----------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    default void forNext(final F action) {
        do { } while (tryNext(action));
    }

    // ----------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    default long whileNext(final Until<Control, F> driver) {
        return ITraverser.whileNext(this, driver);
    }

    // ----------------------------------------------------------
    //  ITRAVERSER.INDEXED
    // ----------------------------------------------------------

    interface Indexed<A, F extends $Fn1<? super A, Unit>>
            extends ICursor.Simplex.Indexed<ICursor.Control, F, Ix1D<F>>,
            ITraverser<A, F>
    {
        // ----------------------------------------------------------

        /**
         * {@inheritDoc}
         */
        @Override
        boolean tryNextIndexed(Ix1D<F> action);

        // ----------------------------------------------------------

        /**
         * {@inheritDoc}
         */
        @Override
        default void forNextIndexed(final Ix1D<F> action) {
            do { } while (tryNextIndexed(action));
        }

        // ----------------------------------------------------------

        /**
         * {@inheritDoc}
         */
        @Override
        default long whileNextIndexed(final Until<Control, Ix1D<F>> driver) {
            return ITraverser.whileNextIndexed(this, driver);
        }
    }

    // ----------------------------------------------------------
    //  ITRAVERSER.DUPLEX
    // ----------------------------------------------------------

    interface Duplex<A, F extends $Fn1<? super A, Unit>>
            extends ICursor.Duplex<ICursor.Control, F>,
            ITraverser<A, F>
    {
        // ----------------------------------------------------------

        /**
         * {@inheritDoc}
         */
        @Override
        boolean tryPrev(F action);

        // ----------------------------------------------------------

        /**
         * {@inheritDoc}
         */
        @Override
        default void forPrev(final F action) {
            do { } while (tryPrev(action));
        }

        // ----------------------------------------------------------

        /**
         * {@inheritDoc}
         */
        @Override
        default long whilePrev(final Until<Control, F> driver) {
            return ITraverser.whilePrev(this, driver);
        }

        // ----------------------------------------------------------
        //  ITRAVERSER.DUPLEX.INDEXED
        // ----------------------------------------------------------

        interface Indexed<A, F extends $Fn1<? super A, Unit>>
                extends ICursor.Duplex.Indexed<ICursor.Control, F, Ix1D<F>>,
                ITraverser.Indexed<A, F>,
                ITraverser.Duplex<A, F>
        {
            // ----------------------------------------------------------

            /**
             * {@inheritDoc}
             */
            @Override
            boolean tryPrevIndexed(Ix1D<F> action);

            // ----------------------------------------------------------

            /**
             * {@inheritDoc}
             */
            @Override
            default void forPrevIndexed(final Ix1D<F> action) {
                do { } while (tryPrevIndexed(action));
            }

            // ----------------------------------------------------------

            /**
             * {@inheritDoc}
             */
            @Override
            default long whilePrevIndexed(final Until<Control, Ix1D<F>> driver) {
                return ITraverser.whilePrevIndexed(this, driver);
            }
        }
    }

    // ----------------------------------------------------------

    private static <A, F extends $Fn1<? super A, Unit>>
    long whileNext(final ITraverser<A, F> traverser,
                   final Until<Control, F> driver) {
        java.util.Objects.requireNonNull(traverser);
        java.util.Objects.requireNonNull(driver);
        try {
            final int[]   state   = { State.S_INIT };
            final Control control = () -> state[0] |= State.S_EXITED;
            final var     action  = driver.onApply(control);
            boolean       canNext = true;
            while (canNext && state[0] == State.S_INIT) {
                canNext = traverser.tryNext(action);
            }
            if (!canNext) {
                state[0] |= State.S_COMPLETED;
            }
            return state[0];
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }


    private static <A, F extends $Fn1<? super A, Unit>>
    long whileNextIndexed(final Indexed<A, F> traverser,
                          final Until<Control, Ix1D<F>> driver) {
        java.util.Objects.requireNonNull(traverser);
        java.util.Objects.requireNonNull(driver);
        try {
            final int[]   state   = { State.S_INIT };
            final Control control = () -> state[0] |= State.S_EXITED;
            final var     action  = driver.onApply(control);
            boolean       canNext = true;
            while (canNext && state[0] == State.S_INIT) {
                canNext = traverser.tryNextIndexed(action);
            }
            if (!canNext) {
                state[0] |= State.S_COMPLETED;
            }
            return state[0];
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    // ----------------------------------------------------------

    private static <A, F extends $Fn1<? super A, Unit>>
    long whilePrev(final Duplex<A, F> traverser,
                   final Until<Control, F> driver) {
        java.util.Objects.requireNonNull(traverser);
        java.util.Objects.requireNonNull(driver);
        try {
            final int[]   state   = { State.S_INIT };
            final Control control = () -> state[0] |= State.S_EXITED;
            final var     action  = driver.onApply(control);
            boolean       canPrev = true;
            while (canPrev && state[0] == State.S_INIT) {
                canPrev = traverser.tryPrev(action);
            }
            if (!canPrev) {
                state[0] |= State.S_COMPLETED;
            }
            return state[0];
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    private static <A, F extends $Fn1<? super A, Unit>>
    long whilePrevIndexed(final Duplex.Indexed<A, F> traverser,
                          final Until<Control, Ix1D<F>> driver) {
        java.util.Objects.requireNonNull(traverser);
        java.util.Objects.requireNonNull(driver);
        try {
            final int[]   state   = { State.S_INIT };
            final Control control = () -> state[0] |= State.S_EXITED;
            final var     action  = driver.onApply(control);
            boolean       canPrev = true;
            while (canPrev && state[0] == State.S_INIT) {
                canPrev = traverser.tryPrevIndexed(action);
            }
            if (!canPrev) {
                state[0] |= State.S_COMPLETED;
            }
            return state[0];
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }
}
