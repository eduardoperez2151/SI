package uy.edu.ucu.si.grupo8.obligatorio3.converters;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import uy.edu.ucu.si.grupo8.obligatorio3.dtos.RoleDTO;
import uy.edu.ucu.si.grupo8.obligatorio3.models.Role;

@Component
@ConfigurationPropertiesBinding
public class RoleToModelConverter implements Converter<RoleDTO, Role> {


    @Nullable
    @Override
    public Role convert(final RoleDTO role) {

        return Role.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .build();
    }

}