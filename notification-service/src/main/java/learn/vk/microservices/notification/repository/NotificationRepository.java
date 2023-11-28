package learn.vk.microservices.notification.repository;

import learn.vk.microservices.notification.entity.Notification;

public interface NotificationRepository extends org.springframework.data.jpa.repository.JpaRepository<Notification, Long> {
}
