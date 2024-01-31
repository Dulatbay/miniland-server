package kz.miniland.minilandserver.services.impl;

import kz.miniland.minilandserver.repositories.RoomRepository;
import kz.miniland.minilandserver.services.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomServiceImpl implements RoomService {
    private RoomRepository roomRepository;
}
