package com.tea.fmScout_api.controller;

import com.tea.fmScout_api.dto.ManagerDto;;
import com.tea.fmScout_api.dto.request.AddManagerRequest;
import com.tea.fmScout_api.service.ManagerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/managers")
public class ManagerController {

    private final ManagerService managerService;

    public ManagerController(ManagerService managerService)
    {
        this.managerService = managerService;
    }

    @GetMapping
    public ResponseEntity<List<ManagerDto>> getAllManagers() {

        List<ManagerDto> managers = managerService.getAllManagers();
        return new ResponseEntity<>(managers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManagerDto> getManagerById(@PathVariable Long id) {
        return managerService.getManagerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<ManagerDto> createManager(@Valid @RequestBody AddManagerRequest createRequest) {
        ManagerDto managerDto = managerService.createManager(createRequest);
        return new ResponseEntity<>(managerDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ManagerDto> updateManager(@PathVariable Long id, @Valid @RequestBody AddManagerRequest updateRequest) {
        ManagerDto managerDto = managerService.updateManager(id, updateRequest);
        return new ResponseEntity<>(managerDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManager(@PathVariable Long id) {
        managerService.deleteManager(id);
        return ResponseEntity.noContent().build();
    }
}
