package kz.miniland.minilandserver.services.impl;

import kz.miniland.minilandserver.dtos.request.RequestCreateTariffDto;
import kz.miniland.minilandserver.dtos.response.ResponseCardRoomTariffDto;
import kz.miniland.minilandserver.dtos.response.ResponseDetailRoomTariffDto;
import kz.miniland.minilandserver.exceptions.DbObjectNotFoundException;
import kz.miniland.minilandserver.mappers.custom.RoomTariffCustomMapper;
import kz.miniland.minilandserver.repositories.RoomTariffRepository;
import kz.miniland.minilandserver.services.RoomTariffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomTariffServiceImpl implements RoomTariffService {
    private final RoomTariffRepository roomTariffRepository;
    private final RoomTariffCustomMapper roomTariffCustomMapper;

    @Override
    public ResponseDetailRoomTariffDto getTariffById(Long id) {
        var roomOrderEntity = roomTariffRepository.findById(id)
                .orElseThrow(() -> new DbObjectNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase(), "Room order doesn't exist"));

        if (!roomOrderEntity.getEnabled())
            throw new DbObjectNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase(), "Room tariff doesn't exist or already deleted");


        return roomTariffCustomMapper.toDto(roomOrderEntity);
    }

    @Override
    public List<ResponseCardRoomTariffDto> getAllTariffsByEnabled(Boolean enabled) {
        var roomTariff = roomTariffRepository.getAllByEnabled(enabled);

        return roomTariff
                .stream()
                .map(roomTariffCustomMapper::toCardDto)
                .collect(Collectors.toList());
    }

    @Override
    public void create(RequestCreateTariffDto requestCreateTariffDto) {
        if (requestCreateTariffDto.getStartedAt().isAfter(requestCreateTariffDto.getFinishedAt())
                || requestCreateTariffDto.getStartedAt().toString().equals(requestCreateTariffDto.getFinishedAt().toString())) {
            throw new IllegalArgumentException("Started at time must be before finished at time.");
        }
        roomTariffRepository.save(roomTariffCustomMapper.toEntity(requestCreateTariffDto));
    }

    @Override
    public void disableRoomTariffById(Long id) {
        var roomTariff = roomTariffRepository.findById(id)
                .orElseThrow(() -> new DbObjectNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase(), "Room tariff doesn't exist"));

        if (!roomTariff.getEnabled())
            throw new DbObjectNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase(), "Room tariff doesn't exist or already deleted");

        roomTariff.setEnabled(false);
        roomTariffRepository.save(roomTariff);
    }
}
