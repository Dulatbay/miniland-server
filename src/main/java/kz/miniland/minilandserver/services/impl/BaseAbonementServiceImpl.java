package kz.miniland.minilandserver.services.impl;

import kz.miniland.minilandserver.dtos.response.ResponseBaseAbonementDto;
import kz.miniland.minilandserver.mappers.BaseAbonementMapper;
import kz.miniland.minilandserver.repositories.BaseAbonementRepository;
import kz.miniland.minilandserver.services.BaseAbonementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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


}
