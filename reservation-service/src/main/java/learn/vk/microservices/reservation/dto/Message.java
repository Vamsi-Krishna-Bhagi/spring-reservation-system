package learn.vk.microservices.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String description;
    private Status status;

    public enum Status {
        SUCCESS, FAILURE
    }
}
