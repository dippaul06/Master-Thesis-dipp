package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

// ------------------------------------------------------------
//                       BUFFER UTILS
// ------------------------------------------------------------
//
public enum FutureUtils {
    ;

    // ----------------------------------------------
    //  FUTURES.
    // ----------------------------------------------
    public static CompletableFuture<Void> allFromSet(Set<CompletableFuture<?>> set) {
        return allAsList(new ArrayList<>(set)).thenRun(() -> {});
    }

    // ----------------------------------------------
    //  FUTURES.
    // ----------------------------------------------
    // Returns a new 'CompletableFuture' which completes to a
    // list of all values of its input stages, if all succeed.
    // The list of results is in the same order as the input
    // stages.
    // <p> If any of the given stages complete exceptionally,
    // then the returned future also does so, with a 'Completion-
    // Exception' holding this exception as its cause.
    public static <T> CompletableFuture<List<T>> allAsList(
            List<? extends CompletionStage<? extends T>> stages) {
        // We use traditional for-loops instead of streams here for performance reasons,
        // see AllAsListBenchmark

        @SuppressWarnings("unchecked") // generic array creation
        final CompletableFuture<? extends T>[] all = new CompletableFuture[stages.size()];
        for (int i = 0; i < stages.size(); i++) {
            all[i] = stages.get(i).toCompletableFuture();
        }
        return CompletableFuture.allOf(all)
                .thenApply(ignored -> {
                    final List<T> result = new ArrayList<>(all.length);
                    for (CompletableFuture<? extends T> completableFuture : all) {
                        result.add(completableFuture.join());
                    }
                    return result;
                });
    }

    @SafeVarargs
    public static <T> CompletableFuture<List<T>> allAsList(CompletionStage<? extends T>... arr) {
        return allAsList(List.of(arr));
    }

    public static CompletionStage<Void> completedEmptyStage() {
        return CompletableFuture.completedStage(null);
    }

    public static CompletableFuture<Void> completedCompletableFuture() {
        return CompletableFuture.completedFuture(null);
    }
}
