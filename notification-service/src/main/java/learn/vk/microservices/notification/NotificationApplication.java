package learn.vk.microservices.notification;

import jakarta.annotation.PostConstruct;
import learn.vk.microservices.notification.entity.Notification;
import learn.vk.microservices.notification.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class NotificationApplication {

	@Autowired
	private NotificationRepository notificationRepository;

	public static void main(String[] args) {
		SpringApplication.run(NotificationApplication.class, args);
	}

}
