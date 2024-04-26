package org.project.unitech.dto;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record UserDTO(@NotBlank(message = "Pin cannot be empty")
                      String pin,
                      @NotBlank(message = "Password cannot be empty")
                      String password,
                      @NotBlank(message = "Name cannot be empty")
                      String name,
                      @NotBlank(message = "Surname cannot be empty")
                      String surname) implements Serializable {
}