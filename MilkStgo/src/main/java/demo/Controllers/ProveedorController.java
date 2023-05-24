package demo.Controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import demo.Services.ProveedorService;
import demo.Models.Proveedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping("/proveedores")
    public String buscarTodos(Model model) {
        List<Proveedor> proveedores = proveedorService.buscarTodos();
        model.addAttribute("proveedores", proveedores);
        return "VerProveedores";
    }

    @GetMapping("/crearProveedor")
    public String proveedor(){
        return "crearProveedor";
    }
    @PostMapping("/crearProveedor")
    public String nuevoProveedor(@RequestParam("codigo") String codigo,
                                 @RequestParam("nombre") String nombre,
                                 @RequestParam("categoria") String categoria,
                                 @RequestParam("retencion") String retencion){
        proveedorService.guardarProveedor(codigo, nombre, categoria, retencion);
        return "redirect:/proveedores";
    }

}