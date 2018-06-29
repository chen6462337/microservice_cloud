package com.itmuch.cloud.microservicesimplecustomermovie.FeignClient;

import com.itmuch.cloud.microservicesimplecustomermovie.entity.User;
import org.springframework.stereotype.Component;


@Component
public class UserFeignClientFallback implements UserFeignClient {
    @Override
    public User findById(Long id) {
        User user=new User();
        user.setId(id);
        user.setUsername("默认用户");
        return user;
    }
}
