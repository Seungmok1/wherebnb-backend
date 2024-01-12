package goorm.wherebnb.domain.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

import java.util.UUID;

@Entity
@Getter
public class PropertyPhoto {

    @Id
    private UUID uuid;
    private String originalName;
    private String savedName;
    private String path;
}
