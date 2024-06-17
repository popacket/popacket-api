package popacketservice.popacketservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import popacketservice.popacketservice.model.dto.PackageRequestDTO;
import popacketservice.popacketservice.model.dto.PackageResponseDTO;
import popacketservice.popacketservice.service.PackageService;

@RestController
@RequestMapping("/packages")
@AllArgsConstructor
public class PackageController {

    private PackageService packageService;

    @PostMapping
    public ResponseEntity<PackageResponseDTO> createPackage(@Validated @RequestBody PackageRequestDTO packageDTO){
        PackageResponseDTO packageResponseDTO = packageService.createPackage(packageDTO);
        return new ResponseEntity<>(packageResponseDTO, HttpStatus.CREATED);
    }
}
