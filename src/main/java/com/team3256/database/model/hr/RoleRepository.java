package com.team3256.database.model.hr;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    Optional<Role> findByName(String name);
    List<Role> findByIdIn(List<Integer> roleIdList);
}
