package drugabm.observer;

import java.awt.Color;

import drugabm.agents.Dealer;
import repast.simphony.visualizationOGL2D.DefaultStyleOGL2D;
import saf.v3d.ShapeFactory2D;
import saf.v3d.scene.VSpatial;

public class DealerStyleOGL2D extends DefaultStyleOGL2D{	
	@Override
	public Color getColor(final Object agent) {
		if (agent instanceof Dealer) {
			final Dealer dealer = (Dealer) agent;
			// If arrested
			if (dealer.getArrested()) {
				return new Color(0x332C2C); // black
			}
			// If trade happens and dealer is not arrested 
			else if (dealer.getSold()) {
				return new Color(0xFFA500); // orange
			}
			else {
				return new Color(0xFF4500); // red
			}
		}
		return super.getColor(agent);
	}
	
	//	@Override	
	//	  public VSpatial getVSpatial(Object agent, VSpatial spatial) {
	//		    if (spatial == null) {
	//		      spatial = shapeFactory.createCircle(4, 16);		      
	////		      spatial = shapeFactory.createCircle(8, 16);
	////	    	  spatial = shapeFactory.createRectangle(12,8);
	////	    	  spatial = shapeFactory.createStar(12);
	//		    }
	//		    return spatial;
	//		  }		
}
