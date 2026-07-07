package com.greentrip.domain.services;

import com.greentrip.domain.dtos.requests.CreateCompanyRequest;
import com.greentrip.domain.dtos.requests.UpdateCompanyRequest;
import com.greentrip.domain.entities.CompanyEntity;
import com.greentrip.domain.mappers.CompanyMapper;
import com.greentrip.domain.models.CompanyModel;
import com.greentrip.infra.client.SireneClient;
import com.greentrip.infra.repositories.CompanyRepository;
import com.greentrip.infra.repositories.UserRepository;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for CompanyService. All dependencies are mocked (no DB, no Sirene call)
 * since the SIREN check is currently commented out in createCompany.
 */
public class CompanyServiceTest {

    @Mock
    CompanyRepository companyRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    CompanyMapper companyMapper;

    @Mock
    SireneClient sireneClient;

    @InjectMocks
    CompanyService companyService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCompanySuccess() {
        // Arrange
        CreateCompanyRequest request = new CreateCompanyRequest("VeloFlex", "111222333", "logo.png", 48.8566, 2.3522);
        CompanyEntity toPersist = new CompanyEntity(null, "VeloFlex", "111222333", 0, 0.0, 0, 0.0, null, 48.8566, 2.3522, "logo.png");
        CompanyEntity persisted = new CompanyEntity(1L, "VeloFlex", "111222333", 0, 0.0, 0, 0.0, null, 48.8566, 2.3522, "logo.png");

        when(companyRepository.findByName("VeloFlex")).thenReturn(Optional.empty());
        when(companyRepository.findBySirenNumber("111222333")).thenReturn(Optional.empty());
        when(companyMapper.toEntity(request)).thenReturn(toPersist);
        when(companyRepository.create(toPersist)).thenReturn(persisted);

        // Act
        CompanyEntity result = companyService.createCompany(request);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.id());
        Assertions.assertEquals("VeloFlex", result.name());
        verify(companyRepository, times(1)).create(toPersist);
    }

    @Test
    public void testCreateCompanyDuplicateNameThrowsConflict() {
        // Arrange
        CreateCompanyRequest request = new CreateCompanyRequest("Takima", "111222333", null, null, null);
        CompanyEntity existing = new CompanyEntity(1L, "Takima", "999888777", 0, 0.0, 0, 0.0, null, 48.8706, 2.3323, "default.png");
        when(companyRepository.findByName("Takima")).thenReturn(Optional.of(existing));

        // Act & Assert
        WebApplicationException ex = Assertions.assertThrows(WebApplicationException.class,
                () -> companyService.createCompany(request));
        Assertions.assertEquals(Response.Status.CONFLICT.getStatusCode(), ex.getResponse().getStatus());
        verify(companyRepository, times(0)).create(any());
    }

    @Test
    public void testCreateCompanyDuplicateSirenThrowsConflict() {
        // Arrange
        CreateCompanyRequest request = new CreateCompanyRequest("VeloFlex", "999888777", null, null, null);
        CompanyEntity existing = new CompanyEntity(2L, "OtherCorp", "999888777", 0, 0.0, 0, 0.0, null, 48.8, 2.3, "default.png");
        when(companyRepository.findByName("VeloFlex")).thenReturn(Optional.empty());
        when(companyRepository.findBySirenNumber("999888777")).thenReturn(Optional.of(existing));

        // Act & Assert
        WebApplicationException ex = Assertions.assertThrows(WebApplicationException.class,
                () -> companyService.createCompany(request));
        Assertions.assertEquals(Response.Status.CONFLICT.getStatusCode(), ex.getResponse().getStatus());
        verify(companyRepository, times(0)).create(any());
    }

    @Test
    public void testGetCompanyByIdSuccess() {
        // Arrange
        CompanyModel model = new CompanyModel();
        model.id = 1L;
        model.name = "Takima";
        CompanyEntity mapped = new CompanyEntity(1L, "Takima", "123456789", 50, 145.2, 1200, 3500.5, null, 48.8706, 2.3323, "logo.png");
        when(companyRepository.findByIdOptional(1L)).thenReturn(Optional.of(model));
        when(companyMapper.toEntity(model)).thenReturn(mapped);

        // Act
        CompanyEntity result = companyService.getCompanyById(1L);

        // Assert
        Assertions.assertEquals(mapped, result);
    }

    @Test
    public void testGetCompanyByIdNotFoundThrows404() {
        // Arrange
        when(companyRepository.findByIdOptional(99L)).thenReturn(Optional.empty());

        // Act & Assert
        WebApplicationException ex = Assertions.assertThrows(WebApplicationException.class,
                () -> companyService.getCompanyById(99L));
        Assertions.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), ex.getResponse().getStatus());
    }

    @Test
    public void testUpdateCompanySuccess() {
        // Arrange
        CompanyModel model = new CompanyModel();
        model.id = 1L;
        model.name = "OldName";
        model.logoPath = "old.png";
        UpdateCompanyRequest request = new UpdateCompanyRequest("NewName", "new.png", 48.9, 2.4);
        CompanyEntity mapped = new CompanyEntity(1L, "NewName", "123456789", 0, 0.0, 0, 0.0, null, 48.9, 2.4, "new.png");

        when(companyRepository.findByIdOptional(1L)).thenReturn(Optional.of(model));
        when(companyRepository.findByName("NewName")).thenReturn(Optional.empty());
        when(companyMapper.toEntity(model)).thenReturn(mapped);

        // Act
        CompanyEntity result = companyService.updateCompany(1L, request);

        // Assert
        Assertions.assertEquals("NewName", model.name);
        Assertions.assertEquals("new.png", model.logoPath);
        Assertions.assertEquals(mapped, result);
    }

    @Test
    public void testUpdateCompanyNotFoundThrows404() {
        // Arrange
        when(companyRepository.findByIdOptional(99L)).thenReturn(Optional.empty());
        UpdateCompanyRequest request = new UpdateCompanyRequest("NewName", null, null, null);

        // Act & Assert
        WebApplicationException ex = Assertions.assertThrows(WebApplicationException.class,
                () -> companyService.updateCompany(99L, request));
        Assertions.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), ex.getResponse().getStatus());
    }

    @Test
    public void testUpdateCompanyNameAlreadyUsedByAnotherCompanyThrowsConflict() {
        // Arrange
        CompanyModel model = new CompanyModel();
        model.id = 1L;
        model.name = "OldName";
        UpdateCompanyRequest request = new UpdateCompanyRequest("TakenName", null, null, null);
        CompanyEntity otherCompany = new CompanyEntity(2L, "TakenName", "555666777", 0, 0.0, 0, 0.0, null, 48.8, 2.3, "default.png");

        when(companyRepository.findByIdOptional(1L)).thenReturn(Optional.of(model));
        when(companyRepository.findByName("TakenName")).thenReturn(Optional.of(otherCompany));

        // Act & Assert
        WebApplicationException ex = Assertions.assertThrows(WebApplicationException.class,
                () -> companyService.updateCompany(1L, request));
        Assertions.assertEquals(Response.Status.CONFLICT.getStatusCode(), ex.getResponse().getStatus());
    }

    @Test
    public void testDeleteCompanySuccess() {
        // Arrange
        when(companyRepository.deleteById(1L)).thenReturn(true);

        // Act
        companyService.deleteCompany(1L);

        // Assert
        verify(companyRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteCompanyNotFoundThrows404() {
        // Arrange
        when(companyRepository.deleteById(99L)).thenReturn(false);

        // Act & Assert
        WebApplicationException ex = Assertions.assertThrows(WebApplicationException.class,
                () -> companyService.deleteCompany(99L));
        Assertions.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), ex.getResponse().getStatus());
    }
}
