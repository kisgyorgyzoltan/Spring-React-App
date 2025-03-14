package edu.bbte.idde.kzim2149.controller;

import edu.bbte.idde.kzim2149.dto.incoming.CreatePcPartDto;
import edu.bbte.idde.kzim2149.dto.outgoing.GetPcPartDto;
import edu.bbte.idde.kzim2149.dto.outgoing.GetPrebuiltPCDto;
import edu.bbte.idde.kzim2149.exception.BadRequestException;
import edu.bbte.idde.kzim2149.exception.NotFoundException;
import edu.bbte.idde.kzim2149.mapper.PcPartMapper;
import edu.bbte.idde.kzim2149.mapper.PrebuiltPcMapper;
import edu.bbte.idde.kzim2149.model.PcPart;
import edu.bbte.idde.kzim2149.service.PcPartService;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.net.URI;
import java.util.Collection;

@CrossOrigin(maxAge = 3600)
@Slf4j
@RestController
@RequestMapping("/pcparts")
public class PcPartController {

    @Autowired
    private PcPartService pcPartsService;
    @Autowired
    private PcPartMapper pcPartMapper;
    @Autowired
    private PrebuiltPcMapper prebuiltPcMapper;

    @PostConstruct
    public void postConstruct() {
        log.info("PcPartController created");
    }

    @GetMapping
    public Collection<GetPcPartDto> findAll(@RequestParam(name = "name", required = false) String name, @RequestParam(name = "producer", required = false) String producer, @RequestParam(name = "type", required = false) String type, @RequestParam(name = "maxPrice", required = false) Integer maxPrice, @RequestParam(name = "minPrice", required = false) Integer minPrice) {
        if (producer != null || type != null || maxPrice != null || minPrice != null) {
            log.info("GET /pcparts?name={}&producer={}&type={}&maxPrice={}&minPrice={} called", name, producer, type, maxPrice, minPrice);
            return pcPartMapper.modelsToGetDtos(pcPartsService.findBySpecification(producer, type, maxPrice, minPrice));
        }

        if (name == null) {
            log.info("GET /pcparts called");
            return pcPartMapper.modelsToGetDtos(pcPartsService.findAll());
        }


        log.info("/pcparts?name={} called", name);
        return pcPartMapper.modelsToGetDtos(pcPartsService.findByName(name));
    }

    @GetMapping("/{id}")
    public GetPcPartDto findById(@PathVariable("id") Long id) {
        log.info("GET /pcparts/{} called", id);
        PcPart pcPart = pcPartsService.findById(id);
        if (pcPart == null) {
            throw new NotFoundException("PcPart not found");
        }

        return pcPartMapper.modelToGetDto(pcPart);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<GetPcPartDto> insert(@RequestBody @Valid CreatePcPartDto createPcPartDto) {
        log.info("POST /pcparts called");
        if (createPcPartDto == null) {
            throw new BadRequestException("Request body is null");
        }

        PcPart pcPartModel = pcPartMapper.createDtoToModel(createPcPartDto);
        pcPartModel = pcPartsService.insert(pcPartModel);
        GetPcPartDto getPcPartDto = pcPartMapper.modelToGetDto(pcPartModel);
        URI createdUri = URI.create("/pcparts/" + pcPartModel.getId());
        return ResponseEntity.created(createdUri).body(getPcPartDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<PcPart> update(@PathVariable("id") Long id, @RequestBody @Valid CreatePcPartDto createPcPartDto) {
        log.info("PUT /pcparts/{} called", id);
        if (id == null) {
            throw new BadRequestException("Id is null");
        }

        if (createPcPartDto == null) {
            throw new BadRequestException("Request body is null");
        }

        if (pcPartsService.findById(id) == null) {
            throw new NotFoundException("PcPart not found");
        }

        PcPart pcPartModel = pcPartMapper.createDtoToModel(createPcPartDto);
        pcPartModel.setId(id);
        pcPartsService.update(id, pcPartModel);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        log.info("DELETE /pcparts/{} called", id);
        if (id == null) {
            throw new BadRequestException("Id is null");
        }

        pcPartsService.delete(id);
    }

    // nem engedte a /{id} után a /prebuiltpcs-t
    @GetMapping("/{id}/pcs")
    public Collection<GetPrebuiltPCDto> findPrebuiltPcByPartId(@PathVariable(name = "id") Long id) {
        log.info("GET /pcparts/{}/prebuiltpcs called", id);
        PcPart pcPart = pcPartsService.findById(id);
        if (pcPart == null) {
            throw new NotFoundException("PcPart not found");
        }

        return prebuiltPcMapper.modelsToGetDtos(pcPartsService.findPrebuiltPcByPartId(id));
    }

    @Data
    @AllArgsConstructor
    public static class Query implements Serializable {
        private String producer;
        private String type;
        private Integer maxPrice;
        private Integer minPrice;
    }
}
