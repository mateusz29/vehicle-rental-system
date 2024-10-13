package org.example.component;

import org.example.user.dto.function.RequestToUserFunction;
import org.example.user.dto.function.UserToResponeFunction;
import org.example.user.dto.function.UsersToResponseFunction;

public class DtoFunctionFactory {
    public UserToResponeFunction userToResponeFunction() {
        return new UserToResponeFunction();
    }

    public UsersToResponseFunction usersToResponseFunction() {
        return new UsersToResponseFunction();
    }

    public RequestToUserFunction requestToUserFunction() {
        return new RequestToUserFunction();
    }
}
