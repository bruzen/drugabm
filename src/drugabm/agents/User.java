package drugabm.agents;

//import drugABM.common.Constants;
import java.util.ArrayList;
import java.util.List;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.query.space.grid.GridCell;
import repast.simphony.query.space.grid.GridCellNgh;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import repast.simphony.util.SimUtilities;

public class User {
	Grid<Object> grid;
	boolean nearDealer;
	boolean boughtDrugs;
	
	public User (Grid<Object> grid) {
		this.grid = grid;
		this.nearDealer = false;
		this.boughtDrugs = false;
	}
	
	@ScheduledMethod(start = 1, interval = 1, priority = 1)
	public void step() {
		move();
		buyDrugs();
	}

	public void move() {
		// Get a shuffled list of neighboring cells and their dealer counts
		GridCellNgh<Dealer> gcn = new GridCellNgh<Dealer>(grid, grid.getLocation(this), Dealer.class, 1, 1);
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
				// LOGGER.log(Level.FINE, "No valid movementRule specified for dealer agent");
				break;
		}	
	}
	
	public void buyDrugs() {
		String buyDrugsRule = "BUY_FROM_RANDOM_DEALER_ON_CELL_ALWAYS";
		switch (buyDrugsRule) {
			case "BUY_FROM_RANDOM_DEALER_ON_CELL_ALWAYS":		
				GridPoint pt = grid.getLocation(this);
				List<Dealer> dealers = new ArrayList<Dealer>();
				for (Object obj : grid.getObjectsAt(pt.getX(), pt.getY())) {
					if (obj instanceof Dealer) {
						dealers.add((Dealer) obj);
					}
				}	
				// If there are dealers on this square, buy from a random dealer
				if (dealers.size() > 0) {
					int index = RandomHelper.nextIntFromTo(0, dealers.size() - 1);
					Dealer dealer = dealers.get(index);
					dealer.setSold(true);
					boughtDrugs = true;
				}
				else {
					boughtDrugs = false;
				}
				break;
				
			case "DONT_BUY":
				break;
				
			default:
				// LOGGER.log(Level.FINE, "No valid buyDrugsRule specified for user agent");
				break;
		}					
	}
	
	public boolean getBoughtDrugs() {
		return boughtDrugs;
	}
}
