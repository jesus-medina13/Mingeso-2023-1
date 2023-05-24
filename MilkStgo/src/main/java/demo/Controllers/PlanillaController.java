package demo.Controllers;
import demo.Models.Planilla;
import demo.Services.PlanillaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class PlanillaController {
    private final PlanillaService planillaService;

    @Autowired
    public PlanillaController(PlanillaService planillaService) {
        this.planillaService = planillaService;
    }

    @GetMapping("/planillas")
    public String listarPlanillas(Model model) {
        if (planillaService.obtenerTodasLasPlanillas().size() != 0) {
            planillaService.eliminarPlanillas();
        }
        planillaService.calcularYGuardarDatosPago();
        ArrayList<Planilla> planillas = (ArrayList<Planilla>) planillaService.obtenerTodasLasPlanillas();
        model.addAttribute("planillas", planillas);
        return "VerPlanillas";
    }

}
