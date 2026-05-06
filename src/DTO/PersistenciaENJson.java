package DTO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import logica.modelo.Localidad;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


import logica.modelo.Localidad;

public class PersistenciaENJson {
	private static final Gson gson=new GsonBuilder().setPrettyPrinting().create();
							// =========================
									//Guardar
							// =========================
	public static void guardarLocalidades(List<Localidad>localidades,String archivo) {
		List<LocalidadDTO>dtos =new ArrayList();
		for(Localidad loc : localidades ) {
			dtos.add(convertirADTO(loc));
		}
		try(FileWriter writer = new FileWriter(archivo)){
			gson.toJson(dtos,writer);
		}
		catch(IOException e){
			e.printStackTrace();
		}
	
			
		}
									
						// =========================
									//CARGAR
						// =========================
		public static List<Localidad> cargarLocalidades(String archivo){
			try (FileReader reader =new FileReader(archivo)){
				Type tipoLista= new TypeToken<List<LocalidadDTO>>() {}.getType();
				List<LocalidadDTO> dtos = gson.fromJson(reader, tipoLista);
				List<Localidad>localidades = new ArrayList<>();
				if(dtos!=null) {
					for(LocalidadDTO dto : dtos) {
						localidades.add(convertirADominio(dto));
					}
				}
				return localidades;
			}
			catch(IOException e ) {
				e.printStackTrace();
				return new ArrayList<>();
			}
		}	
						 // =========================
					    		// CONVERSIONES
						// =========================
	    private static LocalidadDTO convertirADTO(Localidad loc) {
	        LocalidadDTO dto = new LocalidadDTO();
	        dto.nombre = loc.getNombre();
	        dto.provincia = loc.getProvincia();
	        dto.latitud = loc.getLatitud();
	        dto.longitud = loc.getLongitud();
	        return dto;
	    }

	    private static Localidad convertirADominio(LocalidadDTO dto) {
	        return new Localidad(
	                dto.nombre,
	                dto.provincia,
	                dto.latitud,
	                dto.longitud
	        );
	
}	
	
}
	
	
	
	
	
	
	
	