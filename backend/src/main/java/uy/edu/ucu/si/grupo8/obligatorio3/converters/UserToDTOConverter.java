package uy.edu.ucu.si.grupo8.obligatorio3.converters;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import uy.edu.ucu.si.grupo8.obligatorio3.dtos.RoleDTO;
import uy.edu.ucu.si.grupo8.obligatorio3.dtos.UserDTO;
import uy.edu.ucu.si.grupo8.obligatorio3.models.Role;
import uy.edu.ucu.si.grupo8.obligatorio3.models.User;

import java.util.List;

@Component
@ConfigurationPropertiesBinding
public class UserToDTOConverter implements Converter<User, UserDTO> {

    private final ConversionService conversionService;

    @Lazy
    public UserToDTOConverter(final ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Nullable
    @Override
    public UserDTO convert(final User user) {

        final TypeDescriptor sourceType = TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(Role.class));
        final TypeDescriptor targetType = TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(RoleDTO.class));


        return UserDTO.builder()
                .id(user.getId())
                .active(user.getActive())
                .email(user.getEmail())
                .username(user.getUsername())
                .roles((List<RoleDTO>) conversionService.convert(user.getRoles(), sourceType, targetType))
                .build();
    }

}