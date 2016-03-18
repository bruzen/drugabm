package drugabm.agents;

//import drugABM.common.Constants;
import java.util.ArrayList;
import java.util.List;
//import java.util.logging.Level;
import repast.simphony.context.Context;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.query.space.grid.GridCell;
import repast.simphony.query.space.grid.GridCellNgh;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import repast.simphony.util.ContextUtils;
import repast.simphony.util.SimUtilities;

public class Police {
	Grid<Object> grid;
	
	public Police (Grid<Object> grid) {
		this.grid = grid;
	}

	@ScheduledMethod(start = 1, interval = 1, priority = 0)
	public void step() {
		move();
		arrestDealers();
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

			case "MOVE_TOWARDS_MOST_DEALERS":
				// Find neighboring cell with most dealers
				GridPoint pointWithMostDealers = null;
				int maxNoAgents = -1;
				for (GridCell<Dealer> cell : cellList) {
					if (cell.size() > maxNoAgents) {
						pointWithMostDealers = cell.getPoint();
						maxNoAgents = cell.size();
					}
				}
				// Move to that cell's location
				grid.moveTo(this, (int) pointWithMostDealers.getX(), (int) pointWithMostDealers.getY());
				break;
				
			case "DONT_MOVE":
				break;
				
			default:
				// LOGGER.log(Level.FINE, "No valid movementRule specified for police agent");
				break;
		}
	}
	
	public void arrestDealers() {
		// Make a list of all dealers in this cell
		GridPoint pt = grid.getLocation(this);
		List<Dealer> dealers = new ArrayList<Dealer>();
		for (Object obj : grid.getObjectsAt(pt.getX(), pt.getY())) {
			if (obj instanceof Dealer) {
				dealers.add((Dealer) obj);
			}
		}		
		
		String arrestRule = "REMOVE_ONE_DEALER_WITH_SALE"; 
		switch (arrestRule) {
			case "ARREST_ONE_DEALER":
				// Set boolean 'arrested' for one randomly selected dealer in this grid cell
				if (dealers.size() > 0) {
					int index = RandomHelper.nextIntFromTo(0, dealers.size() - 1);
					Dealer dealer = dealers.get(index);
					dealer.setArrested(true);
				}				
				break;
				
			case "REMOVE_ONE_DEALER_WITH_SALE":
				// Make a list of all dealers with a sale in this cell
				List<Dealer> dealersWithSale = new ArrayList<Dealer>();
				for (Dealer d : dealers) {
					if (d.getSold()) {
						dealersWithSale.add(d);
					}
				}
				// Set boolean 'arrested' for one randomly selected dealer in this grid cell with a sale in the last turn
				if (dealersWithSale.size() > 0) {
					int index = RandomHelper.nextIntFromTo(0, dealersWithSale.size() - 1);
					Dealer dealer = dealersWithSale.get(index);
					dealer.setArrested(true);
				}	
				break;
				
			case "REMOVE_ONE_DEALER":
				// Remove one randomly selected dealer from this grid cell
				if (dealers.size() > 0) {
					int index = RandomHelper.nextIntFromTo(0, dealers.size() - 1);
					Dealer dealer = dealers.get(index);
					Context<Dealer> context = ContextUtils.getContext(dealer);
					context.remove(dealer);
				}				
				break;
				
			default:
				// LOGGER.log(Level.FINE, "No valid arrestRule specified for police agent");
				break;
		}
	}
}
