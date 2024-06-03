package popacketservice.popacketservice.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import popacketservice.popacketservice.model.dto.PackageResponseDTO;
import popacketservice.popacketservice.service.PackageService;

import java.util.List;

@RestController
@RequestMapping ("/user/packages")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class PackageController {

    public PackageService packageService;

    @GetMapping("/{id}")
    public ResponseEntity<List<PackageResponseDTO>> getAllPackageById(@PathVariable Long id) {
        List<PackageResponseDTO> paquetesDTO = packageService.getAllPackageBySenderId(id);
        return new ResponseEntity<>(paquetesDTO, HttpStatus.OK);
    }
}
