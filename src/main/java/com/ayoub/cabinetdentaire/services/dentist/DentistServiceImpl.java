package com.ayoub.cabinetdentaire.services.dentist;

import com.ayoub.cabinetdentaire.dtos.dentist.DentistRequest;
import com.ayoub.cabinetdentaire.dtos.dentist.DentistResponse;
import com.ayoub.cabinetdentaire.entities.Dentist;
import com.ayoub.cabinetdentaire.enums.Specialization;
import com.ayoub.cabinetdentaire.mappers.DentistMapper;
import com.ayoub.cabinetdentaire.repoositories.DentistRepository;
import com.ayoub.cabinetdentaire.repoositories.BaseUserRepository;
import com.ayoub.cabinetdentaire.services.base.BaseUserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class DentistServiceImpl
        extends BaseUserServiceImpl<Dentist, DentistRequest, DentistResponse, UUID>
        implements DentistService {

    private final DentistRepository dentistRepository;
    private final DentistMapper dentistMapper;

    @Override
    protected BaseUserRepository<Dentist, UUID> getRepository() {
        return dentistRepository;
    }

    @Override
    protected Dentist toEntity(DentistRequest request) {
        return dentistMapper.toEntity(request);
    }

    @Override
    protected DentistResponse toResponse(Dentist entity) {
        return dentistMapper.toResponse(entity);
    }

    @Override
    protected void updateEntity(Dentist entity, DentistRequest request) {
        entity.setFirstName(request.getFirstName());
        entity.setLastName(request.getLastName());
        entity.setEmail(request.getEmail());
        entity.setPhone(request.getPhone());
        entity.setAddress(request.getAddress());
        entity.setLicenseNumber(request.getLicenseNumber());
        entity.setSpecialization(request.getSpecialization());
        entity.setHourlyRate(request.getHourlyRate());
        entity.setOfficeHours(request.getOfficeHours());
    }

    @Override
    protected Specification<Dentist> searchSpecification(String keyword) {
        return (root, query, cb) ->
                cb.or(
                        cb.like(cb.lower(root.get("firstName")), "%" + keyword.toLowerCase() + "%"),
                        cb.like(cb.lower(root.get("lastName")), "%" + keyword.toLowerCase() + "%"),
                        cb.like(cb.lower(root.get("email")), "%" + keyword.toLowerCase() + "%")
                );
    }

    @Override
    public Page<DentistResponse> search(String keyword, Pageable pageable) {
        return dentistRepository.findAll(searchSpecification(keyword), pageable)
                .map(this::toResponse);
    }

    @Override
    public Page<DentistResponse> getDentistsBySpecialization(Specialization specialization,
                                                             Pageable pageable) {
        return dentistRepository.findBySpecialization(specialization, pageable)
                .map(this::toResponse);
    }

    @Override
    public DentistResponse getDentistByLicenseNumber(String licenseNumber) {
        return dentistRepository.findByLicenseNumber(licenseNumber)
                .map(this::toResponse)
                .orElseThrow(() -> new RuntimeException("Dentist not found"));
    }

    @Override
    public DentistResponse updateDentistSchedule(UUID userId, String schedule) {
        Dentist dentist = dentistRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Dentist not found"));
        dentist.setOfficeHours(schedule);
        return toResponse(dentistRepository.save(dentist));
    }

    @Override
    public List<DentistResponse> getDentistsByRoleName(String roleName) {
        return dentistRepository.findAll().stream()
                .filter(d -> d.getRoles().stream()
                        .anyMatch(r -> r.getName().equalsIgnoreCase(roleName)))
                .map(this::toResponse)
                .toList();
    }
}
