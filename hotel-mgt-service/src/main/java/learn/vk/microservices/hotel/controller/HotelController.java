package learn.vk.microservices.hotel.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import learn.vk.microservices.hotel.dto.HotelDto;
import learn.vk.microservices.hotel.service.HotelService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@OpenAPIDefinition(info = @io.swagger.v3.oas.annotations.info.Info(title = "Hotel Api"))
public class HotelController {
    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/{id}")
    @Parameter(name = "id", description = "hotel id")
    @Operation(summary = "Get hotel by id")
    @ApiResponse(responseCode = "200", description = "Hotel found")
    @ApiResponse(responseCode = "404", description = "Hotel not found")
    public HotelDto getHotelById(@PathVariable Long id) {
        return hotelService.getHotelById(id);
    }

    @PostMapping
    @Operation(summary = "Update hotel")
    @ApiResponse(responseCode = "200", description = "Hotel registered")
    @ApiResponse(responseCode = "400", description = "Hotel not registered")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Hotel details", required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = HotelDto.class)))
    public HotelDto updateHotel(@RequestBody HotelDto hotelDto) {
        return hotelService.updateHotel(hotelDto);
    }
}
