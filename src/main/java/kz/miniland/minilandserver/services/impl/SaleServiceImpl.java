package kz.miniland.minilandserver.services.impl;

import kz.miniland.minilandserver.dtos.request.RequestCreateSaleDto;
import kz.miniland.minilandserver.dtos.response.ResponseSaleDto;
import kz.miniland.minilandserver.entities.Sale;
import kz.miniland.minilandserver.exceptions.DbObjectNotFoundException;
import kz.miniland.minilandserver.mappers.SaleMapper;
import kz.miniland.minilandserver.repositories.SaleRepository;
import kz.miniland.minilandserver.services.SaleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;
    private final SaleMapper saleMapper;

    @Override
    public List<ResponseSaleDto> getAll(Boolean enabled) {
        var entities = saleRepository.findSalesByEnabled(enabled);
        List<ResponseSaleDto> allSales =  saleMapper.toDTO(entities);
        log.info("All sales size: {}", allSales.size());
        return allSales;
    }

    @Override
    public void createSale(RequestCreateSaleDto requestCreateSaleDto) {
        Sale sale = new Sale();
        sale.setTitle(requestCreateSaleDto.getTitle());
        sale.setFullPrice(requestCreateSaleDto.getFullPrice());
        sale.setFullTime(requestCreateSaleDto.getFullTime());
        sale.setEnabled(true);
        log.info("Created new sale: {}", sale);
        saleRepository.save(sale);
    }

    @Override
    public void deleteSale(Long id) {
        var sale = saleRepository.findById(id)
                .orElseThrow(() -> new DbObjectNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase(), "Sale doesn't exist"));

        if (!sale.isEnabled())
            throw new DbObjectNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase(), "Sale doesn't exist or already deleted");
        log.info("Disabled sale by id: {}", id);
        sale.setEnabled(false);
        saleRepository.save(sale);
    }
}
