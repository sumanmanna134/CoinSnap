package com.offlixtrade.crypto.model;

import lombok.Data;

@Data
public class AuthResponse {
    private String jwt;
    private boolean status;
    private boolean isTwoFactorAuthEnabled;
    private String session;
}
