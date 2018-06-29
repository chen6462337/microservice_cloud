package com.itmuch.cloud.microservicesimplecustomermovie.config;


import feign.Contract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {

   @Bean
    public Contract feignContract(){
       return new feign.Contract.Default();
   }


}
