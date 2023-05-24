package demo.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "planilla")
@AllArgsConstructor
@Data
public class Planilla {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String quincena;
    private String codigoProveedor;
    private String nombreProveedor;
    private String totalKlsLeche;
    private int numeroDiasEnvioLeche;
    private double promedioDiarioKlsLeche;
    private double porcentajeVariacionLeche;
    private double porcentajeGrasa;
    private double porcentajeVariacionGrasa;
    private double porcentajeSolidosTotales;
    private double porcentajeVariacionST;
    private double pagoPorLeche;
    private double pagoPorGrasa;
    private double pagoPorSolidosTotales;
    private double bonificacionPorFrecuencia;
    private double descuentoVariacionLeche;
    private double descuentoVariacionGrasa;
    private double descuentoVariacionST;
    private double pagoTotal;
    private double montoRetencion;
    private double montoFinal;

    // Constructor, getters y setters
}
