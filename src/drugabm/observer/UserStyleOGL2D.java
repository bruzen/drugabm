/*
* Copyright (c) 2016 Kirsten Wright
* Model design by Kirsten Wright, Owen Gallupe, and John McLevey
*/

package drugabm.observer;

import java.awt.Color;

import drugabm.agents.User;
import repast.simphony.visualizationOGL2D.DefaultStyleOGL2D;
import saf.v3d.scene.VSpatial;

public class UserStyleOGL2D extends DefaultStyleOGL2D{
	@Override
	public Color getColor(final Object agent) {
		if (agent instanceof User) {
			final User user = (User) agent;
			if (user.getBoughtDrugs()) {
				return new Color(0x778899); // lightslategrey
			}
			else {
				return new Color(0xD3D3D3); // lightgrey
			}
		}
		return super.getColor(agent);
	}
}
