package com.employee.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpDto
{
    private String name;
    private String username;
    private String email;
    private String password;
}
