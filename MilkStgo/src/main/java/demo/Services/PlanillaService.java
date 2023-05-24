package demo.Services;

import demo.Models.Acopio;
import demo.Models.Acopio2;
import demo.Models.Planilla;
import demo.Models.Proveedor;
import demo.Repositories.Acopio2Repository;
import demo.Repositories.AcopioRepository;
import demo.Repositories.PlanillaRepository;
import demo.Repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.Double.parseDouble;

@Service
public class PlanillaService {
    private final PlanillaRepository planillaRepository;
    private final AcopioRepository acopioRepository;
    private final ProveedorRepository proveedorRepository;
    private final Acopio2Repository acopio2Repository;

    @Autowired
    public PlanillaService(PlanillaRepository planillaRepository, AcopioRepository acopioRepository,
                           ProveedorRepository proveedorRepository, Acopio2Repository acopio2Repository) {
        this.planillaRepository = planillaRepository;
        this.acopioRepository = acopioRepository;
        this.proveedorRepository = proveedorRepository;
        this.acopio2Repository = acopio2Repository;
    }

    public void calcularYGuardarDatosPago() {
        List<Proveedor> proveedores = proveedorRepository.findAll();
        for (Proveedor proveedor : proveedores) {
            List<Acopio> acopios = acopioRepository.findByCodigoProveedor(proveedor.getCodigo());
            List<Acopio2> acopios2 = acopio2Repository.findByCodigoProveedor(proveedor.getCodigo());

            Planilla planilla = new Planilla();

            String fecha = obtenerQuincena(acopios.get(0).getFecha());

            planilla.setQuincena(fecha);
            planilla.setCodigoProveedor(proveedor.getCodigo());
            planilla.setNombreProveedor(proveedor.getNombre());
            double totalKlsLeche = calcularTotalKlsLeche(acopios);
            planilla.setTotalKlsLeche(String.valueOf(totalKlsLeche));

            // Cálculo de los datos
            int numeroDias = acopios.size();
            planilla.setNumeroDiasEnvioLeche(numeroDias);

            double promedioDiario = totalKlsLeche / numeroDias;
            planilla.setPromedioDiarioKlsLeche(promedioDiario);

            double porcentajeVariacionLeche = 0;
            planilla.setPorcentajeVariacionLeche(porcentajeVariacionLeche);

            double grasa = parseDouble(acopios2.get(0).getGrasa());
            planilla.setPorcentajeGrasa(grasa);

            double porcentajeVariacionGrasa = 0;
            planilla.setPorcentajeVariacionGrasa(porcentajeVariacionGrasa);

            double st = parseDouble(acopios2.get(0).getSolido_total());
            planilla.setPorcentajeSolidosTotales(st);

            double porcentajeVariacionST = 0;
            planilla.setPorcentajeVariacionST(porcentajeVariacionST);

            //PAGO POR LECHE
            double pagoPorLeche = 0;
            if (proveedor.getCategoria().equalsIgnoreCase( "A")){
                pagoPorLeche = totalKlsLeche * 700;
            }
            else if (proveedor.getCategoria().equalsIgnoreCase("B")){
                pagoPorLeche = totalKlsLeche * 550;
            }
            else if (proveedor.getCategoria().equalsIgnoreCase("C")){
                pagoPorLeche = totalKlsLeche * 400;
            }
            else if (proveedor.getCategoria().equalsIgnoreCase("D")){
                pagoPorLeche = totalKlsLeche * 250;
            }
            planilla.setPagoPorLeche(pagoPorLeche);

            //PAGO POR GRASA

            double pagoPorGrasa = 0;

            if (grasa <= 20){
                pagoPorGrasa = totalKlsLeche * 30;
            }
            else if (grasa <= 45 && grasa > 20){
                pagoPorGrasa = totalKlsLeche * 80;
            }
            else if (grasa >= 46){
                pagoPorGrasa = totalKlsLeche * 120;
            }
            planilla.setPagoPorGrasa(pagoPorGrasa);

            //PAGO POR ST

            double pagoPorST = 0;

            if (st <= 7){
                pagoPorST = totalKlsLeche * (-130);
            }
            else if (st <= 18 && st > 7){
                pagoPorST = totalKlsLeche * (-90);
            }
            else if (st <= 35 && st > 18){
                pagoPorST = totalKlsLeche * 95;
            }
            else if (st >= 36){
                pagoPorST = totalKlsLeche * 150;
            }
            planilla.setPagoPorSolidosTotales(pagoPorST);

            //BONIFICACION

            double bonificacion = verificarTurnoProveedor(acopios);

            double pagoSinBonificacion = pagoPorLeche + pagoPorGrasa+ pagoPorST;

            planilla.setBonificacionPorFrecuencia(pagoSinBonificacion * bonificacion);

            double pagoTotal = pagoSinBonificacion + pagoSinBonificacion * bonificacion;

            planilla.setDescuentoVariacionLeche(0);
            planilla.setDescuentoVariacionGrasa(0);
            planilla.setDescuentoVariacionST(0);

            double pagoFinal = pagoTotal;
            if(proveedor.getRetencion().equalsIgnoreCase("Si")){
                pagoFinal = pagoFinal - pagoFinal * 0.13;
                planilla.setMontoRetencion(pagoFinal * 0.13);
                planilla.setMontoFinal(pagoFinal);
            }else{
                planilla.setMontoFinal(pagoFinal);
                planilla.setMontoRetencion(0);
            }

            // Guarda la planilla en la base de datos
            planillaRepository.save(planilla);
        }
    }

    private double calcularTotalKlsLeche(List<Acopio> acopios) {
        double totalKlsLeche = 0.0;
        for (Acopio acopio : acopios) {
            totalKlsLeche += parseDouble(acopio.getKls_leche());
        }
        return totalKlsLeche;
    }

    public static String obtenerQuincena(String fecha) {
        String[] values = fecha.split("-");
        int dia = Integer.parseInt(values[0]);

        if (dia <= 15) {
            return  ("1-"+values[1]+"-"+values[2]);
        } else {
            return ("16-"+values[1]+"-"+values[2]);
        }
    }

    private double verificarTurnoProveedor(List<Acopio> acopios) {
        boolean turnoM = false;
        boolean turnoT = false;

        for (Acopio acopio : acopios) {

            String turno = acopio.getTurno();

            if (turno.equalsIgnoreCase("M")) {
                turnoM = true;
            } else if (turno.equalsIgnoreCase("T")) {
                turnoT = true;
            }
        }

        if (turnoM && turnoT) {
            return 0.2;
        } else if (turnoM) {
            return 0.12;
        } else if (turnoT) {
            return 0.08;
        } else {
            return 0;
        }
    }
    public List<Planilla> obtenerTodasLasPlanillas() {
        return planillaRepository.findAll();
    }

    public boolean eliminarPlanillas() {
        try{
            planillaRepository.deleteAll();
            return true;
        }catch(Exception err){
            return false;
        }
    }
}