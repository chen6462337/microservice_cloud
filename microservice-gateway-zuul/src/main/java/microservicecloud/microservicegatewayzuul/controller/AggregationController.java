package microservicecloud.microservicegatewayzuul.controller;


import com.google.common.collect.Maps;
import microservicecloud.microservicegatewayzuul.domain.User;
import microservicecloud.microservicegatewayzuul.service.AggregationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import rx.Observable;
import rx.Observer;

import java.util.HashMap;


@RestController
public class AggregationController {

    public static final Logger logger= LoggerFactory.getLogger(AggregationController.class);


    @Autowired
    private AggregationService aggregationService;


    @GetMapping("/aggregate/{id}")
    public DeferredResult<HashMap<String,User>> aggregate(@PathVariable Long id){
        Observable<HashMap<String, User>> hashMapObservable = this.aggregateObservable(id);
        return this.toDeferredResult(hashMapObservable);
    }

    public Observable<HashMap<String,User>> aggregateObservable(Long id){
        return Observable.zip(this.aggregationService.getUserById(id),
                this.aggregationService.getMovieUserByUserId(id),
                (user,movieUser)->{
                    HashMap<String,User> map= Maps.newHashMap();
                    map.put("user",user);
                    map.put("movieUser",movieUser);
                    return map;
                });
    }

    public DeferredResult<HashMap<String,User>> toDeferredResult(Observable<HashMap<String,User>> details){
        DeferredResult<HashMap<String,User>> result=new DeferredResult<>();
        details.subscribe(
                new Observer<HashMap<String, User>>() {
                    @Override
                    public void onCompleted() {
                        logger.info("完成.....");
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        logger.error("发生错误.....",throwable);
                    }

                    @Override
                    public void onNext(HashMap<String, User> stringUserHashMap) {
                        result.setResult(stringUserHashMap);
                    }
                }
        );

        return result;

    }


}
