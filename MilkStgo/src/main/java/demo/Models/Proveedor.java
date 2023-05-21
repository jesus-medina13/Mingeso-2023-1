package demo.Models;

//import com.sun.istack.NotNull;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "proveedores")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Proveedor {
    @Id
    private Long codigo;
    private String nombre;
    private String categoria;
    private String retencion;

    // Constructor, getters y setters
}