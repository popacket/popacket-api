package popacketservice.popacketservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import popacketservice.popacketservice.exception.ResourceNotFoundException;
import popacketservice.popacketservice.mapper.PackageMapper;
import popacketservice.popacketservice.model.dto.PackageRequestDTO;
import popacketservice.popacketservice.model.dto.PackageResponseDTO;
import popacketservice.popacketservice.model.entity.Package;
import popacketservice.popacketservice.model.entity.User;
import popacketservice.popacketservice.repository.PackageRepository;
import popacketservice.popacketservice.repository.UserRepository;

import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PackageServiceTests {

    @Mock
    private PackageRepository packageRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PackageMapper packageMapper;

    @InjectMocks
    private PackageService packageService;

    private PackageRequestDTO requestDTO;
    private Package aPackage;
    private User sender, recipient;

    @BeforeEach
    void setUp() {
        sender = new User();
        sender.setId(3L);
        recipient = new User();
        recipient.setId(4L);

        requestDTO = new PackageRequestDTO();
        requestDTO.setSenderId(3L);
        requestDTO.setRecipientId(4L);

        aPackage = new Package();
        aPackage.setSender(sender);
        aPackage.setRecipient(recipient);
        aPackage.setCreatedAt(LocalDate.now());

        lenient().when(packageMapper.convertToEntity(requestDTO)).thenReturn(aPackage);
        lenient().when(packageMapper.convertToDTO(aPackage)).thenReturn(new PackageResponseDTO());
    }


    @Test
    void createPackage_Success() {
        when(userRepository.findById(sender.getId())).thenReturn(java.util.Optional.of(sender));
        when(userRepository.findById(recipient.getId())).thenReturn(java.util.Optional.of(recipient));
        when(packageRepository.save(any(Package.class))).thenReturn(aPackage);
        PackageResponseDTO result = packageService.createPackage(requestDTO);
        assertNotNull(result);
        verify(packageRepository).save(aPackage);
        verify(packageMapper).convertToDTO(aPackage);
    }

    @Test
    void createPackage_SenderNotFound_ThrowsException() {
        when(userRepository.findById(sender.getId())).thenReturn(java.util.Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> packageService.createPackage(requestDTO));
        verify(packageRepository, never()).save(any(Package.class));
    }

    @Test
    void createPackage_RecipientNotFound_ThrowsException() {
        when(userRepository.findById(recipient.getId())).thenReturn(java.util.Optional.empty());
        when(userRepository.findById(sender.getId())).thenReturn(java.util.Optional.of(sender));
        assertThrows(ResourceNotFoundException.class, () -> packageService.createPackage(requestDTO));

        verify(packageRepository, never()).save(any(Package.class));
    }

}
