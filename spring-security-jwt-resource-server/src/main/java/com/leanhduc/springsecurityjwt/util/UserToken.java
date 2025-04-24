package com.leanhduc.springsecurityjwt.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserToken {
    String accessToken;
    String refreshToken;

}
