package it.previsuite.bean.utils;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectionUtils {

    private CollectionUtils() {}

    public static boolean isNullOrEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }
    public static boolean isNullOrEmpty(Set<?> set) {
        return set == null || set.isEmpty();
    }

    public static <T> List<T> distinctBy(Collection<T> collection, Function<? super T, ?> keyExtractor) {
        return CollectionUtils
                .distinctBy(collection.stream(), keyExtractor)
                .toList();
    }

    public static <T> Stream<T> distinctBy(Stream<T> stream, Function<? super T, ?> keyExtractor) {
        return stream
            .collect(Collectors
                    .toMap(keyExtractor, Function.identity(), (element1,element2) -> element1))
                .values()
                .stream();
    }
}
