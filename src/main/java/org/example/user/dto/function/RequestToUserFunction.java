package org.example.user.dto.function;

import org.example.user.dto.PutUserRequest;
import org.example.user.entity.User;

import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToUserFunction implements BiFunction<UUID, PutUserRequest, User> {
    @Override
    public User apply(UUID uuid, PutUserRequest request) {
        return User.builder()
                .uuid(uuid)
                .username(request.getUsername())
                .birthday(request.getBirthday())
                .build();
    }
}
