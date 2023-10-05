package fr.chatop.api.controller;

import fr.chatop.api.dto.AuthRequest;
import fr.chatop.api.dto.AuthResponse;
import fr.chatop.api.dto.NewUserDto;
import fr.chatop.api.dto.UserDto;
import fr.chatop.api.model.User;
import fr.chatop.api.service.UserService;
import fr.chatop.api.service.impl.UserServiceImpl;
import fr.chatop.api.util.JwtTokenUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
public class AuthController {
	@Autowired AuthenticationManager authManager;
	@Autowired JwtTokenUtil jwtUtil;
    @Autowired private UserService userService=new UserServiceImpl();

    @ApiOperation("Registers new User (email must be unique)")
    @Operation(responses={
            @ApiResponse(responseCode = "200", description="User successfully registered"),
            @ApiResponse(responseCode = "400", description="Email is not available")
    })
    @PostMapping("/auth/register")
    public AuthResponse addUser(@RequestBody NewUserDto user) {
        User candidate = userService.saveUser(user);
        String accessToken = jwtUtil.generateAccessToken(candidate);
        return new AuthResponse(candidate.getEmail(), accessToken);
    }

    @ApiOperation("Gets my own data if I'm logged in")
    @Operation(responses={
            @ApiResponse(responseCode = "200", description="User public data retrieval"),
            @ApiResponse(responseCode = "401", description="Bad credentials"),
    })
    @GetMapping("/auth/me")
    // Endpoint is secured so no need to check if jwt exists and is valid
    public UserDto getMe(@RequestHeader("Authorization") String jwt) {
        Long id = jwtUtil.getIdFromValidToken(jwt);
        return userService.getUser(id);
    }

    @Operation(responses={
            @ApiResponse(responseCode = "200", description="Successfully logged in"),
            @ApiResponse(responseCode = "401", description="Bad credentials")
    })
	@PostMapping("/auth/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest request) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword())
            );
             
            User user = (User) authentication.getPrincipal();
            String accessToken = jwtUtil.generateAccessToken(user);
			AuthResponse response = new AuthResponse(user.getEmail(), accessToken);
             
            return ResponseEntity.ok().body(response);
             
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
	}
}