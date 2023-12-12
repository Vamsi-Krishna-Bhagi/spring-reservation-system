package learn.vk.microservices.customer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "CustomerDto", description = "Customer details")
public class CustomerDto {
    @Schema(description = "Customer id")
    private Long id;

    @Schema(description = "Customer name")
    private String name;

    @Schema(description = "Customer email")
    private String email;

    @Schema(description = "Customer password")
    private String password;
}
