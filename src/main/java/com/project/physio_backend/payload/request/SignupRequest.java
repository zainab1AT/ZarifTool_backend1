package com.project.physio_backend.payload.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SignupRequest {
  @NotBlank
  private String username;

  @NotBlank
  private String password;

	public SignupRequest(String username,String password){
		this.username = username;
		this.password = password;
	}

}