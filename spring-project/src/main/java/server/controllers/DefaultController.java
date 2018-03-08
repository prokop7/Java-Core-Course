package server.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/home")
public class DefaultController {
    private Random r = new Random();

    @RequestMapping(method = RequestMethod.GET, value = "/")
    int getRandom() {
        return r.nextInt();
    }
}
