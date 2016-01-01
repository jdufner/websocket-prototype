package de.jdufner.websocket.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import de.jdufner.websocket.domain.Message;
import de.jdufner.websocket.service.EchoService;

@Controller
public class EchoSubscribeController {

  @Autowired
  private EchoService echoService;

  @MessageMapping("/hello")
  @SendTo("/topic/greetings")
  public Message echo(final Message message) throws Exception {
    Thread.sleep(3000); // simulated delay
    return echoService.echo(message.getContent());
  }

}
