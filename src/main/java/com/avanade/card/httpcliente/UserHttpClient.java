package com.avanade.card.httpcliente;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.UUID;

//TODO mudar a url para variavel de ambiente
@Component
@FeignClient(value = "userService", url = "http://localhost:9080/users")
public interface UserHttpClient {

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    Object getUserById(@PathVariable("id") UUID userId);

}
