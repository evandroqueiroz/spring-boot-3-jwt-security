package com.alibou.core.security.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoogleUserInfo {
    private String email;
    private boolean emailVerified;
    private String name;
    private String pictureUrl;
    private String givenName;
    private String familyName;
}