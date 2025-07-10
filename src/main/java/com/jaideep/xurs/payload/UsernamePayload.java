package com.jaideep.xurs.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsernamePayload {
    private String userId;
    private String username;
}
