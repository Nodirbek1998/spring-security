package uz.example.springsecurity.service.mapper;
import org.mapstruct.Mapper;
import uz.example.springsecurity.entity.Role;
import uz.example.springsecurity.service.dto.RoleDTO;

@Mapper(componentModel = "spring")
public interface RoleMapper extends EntityMapper<RoleDTO, Role> {
}
