package kz.miniland.minilandserver.services;

import kz.miniland.minilandserver.dtos.request.RequestCreateBaseAbonementDto;
import kz.miniland.minilandserver.dtos.response.ResponseBaseAbonementDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BaseAbonementService {

    List<ResponseBaseAbonementDto> getAllByEnabled(Boolean enabled);

    void createBaseAbonement(RequestCreateBaseAbonementDto requestCreateBaseAbonementDto);

    void deleteById(Long id);

}
