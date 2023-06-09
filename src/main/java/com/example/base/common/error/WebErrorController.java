package com.example.base.common.error;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebErrorController implements ErrorController {

  @RequestMapping(value = "/error")
  public String handlerError(HttpServletRequest request) {
    return "error";
  }
}
