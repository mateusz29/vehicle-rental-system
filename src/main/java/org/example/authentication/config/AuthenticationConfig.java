package org.example.authentication.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import jakarta.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;

@ApplicationScoped
@BasicAuthenticationMechanismDefinition(realmName = "Vehicle Rental")
//@FormAuthenticationMechanismDefinition(
//        loginToContinue = @LoginToContinue(
//                loginPage = "/authentication/form/login.xhtml",
//                errorPage = "/authentication/form/login_error.xhtml"
//        )
//)
//@CustomFormAuthenticationMechanismDefinition(
//        loginToContinue = @LoginToContinue(
//                loginPage = "/authentication/custom/login.xhtml",
//                errorPage = "/authentication/custom/login_error.xhtml"
//        )
//)
@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "jdbc/RentalSystem",
        callerQuery = "select password from users where username = ?",
        groupsQuery = "select role from users_roles where id = (select id from users where username = ?)",
        hashAlgorithm = Pbkdf2PasswordHash.class
)
public class AuthenticationConfig {
}