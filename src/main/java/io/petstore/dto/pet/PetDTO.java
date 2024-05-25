
package io.petstore.dto.pet;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.petstore.dto.inner_parts.Category;
import io.petstore.dto.inner_parts.TagDTO;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@EqualsAndHashCode
public class PetDTO {
    private Category category;
    private Long id;
    private String name;
    private List<String> photoUrls;
    private String status;
    private List<TagDTO> tags;
}