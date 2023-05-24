package demo.Services;

import demo.Models.Acopio;
import demo.Models.Acopio2;
import demo.Repositories.AcopioRepository;
import demo.Repositories.Acopio2Repository;
import org.springframework.stereotype.Service;

import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

@Service
public class AcopioService {

    @Autowired
    private AcopioRepository acopioRepository;

    private final Logger logg = LoggerFactory.getLogger(AcopioService.class);

    public ArrayList<Acopio> obtenerData(){
        return (ArrayList<Acopio>) acopioRepository.findAll();
    }

    @Generated
    public String guardar(MultipartFile file){
        String filename = file.getOriginalFilename();
        if(filename != null){
            if(!file.isEmpty()){
                try{
                    byte [] bytes = file.getBytes();
                    Path path  = Paths.get(file.getOriginalFilename());
                    Files.write(path, bytes);
                    logg.info("Archivo guardado");
                }
                catch (IOException e){
                    logg.error("ERROR", e);
                }
            }
            return "Archivo guardado con exito";
        }
        else{
            return "No se pudo guardar el archivo";
        }
    }

    @Generated
    public void leerCsv(String direccion){
        String texto = "";
        BufferedReader bf = null;
        acopioRepository.deleteAll();
        try {
            bf = new BufferedReader(new FileReader(direccion));
            String temp = "";
            String bfRead;
            int count = 1;
            bfRead = bf.readLine();
            while ((bfRead = bf.readLine()) != null) {
                String[] values = bfRead.split(";");
                if (values.length == 4) {
                    // Formato 4 columnas: fecha;turno;proveedor;kls leche;
                    String fecha = values[0];
                    String turno = values[1];
                    String proveedor = values[2];
                    String klsLeche = values[3];

                    // Guardar en la base de datos
                    guardarDataDB(fecha, turno, proveedor, klsLeche);
                } else if (values.length == 3) {
                    // Formato 3 columnas: proveedor;%grasa;solido total;
                    String proveedor = values[0];
                    String grasa = values[1];
                    String solidoTotal = values[2];

                    guardarDataDB2(proveedor, grasa, solidoTotal);

                }
            }
        }catch(Exception e){
            System.err.println("No se encontro el archivo");
        }finally{
            if(bf != null){
                try{
                    bf.close();
                }catch(IOException e){
                    logg.error("ERROR", e);
                }
            }
        }
    }

    public void guardarData(Acopio data){
        acopioRepository.save(data);
    }


    public void guardarDataDB(String fecha, String turno, String proveedor, String kls_leche){
        Acopio newData = new Acopio();
        newData.setFecha(fecha);
        newData.setTurno(turno);
        newData.setCodigo(proveedor);
        newData.setKls_leche(kls_leche);
        guardarData(newData);
    }
    @Autowired
    private Acopio2Repository acopio2Repository;

    public ArrayList<Acopio2> obtenerData2(){
        return (ArrayList<Acopio2>) acopio2Repository.findAll();
    }


    public void guardarData2(Acopio2 data){
        acopio2Repository.save(data);
    }


    public void guardarDataDB2(String codigo, String grasa, String solido_total){
        Acopio2 newData = new Acopio2();
        newData.setCodigo(codigo);
        newData.setGrasa(grasa);
        newData.setSolido_total(solido_total);
        guardarData2(newData);
    }
}
