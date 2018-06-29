package com.itmuch.cloud.microservicesimplecustomermovie.FeignClient;

import com.itmuch.cloud.microservicesimplecustomermovie.entity.User;

import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class UserFeignClientFallbackFactory implements FallbackFactory<UserFeignClient> {

    private static final Logger logger=LoggerFactory.getLogger(UserFeignClientFallbackFactory.class);

    @Override
    public UserFeignClient create(Throwable throwable) {
        return new UserFeignClient() {
            @Override
            public User findById(Long id) {
                logger.info("fallback;reason was:",throwable);
                User user=new User();
                user.setId(id);
                user.setUsername("默认用户");
                return user;
            }
        };
    }
}
