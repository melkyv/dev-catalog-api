package com.mkv.devcatalog.domain.category;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "Category")
@Table(name = "categories")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Category(CategoryDTO dto) {
        this.name = dto.name();
    }

    public void updateData(CategoryDTO dto) {
        this.name = dto.name();
    }
}
