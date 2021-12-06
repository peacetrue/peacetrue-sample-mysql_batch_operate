package com.github.peacetrue.sample.mysql_batch_operate;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author xiayx
 */
public interface UserService {

    void setBatch(boolean batch);

    int save(List<? extends User> entities);

    default int update(List<? extends User> entities) {
        return 0;
    }

    static void save(UserService userService, Function<Integer, User> userGenerator, int count) {
        List<User> users = generate(userGenerator, count);
        TimeUtils.printTime("batch save", () -> userService.save(users));
    }

    static List<User> generate(Function<Integer, User> generator, int count) {
        return IntStream.range(0, count)
                .boxed().map(generator)
                .collect(Collectors.toList());
    }
}
