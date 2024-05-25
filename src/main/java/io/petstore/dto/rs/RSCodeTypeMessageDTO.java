
package io.petstore.dto.rs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RSCodeTypeMessageDTO {
    private Long code;
    private String message; //id
    private String type;
}