package com.springbootcallingexternalapi.LeagueOfLegends.Models;

import com.springbootcallingexternalapi.LeagueOfLegends.Enums.RoleName;
import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "role" , schema = "public")
public class SecurityRoleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    public SecurityRoleModel() {
    }

    public SecurityRoleModel(RoleName roleName) {
        this.roleName = roleName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }
}
