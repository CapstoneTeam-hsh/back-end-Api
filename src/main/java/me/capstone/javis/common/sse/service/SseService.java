package me.capstone.javis.common.sse.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.capstone.javis.common.exception.customs.CustomException;
import me.capstone.javis.common.exception.customs.ExceptionCode;
import me.capstone.javis.todo.data.dto.response.TodoSseResDto;
import me.capstone.javis.todo.data.repository.TodoRepository;
import me.capstone.javis.user.data.domain.User;
import me.capstone.javis.user.data.repository.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SseService {
    private static final Long DEFAULT_TIMEOUT = 30L * 1000;

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    private Map<Long, SseEmitter> session = new ConcurrentHashMap<>();

    public SseEmitter subscribe(String loginId){
        User user = userRepository.findByLoginId(loginId).orElseThrow(()-> new CustomException(ExceptionCode.USER_NOT_EXIST));

        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);

        if(session.containsKey(user.getId())){
            session.remove(user.getId());
        }

        session.put(user.getId(),emitter);
        sendToClient(user.getId(), "mockTodo");

        return emitter;
    }

    private void sendToClient(Long id, Object object){
        SseEmitter emitter = session.get(id);
        try{
            emitter.send(SseEmitter.event()
                    .id(id.toString())
                    .name("sse")
                    .data(object)
                    .reconnectTime(DEFAULT_TIMEOUT)
            );
        }catch (IOException e){
            throw new RuntimeException("SSE 연결을 실패하였습니다.");
        }
    }

    //매일 12시 정각에 메서드 실행
    @Scheduled(cron = "0/5 * * * * *")
    public void send() throws ParseException {
        List<TodoSseResDto> result = todoRepository.findTodoAndUserId();
        String todayFm = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date(dateFormat.parse(todayFm).getTime());

        for (TodoSseResDto todoSse: result) {
            Long id = todoSse.userId();
            String deadLine = todoSse.deadLine();
            Date todoDay = new Date(dateFormat.parse(deadLine).getTime());

            if (today.compareTo(todoDay) == 0) {
                if(session.get(id) != null){
                    log.info("제대로 호출되었습니다!");
                    sendToClient(id, todoSse);
                }
            }
        }
    }
}
