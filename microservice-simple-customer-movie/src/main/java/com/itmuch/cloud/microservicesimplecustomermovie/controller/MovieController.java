package com.itmuch.cloud.microservicesimplecustomermovie.controller;

import com.itmuch.cloud.microservicesimplecustomermovie.FeignClient.UserFeignClient;
import com.itmuch.cloud.microservicesimplecustomermovie.entity.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class MovieController {


    private static  final Logger logger=LoggerFactory.getLogger(MovieController.class);

   /* @Autowired
    private RestTemplate restTemplate;
*/
    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private UserFeignClient userFeignClient;


   /* @HystrixCommand(fallbackMethod = "findByIdFallback")*/
    @GetMapping("/user/{id}")
    public User findById(@PathVariable Long id){
      /*  return this.restTemplate.getForObject("http://microservice-provider-user/"+id,User.class);*/
       return this.userFeignClient.findById(id);
    }

    @GetMapping("/user-instance")
    public List<ServiceInstance> showinfo(){
        return this.discoveryClient.getInstances("microservice-provider-user");
    }

    @GetMapping("/log-instance")
    public void logUserInstance(){
        ServiceInstance choose = this.loadBalancerClient.choose("microservice-provider-user");
        logger.info("{}:{}:{}",choose.getServiceId(),choose.getHost(),choose.getPort());
    }

  /*  public User findByIdFallback(Long id){
        User user=new User();
        user.setId(id);
        user.setName("默认用户");
        return user;
    }*/
}
