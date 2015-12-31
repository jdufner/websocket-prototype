package de.jdufner.doppelt.ui;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
public class SampleController {

  @RequestMapping("/ui")
  @ResponseBody
  String home() {
    return "Hello World!";
  }

}
