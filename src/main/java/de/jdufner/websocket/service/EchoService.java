package de.jdufner.websocket.service;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import de.jdufner.websocket.domain.Message;

@Service
public class EchoService {

  private final AtomicLong counter = new AtomicLong();

  public Message echo(final String content) {
    return new Message(counter.incrementAndGet(), content);
  }

}
