package demo.Models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "acopio2")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Acopio2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigo;
    private String grasa;
    private String solido_total;

}