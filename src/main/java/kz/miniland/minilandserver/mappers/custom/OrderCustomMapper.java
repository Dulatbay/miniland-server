package kz.miniland.minilandserver.mappers.custom;

import kz.miniland.minilandserver.constants.ValueConstants;
import kz.miniland.minilandserver.dtos.RequestCreateOrderDto;
import kz.miniland.minilandserver.dtos.ResponseCardOrderDto;
import kz.miniland.minilandserver.dtos.ResponseDetailOrderDto;
import kz.miniland.minilandserver.entities.Order;
import kz.miniland.minilandserver.entities.Sale;
import kz.miniland.minilandserver.mappers.SaleMapper;
import kz.miniland.minilandserver.repositories.PriceRepository;
import kz.miniland.minilandserver.repositories.SaleRepository;
import kz.miniland.minilandserver.services.KeycloakService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static kz.miniland.minilandserver.constants.ValueConstants.ZONE_ID;

@Component
@RequiredArgsConstructor
public class OrderCustomMapper {
    private final SaleRepository saleRepository;
    private final PriceRepository priceRepository;
    private final SaleMapper saleMapper;

    public Order toEntity(RequestCreateOrderDto requestCreateOrderDto) {
        Order order = new Order();
        order.setAuthorName(requestCreateOrderDto.getAuthorId());
        order.setParentName(requestCreateOrderDto.getParentName());
        order.setPhoneNumber(requestCreateOrderDto.getParentPhoneNumber());
        order.setChildName(requestCreateOrderDto.getChildName());
        order.setChildAge(requestCreateOrderDto.getChildAge());

        Sale sale = new Sale();
        if (requestCreateOrderDto.getSaleId() != null) {
            sale = saleRepository.findById(requestCreateOrderDto.getSaleId()).orElseThrow(() -> new IllegalArgumentException("Sale doesn't exist"));
            order.setSale(sale);
        } else {
            sale.setFullTime(0L);
            sale.setFullPrice(0.0);
            order.setSale(null);
        }


        double resultPrice = 0;
        if (requestCreateOrderDto.getExtraTime() != 0) {
            var prices = priceRepository.findAllByOrderByFullPriceDesc();
            if (prices.isEmpty())
                throw new IllegalArgumentException("Price list is empty");
            if (prices.getLast().getFullTime() > requestCreateOrderDto.getExtraTime())
                throw new IllegalArgumentException("Extra time is too short");


            double requestTime = requestCreateOrderDto.getExtraTime();

            for (var p : prices) {
                while (requestTime >= p.getFullTime()) {
                    requestTime -= p.getFullTime();
                    resultPrice += p.getFullPrice();
                }
            }

            while (requestTime > 0) {
                requestTime -= prices.getLast().getFullTime();
                resultPrice += prices.getLast().getFullPrice();
            }
        }


        order.setExtraTime(requestCreateOrderDto.getExtraTime());
        order.setFullTime(sale.getFullTime() + requestCreateOrderDto.getExtraTime());
        order.setFullPrice(resultPrice + sale.getFullPrice());
        order.setIsPaid(requestCreateOrderDto.getIsPaid());
        order.setCreatedAt(LocalDateTime.now(ZONE_ID));
        order.setIsFinished(false);
        return order;
    }

    public List<ResponseCardOrderDto> toCardDto(List<Order> orderEntities) {
        var enteredTimeFormat = DateTimeFormatter.ofPattern("hh:mm");
        var responseCardOrderDtos = new ArrayList<ResponseCardOrderDto>();
        var now = LocalDateTime.now(ZONE_ID);
        for (var orderEntity : orderEntities) {
            ResponseCardOrderDto responseCardOrderDto = new ResponseCardOrderDto();
            responseCardOrderDto.setId(orderEntity.getId());
            responseCardOrderDto.setChildName(orderEntity.getChildName());
            responseCardOrderDto.setAge(orderEntity.getChildAge());
            responseCardOrderDto.setParentName(orderEntity.getParentName());
            responseCardOrderDto.setEnteredTime(orderEntity.getCreatedAt().format(enteredTimeFormat));
            Duration duration = Duration.between(orderEntity.getCreatedAt(), orderEntity.getFinishedAt() == null ? now : orderEntity.getFinishedAt());
            responseCardOrderDto.setRemainTime(orderEntity.getFullTime() - duration.getSeconds());
            responseCardOrderDto.setFullPrice(orderEntity.getFullPrice());
            responseCardOrderDto.setFullTime(orderEntity.getFullTime());
            responseCardOrderDto.setIsPaid(orderEntity.getIsPaid());
            responseCardOrderDto.setIsFinished(orderEntity.getIsFinished());
            responseCardOrderDtos.add(responseCardOrderDto);
        }
        return responseCardOrderDtos;
    }

    public ResponseDetailOrderDto toDetailDto(Order orderEntity) {
        var enteredTimeFormat = DateTimeFormatter.ofPattern("hh:mm");

        ResponseDetailOrderDto responseDetailOrderDto = new ResponseDetailOrderDto();
        responseDetailOrderDto.setId(orderEntity.getId());
        responseDetailOrderDto.setChildName(orderEntity.getChildName());
        responseDetailOrderDto.setChildAge(orderEntity.getChildAge());
        responseDetailOrderDto.setParentName(orderEntity.getParentName());
        responseDetailOrderDto.setEnteredTime(orderEntity.getCreatedAt().format(enteredTimeFormat));

        var now = orderEntity.getFinishedAt() == null ? LocalDateTime.now(ZONE_ID) : orderEntity.getFinishedAt();
        Duration duration = Duration.between(orderEntity.getCreatedAt(), now);
        responseDetailOrderDto.setRemainTime(orderEntity.getFullTime() - duration.getSeconds());

        responseDetailOrderDto.setSale(saleMapper.toDto(orderEntity.getSale()));
        responseDetailOrderDto.setExtraTime(orderEntity.getExtraTime());
        responseDetailOrderDto.setFullPrice(orderEntity.getFullPrice());
        responseDetailOrderDto.setFullTime(orderEntity.getFullTime());
        responseDetailOrderDto.setIsPaid(orderEntity.getIsPaid());
        responseDetailOrderDto.setIsFinished(orderEntity.getIsFinished());
        responseDetailOrderDto.setParentPhoneNumber(orderEntity.getPhoneNumber());
        responseDetailOrderDto.setFinishedAt(orderEntity.getFinishedAt());
        responseDetailOrderDto.setAuthorName(orderEntity.getAuthorName());

        return responseDetailOrderDto;
    }
}
