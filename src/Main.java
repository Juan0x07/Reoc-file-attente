
public class Main {

	private static float lambda= 0.1f;
	//muH=muC=muB=2
	private static float muH=2;
	private static float muC=2;
	private static float muB=2;
	// c=5
	private static int nbCoeur=5;
	//number of thread -> inifinit
	private static int nbThread=20000000;
	// p=0.5
	private static float probDB=0.5f;
	
	private static float vH = lambda;
	private static float vC = lambda/(1-probDB);
	private static float vB = probDB*lambda/(1-probDB);
	
	private static float pH = lambda/muH;
	//private static float pC = (lambda/(1-probDB))/muC;
	private static float pB = (probDB*lambda/(1-probDB))/muB;
	
	public static void main(String[]args) {
		State state = new State();
	
		float sumTotal=0;
		float sum =0;
		float sumC =0;
		float sumB =0;
		float sumH =0;
		float loiDePetit=0;
		
		for(int i=1; i<=180000;i++) {
			// event proba
			// sum of all events
			float probS = lambda+muH*state.isXhSupZero()+Math.min(state.getXc(), nbCoeur)*muC+muB*state.isXbSupZero();
			// arrival of packets
			float probARR=(lambda)/probS;
			// http sever to core 
			float probHC = muH*state.isXhSupZero()/probS;
			// core to database
			float probCB = Math.min(state.getXc(), nbCoeur)*muC*probDB/probS;
			// packets leave
			float probCS= Math.min(state.getXc(), nbCoeur)*muC*(1-probDB)/probS;
			// database to core
			float probBC = muB*state.isXbSupZero()/probS;
			
			// GET A RANDOM VALUE
			float r = (float) Math.random();
			if(r<=probARR) {
				//ARR
				sum++;
				sumB = sumB+state.getXb();
				sumC = sumC+state.getXc();
				sumH = sumH+state.getXh();
				sumTotal=sumTotal+state.getXb()+state.getXc()+state.getXh();
				state.addXh();
			}
			else if(r<=probARR+probHC) {
				//HC
				state.deleteXh();
				state.addXc();
			}
			else if (r<=probARR+probHC+probCB) {
				//CB
				state.deleteXc();
				state.addXb();
			}
			else if (r<=probARR+probHC+probCB+probCS){
				//CS
				state.deleteXc();
			}
			else { 
				//BC
				state.deleteXb();
				state.addXc();
			}
			
		} 
		float mean = sumTotal/sum;
		float meanH=sumH/sum;
		float meanC=sumC/sum;
		float meanB=sumB/sum;
		System.out.println("mean: "+mean);
		System.out.println("meanH: "+meanH);
		System.out.println("meanC: "+meanC);
		System.out.println("meanB: "+meanB);
		loiDePetit=mean/lambda; 
		System.out.println("Loi de Little: "+loiDePetit);
		
		
		float E = ((pH/(1-pH))+(pB/(1-pB))+EC(10))/lambda;
		System.out.println("E sim: "+E);	
		
		//forte charge
		// pc = vc/(C*muC)
		float pC = vC/(nbCoeur*muC);
		float Eforte = ((pH/(1-pH))+(pB/(1-pB))+(pC/(1-pC)))/lambda;
		System.out.println("E forte charge: "+Eforte);
		
		//faible charge
		// pc = vc/(C*muC)
		float pC1 = vC/muC;
		float Efaible = ((pH/(1-pH))+(pB/(1-pB))+pC1)/lambda;
		System.out.println("E faible  charge: "+Efaible);
	}
	
	public static float EC(int nb) {
		float ec=0;
		float p0=1;
		float p1=0;
		for (int i = 0; i<nb; i++) {
			p0=(float) (p0*Math.pow(vC,i));
			for (int j = 0; j<i;j++) {
				p0=p0/(Math.min(j+1,nbCoeur)*muC);
			}
			p1=p1+i*p0;
			ec=ec+p0;
		}
		return p1/ec;
	}
	
	
}
