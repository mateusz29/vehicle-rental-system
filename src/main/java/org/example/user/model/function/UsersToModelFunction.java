package org.example.user.model.function;

import org.example.user.entity.User;
import org.example.user.model.UsersModel;

import java.util.List;
import java.util.function.Function;

public class UsersToModelFunction implements Function<List<User>, UsersModel> {
    @Override
    public UsersModel apply(List<User> entities) {
        return UsersModel.builder()
                .users(entities.stream()
                        .map(user -> UsersModel.User.builder()
                                .id(user.getId())
                                .username(user.getUsername())
                                .birthday(user.getBirthday())
                                .build())
                        .toList())
                .build();
    }
}
