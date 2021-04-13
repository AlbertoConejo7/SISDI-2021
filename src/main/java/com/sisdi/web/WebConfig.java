package com.sisdi.web;

import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//Redireccionamiento
public class WebConfig implements WebMvcConfigurer{
        @Override 
        public void addViewControllers(ViewControllerRegistry registro ){
            registro.addViewController("/").setViewName("index");
            registro.addViewController("/login");
        }
}
