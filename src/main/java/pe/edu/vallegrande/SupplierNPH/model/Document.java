package pe.edu.vallegrande.SupplierNPH.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("documents")
public class Document {

    @Id
    @Column("document_id")
    private Long documentId;

    @Column("issue_date")
    private LocalDate issueDate;

    private String concept;

    @Column("document_type")
    private String documentType;
    
    @Column("document_number")
    private String documentNumber;

    @Column("type_kardex_id")
    private Integer typeKardexId;
}
