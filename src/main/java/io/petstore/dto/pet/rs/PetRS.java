
package io.petstore.dto.pet.rs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PetRS {
    private Long code;
    private String message; //id
    private String type;
}