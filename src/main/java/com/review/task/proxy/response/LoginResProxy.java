package com.review.task.proxy.response;

import com.review.task.enums.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResProxy {
    private String username;

    private String  token;

    private String accessRole;
}
