package me.bajmo.kanbaniser.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignInRequest {

    @NotBlank
    private String username;
    @NotBlank
    private String password;

}