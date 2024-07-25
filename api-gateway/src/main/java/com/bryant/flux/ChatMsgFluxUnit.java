package com.bryant.flux;

import java.util.function.Function;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ChatMsgFluxUnit<T,R> {

    public Mono<R> getMonoChatMsg(T t, Function<T,R> function){
        return Mono.just(t).map(function).onErrorResume(e->Mono.empty());
    }

    public Flux<R> getMoreChatMsg(T t, Function<T,R> function){
        return Flux.just(t).map(function).onErrorResume(e->Flux.empty());
    }
}