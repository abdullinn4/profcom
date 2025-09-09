package ru.profcom.controllers;

import static ru.profcom.util.ConverterFromSnakeToCamel.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import ru.profcom.dto.UserProfileDto;
import ru.profcom.entities.UserEntity;
import ru.profcom.services.UserService;

@RestController
@RequestMapping("/profcom")
@Scope("session")
@RequiredArgsConstructor
@Tag(name = "Profile", description = "Profile management APIs")
@SecurityRequirement(name = "oauth2")
public class ProfileController {
    private final UserService userService;

    @Operation(summary = "Get user profile",
        description = "Retrieves the profile information of the authenticated user",
        responses =
        {
            @ApiResponse(responseCode = "200", description = "Successful operation")
            , @ApiResponse(responseCode = "401", description = "Unauthorized")
        })
    @GetMapping("/profile")
    public Map<String, Object>
    user(@Parameter(description = "OAuth2 authentication token") OAuth2AuthenticationToken authentication) {
        //        OAuth2User user = authentication.getPrincipal();
        OAuth2User user = (OAuth2User) authentication.getPrincipal();

        var at = user.getAttributes();
        Map<String, Object> readonlyCamelMap =
            Collections.unmodifiableMap(userEntityToUserProfileMap(userService.findOrCreateUser(user)));

        return readonlyCamelMap;
    }

    @Operation(summary = "Get user data",
        description = "Retrieves detailed user data including personal information and student details",
        responses =
        {
            @ApiResponse(responseCode = "200", description = "Successful operation")
            , @ApiResponse(responseCode = "401", description = "Unauthorized")
        })
    @GetMapping("/user-data")
    public ResponseEntity<UserProfileDto>
    getUserData(@Parameter(description = "OAuth2 authentication token") OAuth2AuthenticationToken token) {
        UserProfileDto dto = userService.getUserProfileDto(token);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/profile/update")
    public ResponseEntity<?> updateProfile(@RequestBody Map<String, Object> updates, Authentication authentication) {
        try {
            var set = updates;
            userService.updateUserProfileFields(authentication, updates);
            return ResponseEntity.ok(Map.of("message", "Профиль успешно обновлён"));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(400).body(
                Map.of("message", "Ошибка при обновлении профиля: " + ex.getMessage()));
        }
    }
}
