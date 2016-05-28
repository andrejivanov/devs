package de.andrejivanov;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ParallelExecutor {
    private ForkJoinPool pool = new ForkJoinPool(8);

    public <U, T> List<T> getInParallelAndCollect(final Stream<U> streamWithSourceObjects, Function<U, T> functionToRunInParallel) {
        try {
            final List<List<T>> holder = new ArrayList<>(1);
            pool.submit(() -> {
                final List<T> resultList =
                        streamWithSourceObjects
                                .parallel()
                                .map(functionToRunInParallel)
                                .collect(Collectors.toList());
                holder.add(resultList);
            }).get();
            return holder.get(0);
        } catch (InterruptedException | ExecutionException e) {
            final Throwable throwable = e.getCause();
            if (throwable != null
                    && (throwable instanceof HttpServerErrorException
                    || throwable instanceof HttpClientErrorException)) {
                throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Http error while get subcontent. Message from subservice:" + throwable.getMessage());
            } else {
                throw new IllegalStateException("Concurrency problem while executing parallel", e);
            }
        }
    }

}