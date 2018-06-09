package uy.edu.ucu.si.grupo8.obligatorio3.services;

import uy.edu.ucu.si.grupo8.obligatorio3.dtos.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO singUp(final UserDTO user);

    List<UserDTO> getAll();

    UserDTO setUserActivation(final Long id, final Boolean isActive);

    UserDTO userExist(final Long id);


}
