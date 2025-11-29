package com.ayoub.cabinetdentaire.services.base;

import com.ayoub.cabinetdentaire.repoositories.BaseUserRepository;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public abstract class BaseUserServiceImpl<T, REQ, RES, ID> implements BaseUserService<REQ, RES, ID> {
    private final String USER_NOT_FOUND = "User Not Found";

    protected abstract BaseUserRepository<T, ID> getRepository();
    protected abstract T toEntity(REQ request);
    protected abstract RES toResponse(T entity);
    protected abstract void updateEntity(T entity, REQ request);
    protected abstract Specification<T> searchSpecification(String keyword);

    @Override
    public RES create(REQ request) {
        T entity = toEntity(request);
        return toResponse(getRepository().save(entity));
    }

    @Override
    public RES update(ID id, REQ request) {
        T entity = getRepository().findById(id)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND));
        updateEntity(entity, request);
        return toResponse(getRepository().save(entity));
    }

    @Override
    public RES getById(ID id) {
        return getRepository().findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND));
    }

    @Override
    public List<RES> getAll() {
        return getRepository().findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public void delete(ID id) {
        getRepository().deleteById(id);
    }

    @Override
    public RES getByEmail(String email) {
        return getRepository().findByEmail(email)
                .map(this::toResponse)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND));
    }

    @Override
    public RES changeStatus(ID id, boolean enabled) {
        T entity = getRepository().findById(id)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND));
        try {
            entity.getClass().getMethod("setEnabled", boolean.class)
                    .invoke(entity, enabled);
        } catch (Exception e) {
            throw new RuntimeException("Cannot change status");
        }
        return toResponse(getRepository().save(entity));
    }

    @Override
    public boolean existsById(ID id) {
        return getRepository().existsById(id);
    }

    @Override
    public boolean emailExists(String email) {
        return getRepository().existsByEmail(email);
    }

    @Override
    public long count() {
        return getRepository().count();
    }
}
