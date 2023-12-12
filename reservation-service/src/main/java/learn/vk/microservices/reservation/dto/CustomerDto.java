package learn.vk.microservices.reservation.dto;

import lombok.Data;

@Data
public class CustomerDto {
    private Long id;
    private String name;
    private String email;
    private String password;
}
