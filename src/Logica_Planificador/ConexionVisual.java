package Logica_Planificador;

public class ConexionVisual {
	    private final double lat1;
	    private final double lon1;

	    private final double lat2;
	    private final double lon2;

	    public ConexionVisual(double lat1,double lon1,double lat2,double lon2) {
	        this.lat1 = lat1;
	        this.lon1 = lon1;
	        this.lat2 = lat2;
	        this.lon2 = lon2;
	    }
	    public double getLat1() {return lat1;}
	    public double getLon1() {return lon1;}
	    public double getLat2() {return lat2;}
	    public double getLon2() {return lon2;}
	}
