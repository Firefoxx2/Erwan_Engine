package fr.firefoxx.graphicengine.other;

import java.util.Date;

public class TimeAnalyse {
	private long t0;
	private long tn;
	public TimeAnalyse() {
		t0 = new Date().getTime();
		tn = t0;
	}

	public void elapsedTime(String txt) {
		long t = new Date().getTime();
		System.out.println(txt + " : " + (t-tn) + "ms");
		tn = t;
	}
	public void timeSinceStard() {
		long t = new Date().getTime();
		System.out.println("Total : \t" + (tn-t0) + "ms");
		tn = t;
	}
	public void ResetTimer() {
		long t0 = new Date().getTime();
		tn = t0;
	}
}
