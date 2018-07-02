package bloodVolume;

public class Volume_Generic_Value {
	private double cpmMl;
	private double cpm;
	private double ml;
	private boolean use;
	private double timeGR;

	public Volume_Generic_Value(double cpm, double ml, boolean use) {
		this.cpmMl=cpm/ml;
		this.cpm=cpm;
		this.ml=ml;
		this.use=use;
	}
	
	public Volume_Generic_Value(double cpm, double ml, boolean use, double time) {
		this.cpmMl=cpm/ml;
		this.cpm=cpm;
		this.ml=ml;
		this.use=use;
		this.timeGR=time;
	}
	
	public double getcpmMl() {
		
		return this.cpmMl;
	}
	
	public double getMl() {
		return this.ml;
	}
	
	public double getcpm() {
		return this.cpm;
	}
	
	public boolean getuse() {
		return use;
	}
	public void setTime(double time) {
		this.timeGR=time;
	}
	public double getTime() {
		return timeGR;
	}
	
}
