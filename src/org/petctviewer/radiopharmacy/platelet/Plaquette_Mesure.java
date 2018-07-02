package org.petctviewer.radiopharmacy.platelet;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Plaquette_Mesure {
	
	private Date dateMeasure;
	private int count;
	private double countsCorrected, fractionRecuperation;
	private boolean useForFit;
	private double delayHourFromInjection;
	
	public Plaquette_Mesure (String date, int hour, int minutes, int count, boolean useForFit) {
		String year=date.substring(0,4);
		String month=date.substring(4,6);
		String day=date.substring(6);
		Calendar calendar = new GregorianCalendar(Integer.parseInt(year), Integer.parseInt(month)-1, Integer.parseInt(day), hour, minutes, 00);
		dateMeasure=calendar.getTime();
		this.count=count;
		this.useForFit=useForFit;
	}
	
	public Date getMesureTime() {
		return dateMeasure;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCountdecayCorrected(double correctedCount) {
		this.countsCorrected=correctedCount;
	}
	
	public double getCountdecayCorrected() {
		return this.countsCorrected;
	}
	public void setFractionRecuperation(double fraction) {
		this.fractionRecuperation=fraction;
	}
	public double getFractionRecuperation() {
		return this.fractionRecuperation;
	}
	public boolean getuserForFit() {
		return useForFit;
	}
	public void setuserForFit(Boolean useForFit) {
		this.useForFit=useForFit;
	}
	
	public void setDelayHourFromInjection (double delayHourFromInjection) {
		this.delayHourFromInjection=delayHourFromInjection;
	}
	public double getDelayHourFromInjection () {
		return delayHourFromInjection;
	}


	
}
