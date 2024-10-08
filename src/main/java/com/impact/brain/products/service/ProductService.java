package com.impact.brain.products.service;

import com.impact.brain.email.dto.SendRequest;
import com.impact.brain.email.service.impl.EmailSendService;
import com.impact.brain.email.util.EmailServiceUtil;
import com.impact.brain.products.dto.ProductCategoryCountDTO;
import com.impact.brain.products.entity.*;
import com.impact.brain.products.repository.*;
import com.impact.brain.user.entity.User;
import com.impact.brain.user.entity.UserRole;
import com.impact.brain.user.repository.UserRepository;
import com.impact.brain.user.repository.UserRoleRepository;
import com.impact.brain.user.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@org.springframework.stereotype.Service("productService")
public class ProductService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UnitMeasurementRepository unitMeasurementRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductStatusRepository productStatusRepository;
    /**Necessary to send emails to administrators*/
    @Autowired
    private UserService userService;
    @Autowired
    private EmailSendService emailSendService;

    private static final Duration MIN_TIME_BETWEEN_EMAILS = Duration.ofMinutes(5); // Ajusta según tu necesidad

    private LocalDateTime lastEmailSentTime;

    public Iterable<Product> all(){return productRepository.findAll();}
    public Iterable<UnitOfMeasurement> units(){ return unitMeasurementRepository.findAll(); }
    public Iterable<CategoryType> types(){ return categoryRepository.findAll();}

    public Iterable<ProductCategory> categories() {return productCategoryRepository.findAll();}
    public Long productsCount(int id){return productRepository.countProductsByCategoryId(id);}
    public void saveC(ProductCategory category){
        productCategoryRepository.save(category);
        System.out.println("Saving category: " + category);
    }

    public Optional<CategoryType> findById(int id){ return categoryRepository.findById(id); }

    public Optional<UnitOfMeasurement> findByIdU(int id){ return unitMeasurementRepository.findById(id); }

    public ProductStatus findByNamePS(String name){ return productStatusRepository.findByName(name); }
    public Optional<ProductCategory> findByIdPC(int id){return productCategoryRepository.findById(id);}

    public void saveP(Product product){
        productRepository.save(product);
        System.out.println("Saving product: " + product);
    }

    public List<ProductCategoryCountDTO> getCategoryProductCounts() throws Exception {
        // Obtener todas las categorías
        List<ProductCategory> categories = (List<ProductCategory>) productCategoryRepository.findAll();

        // Generar los DTOs para cada categoría
        List<ProductCategoryCountDTO> categoryCountDTOs = categories.stream().map(category -> {
            Long count = productRepository.countProductsByCategoryId(category.getId()); // Contar productos por categoría

            // Crear el DTO
            ProductCategoryCountDTO dto = new ProductCategoryCountDTO();
            dto.setId(category.getId());
            dto.setName(category.getName());
            dto.setUnitOfMeasurement(category.getUnitOfMeasurement().getName());
            dto.setCantidadMinima(category.getCantidadMinima());
            dto.setAvailableQuantity(count);
            dto.setProductCategory(category.getCategorieType().getName());

            // Evaluar si la cantidad disponible está por debajo de la mínima
            if (count <= category.getCantidadMinima()) {
                dto.setStatus("Por solicitar");
            } else {
                dto.setStatus("Suficiente");
            }

            return dto;
        }).collect(Collectors.toList());

        // Verificar si hay al menos una categoría con el estatus "Por solicitar"
        boolean shouldSendEmail = categoryCountDTOs.stream()
                .anyMatch(dto -> "Por solicitar".equals(dto.getStatus()));

        // Verificar si es necesario enviar un correo y si ha pasado suficiente tiempo desde el último envío
        if (shouldSendEmail && canSendEmail()) {
            System.out.println("Al menos una categoría requiere reabastecimiento. Se enviará notificación.");
            new Thread(() -> {
                try {
                    sendEmailNotificationToAdmins();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).start();
            lastEmailSentTime = LocalDateTime.now(); // Actualizar la última vez que se envió un correo
        } else {
            System.out.println("No se necesita enviar correos o ha pasado muy poco tiempo desde el último envío.");
        }

        return categoryCountDTOs;
    }

    // Método que verifica si ha pasado suficiente tiempo desde el último envío de correo
    private boolean canSendEmail() {
        if (lastEmailSentTime == null) {
            return true; // Nunca se ha enviado un correo, por lo tanto se puede enviar
        }
        Duration timeSinceLastEmail = Duration.between(lastEmailSentTime, LocalDateTime.now());
        return timeSinceLastEmail.compareTo(MIN_TIME_BETWEEN_EMAILS) > 0;
    }

    public void sendEmailNotificationToAdmins() throws Exception {
        System.out.println("Preparando correos para administradores...");

        // Obtener administradores
        List<User> admins = userService.findUserByAdministratorRole();
        System.out.println("Número de administradores encontrados: " + admins.size());

        // Preparar solicitudes de correo
        List<SendRequest> sendRequests = EmailServiceUtil.prepareNotificationEmailToAdminAboutLowCuantityProduct(admins);
        System.out.println("Solicitudes de envío preparadas: " + sendRequests.size());

        // Enviar correos
        for (SendRequest sendRequest : sendRequests) {
            System.out.println("Mandando correo a: " + sendRequest.getTo());
            emailSendService.sendMessage(sendRequest, true);
        }
    }


}
