
package io.petstore.dto.inner_parts;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Category {
    private Long id;
    private String name;
}