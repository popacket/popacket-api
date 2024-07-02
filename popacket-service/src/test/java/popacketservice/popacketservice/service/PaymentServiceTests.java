package popacketservice.popacketservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import popacketservice.popacketservice.exception.ConflictException;
import popacketservice.popacketservice.mapper.PaymentMapper;
import popacketservice.popacketservice.model.dto.PaymentRequestDTO;
import popacketservice.popacketservice.model.dto.PaymentResponseDTO;
import popacketservice.popacketservice.model.entity.Package;
import popacketservice.popacketservice.model.entity.Payment;
import popacketservice.popacketservice.repository.PackageRepository;
import popacketservice.popacketservice.repository.PaymentRepository;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PaymentServiceTests {

    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private PackageRepository packageRepository;
    @Mock
    private PaymentMapper paymentMapper;

    @InjectMocks
    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void whenPaymentAlreadyExists_thenThrowConflictException() {
        Long packageId = 1L;
        PaymentRequestDTO dto = new PaymentRequestDTO();
        dto.setPackageId(packageId);

        when(paymentRepository.existsByPackageId(packageId)).thenReturn(true);

        assertThrows(ConflictException.class, () -> paymentService.registerPayment(dto));
    }

    @Test
    void whenPackageNotFound_thenThrowRuntimeException() {
        Long packageId = 1L;
        PaymentRequestDTO dto = new PaymentRequestDTO();
        dto.setPackageId(packageId);

        when(paymentRepository.existsByPackageId(packageId)).thenReturn(false);
        when(packageRepository.findById(packageId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> paymentService.registerPayment(dto));
    }

    @Test
    void whenPaymentIsSuccessful_thenReturnPaymentResponseDTO() {
        Long packageId = 1L;
        PaymentRequestDTO dto = new PaymentRequestDTO();
        dto.setPackageId(packageId);
        Package pkg = new Package();
        Payment payment = new Payment();
        PaymentResponseDTO expectedResponse = new PaymentResponseDTO();

        when(paymentRepository.existsByPackageId(packageId)).thenReturn(false);
        when(packageRepository.findById(packageId)).thenReturn(Optional.of(pkg));
        when(paymentMapper.convertToEntity(dto)).thenReturn(payment);
        when(paymentMapper.convertToDTO(payment)).thenReturn(expectedResponse);

        PaymentResponseDTO actualResponse = paymentService.registerPayment(dto);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
    }
}
