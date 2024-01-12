package goorm.wherebnb.domain.dao;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@Table(name = "property_photos")
@NoArgsConstructor
public class PropertyPhoto {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uuid;

    @ManyToOne
    private Property property;

    private String originalName;

    private String savedName;

    private String path;

    @Builder
    public PropertyPhoto(UUID uuid, Property property, String originalName, String savedName, String path) {
//        this.uuid = uuid;
        this.property = property;
        this.originalName = originalName;
        this.savedName = savedName;
        this.path = path;
    }
}
