//package me.capstone.javis.common.sse.controller;
//
//import io.swagger.v3.oas.annotations.Operation;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import me.capstone.javis.common.sse.service.SseService;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
//
//@RestController
//@RequestMapping("/sse")
//@RequiredArgsConstructor
//@Slf4j
//public class SseController {
//
//    private final SseService sseService;
//
//    @Operation(summary = "sse 연결", description = "유저와 sse를 연결합니다.")
//    @GetMapping(value = "/subscribe", produces = "text/event-stream")
//    public SseEmitter subscribe(@AuthenticationPrincipal UserDetails principal){
//        return sseService.subscribe(principal.getUsername());
//    }
//}
