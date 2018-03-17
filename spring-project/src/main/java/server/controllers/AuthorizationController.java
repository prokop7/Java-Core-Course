package server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import server.services.*;

@RestController
@RequestMapping(value = "/auth")
public class AuthorizationController {
    private AuthService authService;

    @Autowired
    public AuthorizationController(AuthService authService) {
        this.authService = authService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    ResponseEntity<?> proceedRegistration(@RequestParam("login") String login,
                                          @RequestParam("password") String password) {
        try {
            authService.register(login, password);
            return ResponseEntity.ok().build();
        } catch (NullFieldException |
                InvalidFieldException |
                EmptyFieldException |
                DuplicatedLoginException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/login")
    String proceedAuthentication(@RequestParam("login") String login,
                                 @RequestParam("password") String password) {
        try {
            String token = authService.authenticate(login, password);
            if (token == null)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            return String.format("{\"token\":\"%s\"}", token);
        } catch (NullFieldException | EmptyFieldException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }


    @RequestMapping(method = RequestMethod.POST, value = "/logout")
    ResponseEntity<?> proceedLogout(@RequestHeader("token") String token) {
        authService.logout(token);
        return ResponseEntity.ok().build();
    }
}
