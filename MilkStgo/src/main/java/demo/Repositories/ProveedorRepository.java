package demo.Repositories;


import demo.Models.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
    @Query("select e from Proveedor e where e.nombre = :nombre")
    Proveedor findByNameCustomQuery(@Param("nombre") String nombre);

    @Query("select e.categoria from Proveedor e where e.codigo = :codigo")
    String findCategory(@Param("codigo") String codigo);

    @Query("select e from Proveedor e where e.codigo = :codigo")
    Proveedor findByCodigo(@Param("codigo")String codigo);
}
