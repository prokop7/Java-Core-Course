package server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import server.services.*;

@RestController
@CrossOrigin(origins = "*")
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
        } catch (NullFieldException | InvalidFieldException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid credentials");
        } catch (EmptyFieldException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login and password can not be empty");
        } catch (DuplicatedLoginException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login is already taken");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/login")
    String proceedAuthentication(@RequestParam("login") String login,
                                 @RequestParam("password") String password) {
        try {
            String token = authService.authenticate(login, password);
            if (token == null)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with this credentials not found");
            return String.format("{\"token\":\"%s\"}", token);
        } catch (NullFieldException | EmptyFieldException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid fields");
        }
    }


    @RequestMapping(method = RequestMethod.POST, value = "/logout")
    ResponseEntity<?> proceedLogout(@RequestHeader("token") String token) {
        authService.logout(token);
        return ResponseEntity.ok().build();
    }
}

