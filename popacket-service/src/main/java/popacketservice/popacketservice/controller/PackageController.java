package popacketservice.popacketservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import popacketservice.popacketservice.model.dto.PackageRequestDTO;
import popacketservice.popacketservice.model.dto.PackageResponseDTO;
import popacketservice.popacketservice.service.PackageService;

import java.util.List;

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

    @GetMapping("/by-sender/{senderId}")
    public ResponseEntity<List<PackageResponseDTO>> getPackagesBySender(@PathVariable Long senderId) {
        List<PackageResponseDTO> packages = packageService.getPackagesBySender(senderId);
        return new ResponseEntity<>(packages, HttpStatus.OK);
    }

    @GetMapping("/by-recipient/{recipientId}")
    public ResponseEntity<List<PackageResponseDTO>> getPackagesByRecipient(@PathVariable Long recipientId) {
        List<PackageResponseDTO> packages = packageService.getPackagesByRecipient(recipientId);
        return new ResponseEntity<>(packages, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<PackageResponseDTO>> getAllPackageById(@PathVariable Long id) {
        List<PackageResponseDTO> paquetesDTO = packageService.getAllPackageBySenderId(id);
        return new ResponseEntity<>(paquetesDTO, HttpStatus.OK);
    }
}
