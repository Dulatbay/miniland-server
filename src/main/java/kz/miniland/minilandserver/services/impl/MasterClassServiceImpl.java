package kz.miniland.minilandserver.services.impl;

import kz.miniland.minilandserver.dtos.request.RequestCreateMasterClassDto;
import kz.miniland.minilandserver.dtos.response.ResponseCardMasterClassDto;
import kz.miniland.minilandserver.entities.MasterClass;
import kz.miniland.minilandserver.entities.OrderMasterClass;
import kz.miniland.minilandserver.exceptions.DbObjectNotFoundException;
import kz.miniland.minilandserver.mappers.MasterClassMapper;
import kz.miniland.minilandserver.repositories.MasterClassRepository;
import kz.miniland.minilandserver.repositories.OrderMasterClassRepository;
import kz.miniland.minilandserver.repositories.OrderRepository;
import kz.miniland.minilandserver.services.FileService;
import kz.miniland.minilandserver.services.MasterClassService;
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
public class MasterClassServiceImpl implements MasterClassService {
    private final OrderMasterClassRepository orderMasterClassRepository;
    private final OrderRepository orderRepository;
    private final MasterClassRepository masterClassRepository;
    private final MasterClassMapper masterClassMapper;
    private final FileService fileService;

    @Override
    public void addMasterClassToOrder(String token, Long orderId, Long masterClassId) {
        var masterClass = masterClassRepository.findById(masterClassId)
                .orElseThrow(() -> new DbObjectNotFoundException(HttpStatus.BAD_REQUEST.getReasonPhrase(), "Master class doesn't exist"));

        var order = orderRepository.findById(orderId)
                .orElseThrow(() -> new DbObjectNotFoundException(HttpStatus.BAD_REQUEST.getReasonPhrase(), "Order doesn't exist"));

        OrderMasterClass orderMasterClass = new OrderMasterClass();
        orderMasterClass.setAuthorName(token);
        orderMasterClass.setAddedAt(LocalDateTime.now(ZONE_ID));
        orderMasterClass.setOrder(order);
        orderMasterClass.setMasterClass(masterClass);

        order.setFullPrice(order.getFullPrice() + orderMasterClass.getMasterClass().getPrice());
        orderRepository.save(order);
        orderMasterClassRepository.save(orderMasterClass);
    }

    public void createMasterClass(RequestCreateMasterClassDto requestCreateMasterClassDto) {
        // todo: make rollback if entity didn't saved
        MasterClass masterClass = new MasterClass();
        masterClass.setTitle(requestCreateMasterClassDto.getTitle());
        masterClass.setDescription(requestCreateMasterClassDto.getDescription());
        masterClass.setPrice(requestCreateMasterClassDto.getPrice());
        masterClass.setEnabled(true);

        if (requestCreateMasterClassDto.getImage() != null) {
            var image = requestCreateMasterClassDto.getImage();
            String filename = fileService.save(image);

            masterClass.setImageUrl(filename);
        }

        log.info("Created new master class, {}", masterClass);
        masterClassRepository.save(masterClass);
    }

    @Override
    public void disableMasterClass(Long id) {
        var masterClass = masterClassRepository.findById(id)
                .orElseThrow(() -> new DbObjectNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase(), "Master class doesn't exist"));

        if (!masterClass.getEnabled())
            throw new DbObjectNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase(), "Master class doesn't exist or already deleted");

        masterClass.setEnabled(false);
        masterClassRepository.save(masterClass);
    }

    @Override
    public List<ResponseCardMasterClassDto> getAllMasterClasses(boolean enabled) {
        var masterClasses = masterClassRepository.findAllByEnabled(enabled);
        return masterClassMapper.toDTO(masterClasses);
    }

    @Override
    public ResponseCardMasterClassDto getMasterClassById(Long id) {
        var masterClass = masterClassRepository.findById(id)
                .orElseThrow(() -> new DbObjectNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase(), "Master class doesn't exist"));

        return masterClassMapper.toDto(masterClass);
    }
}
