package com.fortrue.common.tool.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CollectionMapUtil<A, B, C> {

    private CollectionMapUtil() {
    }

    private final Predicate<Collection<A>> IS_COLLECTION_EMPTY = t -> t == null || t.isEmpty();

    private final Predicate<Map<B, A>> IS_MAP_EMPTY = t -> t == null || t.isEmpty();

    /**
     * Collection<B> -> Map<A, B>
     */
    private final BiFunction<Collection<B>, Function<B, A>, Map<A, B>> collection2Map1 =
            (c, f) -> c.stream().collect(Collectors.toMap(f, Function.identity(), (t1, t2) -> t1));

    /**
     * Collection<B> -> Map<A, U>
     */
    private final ThreeFunction<Collection<B>, Function<B, A>, Function<B, C>, Map<A, C>> collection2Map2 =
            (c, fa, fb) -> c.stream().collect(Collectors.toMap(fa, fb, (t1, t2) -> t1));

    /**
     * Collection<B> -> Map<A, List<B>>
     */
    private final BiFunction<Collection<B>, Function<B, A>, Map<A, List<B>>> collection2Map3 =
            (c, f) -> c.stream().collect(Collectors.groupingBy(f));

}
