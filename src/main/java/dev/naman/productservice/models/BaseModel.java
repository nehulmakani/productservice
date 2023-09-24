package dev.naman.productservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.factory.spi.GenerationTypeStrategy;

import java.util.UUID;
@Getter
@MappedSuperclass
public class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
//    @GenericGenerator(name = "naman", strategy = "uuid2")
    @Column(name = "id", nullable = false, updatable = false)
    private Long uuid;
}
