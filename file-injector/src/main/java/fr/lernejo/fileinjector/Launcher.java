package fr.lernejo.fileinjector;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.util.List;

@SpringBootApplication
public class Launcher {
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext springContext = new AnnotationConfigApplicationContext(Launcher.class)) {
            RabbitTemplate rabbitTemplate = springContext.getBean(RabbitTemplate.class);
            ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
            List<GameInfo> messages = mapper.readValue(new File(args[0]), mapper.getTypeFactory().constructCollectionType(List.class, GameInfo.class));
            messages.forEach(message -> rabbitTemplate.convertAndSend("game_info", message, m -> {
                m.getMessageProperties().setContentType("application/json");
                m.getMessageProperties().setHeader("game_id", message.id());
                return m;
            }));
        } catch (Exception e) { e.printStackTrace(); }
    }
}
