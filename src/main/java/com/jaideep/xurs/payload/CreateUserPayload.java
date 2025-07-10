package com.jaideep.xurs.payload;

import com.jaideep.xurs.entity.AuthProviderType;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
public class CreateUserPayload {
    RegisterPayload registerPayload;
    AuthProviderType authProviderType;
    String authUserId;
}
