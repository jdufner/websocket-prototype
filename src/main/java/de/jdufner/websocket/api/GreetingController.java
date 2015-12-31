package de.jdufner.websocket.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import de.jdufner.websocket.configuration.ApiController;
import de.jdufner.websocket.domain.Greeting;
import de.jdufner.websocket.service.GreetingService;

@ApiController
public class GreetingController {

  @Autowired
  private GreetingService greetingService;

  @RequestMapping("/greeting")
  public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") final String name) {
    return greetingService.greets(name);
  }

}
