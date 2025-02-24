package com.project.physio_backend.payload.request;

import com.project.physio_backend.Entities.Users.Profile;

import jakarta.validation.constraints.*;

public class SignupRequest {
  @NotBlank
  private String username;

  @NotBlank
  private String password;

  public SignupRequest(String username2, String password2, Profile profile) {
    // TODO Auto-generated constructor stub
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}