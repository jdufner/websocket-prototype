package de.jdufner.doppelt.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.jdufner.doppelt.domain.Greeting;
import de.jdufner.doppelt.service.GreetingService;

@RestController
public class GreetingController {

  @Autowired
  private GreetingService greetingService;

  @RequestMapping("/greeting")
  public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") final String name) {
    return greetingService.greets(name);
  }

}
