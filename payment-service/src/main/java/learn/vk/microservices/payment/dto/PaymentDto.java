package learn.vk.microservices.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "PaymentDto", description = "Payment details")
public class PaymentDto {
    @Schema(description = "Payment id")
    private Long id;

    @Schema(description = "Reservation id")
    private Long reservationId;

    @Schema(description = "Payment amount")
    private Double amount;
}
