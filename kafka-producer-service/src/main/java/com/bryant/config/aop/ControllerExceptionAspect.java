package com.bryant.config.aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@Slf4j
public class ControllerExceptionAspect {

    @ExceptionHandler
    public ModelAndView handleException(Exception e)
    {
        ModelAndView modelAndView = new ModelAndView();
        log.info("ControllerExceptionAspect handle exception...");
        modelAndView.addObject("error", "ControllerExceptionAspect#handleException");
        return modelAndView;
    }

}
