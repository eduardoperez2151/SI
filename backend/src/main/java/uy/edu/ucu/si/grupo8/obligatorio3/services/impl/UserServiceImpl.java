package uy.edu.ucu.si.grupo8.obligatorio3.services.impl;

import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import uy.edu.ucu.si.grupo8.obligatorio3.dtos.UserDTO;
import uy.edu.ucu.si.grupo8.obligatorio3.models.User;
import uy.edu.ucu.si.grupo8.obligatorio3.repositories.UserRepository;
import uy.edu.ucu.si.grupo8.obligatorio3.services.UserService;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final ConversionService conversionService;

    public UserServiceImpl(final BCryptPasswordEncoder bCryptPasswordEncoder, final UserRepository userRepository, final ConversionService conversionService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.conversionService = conversionService;
    }

    @Override
    public UserDTO singUp(final UserDTO userDTO) {


        final Long userId = userDTO.getId();

        if (userId != null) {
            getOptionalUserById(userId).ifPresent(user -> {
                throw new EntityExistsException("El usuario con Id" + userId + " ya existe.");
            });
        }

        final String password = userDTO.getPassword();
        final String encodedPassword = bCryptPasswordEncoder.encode(password);
        userDTO.setPassword(encodedPassword);

        final User user = conversionService.convert(userDTO, User.class);
        final User persistedUser = userRepository.save(user);
        return conversionService.convert(persistedUser, UserDTO.class);
    }

    @Override
    public List<UserDTO> getAll() {
        final TypeDescriptor sourceType = TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(User.class));
        final TypeDescriptor targetType = TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(UserDTO.class));
        final List<User> recipes = userRepository.findAll();
        return (List<UserDTO>) conversionService.convert(recipes, sourceType, targetType);
    }

    @Override
    public UserDTO setUserActivation(final Long id, final Boolean isActive) {
        userExist(id);
        final Optional<User> updatedUserOptional = userRepository.setUserActive(id, isActive);
        final User updatedUser = updatedUserOptional.orElseThrow(() -> new EntityNotFoundException("Error al actualizar el usuario"));
        return conversionService.convert(updatedUser, UserDTO.class);
    }

    @Override
    public UserDTO userExist(final Long id) {
        final User user = getOptionalUserById(id).orElseThrow(() -> new EntityNotFoundException("El usuario no existe"));
        return conversionService.convert(user, UserDTO.class);
    }

    private Optional<User> getOptionalUserById(final Long id) {
        return userRepository.findById(id);
    }

}
