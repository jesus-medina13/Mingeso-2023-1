package demo.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "acopio")

public class Acopio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fecha;
    private String turno;
    private String codigo;
    private String kls_leche;

    public Acopio() {
    }

    public Acopio(String fecha, String turno, String codigo, String kls_leche) {
        this.fecha = fecha;
        this.turno = turno;
        this.codigo = codigo;
        this.kls_leche = kls_leche;
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getKls_leche() {
        return kls_leche;
    }

    public void setKls_leche(String kls_leche) {
        this.kls_leche = kls_leche;
    }
}