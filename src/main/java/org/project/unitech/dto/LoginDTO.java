package org.project.unitech.dto;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record LoginDTO(@NotBlank(message = "Pin cannot be empty")
                       String pin,
                       @NotBlank(message = "Password cannot be empty")
                       String password) implements Serializable {
}
