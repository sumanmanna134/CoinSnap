package com.offlixtrade.crypto.service;

import com.offlixtrade.crypto.model.AuthResponse;
import com.offlixtrade.crypto.model.User;

public interface IAuthService {
    public AuthResponse register(User user);

}
