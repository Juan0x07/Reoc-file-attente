
public class State {
	private int Xh;
	private int Xc;
	private int Xb;
	
	public State() {
		this.setXh(2);
		this.setXc(3);
		this.setXb(4);
	}

	public int getXh() {
		return Xh;
	}

	public void setXh(int xh) {
		Xh = xh;
	}

	public int getXc() {
		return Xc;
	}

	public void setXc(int xc) {
		Xc = xc;
	}

	public int getXb() {
		return Xb;
	}

	public void setXb(int xb) {
		Xb = xb;
	}
	
	public int isXhSupZero() {
		int i = 1;
		if (this.Xh==0) {
			i=0;
		}
		return i;
	}
	public int isXbSupZero() {
		int i = 1;
		if (this.Xb==0) {
			i=0;
		}
		return i;
	}
	public void addXb() {
		Xb= this.Xb+1;
	}

	public void deleteXb(){
		Xb= this.Xb-1;
	}
	public void addXc() {
		Xc= this.Xc+1;
	}

	public void deleteXc() {
		Xc= this.Xc-1;
	}
	public void addXh() {
		Xh= this.Xh+1;
	}

	public void deleteXh() {
		Xh= this.Xh-1;
	}
	public void ToString() {
		System.out.println("Xh: "+this.Xh+" Xc: "+this.Xc+" Xb: "+this.Xb);
		
	}

}
