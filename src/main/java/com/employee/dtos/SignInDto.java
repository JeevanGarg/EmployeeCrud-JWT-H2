package com.employee.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignInDto
{
    private String usernameOrEmail;
    private String password;
}
