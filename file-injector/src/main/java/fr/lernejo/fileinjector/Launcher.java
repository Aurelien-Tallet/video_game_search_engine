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
        try (AbstractApplicationContext springContext = new AnnotationConfigApplicationContext(Launcher.class)) {
            RabbitTemplate rabbitTemplate = springContext.getBean(RabbitTemplate.class);
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            File file = new File(args[0]);
            List<GameInfo> messages = mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(List.class, GameInfo.class)
                );
            for (GameInfo message : messages) {
                rabbitTemplate.convertAndSend("game_info", message, m -> {
                    m.getMessageProperties().setContentType("application/json");
                    m.getMessageProperties().setHeader("game_id" , message.id());
                    return m;
                });
                System.out.println(message.id() + " envoyé");
            }

            System.out.println("Messages envoyés");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
