package kz.miniland.minilandserver;

import kz.miniland.minilandserver.services.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class MinilandServerApplication implements WebMvcConfigurer {

    @Value("${application.delete-all-files}")
    private Boolean deleteAllFiles;

    public static void main(String[] args) {
        SpringApplication.run(MinilandServerApplication.class, args);
    }

    @Bean
    CommandLineRunner init(FileService fileService) {
        return (args) -> {
            if (deleteAllFiles)
                fileService.deleteAll();
            fileService.init();
        };
    }
}
