package edu.bbte.idde.kzim2149.controller;

import edu.bbte.idde.kzim2149.dto.incoming.CreatePromoDto;
import edu.bbte.idde.kzim2149.dto.outgoing.GetPromoDto;
import edu.bbte.idde.kzim2149.exception.BadRequestException;
import edu.bbte.idde.kzim2149.mapper.PromoMapper;
import edu.bbte.idde.kzim2149.model.Promo;
import edu.bbte.idde.kzim2149.service.PromoService;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@CrossOrigin(maxAge = 3600)
@Slf4j
@Profile("jpa")
@RestController
@RequestMapping("/promos")
public class PromoController {
    @Autowired
    private PromoService promoService;

    @Autowired
    private PromoMapper promoMapper;

    @PostConstruct
    public void postConstruct() {
        log.info("PromoController created");
    }

    @GetMapping
    public Collection<GetPromoDto> findAll() {
        log.info("GET /promos called");
        return promoMapper.modelsToGetDtos(promoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetPromoDto> findById(@PathVariable("id") Long id) {
        log.info("GET /promos/{} called", id);
        GetPromoDto dto =  promoMapper.modelToGetDto(promoService.findById(id));
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<GetPromoDto> create(@RequestBody @Valid CreatePromoDto promoDto) {
        log.info("POST /promos called");

        if (promoDto == null) {
            throw new BadRequestException("Request body is null");
        }

        Promo promo = promoMapper.createDtoToModel(promoDto);
        promo = promoService.insert(promo);
        GetPromoDto getPromoDto = promoMapper.modelToGetDto(promo);
        URI createdUri = URI.create("/promos/" + getPromoDto.getId());
        return ResponseEntity.created(createdUri).body(getPromoDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody @Valid CreatePromoDto promoDto) {
        log.info("PUT /promos/{} called", id);

        if (promoDto == null) {
            throw new BadRequestException("Request body is null");
        }

        Promo promo = promoMapper.createDtoToModel(promoDto);
        promo.setId(id);
        promoService.update(id, promo);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        log.info("DELETE /promos/{} called", id);
        if (id == null) {
            throw new BadRequestException("Id is null");
        }

        promoService.delete(id);
    }

}
