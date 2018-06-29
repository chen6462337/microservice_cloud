package com.itmuch.cloud.microservicesimplecustomermovie.FeignClient;



import com.itmuch.cloud.microservicesimplecustomermovie.config.FeignDisableHystrixConfiguration;
import com.itmuch.cloud.microservicesimplecustomermovie.entity.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Primary
@FeignClient(name ="microservice-provider-user",fallbackFactory = UserFeignClientFallbackFactory.class)
public interface UserFeignClient {

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public User findById(@PathVariable("id") Long id);

}
