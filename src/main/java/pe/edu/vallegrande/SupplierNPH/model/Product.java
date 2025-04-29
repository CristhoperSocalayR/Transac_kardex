package pe.edu.vallegrande.SupplierNPH.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data

@Table("product")
public class Product {

    @Id
    private Long id;

    private String type;

    private String description;

    @Column("package_weight")
    private BigDecimal packageWeight;

    
    private Integer stock;

    @Column("entry_date")
    private LocalDate entryDate;

    @Column("expiry_date")
    private LocalDate expiryDate;

    private String status = "A"; 

}
