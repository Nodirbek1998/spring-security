package uz.example.springsecurity.service;


import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;
import uz.example.springsecurity.service.dto.UserDTO;

import java.util.List;

public interface  UserService {

    List<UserDTO> getAll(Pageable pageable, MultiValueMap<String , String> queryParams);


    UserDTO create(UserDTO userDTO);

    UserDTO update(UserDTO userDTO, Long id);

    void delete(Long id);

    UserDTO getOne(Long id);
}
