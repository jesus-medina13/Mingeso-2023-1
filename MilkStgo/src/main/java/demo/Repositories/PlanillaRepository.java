package demo.Repositories;
import demo.Models.Planilla;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanillaRepository extends JpaRepository<Planilla, Long> {
}