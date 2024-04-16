package me.capstone.javis.sse.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.capstone.javis.sse.dto.EventPayload;
import me.capstone.javis.sse.service.SeeEmitterService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/sse")
public class SSEController {
    private final SeeEmitterService seeEmitterService;

    //응답 mime type 은 반드시 text/event-stream 이여야 한다.
    //클라이언트로 부터 SSE subscription 을 수락한다.
    @GetMapping(path = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> subscribe() {
        String sseId = UUID.randomUUID().toString();
        SseEmitter emitter = seeEmitterService.subscribe(sseId);
        return ResponseEntity.ok(emitter);
    }

    //eventPayload를 SSE로 연결된 모든 클라이언트에게 broadcasting 한다.
    @PostMapping(path = "/broadcast")
    public ResponseEntity<Void> broadcast(@RequestBody EventPayload eventPayload){
        seeEmitterService.broadcast(eventPayload);
        return ResponseEntity.ok().build();
    }
}
