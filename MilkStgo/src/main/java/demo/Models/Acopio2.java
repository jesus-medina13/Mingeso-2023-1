package demo.Models;
import jakarta.persistence.*;

@Entity
@Table(name = "acopio2")

public class Acopio2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigo;
    private String grasa;
    private String solido_total;

    public Acopio2() {
    }

    public Acopio2(String codigo, String grasa, String solido_total) {
        this.codigo = codigo;
        this.grasa = grasa;
        this.solido_total = solido_total;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setGrasa(String grasa) {
        this.grasa = grasa;
    }

    public void setSolido_total(String solido_total) {
        this.solido_total = solido_total;
    }
}