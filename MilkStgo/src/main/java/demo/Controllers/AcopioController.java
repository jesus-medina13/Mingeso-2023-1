package demo.Controllers;

import demo.Models.Acopio2;
import org.springframework.ui.Model;
import demo.Models.Acopio;
import demo.Services.AcopioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
@RequestMapping
public class AcopioController {

    @Autowired
    private AcopioService acopioService;

    @GetMapping("/subirArchivo")
    public String main() {
        return "subirArchivo";
    }

    @PostMapping("/subirArchivo")
    public String upload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        acopioService.guardar(file);
        redirectAttributes.addFlashAttribute("mensaje", "Archivo cargado exitosamente");
        acopioService.leerCsv(file.getOriginalFilename());
        return "redirect:/subirArchivo";
    }

    @GetMapping("/acopios")
    public String listar(Model model) {
        ArrayList<Acopio> acopio = acopioService.obtenerData();
        ArrayList<Acopio2> acopio2 = acopioService.obtenerData2();

        model.addAttribute("acopio", acopio);
        model.addAttribute("acopio2", acopio2);
        return "VerAcopios";
    }
}