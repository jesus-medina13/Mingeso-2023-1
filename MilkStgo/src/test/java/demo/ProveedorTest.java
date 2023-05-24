package demo;

import demo.Services.ProveedorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProveedorTest {
    @Autowired
    ProveedorService proveedorService;

    @Test
    public void testGuardarProveedor(){
        proveedorService.guardarProveedor("1234", "prov1", "A", "Si");
    }


}
