package ru.svetlov.webstore.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "permission_groups")
public class PermissionGroup {
    @Id
    @Column(name = "group_id")
    private Integer id;

    @Column(name = "permission_group")
    private String groupName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "group_permissions",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Collection<UserPermission> permissions;

}