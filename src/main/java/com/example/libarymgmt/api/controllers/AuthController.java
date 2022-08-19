package com.example.libarymgmt.api.controllers;

import com.example.libarymgmt.api.requests.AuthRequest;
import com.example.libarymgmt.api.responses.AuthResponse;
import com.example.libarymgmt.user.User;
import com.example.libarymgmt.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value="auth")
public class AuthController {

  final AuthenticationManager authenticationManager;
  @Autowired
  JwtTokenUtil jwtUtil;

  public AuthController(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody AuthRequest request) {
    try {
//      System.out.println(new BCryptPasswordEncoder().encode("password1"));
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              request.getUsername(), request.getPassword())
      );

      User user = (User) authentication.getPrincipal();
      String accessToken = jwtUtil.generateAccessToken(user);
      AuthResponse response = new AuthResponse(user.getUsername(), accessToken);

      return ResponseEntity.ok().body(response);

    } catch (BadCredentialsException ex) {
      System.out.println(ex.getMessage());
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }
}
