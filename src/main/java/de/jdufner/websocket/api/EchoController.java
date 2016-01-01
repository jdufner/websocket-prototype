package de.jdufner.websocket.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import de.jdufner.websocket.configuration.ApiController;
import de.jdufner.websocket.domain.Message;
import de.jdufner.websocket.service.EchoService;

@ApiController
public class EchoController {

  @Autowired
  private EchoService echoService;

  @RequestMapping("/greeting")
  public Message greeting(@RequestParam(value = "name", defaultValue = "World") final String name) {
    return echoService.echo(name);
  }

}
