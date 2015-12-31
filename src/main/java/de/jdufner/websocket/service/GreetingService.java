package de.jdufner.websocket.service;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import de.jdufner.websocket.domain.Greeting;

@Service
public class GreetingService {

  private static final String template = "Hello, %s!";
  private final AtomicLong counter = new AtomicLong();

  public Greeting greets(final String name) {
    return new Greeting(counter.incrementAndGet(), String.format(template, name));
  }

}
