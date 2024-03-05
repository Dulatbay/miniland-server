package kz.miniland.minilandserver.services.impl;

import kz.miniland.minilandserver.dtos.request.RequestCreateBaseAbonementDto;
import kz.miniland.minilandserver.dtos.response.ResponseBaseAbonementDto;
import kz.miniland.minilandserver.entities.BaseAbonement;
import kz.miniland.minilandserver.exceptions.DbObjectNotFoundException;
import kz.miniland.minilandserver.mappers.BaseAbonementMapper;
import kz.miniland.minilandserver.repositories.BaseAbonementRepository;
import kz.miniland.minilandserver.services.BaseAbonementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static kz.miniland.minilandserver.constants.ValueConstants.ZONE_ID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BaseAbonementServiceImpl implements BaseAbonementService {

    private final BaseAbonementMapper baseAbonementMapper;
    private final BaseAbonementRepository baseAbonementRepository;


    @Override
    public List<ResponseBaseAbonementDto> getAllByEnabled(Boolean enabled) {

        return baseAbonementMapper
                .toDTO(baseAbonementRepository.getBaseAbonementsByEnabled(enabled));

    }

    @Override
    public void createBaseAbonement(RequestCreateBaseAbonementDto requestCreateBaseAbonementDto) {

        var baseAbonement = BaseAbonement.builder()
                .title(requestCreateBaseAbonementDto.getTitle())
                .description(requestCreateBaseAbonementDto.getDescription())
                .fullPrice(requestCreateBaseAbonementDto.getFullPrice())
                .fullTime(requestCreateBaseAbonementDto.getFullTime())
                .createdAt(LocalDateTime.now(ZONE_ID))
                .enabled(true)
                .build();

        log.info("Create Base Abonement: {}", baseAbonement);

        baseAbonementRepository.save(baseAbonement);

    }

    @Override
    public void deleteById(Long id) {

        var baseAbonement = baseAbonementRepository.findById(id)
                .orElseThrow(() -> new DbObjectNotFoundException(HttpStatus.BAD_REQUEST.getReasonPhrase(), "BaseAbonement doesn't exist"));

        if(!baseAbonement.isEnabled())
            throw new DbObjectNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase(), "BaseAbonement doesn't exist or already deleted");


        log.info("Disabled baseAbonement: {}", baseAbonement);

        baseAbonement.setEnabled(false);

        baseAbonementRepository.save(baseAbonement);

    }


}
