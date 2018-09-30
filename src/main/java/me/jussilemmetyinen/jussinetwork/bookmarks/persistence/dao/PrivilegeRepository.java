package me.jussilemmetyinen.jussinetwork.bookmarks.persistence.dao;

import me.jussilemmetyinen.jussinetwork.bookmarks.persistence.domain.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    Privilege findByName(String name);

    @Override
    void delete(Privilege privilege);
}
