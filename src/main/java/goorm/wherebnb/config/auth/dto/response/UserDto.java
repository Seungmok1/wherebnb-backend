package goorm.wherebnb.config.auth.dto.response;

import goorm.wherebnb.domain.dao.*;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {

    private Long userId;

    private String accessToken;

    private String refreshToken;

    private Integer expTime = 3600;
}