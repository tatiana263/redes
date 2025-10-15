package edu.unbosque.labred.qos_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QosServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(QosServerApplication.class, args);
        System.out.println("🚀 Servidor WebSocket iniciado en http://localhost:8080/");
    }
}
