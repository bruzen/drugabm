/*
* Copyright (c) 2016 Kirsten Wright
* Model design by Kirsten Wright, Owen Gallupe, and John McLevey
*/

package drugabm.agents;

//import drugABM.common.Constants;
import java.util.List;

import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.query.space.grid.GridCell;
import repast.simphony.query.space.grid.GridCellNgh;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import repast.simphony.util.SimUtilities;

public class Dealer {
	Grid<Object> grid;
	boolean sold;
	boolean arrested;
	int soldCounter;
	
	public Dealer (Grid<Object> grid) {
		this.grid = grid;
		this.sold = false;
		this.arrested = false;
	}
	
	@ScheduledMethod(start = 1, interval = 1, priority = -1)
	public void step() {		
		if (!arrested) {
			move();
			soldCounter -= 1;
			if (soldCounter < 0) {
				sold = false;
			}
		}
	}
	
	public void move() {
		// Get a shuffled list of neighboring cells and their dealer counts
		GridCellNgh<Dealer> gcn = new GridCellNgh<Dealer>(grid, grid.
								      getLocation(this), Dealer.class, 1, 1);
		List<GridCell<Dealer>> cellList = gcn.getNeighborhood(true);
		SimUtilities.shuffle(cellList, RandomHelper.getUniform());		
		
		String movementRule = "MOVE_RANDOMLY";
		switch (movementRule) {
			case "MOVE_RANDOMLY":
				// Pick a random element from the shuffled list
				GridCell<Dealer> chosenCell = cellList.get(0);
				// Move to that cell's location
				final GridPoint newLocation = chosenCell.getPoint();		
				grid.moveTo(this, newLocation.getX(), newLocation.getY());
				break;	
				
			case "DONT_MOVE":
				break;
				
			default:
				// LOGGER.log(Level.FINE, 
				// "No valid movementRule specified for dealer agent");
				break;
		}	
	}
	
	// Setters and getters	
	public void setArrested(boolean a) {
		arrested = a;
	}
	
	public boolean getArrested() {
		return arrested;
	}
	
	public void setSold(boolean s) {		
		sold = s;
		if (sold = true) {
			soldCounter = 1;
		}
	}
	
	public boolean getSold() {
		return sold;
	}
}
