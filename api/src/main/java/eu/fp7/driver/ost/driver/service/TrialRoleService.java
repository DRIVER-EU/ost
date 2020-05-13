package eu.fp7.driver.ost.driver.service;

import eu.fp7.driver.ost.driver.model.AuthUser;
import eu.fp7.driver.ost.driver.dto.TrialRoleDTO;
import eu.fp7.driver.ost.driver.model.Trial;
import eu.fp7.driver.ost.driver.model.TrialRole;
import eu.fp7.driver.ost.driver.model.enums.RoleType;
import eu.fp7.driver.ost.driver.repository.TrialRepository;
import eu.fp7.driver.ost.driver.repository.TrialRoleRepository;
import eu.fp7.driver.ost.driver.repository.TrialUserRepository;
import eu.fp7.driver.ost.driver.repository.specification.TrialRoleSpecification;
import eu.fp7.driver.ost.driver.util.InvalidDataException;
import eu.fp7.driver.ost.driver.util.RepositoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class TrialRoleService {

    @Autowired
    private TrialUserRepository trialUserRepository;

    @Autowired
    private TrialRoleRepository trialRoleRepository;

    @Autowired
    private TrialRepository trialRepository;

    @Autowired
    private TrialUserService trialUserService;

    public Page<TrialRole> findByTrialSessionId(Long trialSessionId, Pageable pageable) {
        String keycloakUserId = trialUserService.getCurrentKeycloakUserId();
        trialUserService.checkIsTrialSessionManager(keycloakUserId, trialSessionId);

        Set<Specification<TrialRole>> conditions = new HashSet<>();
        conditions.add(TrialRoleSpecification.trialRole(trialSessionId));
        return trialRoleRepository.findAll(RepositoryUtils.concatenate(conditions), pageable);
    }

    public Page<TrialRole> findByTrialId(Long trialId, Pageable pageable) {
        return trialRoleRepository.findAllByTrialId(trialId, pageable);
    }

    @Transactional(readOnly = true)
    public TrialRole findById(Long id) {
        TrialRole trialRole = trialRoleRepository.findById(id).orElseThrow(() -> new InvalidDataException("No trial role found with given id = " + id));
        return trialRole;
    }

    @Transactional
    public void delete(long id) {
        TrialRole trialRole = trialRoleRepository.findById(id).orElseThrow(() -> new InvalidDataException("No trial role found with given id = " + id));
        trialRoleRepository.delete(trialRole);
    }

    @Transactional
    public TrialRole update(TrialRoleDTO.FullItem trialRoleDTO) {

        validateTrialRoleDTO(trialRoleDTO);

        TrialRole trialRole = trialRoleRepository.findById(trialRoleDTO.getId()).orElseThrow(() -> new InvalidDataException("No trial role found with given id = " + trialRoleDTO.getId()));
        trialRole.setName(trialRoleDTO.getName());
        trialRole.setRoleType(RoleType.valueOf(trialRoleDTO.getRoleType()));

        return trialRoleRepository.save(trialRole);
    }



    private List<RoleType> getRoleTypes() {
        ArrayList<RoleType> roleTypes = new ArrayList<>();

        for (RoleType roleType : RoleType.values()) {
            roleTypes.add(roleType);
        }
        return roleTypes;
    }
    private  boolean isRoleTypeDefined(String trialRoleType)
    {
        return getRoleTypes().contains(RoleType.valueOf(trialRoleType));
    }

    private void validateTrialRoleDTO(TrialRoleDTO.FullItem trialRoleDTO) {
        if (trialRoleDTO.getId() == 0) {
            throw new InvalidDataException("No trial role Id was given");
        }
        if (!isRoleTypeDefined(trialRoleDTO.getRoleType())) {
            throw new InvalidDataException("Undefined role type was given Id was given. Defined role types: "+ getRoleTypes().toString());
        }
    }



    @Transactional
    public TrialRole insert(TrialRoleDTO.FullItem trialRoleDTO) {

        if (!isRoleTypeDefined(trialRoleDTO.getRoleType())) {
            throw new InvalidDataException("Undefined role type was given Id was given. Defined role types: "+getRoleTypes().toString());
        }
        Trial trial = trialRepository.findById(trialRoleDTO.getTrialId())
                .orElseThrow(() -> new InvalidDataException("No trial was found with given id = " + trialRoleDTO.getTrialId()));


        TrialRole trialRole = TrialRole.builder()

                .name(trialRoleDTO.getName())
                .roleType(RoleType.valueOf(trialRoleDTO.getRoleType()))
                .trial(trial)
                .build();

        return trialRoleRepository.save(trialRole);

    }

    @Transactional
    public TrialRoleDTO.FullItem getFullTrialRole(Long id) {
        TrialRole trialRole = trialRoleRepository.findById(id).orElseThrow(() -> new InvalidDataException("No trial role found with given id = " + id));
        TrialRoleDTO.FullItem trialRoleDTO = new TrialRoleDTO.FullItem();
        trialRoleDTO.toDto(trialRole);
        return trialRoleDTO;
    }


}
