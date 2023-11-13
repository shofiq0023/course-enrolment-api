package com.shofiqul.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "role_definition")
@Data
public class RoleDefinitionModel {
	@Id
	private long id;
	private String roleName;
	private String roleDesc;
}
