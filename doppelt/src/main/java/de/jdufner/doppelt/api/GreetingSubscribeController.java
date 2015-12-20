package de.jdufner.doppelt.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import de.jdufner.doppelt.domain.Greeting;
import de.jdufner.doppelt.domain.HelloMessage;
import de.jdufner.doppelt.service.GreetingService;

@Controller
public class GreetingSubscribeController {

  @Autowired
  private GreetingService greetingService;

  @MessageMapping("/hello")
  @SendTo("/topic/greetings")
  public Greeting greeting(final HelloMessage message) throws Exception {
    Thread.sleep(3000); // simulated delay
    return greetingService.greets(message.getName());
  }

}
