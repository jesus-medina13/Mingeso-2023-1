package demo.Repositories;
import demo.Models.Acopio;
import demo.Models.Acopio2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Acopio2Repository extends JpaRepository<Acopio2, Long> {

    @Query("select e from Acopio2 e where e.codigo = :codigo")
    List<Acopio2> findByCodigoProveedor(@Param("codigo")String codigo);
}
