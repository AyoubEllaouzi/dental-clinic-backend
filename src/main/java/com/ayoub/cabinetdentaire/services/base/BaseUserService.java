package com.ayoub.cabinetdentaire.services.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

// Interface générique
public interface BaseUserService<REQ, RES, ID> {
    RES create(REQ request);

    RES update(ID id, REQ request);

    RES getById(ID id);

    List<RES> getAll();

    void delete(ID id);

    RES getByEmail(String email);

    RES changeStatus(ID id, boolean enabled);

    Page<RES> search(String keyword, Pageable pageable);

    boolean existsById(ID id);

    boolean emailExists(String email);

    long count();
}
