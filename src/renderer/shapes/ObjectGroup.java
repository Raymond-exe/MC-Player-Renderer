package renderer.shapes;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//a class that can hold multiple 3d objects, such
//as tetrahedrons, and even other object groups
//and transform them as one
public class ObjectGroup implements Groupable {

	private double xOrigin;
	private double yOrigin;
	private double zOrigin;
	private double xRotation;
	private double yRotation;
	private double zRotation;
	
	private List<Groupable> children;
	private ObjectGroup parent = null;
	
	public ObjectGroup() {
		children = new ArrayList<>();
		xOrigin = 0;
		yOrigin = 0;
		zOrigin = 0;
		xRotation = 0;
		yRotation = 0;
		zRotation = 0;
	}
	
	public ObjectGroup(Groupable... group) {
		this(0, 0, 0, group);
	}
	
	public ObjectGroup(List<Groupable> group) {
		this(0, 0, 0, group);
	}
	
	public ObjectGroup(double x, double y, double z, Groupable... group) {
		children = new ArrayList<>();
		add(group);
		xOrigin = x;
		yOrigin = y;
		zOrigin = z;
		xRotation = 0;
		yRotation = 0;
		zRotation = 0;
	}
	
	public ObjectGroup(double x, double y, double z, List<Groupable> group) {
		children = new ArrayList<>();
		add(group);
		xOrigin = x;
		yOrigin = y;
		zOrigin = z;
		xRotation = 0;
		yRotation = 0;
		zRotation = 0;
	}
	
	public ObjectGroup merge(ObjectGroup otherGroup) {
		add(otherGroup.getChildren());
		return this;
	}
	
	public List<Tetrahedron> getTetrahedrons() {
		List<Tetrahedron> tetras = new ArrayList<>();	//list of tetrahedrons to be sorted
		
		//pull all tetrahedrons out of children list
		for(int i = 0; i < children.size(); i++) {
			if(children.get(i) instanceof Tetrahedron) {
				tetras.add((Tetrahedron)children.get(i));
			}
		}		
		
		return tetras;
	}
	
	public void render(Graphics g) {
		for(Groupable obj : children) {
			if(obj instanceof Tetrahedron) {
				((Tetrahedron)obj).render(g);
			}
		}
	}
	
	public void liteRenderAll(Graphics g) {
		for(Groupable obj : children) {
			if(obj instanceof Tetrahedron) {
				((Tetrahedron)obj).liteRender(g, 3);
			} else {
				((ObjectGroup)obj).liteRenderAll(g);
			}
		}
	}
	
	public void liteRender(Graphics g, int num) {
		List<Tetrahedron> tetras = getTetrahedrons();
		Collections.sort(tetras, new TetrahedronSorter());
		
		if(num>tetras.size())
			num=tetras.size();
		
		for(int i = tetras.size()-num; i< tetras.size(); i++) {
			tetras.get(i).render(g);
		}
	}
	
	private boolean containsTetrahedrons() {
		for(Groupable child : children)
			if(child instanceof Tetrahedron)
				return true;
		
		return false;
	}
	
	private boolean containsObjectGroups() {
		for(Groupable child : children) 
			if(child instanceof ObjectGroup)
				return true;
		return false;
	}
	
	public void recursiveLiteRender(Graphics g, int num) {
		for(Groupable child : children) {
			if(child instanceof ObjectGroup) {
				ObjectGroup obj = (ObjectGroup)child;
				if(obj.containsTetrahedrons())
					obj.liteRender(g, num);
				if(obj.containsObjectGroups())
					obj.recursiveLiteRender(g, num);
			}
		}
	}
	
	public void sort() {
		sortAll();
	}
	
	private void sortAll() {
		Collections.sort(children, new GroupableSorter());
	}
	
	public void add(Groupable... group) {
		for(Groupable g : group) {
			children.add(g);
		}
	}
	
	public void add(List<Groupable> group) {
		for(Groupable g : group) {
			children.add(g);
		}		
	}
	
	public void addTetrahedron(List<Tetrahedron> group) {
		for(Groupable g : group) {
			children.add(g);
		}		
	}

	@Override
	public boolean addToGroup(ObjectGroup group) {
		if(parent==null) {
			group.add(this);
			parent = group;
			return true;
		}
		
		return false;
	}

	public List<Groupable> getChildren() {
		return children;
	}

	@Override
	public ObjectGroup getParent() {
		return parent;
	}
	
	
	////////// TRANSFORMATION METHODS \\\\\\\\\\
	@Override
	public void move(double deltaX, double deltaY, double deltaZ) {
		xOrigin+= deltaX;
		yOrigin+= deltaY;
		zOrigin+= deltaZ;
		
		for(Groupable g : children) {
			g.move(deltaX, deltaY, deltaZ);
		}
	}
	
	//moves object group back to 0, 0, 0
	@Override
	public void resetLocation() {
		move(-xOrigin, -yOrigin, -zOrigin);
	}

	@Override
	public void setLocation(double newX, double newY, double newZ) {
		resetLocation();
		
		move(newX, newY, newZ);		
	}

	@Override
	public void rotate(boolean CW, double xRotation, double yRotation, double zRotation) {
		if(!CW) {
			xRotation*=-1;
			yRotation*=-1;
			zRotation*=-1;
		}

		this.xRotation+=xRotation;
		this.yRotation+=yRotation;
		this.zRotation+=zRotation;
		
		//double tempX = xOrigin;
		//double tempY = yOrigin;
		//double tempZ = zOrigin;
		//resetLocation();
		
		for(Groupable g : children) {
			g.rotate(CW, xRotation, yRotation, zRotation);
		}
		
		//move(tempX, tempY, tempZ);
	}

	@Override
	public void setRotation(double xRotation, double yRotation, double zRotation) {
		resetRotation();
		
		rotate(true, xRotation, yRotation, zRotation);		
	}

	@Override
	public void resetRotation() {
		rotate(true, -this.xRotation, -this.yRotation, -this.zRotation);		
	}
	
	public double getLocalXCoordinate() {
		return xOrigin;
	}
	
	public double getLocalYCoordinate() {
		return yOrigin;
	}
	
	public double getLocalZCoordinate() {
		return zOrigin;
	}
	
	//TODO fix global coords to account for rotation
	
	public double getGlobalXCoordinate() {
		double globalX = xOrigin*Math.cos(Math.toRadians(zRotation));
		
		if(parent==null) {
			return globalX;
		} else {
			return globalX + parent.getGlobalXCoordinate();
		}
	}
	
	public double getGlobalYCoordinate() {
		if(parent==null) {
			System.out.println("No parent found for object with " + children.size() + " children.");
			return yOrigin;
		} else {
			return yOrigin*Math.sin(Math.toRadians(parent.getXRotation())) + parent.getGlobalYCoordinate();
		}
	}
	
	public double getGlobalZCoordinate() {
		double globalZ = zOrigin*Math.sin(Math.toRadians(zRotation));
		
		if(parent==null) {
			return globalZ;
		} else {
			return globalZ + parent.getGlobalZCoordinate();
		}
	}

	@Override
	public double getXCoordinate() {
		return getGlobalXCoordinate();
	}

	@Override
	public double getYCoordinate() {
		return getGlobalYCoordinate();
	}

	@Override
	public double getZCoordinate() {
		return getGlobalZCoordinate();
	}
	
	protected double getXRotation() {
		return xRotation;
	}
	
	protected double getZYRotation() {
		return yRotation;
	}
	
	protected double getZRotation() {
		return zRotation;
	}
	
}
