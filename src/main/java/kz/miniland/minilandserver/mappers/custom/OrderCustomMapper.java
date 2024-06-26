package kz.miniland.minilandserver.mappers.custom;

import kz.miniland.minilandserver.dtos.request.RequestCreateOrderDto;
import kz.miniland.minilandserver.dtos.response.ResponseCardOrderDto;
import kz.miniland.minilandserver.dtos.response.ResponseDetailOrderDto;
import kz.miniland.minilandserver.entities.Order;
import kz.miniland.minilandserver.entities.Sale;
import kz.miniland.minilandserver.entities.SaleWithPercent;
import kz.miniland.minilandserver.entities.WeekDays;
import kz.miniland.minilandserver.mappers.SaleMapper;
import kz.miniland.minilandserver.mappers.SaleWithPercentMapper;
import kz.miniland.minilandserver.repositories.PriceRepository;
import kz.miniland.minilandserver.repositories.SaleRepository;
import kz.miniland.minilandserver.repositories.SaleWithPercentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static kz.miniland.minilandserver.constants.ValueConstants.ZONE_ID;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderCustomMapper {
    private final SaleRepository saleRepository;
    private final PriceRepository priceRepository;
    private final SaleMapper saleMapper;
    private final SaleWithPercentRepository saleWithPercentRepository;
    private final SaleWithPercentMapper saleWithPercentMapper;
    private final DateTimeFormatter enteredTimeFormat = DateTimeFormatter.ofPattern("HH:mm");

    private Double getFullPrice(LocalDateTime now, Long extraTime, boolean isPenalty) {
        if (extraTime == 0) return 0.0;

        double resultPrice = 0;

        DayOfWeek dayOfWeek = now.getDayOfWeek();
        var prices = priceRepository.findAllByEnabledIsTrueOrderByFullPriceDesc()
                .stream()
                .filter(i -> i
                        .getDays()
                        .stream()
                        .map(WeekDays::getInteger)
                        .anyMatch(integer -> dayOfWeek.getValue() == integer)
                ).toList();


        if (prices.isEmpty())
            throw new IllegalArgumentException("Price list today is empty");

        log.info("{}, {}", prices.getFirst().getFullTime(), prices.getLast().getFullTime());

        if (prices.getFirst().getFullTime() > extraTime)
            if (!isPenalty)
                throw new IllegalArgumentException("Extra time is too short, minimum: " + prices.getFirst().getFullTime());


        double requestTime = extraTime;

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
        return resultPrice;
    }

    public Order toEntity(RequestCreateOrderDto requestCreateOrderDto) {
        var now = LocalDateTime.now(ZONE_ID);
        log.info("Create order at {}", now);
        Order order = new Order();
        order.setAuthorName(requestCreateOrderDto.getAuthorId());
        order.setParentName(requestCreateOrderDto.getParentName());
        order.setPhoneNumber(requestCreateOrderDto.getParentPhoneNumber());
        order.setChildName(requestCreateOrderDto.getChildName());
        order.setChildAge(requestCreateOrderDto.getChildAge());

        Sale sale = new Sale();
        if (requestCreateOrderDto.getSaleId() != null) {
            sale = saleRepository.findById(requestCreateOrderDto.getSaleId())
                    .orElseThrow(() -> new IllegalArgumentException("Sale doesn't exist"));
            order.setSale(sale);
        } else {
            sale.setFullTime(0L);
            sale.setFullPrice(0.0);
            order.setSale(null);
        }
        SaleWithPercent saleWithPercent = new SaleWithPercent();

        if (requestCreateOrderDto.getSaleWithPercentId() != null) {

            saleWithPercent = saleWithPercentRepository
                    .findById(requestCreateOrderDto.getSaleWithPercentId())
                    .orElseThrow(() -> new IllegalArgumentException("SaleWithPercent doesn't exist"));
            log.info("Selected sale: {}", sale);

            order.setSaleWithPercent(saleWithPercent);

        } else {

            saleWithPercent.setPercent(0);
            order.setSaleWithPercent(null);

        }

        var priceWithSales = getFullPrice(now, requestCreateOrderDto.getExtraTime(), false) + sale.getFullPrice();
        var finalPrice = priceWithSales - priceWithSales * (double) saleWithPercent.getPercent() / 100.0;

        log.info("Discount: {}, price: {}, finalPrice: {}", saleWithPercent.getPercent(), priceWithSales, finalPrice);

        order.setExtraTime(requestCreateOrderDto.getExtraTime());
        order.setFullTime(sale.getFullTime() + requestCreateOrderDto.getExtraTime());
        order.setFullPrice(finalPrice);

        log.info("requestCreateOrderDto.getExtraTime(): {}\tsale.getFullPrice(): {}", requestCreateOrderDto.getExtraTime(), sale.getFullPrice());
        // order.setFullPrice(getFullPriceWithPromotionPercent(order.getFullPrice(), promotion))


        order.setIsPaid(requestCreateOrderDto.getIsPaid());
        order.setCreatedAt(now);
        order.setIsFinished(false);

        return order;
    }

    // todo: сколько осталось заплатить
    public List<ResponseCardOrderDto> toCardDto(List<Order> orderEntities) {
        var responseCardOrderDtos = new ArrayList<ResponseCardOrderDto>();
        var now = LocalDateTime.now(ZONE_ID);
        for (var orderEntity : orderEntities) {
            ResponseCardOrderDto responseCardOrderDto = new ResponseCardOrderDto();
            responseCardOrderDto.setId(orderEntity.getId());
            responseCardOrderDto.setChildName(orderEntity.getChildName());
            responseCardOrderDto.setAge(orderEntity.getChildAge());
            responseCardOrderDto.setParentName(orderEntity.getParentName());
            responseCardOrderDto.setPhoneNumber(orderEntity.getPhoneNumber());
            responseCardOrderDto.setEnteredTime(orderEntity.getCreatedAt().format(enteredTimeFormat));
            Duration duration = Duration.between(orderEntity.getCreatedAt(), orderEntity.getFinishedAt() == null ? now : orderEntity.getFinishedAt());
            var remainTime = orderEntity.getFullTime() - duration.getSeconds();
            responseCardOrderDto.setRemainTime(remainTime);
            responseCardOrderDto.setFullPrice(orderEntity.getFullPrice());
            responseCardOrderDto.setFullTime(orderEntity.getFullTime());
            responseCardOrderDto.setIsPaid(remainTime > 0 ? orderEntity.getIsPaid() : false);
            responseCardOrderDto.setIsFinished(orderEntity.getIsFinished());
            responseCardOrderDto.setAuthorName(orderEntity.getAuthorName());
            responseCardOrderDtos.add(responseCardOrderDto);
        }
        return responseCardOrderDtos;
    }

    public ResponseDetailOrderDto toDetailDto(Order orderEntity) {
        ResponseDetailOrderDto responseDetailOrderDto = new ResponseDetailOrderDto();
        responseDetailOrderDto.setId(orderEntity.getId());
        responseDetailOrderDto.setChildName(orderEntity.getChildName());
        responseDetailOrderDto.setChildAge(orderEntity.getChildAge());
        responseDetailOrderDto.setParentName(orderEntity.getParentName());
        responseDetailOrderDto.setEnteredTime(orderEntity.getCreatedAt().format(enteredTimeFormat));

        var now = orderEntity.getFinishedAt() == null ? LocalDateTime.now(ZONE_ID) : orderEntity.getFinishedAt();
        Duration duration = Duration.between(orderEntity.getCreatedAt(), now);
        var remainTime = orderEntity.getFullTime() - duration.getSeconds();
        responseDetailOrderDto.setRemainTime(remainTime);

        responseDetailOrderDto.setSale(saleMapper.toDto(orderEntity.getSale()));
        //added
        responseDetailOrderDto.setSaleWithPercent(saleWithPercentMapper.toDto(orderEntity.getSaleWithPercent()));

        responseDetailOrderDto.setExtraTime(orderEntity.getExtraTime());

        var penaltyPrice = 0;
        if (remainTime < 0) {
            log.info("remainTime: {}", remainTime);
            penaltyPrice += getFullPrice(now, -1 * remainTime, true);
        }

        responseDetailOrderDto.setFullPrice(orderEntity.getFullPrice() + penaltyPrice);

        responseDetailOrderDto.setFullTime(orderEntity.getFullTime());
        responseDetailOrderDto.setIsPaid(orderEntity.getIsPaid());
        responseDetailOrderDto.setIsFinished(orderEntity.getIsFinished());
        responseDetailOrderDto.setParentPhoneNumber(orderEntity.getPhoneNumber());
        responseDetailOrderDto.setFinishedAt(orderEntity.getFinishedAt());
        responseDetailOrderDto.setAuthorName(orderEntity.getAuthorName());

        return responseDetailOrderDto;
    }
}
