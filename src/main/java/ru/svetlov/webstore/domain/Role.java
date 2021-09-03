package ru.svetlov.webstore.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @Column(name = "role_id")
    private Integer id;

    @Length(max = 255, message = "Max field length: 255")
    private String role;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_permission_groups",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Collection<PermissionGroup> permissionGroups;

    @ManyToMany
    @JoinTable(name = "role_permissions",
    joinColumns = @JoinColumn(name = "role_id"),
    inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Collection<UserPermission> directPermissions;
}
