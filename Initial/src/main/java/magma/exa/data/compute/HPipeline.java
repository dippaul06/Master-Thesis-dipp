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

package magma.exa.data.compute;

import magma.exa.adt.generic.HList;
import magma.exa.base.Force;
import magma.exa.control.traversal.Traversable;

/**
 * Lazy pipelines are a structure where a computation is organized
 * as a sequence of operations that are assembled by taking a flow
 * of values as the output of one operation and feeding it into
 * some subsequent operation.
 *
 * @param <OUT>
 */
public interface HPipeline<OUT extends HPipe<?>> extends HList {

    /**
     * Returns the traversal source.
     */
    Traversable<?> source();

    // ----------------------------------------------------------
    //  HPIPELINE.STAGE
    // ----------------------------------------------------------

    /**
     * Common superinterface of a heterogeneous pipeline stage.
     *
     * @param <HEAD> type of outbound pipe of this stage.
     * @param <TAIL> linear recursive pipeline type.
     */
    interface Stage<HEAD extends HPipe<?>, TAIL extends HPipeline<?>>
            extends HPipeline<HEAD>, HList.Cell<HOperator<?, HEAD>, TAIL> {


        // ----------------------------------------------------------
        //  HPIPELINE.STAGE.ABSTRACT-BASE
        // ----------------------------------------------------------

        /**
         * Abstract base implementation of a heterogeneous pipeline stage.
         *
         * @param <HEAD> type of outbound pipe of this stage.
         * @param <TAIL> linear recursive pipeline type.
         */
        abstract class AbstractBase<
                HEAD extends HPipe<?>,
                TAIL extends HPipeline<?>,
                SELF extends Stage<HEAD, TAIL>>
                extends HList.Cell.Base<HOperator<?, HEAD>, TAIL>
                implements Stage<HEAD, TAIL>
        {
            /**
             * Constructs a heterogeneous pipeline head.
             */
            protected AbstractBase() {
                this(HOperator.identity(), HPipeline.nil());
            }

            /**
             * Constructs a heterogeneous pipeline stage.
             *
             * @param head operator of this pipeline.
             * @param tail pipeline.
             */
            protected AbstractBase(final HOperator<?, HEAD> head, final TAIL tail) {
                super(head, tail);
            }
        }
    }

    // ----------------------------------------------------------
    //  HPIPELINE.SOURCE
    // ----------------------------------------------------------

    /**
     * Designated first pipeline stage that provides the cursor
     * over the source of the values.
     *
     * @param <HEAD> type of outbound pipe of this stage.
     * @param <DATA> type of traversal source.
     */
    interface Source<
            HEAD extends HPipe<?>,
            DATA extends Traversable<?>>
            extends Stage<HEAD, NIL> {

        /**
         * Returns the data source of this pipeline.
         */
        DATA source();
    }


    // ----------------------------------------------------------
    //  HPIPELINE.NIL
    // ----------------------------------------------------------

    /**
     * Termination of linear recursive pipeline type.
     */
    enum NIL implements HPipe<NIL>, HPipeline<NIL> { stage;

        /**
         * Returns the traversal source.
         */
        @Override
        public Traversable<?> source() {
            return null;
        }
    }

    static <TAIL extends HPipeline<?>> TAIL nil() {
        return Force.cast(NIL.stage);
    }
}
