package org.example.component;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.user.dto.function.RequestToUserFunction;
import org.example.user.dto.function.UserToResponeFunction;
import org.example.user.dto.function.UsersToResponseFunction;

@ApplicationScoped
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
