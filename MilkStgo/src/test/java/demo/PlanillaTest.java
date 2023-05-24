package demo;
import demo.Models.Acopio;
import demo.Services.PlanillaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PlanillaTest {
    @Autowired
    PlanillaService planillaService;

    @Test
    public void testCalcularTotalKlsLeche() {
        List<Acopio> acopios = new ArrayList<>();
        acopios.add(createAcopio("1.5"));
        acopios.add(createAcopio("2.3"));
        acopios.add(createAcopio("0.8"));

        double resultado = planillaService.calcularTotalKlsLeche(acopios);

        Assertions.assertEquals(4.6, resultado);
    }

    private Acopio createAcopio(String klsLeche) {
        Acopio acopio = mock(Acopio.class);
        when(acopio.getKls_leche()).thenReturn(klsLeche);
        return acopio;
    }
    private Acopio createAcopio2(String turno) {
        Acopio acopio = mock(Acopio.class);
        when(acopio.getTurno()).thenReturn(turno);
        return acopio;
    }

    @Test
    public void testObtenerQuincena() {
        String fecha1 = "17-03-2023";
        String resultado1 = PlanillaService.obtenerQuincena(fecha1);
        Assertions.assertEquals("16-03-2023", resultado1);

        String fecha2 = "04-03-2023";
        String resultado2 = PlanillaService.obtenerQuincena(fecha2);
        Assertions.assertEquals("1-03-2023", resultado2);
    }

    @Test
    public void testVerificarTurnoProveedor() {
        List<Acopio> acopios1 = new ArrayList<>();
        acopios1.add(createAcopio2("M"));
        acopios1.add(createAcopio2("T"));

        double resultado1 = PlanillaService.verificarTurnoProveedor(acopios1);
        Assertions.assertEquals(0.2, resultado1);

        List<Acopio> acopios2 = new ArrayList<>();
        acopios2.add(createAcopio2("M"));

        double resultado2 = PlanillaService.verificarTurnoProveedor(acopios2);
        Assertions.assertEquals(0.12, resultado2);

        List<Acopio> acopios3 = new ArrayList<>();
        acopios3.add(createAcopio2("T"));

        double resultado3 = PlanillaService.verificarTurnoProveedor(acopios3);
        Assertions.assertEquals(0.08, resultado3);

        List<Acopio> acopios4 = new ArrayList<>();

        double resultado4 = PlanillaService.verificarTurnoProveedor(acopios4);
        Assertions.assertEquals(0, resultado4);
    }

}
