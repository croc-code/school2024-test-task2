import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
// Класс, который содержит данные самого продукта
public class Item {

    private String id;
    private String name;
    private Category category;
}

