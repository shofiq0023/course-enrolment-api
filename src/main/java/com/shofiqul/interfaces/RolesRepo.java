package com.shofiqul.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shofiqul.entities.RoleDefinitionModel;

public interface RolesRepo extends JpaRepository<RoleDefinitionModel, Long> {

}
