package server.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/content")
public class DefaultController {
    private Random r = new Random();

    @RequestMapping(method = RequestMethod.GET)
    int getRandom() {
        return r.nextInt();
    }
}
