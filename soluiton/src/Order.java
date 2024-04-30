import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
// Класс, который содержит данные о заказе
public class Order {

    @JsonProperty("ordered_at")
    private String orderedAt;
    private List<Item> items;
}
