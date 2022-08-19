package uz.example.springsecurity.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.example.springsecurity.entity.User;
import uz.example.springsecurity.service.dto.UserDTO;


@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface UserMapper extends EntityMapper<UserDTO, User> {

    @Override
    User toEntity(UserDTO dto);

    @Override
    UserDTO toDto(User entity);

    default User fromId(Long id) {
        if (id == null) {
            return null;
        }
        User  user = new User();
        user.setId(id);
        return user;
    }
}
