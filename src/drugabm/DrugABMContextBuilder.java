/*
* Copyright (c) 2016 Kirsten Wright
* Model design by Kirsten Wright, Owen Gallupe, and John McLevey
*/
package drugabm;

import drugabm.agents.Dealer;
import drugabm.agents.User;
import drugabm.agents.Police;
import drugabm.common.Constants;
import repast.simphony.context.Context;
import repast.simphony.context.space.grid.GridFactory;
import repast.simphony.context.space.grid.GridFactoryFinder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.parameter.Parameters;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridBuilderParameters;
import repast.simphony.space.grid.RandomGridAdder;
import repast.simphony.space.grid.WrapAroundBorders;

public class DrugABMContextBuilder implements ContextBuilder<Object>{
	@Override
	public Context<Object> build(Context<Object> context) {
		// Set context id so it matches the id in context.xml
		context.setId("drugabm");

		// Get user controlled parameters
		// Default values are set in parameters.xml
		final Parameters params = RunEnvironment.getInstance().getParameters();
		final int INITIAL_NUMBER_OF_USERS 	= 
				((Integer) params.getValue("initNoUsers")).intValue();
		final int INITIAL_NUMBER_OF_DEALERS = 
				((Integer) params.getValue("initNoUsers")).intValue();
		final int INITIAL_NUMBER_OF_POLICE 	= 
				((Integer) params.getValue("initNoPolice")).intValue();
		final double PROB_POLICE_ARREST 	= 
				((Double) params.getValue("probArrest")).doubleValue();
		final String USER_MOVEMENT_RULE 	= 
				((String) params.getValue("userMovementRule")).toString();
		final String USER_BUY_DRUGS_RULE 	= 
				((String) params.getValue("userBuyDrugsRule")).toString();
		final String DEALER_MOVEMENT_RULE 	= 
				((String) params.getValue("dealerMovementRule")).toString();
		final String POLICE_MOVEMENT_RULE 	= 
				((String) params.getValue("policeMovementRule")).toString();
		final String POLICE_ARREST_RULE 	= 
				((String) params.getValue("policeArrestRule")).toString();		
		// Add projections
		GridFactory gf = GridFactoryFinder.createGridFactory(null);
		Grid grid = gf.createGrid("grid", context, 
					new GridBuilderParameters(new WrapAroundBorders(), 
					new RandomGridAdder(), 
					true, 
					Constants.SIZE_OF_GRID_X, Constants.SIZE_OF_GRID_Y));
		
		// Add agents
		for (int i = 0; i < INITIAL_NUMBER_OF_USERS; i++) {
			context.add(new User(grid, USER_MOVEMENT_RULE, USER_BUY_DRUGS_RULE));
		}
		
		for (int i = 0; i < INITIAL_NUMBER_OF_DEALERS; i++) {
			context.add(new Dealer(grid,DEALER_MOVEMENT_RULE));
		}

		for (int i = 0; i < INITIAL_NUMBER_OF_POLICE; i++) {
			context.add(new Police(grid,POLICE_MOVEMENT_RULE,POLICE_ARREST_RULE));
		}
		
		RunEnvironment.getInstance().endAt(Constants.DEFAULT_END_TIME);
		
		return context;
	}
}
