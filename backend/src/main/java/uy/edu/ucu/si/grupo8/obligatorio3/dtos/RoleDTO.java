package uy.edu.ucu.si.grupo8.obligatorio3.dtos;

import lombok.*;


@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {

    private Long id;
    private String name;
    private String description;

}