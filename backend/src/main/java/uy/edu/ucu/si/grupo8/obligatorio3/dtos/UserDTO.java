package uy.edu.ucu.si.grupo8.obligatorio3.dtos;

import lombok.*;

import java.util.List;


@Builder
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String password;
    private String username;
    private String email;
    private Boolean active;
    private List<RoleDTO> roles;


}