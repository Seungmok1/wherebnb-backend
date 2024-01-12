package goorm.wherebnb.domain.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@Table(name = "property_photos")
@NoArgsConstructor
public class PropertyPhoto {

    @Id
    private UUID uuid;

    @ManyToOne
    private Property property;

    private String originalName;

    private String savedName;

    private String path;

    @Builder
    public PropertyPhoto(UUID uuid, Property property, String originalName, String savedName, String path) {
        this.uuid = uuid;
        this.property = property;
        this.originalName = originalName;
        this.savedName = savedName;
        this.path = path;
    }
}
