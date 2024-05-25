
package io.petstore.dto.inner_parts;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Tag {
    private Long id;
    private String name;
}