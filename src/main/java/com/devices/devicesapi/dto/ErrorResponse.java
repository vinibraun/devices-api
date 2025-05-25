package com.devices.devicesapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Error response body")
public class ErrorResponse {

    @Schema(example = "400")
    private int status;

    @Schema(example = "Cannot update/delete a device while it's in use.")
    private String message;

    @Schema(example = "2025-05-24T20:00:00")
    private LocalDateTime timestamp;
}