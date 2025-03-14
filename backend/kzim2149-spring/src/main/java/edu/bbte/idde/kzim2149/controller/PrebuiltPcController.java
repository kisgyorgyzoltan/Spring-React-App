package edu.bbte.idde.kzim2149.controller;

import edu.bbte.idde.kzim2149.dto.incoming.CreatePrebuiltPcDto;
import edu.bbte.idde.kzim2149.dto.outgoing.GetPcPartDto;
import edu.bbte.idde.kzim2149.dto.outgoing.GetPrebuiltPCDto;
import edu.bbte.idde.kzim2149.mapper.PcPartMapper;
import edu.bbte.idde.kzim2149.mapper.PrebuiltPcMapper;
import edu.bbte.idde.kzim2149.model.PcPart;
import edu.bbte.idde.kzim2149.model.PrebuiltPc;
import edu.bbte.idde.kzim2149.service.PrebuiltPcService;
import edu.bbte.idde.kzim2149.validator.PrebuiltPcValidator;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin(maxAge = 3600)
@Slf4j
@Profile("jpa")
@RestController
@RequestMapping("/prebuiltpcs")
public class PrebuiltPcController {
    @Autowired
    private PrebuiltPcService prebuiltPCService;

    @Autowired
    private PrebuiltPcMapper prebuiltPCMapper;

    @Autowired
    private PcPartMapper pcPartMapper;

    @Autowired
    private PrebuiltPcValidator prebuiltPcValidator;

    @PostConstruct
    public void postConstruct() {
        log.info("PrebuiltPCController created");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GetPrebuiltPCDto create(@RequestBody @Valid CreatePrebuiltPcDto createPrebuiltPCDto,
                                   @RequestBody(required = false) Boolean createParts) {

        log.info("POST /prebuiltpcs called");
        if (createParts == null || !createParts) {
            prebuiltPcValidator.validate(createPrebuiltPCDto);
        }
        PrebuiltPc prebuiltPc = prebuiltPCMapper.createDtoToModel(createPrebuiltPCDto);
        PrebuiltPc insertedPrebuiltPC = prebuiltPCService.insert(prebuiltPc);
        return prebuiltPCMapper.modelToGetDto(insertedPrebuiltPC);
    }

    @GetMapping
    public Collection<GetPrebuiltPCDto> findAll() {
        log.info("GET /prebuiltpcs called");
        return prebuiltPCMapper.modelsToGetDtos(prebuiltPCService.findAll());
    }

    @GetMapping("/{id}")
    public GetPrebuiltPCDto findById(@PathVariable("id") Long id) {
        log.info("GET /prebuiltpcs/{} called", id);
        PrebuiltPc prebuiltPc = prebuiltPCService.findById(id);

        return prebuiltPCMapper.modelToGetDto(prebuiltPc);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        log.info("DELETE /prebuiltpcs/{} called", id);
        prebuiltPCService.delete(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public GetPrebuiltPCDto update(@PathVariable("id") Long id,
                                   @RequestBody CreatePrebuiltPcDto createPrebuiltPCDto) {
        log.info("PUT /prebuiltpcs/{} called", id);
        prebuiltPcValidator.validate(createPrebuiltPCDto);
        PrebuiltPc updatedPrebuiltPc = prebuiltPCMapper.createDtoToModel(createPrebuiltPCDto);
        updatedPrebuiltPc = prebuiltPCService.update(id, updatedPrebuiltPc);
        return prebuiltPCMapper.modelToGetDto(updatedPrebuiltPc);
    }

    @GetMapping("/{id}/{type}")
    public GetPcPartDto getPartById(@PathVariable("id") Long id, @PathVariable("type") String type) {
        log.info("GET /prebuiltpcs/{}/{} called", id, type);
        PcPart pcPart = prebuiltPCService.getPartById(id, type);
        log.info(pcPartMapper.modelToGetDto(pcPart).toString());
        return pcPartMapper.modelToGetDto(pcPart);
    }

    @PostMapping("/{id}/{type}")
    @ResponseStatus(HttpStatus.CREATED)
    public GetPcPartDto insertPartById(@PathVariable("id") Long id, @PathVariable("type") String type,
                                       @RequestBody PcPart pcPart) {
        log.info("POST /prebuiltpcs/{}/{} called", id, type);
        return pcPartMapper.modelToGetDto(
                prebuiltPCService.insertPartById(id, type, pcPart)
        );
    }

    @PutMapping("/{id}/{type}")
    public GetPcPartDto updatePartById(@PathVariable("id") Long id, @PathVariable("type") String type,
                                       @RequestBody PcPart updatedPcPart) {
        log.info("PUT /prebuiltpcs/{}/{} called", id, type);

        return pcPartMapper.modelToGetDto(
                prebuiltPCService.updatePartById(id, type, updatedPcPart)
        );
    }

    @DeleteMapping("/{id}/{type}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePartById(@PathVariable("id") Long id, @PathVariable("type") String type) {
        log.info("DELETE /prebuiltpcs/{}/{} called", id, type);
        prebuiltPCService.deletePcPartThroughPrebuiltPc(id, type);
    }
}
