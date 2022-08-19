package uz.example.springsecurity.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.management.relation.RoleStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {

    private Long id;

    private String roleName;
}
