package org.example.user.dto.function;

import org.example.user.dto.PutUserRequest;
import org.example.user.entity.User;

import java.util.function.Function;

public class RequestToUserFunction implements Function<PutUserRequest, User> {
    @Override
    public User apply(PutUserRequest request) {
        return User.builder()
                .uuid(request.getUuid())
                .username(request.getUsername())
                .birthday(request.getBirthday())
                .build();
    }
}
