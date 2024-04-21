package kz.miniland.minilandserver.services.impl;

import kz.miniland.minilandserver.dtos.request.RequestCreateSaleWithPercentDto;
import kz.miniland.minilandserver.dtos.response.ResponseSaleWithPercentDto;
import kz.miniland.minilandserver.entities.SaleWithPercent;
import kz.miniland.minilandserver.exceptions.DbObjectNotFoundException;
import kz.miniland.minilandserver.mappers.SaleWithPercentMapper;
import kz.miniland.minilandserver.repositories.SaleWithPercentRepository;
import kz.miniland.minilandserver.services.SaleWithPercentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SaleWithPercentServiceImpl implements SaleWithPercentService {

    private final SaleWithPercentRepository saleWithPercentRepository;
    private final SaleWithPercentMapper saleWithPercentMapper;

    @Override
    public List<ResponseSaleWithPercentDto> getAllSalesWithPercent(Boolean enabled) {

        var salesWithPercent = saleWithPercentRepository
                .getSaleWithPercentByEnabled(enabled);

        List<ResponseSaleWithPercentDto> allSalesWithPercent = saleWithPercentMapper
                .toDTO(salesWithPercent);
        log.info("Size of list all sales with percent : {}", allSalesWithPercent.size());
        return allSalesWithPercent;
    }

    @Override
    public void createSaleWithPercent(RequestCreateSaleWithPercentDto requestCreateSaleWithPercentDto) {

        var saleWithPercent = new SaleWithPercent();

        saleWithPercent.setPercent(requestCreateSaleWithPercentDto.getPercent());
        saleWithPercent.setTitle(requestCreateSaleWithPercentDto.getTitle());
        saleWithPercent.setEnabled(true);

        log.info("created new saleWithPercent: {}", requestCreateSaleWithPercentDto);

        saleWithPercentRepository.save(saleWithPercent);
    }

    @Override
    public void deleteSaleWithPercent(Long id) {

        var saleWithPercent = saleWithPercentRepository.findById(id)
                .orElseThrow(() -> new DbObjectNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase(), "SaleWithPercent doesn't exist"));

        if(!saleWithPercent.isEnabled())
            throw new DbObjectNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase(), "SaleWithPercent doesn't exist or already deleted");

        log.info("Deleted saleWithPercent: {}", saleWithPercent);

        saleWithPercentRepository.deleteById(id);
    }
}
