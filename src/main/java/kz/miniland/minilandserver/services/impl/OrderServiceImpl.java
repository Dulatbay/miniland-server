package kz.miniland.minilandserver.services.impl;

import kz.miniland.minilandserver.dtos.RequestCreateOrderDto;
import kz.miniland.minilandserver.dtos.ResponseCardOrderDto;
import kz.miniland.minilandserver.dtos.ResponseDetailOrderDto;
import kz.miniland.minilandserver.dtos.ResponseDirectorMainReport;
import kz.miniland.minilandserver.exceptions.DbObjectNotFoundException;
import kz.miniland.minilandserver.mappers.custom.OrderCustomMapper;
import kz.miniland.minilandserver.repositories.OrderRepository;
import kz.miniland.minilandserver.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.collection.spi.PersistentMap;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static kz.miniland.minilandserver.constants.ValueConstants.ZONE_ID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderCustomMapper orderCustomMapper;
    private int reqCount = 0;
    @Override
    public void createOrder(RequestCreateOrderDto requestCreateOrderDto) {
        var orderEntity = orderCustomMapper.toEntity(requestCreateOrderDto);
        var entity = orderRepository.save(orderEntity);
        log.info("{}", entity);
    }

    @Override
    public List<ResponseCardOrderDto> getOrderCards() {
        var orderEntities = orderRepository.findAll();
        log.info("{}", reqCount++);
        return orderCustomMapper.toCardDto(orderEntities);
    }

    @Override
    public ResponseDetailOrderDto getDetailOrderById(Long id) {
        var orderEntity = orderRepository.findById(id).orElseThrow(() -> new DbObjectNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase(), "Order doesn't exist"));
        return orderCustomMapper.toDetailDto(orderEntity);
    }

    @Override
    public void finishOrderById(Long id, Boolean isPaid) {
        var orderEntity = orderRepository.findById(id).orElseThrow(() -> new DbObjectNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase(), "Order doesn't exist"));
        if(!isPaid) throw new IllegalArgumentException("Order must be paid before finishing!");
        orderEntity.setIsPaid(true);
        orderEntity.setIsFinished(true);
        orderEntity.setFinishedAt(LocalDateTime.now(ZONE_ID));
        orderRepository.save(orderEntity);
    }

    @Override
    public ResponseDirectorMainReport getMainDirectorReport() {
        // Get the current date
        LocalDate today = LocalDate.now();

        LocalDateTime startOfDay = LocalDateTime.of(today, LocalTime.MIN);

        LocalDateTime endOfDay = LocalDateTime.of(today, LocalTime.MAX);

        var entities = orderRepository.findByCreatedAtBetween(startOfDay, endOfDay);

        ResponseDirectorMainReport directorMainReport = new ResponseDirectorMainReport();
        directorMainReport.setOrdersCount(entities.size());

        Map<String, ResponseDirectorMainReport.Employee> employeeMap = new HashMap<>();

        entities.forEach((entity)->{
            var employee = employeeMap.get(entity.getAuthorName());
            if(employee ==  null){
                employee = new ResponseDirectorMainReport.Employee();
                employee.setProfit(entity.getFullPrice());
                employee.setUsername(entity.getAuthorName());
                employee.setOrdersCount(1);
                employee.setServeTime(entity.getFullTime());
                employeeMap.put(employee.getUsername(), employee);
            }
            else{
                employee.setOrdersCount(employee.getOrdersCount()+1);
                employee.setProfit(entity.getFullPrice() + entity.getFullPrice());
                employee.setServeTime(employee.getServeTime() + entity.getFullTime());
            }
        });

        directorMainReport.setEmployees(new ArrayList<>(employeeMap.values()));

        return directorMainReport;
    }
}
