package demo.Repositories;

import demo.Models.Acopio;
import demo.Models.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AcopioRepository extends JpaRepository<Acopio, Long> {

    @Query("select e from Acopio e where e.codigo = :codigo")
    List<Acopio> findByCodigoProveedor(@Param("codigo")String codigo);
}