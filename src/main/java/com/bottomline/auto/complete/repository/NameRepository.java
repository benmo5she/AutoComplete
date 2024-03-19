package com.bottomline.auto.complete.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bottomline.auto.complete.model.Name;

public interface NameRepository extends JpaRepository<Name, String>
{
    Name findByNameIgnoreCase(String name);

}
