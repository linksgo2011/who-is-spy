package com.spy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ErrorsController {
    @RequestMapping(value = "/400", method = RequestMethod.GET)
    public String badRequest() {

        return "errors/400";
    }

    @RequestMapping(value = "/404", method = RequestMethod.GET)
    public String notFound() {

        return "errors/404";
    }

    @RequestMapping(value = "/500", method = RequestMethod.GET)
    public String serverError() {

        return "errors/500";
    }
}
