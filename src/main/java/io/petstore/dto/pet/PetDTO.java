
package io.petstore.dto.pet;

import io.petstore.dto.inner_parts.Category;
import io.petstore.dto.inner_parts.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class PetDTO {
    private Category category;
    private Long id;
    private String name;
    private List<String> photoUrls;
    private String status;
    private List<Tag> tags;
}