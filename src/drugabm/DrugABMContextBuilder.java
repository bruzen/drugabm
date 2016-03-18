package drugabm;


import drugabm.agents.Dealer;
import drugabm.agents.User;
import drugabm.agents.Police;
import drugabm.common.Constants;
import repast.simphony.context.Context;
import repast.simphony.context.space.grid.GridFactory;
import repast.simphony.context.space.grid.GridFactoryFinder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridBuilderParameters;
import repast.simphony.space.grid.RandomGridAdder;
import repast.simphony.space.grid.WrapAroundBorders;

public class DrugABMContextBuilder implements ContextBuilder<Object>{
	@Override
	public Context<Object> build(Context<Object> context) {
		context.setId("drugabm");
		
		// Add projections
		GridFactory gf = GridFactoryFinder.createGridFactory(null);
		Grid grid = gf.createGrid("grid", context, new GridBuilderParameters(new WrapAroundBorders(), new RandomGridAdder(), true, Constants.SIZE_OF_GRID_X, Constants.SIZE_OF_GRID_Y));
		
		// Add agents
		for (int i = 0; i < Constants.INITIAL_NUMBER_OF_USERS; i++) {
			context.add(new User(grid));
		}
		
		for (int i = 0; i < Constants.INITIAL_NUMBER_OF_DEALERS; i++) {
			context.add(new Dealer(grid));
		}

		for (int i = 0; i < Constants.INITIAL_NUMBER_OF_POLICE; i++) {
			context.add(new Police(grid));
		}
		
		return context;
	}
}
