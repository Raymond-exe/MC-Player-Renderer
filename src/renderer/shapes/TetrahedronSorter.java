package renderer.shapes;

import java.util.Comparator;

public class TetrahedronSorter implements Comparator<Tetrahedron> {

	@Override
	public int compare(Tetrahedron o1, Tetrahedron o2) {
		Tetrahedron tetra1 = (Tetrahedron)o1;		
		Tetrahedron tetra2 = (Tetrahedron)o2;
		
		if(tetra1.getYCoordinate() > tetra2.getYCoordinate()) 
			return 1;
		else if (tetra2.getYCoordinate() > tetra1.getYCoordinate())
			return -1;
		else
			return 0;
	}
	
}
