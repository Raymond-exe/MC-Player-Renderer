package me.raymondexe.mcplayerrenderer.renderer.shapes;

/** 
 * A group of 3D objects, being either Tetrahedrons or other ObjectGroups
 * @author https://github.com/Raymond-exe/
 * @version 0.1
 * @since 0.1
*/

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
	public String identifier;	//mostly used for debug purposes, not seen by user
	
	private List<Groupable> children;
	private ObjectGroup parent;
	
	/**
	 * Creates a new ObjectGroup
	 */
	public ObjectGroup() {
		children = new ArrayList<>();
		xOrigin = 0;
		yOrigin = 0;
		zOrigin = 0;
		xRotation = 0;
		yRotation = 0;
		zRotation = 0;
		parent = null;
	}
	
	/**
	 * Creates a new ObjectGroup
	 * @param group Groupable objects to add to this ObjectGroup
	 */
	public ObjectGroup(Groupable... group) {
		this(0, 0, 0, group);
	}
	
	/**
	 * Creates a new ObjectGroup
	 * @param group Groupable objects to add to this ObjectGroup
	 */	
	public ObjectGroup(List<Groupable> group) {
		this(0, 0, 0, group);
	}
	
	/**
	 * Creates a new ObjectGroup
	 * @param x The x coordinate this ObjectGroup will be centered at
	 * @param y The y coordinate this ObjectGroup will be centered at
	 * @param z The z coordinate this ObjectGroup will be centered at
	 * @param group Groupable objects to add to this ObjectGroup
	 */
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

	/**
	 * Creates a new ObjectGroup
	 * @param x The x coordinate this ObjectGroup will be centered at
	 * @param y The y coordinate this ObjectGroup will be centered at
	 * @param z The z coordinate this ObjectGroup will be centered at
	 * @param group Groupable objects to add to this ObjectGroup
	 */
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
	
	/**
	 * Merges another ObjectGroup's Groupables into this ObjectGroup
	 * @param otherGroup The other ObjectGroup to add objects from
	 * @return This ObjectGroup
	 */
	public ObjectGroup merge(ObjectGroup otherGroup) {
		add(otherGroup.getChildren());
		return this;
	}
	
	/**
	 * Gets the Tetrahedrons in this ObjectGroup (and NOT in any subgroups)
	 * @return Tetrahedrons in this ObjectGroup
	 */
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
	
	public ObjectGroup getChild(String str) {
		for(Groupable child : children) {
			if(child instanceof ObjectGroup && ((ObjectGroup)child).identifier.equals(str)) {
				return (ObjectGroup)child;
			}
		}
		
		return null;
	}
	
	/**
	 * Lite Renders all Tetrahedrons in this ObjectGroup and in any subgroups
	 * @param g The Graphics object to render to
	 */
	public void liteRenderAll(Graphics g) {
		for(Groupable obj : children) {
			if(obj instanceof Tetrahedron) {
				((Tetrahedron)obj).liteRender(g, 3);
			} else {
				((ObjectGroup)obj).liteRenderAll(g);
			}
		}
	}
	
	/**
	 * Only renders the closest given number of Tetrahedrons in this ObjectGroup and each sub group
	 * @param g The Graphics object to render to
	 * @param num The number of Tetrahedrons to render
	 */
	public void liteRender(Graphics g, int num) {
		List<Tetrahedron> tetras = getTetrahedrons();
		Collections.sort(tetras, new TetrahedronSorter());
		
		if(num>tetras.size())
			num=tetras.size();
		
		for(int i = tetras.size()-num; i< tetras.size(); i++) {
			tetras.get(i).render(g);
		}
	}
	
	@Override
	public void render(Graphics g) {
		sortObjectGroups();
		recursiveRender(g);
	}
	
	/**
	 * Recursively renders the object group by calling itself of any object group children,
	 * and calling render on any Tetrahedrons
	 * @param g The Graphics object to render to
	 */
	private void recursiveRender(Graphics g) {		
		
		for(Groupable child : children) {
			if(child instanceof ObjectGroup) {
				((ObjectGroup)child).recursiveRender(g);				
			} else if (child instanceof Tetrahedron) {
				((Tetrahedron)child).render(g);
			}
		}
	}
	
	/**
	 * Recursively Lite Renders the given number of Tetrahedrons
	 * @param g The Graphics object to render to
	 * @param num The number of Tetrahedrons to render
	 */
	public void recursiveLiteRender(Graphics g, int num) {
		sort();
		
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
	
	/**
	 * Determines whether or not this group (not any sub groups) contains Tetrahedrons
	 * @return Whether or not this group has Tetrahedrons
	 */
	private boolean containsTetrahedrons() {
		for(Groupable child : children)
			if(child instanceof Tetrahedron)
				return true;
		
		return false;
	}
	
	/**
	 * Determines whether or not this group (not any sub groups) contains other ObjectGroups
	 * @return Whether or not this group has ObjectGroups
	 */
	private boolean containsObjectGroups() {
		for(Groupable child : children) 
			if(child instanceof ObjectGroup)
				return true;
		return false;
	}
	
	/**
	 * Sorts Groupables based on which Groupables are closest to the camera
	 */
	public void sort() {
		Collections.sort(children, new GroupableSorter());
	}
	
	public void sortObjectGroups() {
		List<ObjectGroup> sortMe = new ArrayList<>();
		for(int i = 0; i < children.size(); i++) {
			if(children.get(i) instanceof ObjectGroup) {
				((ObjectGroup)children.get(i)).sort();
				sortMe.add((ObjectGroup)children.remove(i--));
			}
		}
		
		Collections.sort(sortMe, new ObjectGroupSorter());
		children.addAll(sortMe);
	}
	
	/**
	 * Adds new Groupable objects to this ObjectGroup.
	 * @param group Groupable objects to add.
	 */
	public void add(Groupable... group) {
		for(Groupable g : group) {
			children.add(g);
			g.setParent(this);
		}
	}
	
	/**
	 * Adds new Groupable objects to this ObjectGroup.
	 * @param group Groupable objects to add.
	 */
	public void add(List<Groupable> group) {
		for(Groupable g : group) {
			children.add(g);
			g.setParent(this);
		}		
	}
	
	/**
	 * Adds new Tetrahedron objects to this ObjectGroup.
	 * @param group List of Tetrahedron objects to add.
	 */
	public void addTetrahedron(List<Tetrahedron> group) {
		for(Groupable g : group) {
			children.add(g);
			g.setParent(this);
		}		
	}

	@Override
	/**
	 * Adds a new ObjectGroup to this ObjectGroup.
	 * @param group ObjectGroup to add.
	 */
	public boolean addToGroup(ObjectGroup group) {
		if(parent==null) {
			group.add(this);
			parent = group;
			return true;
		}
		
		return false;
	}

	/**
	 * Gets the Groupable objects in this ObjectGroup
	 * @return Groupable objects in this ObjectGroup
	 */
	public List<Groupable> getChildren() {
		return children;
	}

	@Override
	/**
	 * Gets the ObjectGroup this ObjectGroup is in
	 * @return the ObjectGroup this ObjectGroup is in
	 */
	public ObjectGroup getParent() {
		return parent;
	}
	
	@Override
	/**
	 * Assigns this ObjectGroup to another ObjectGroup
	 * @param objectGroup The ObjectGroup this ObjectGroup will be assigned to
	 */
	public void setParent(ObjectGroup objectGroup) {
		parent = objectGroup;
	}
	
	
	////////// TRANSFORMATION METHODS \\\\\\\\\\
	@Override
	/**
	 * Moves this ObjectGroup based on the given values, and moves all assigned Groupables accordingly
	 * @param deltaX The movement along the x-axis
	 * @param deltaY The movement along the y-axis
	 * @param deltaZ The movement along the z-axis
	 */
	public void move(double deltaX, double deltaY, double deltaZ) {
		xOrigin+= deltaX;
		yOrigin+= deltaY;
		zOrigin+= deltaZ;
		
		for(Groupable g : children) {
			g.move(deltaX, deltaY, deltaZ);
		}
	}
	
	
	@Override
	/**
	 * Moves this ObjectGroup back to the origin (0.0, 0.0, 0.0).
	 */
	public void resetLocation() {
		move(-xOrigin, -yOrigin, -zOrigin);
	}

	@Override
	/**
	 * Sets the location of this ObjectGroup
	 * @param newX The x value this ObjectGroup will be moved to.
	 * @param newY The y value this ObjectGroup will be moved to.
	 * @param newZ The z value this ObjectGroup will be moved to.
	 */
	public void setLocation(double newX, double newY, double newZ) {
		resetLocation();
		
		move(newX, newY, newZ);		
	}

	@Override
	/**
	 * Rotates this ObjectGroup around the origin
	 * @param CW Whether or not to rotate it clockwise
	 * @param xRotation The rotation, in degrees, along the x-axis
	 * @param yRotation The rotation, in degrees, along the y-axis
	 * @param zRotation The rotation, in degrees, along the z-axis
	 */
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
	/**
	 * Sets this ObjectGroup's rotation
	 * @param xRotation The rotation, in degrees, this ObjectGroup should be along the x-axis
	 * @param yRotation The rotation, in degrees, this ObjectGroup should be along the y-axis
	 * @param zRotation The rotation, in degrees, this ObjectGroup should be along the z-axis
	 */
	public void setRotation(double xRotation, double yRotation, double zRotation) {
		resetRotation();
		
		rotate(true, xRotation, yRotation, zRotation);		
	}

	@Override
	/**
	 * Resets this ObjectGroup's rotation
	 */
	public void resetRotation() {
		rotate(true, -this.xRotation, -this.yRotation, -this.zRotation);		
	}
	
	/**
	 * Gets the x coordinate relative to this ObjectGroup's parent ObjectGroup
	 * If there is no parent, get the global x coordinate
	 * @return The local x coordinate
	 */
	public double getLocalXCoordinate() {
		return xOrigin;
	}
	
	/**
	 * Gets the y coordinate relative to this ObjectGroup's parent ObjectGroup
	 * If there is no parent, get the global y coordinate
	 * @return The local y coordinate
	 */
	public double getLocalYCoordinate() {
		return yOrigin;
	}
	
	/**
	 * Gets the z coordinate relative to this ObjectGroup's parent ObjectGroup
	 * If there is no parent, get the global z coordinate
	 * @return The local z coordinate
	 */
	public double getLocalZCoordinate() {
		return zOrigin;
	}
	
	//TODO fix global coords to account for rotation
	
	/**
	 * Gets the x position of this ObjectGroup relative to the origin of the 3D space
	 * @return The global x coordinate, NOT relative to the parent ObjectGroup
	 */
	public double getGlobalXCoordinate() {
		//TODO calc global coords
		return xOrigin;
	}
	
	/**
	 * Gets the y position of this ObjectGroup relative to the origin of the 3D space
	 * @return The global y coordinate, NOT relative to the parent ObjectGroup
	 */
	public double getGlobalYCoordinate() {
		//TODO calc global coords
		return //yOrigin*Math.sin(Math.toRadians(-xRotation))*Math.sin(Math.toRadians(-zRotation))
			  xOrigin*Math.cos(Math.toRadians(zRotation));
			 //+ zOrigin*Math.sin(Math.toDegrees(-xRotation));
	}
	
	/**
	 * Gets the z position of this ObjectGroup relative to the origin of the 3D space
	 * @return The global z coordinate, NOT relative to the parent ObjectGroup
	 */
	public double getGlobalZCoordinate() {
		//TODO calc global coords
		return zOrigin;
	}

	@Override
	/**
	 * @return The global x coordinate
	 * @see getGlobalXCoordinate()
	 */
	public double getXCoordinate() {
		return getLocalXCoordinate();
	}

	@Override
	/**
	 * @return The global y coordinate
	 * @see getGlobalYCoordinate()
	 */
	public double getYCoordinate() {
		return getGlobalYCoordinate();
	}

	@Override
	/**
	 * @return The global z coordinate
	 * @see getGlobalZCoordinate()
	 */
	public double getZCoordinate() {
		return getLocalZCoordinate();
	}
	
	/**
	 * Gets this ObjectGroup's rotation along the x-axis (in degrees)
	 * @return Rotation along the x-axis (in degrees)
	 */
	protected double getXRotation() {
		return xRotation;
	}
	
	/**
	 * Gets this ObjectGroup's rotation along the y-axis (in degrees)
	 * @return Rotation along the y-axis (in degrees)
	 */
	protected double getYRotation() {
		return yRotation;
	}
	
	/**
	 * Gets this ObjectGroup's rotation along the z-axis (in degrees)
	 * @return Rotation along the z-axis (in degrees)
	 */
	protected double getZRotation() {
		return zRotation;
	}
	
	//*
	public Tetrahedron mergeAll() {
		Tetrahedron output = new Tetrahedron();
		
		for(Groupable child : children) {
			if(child instanceof Tetrahedron) {
				output.merge((Tetrahedron)child);
			} else if (child instanceof ObjectGroup) {
				output.merge(((ObjectGroup)child).mergeAll());
			}
		}
		
		return output;
		
	} //*/
	
	/**
	 * Returns a String representation of this object group
	 * @return String data type including this object group's identifier, parent, and children
	 */
	public String toString() {
		StringBuilder output = new StringBuilder();
		
		output.append("Identifier: \"" + identifier + "\"");
		//output.append("Local X:" + getLocalXCoordinate() + "\t");
		//output.append("Local Y:" + getLocalYCoordinate() + "\t");
		//output.append("Local Z:" + getLocalZCoordinate() + "\n");
		//output.append("Global X:" + getGlobalXCoordinate() + "\t");
		//output.append("Global Y:" + getGlobalYCoordinate() + "\t");
		//output.append("Global Z:" + getGlobalZCoordinate() + "");
		//output.append("Parent: \"" + (parent.identifier==null? parent.identifier : "null") + "\"\n");
		//output.append("Children (" + children.size() + "): " + children);
		
		return output.toString();
	}

	@Override
	public void delete() {
		while(children.size()>0) {
			children.get(0).delete();
			children.remove(0);
		}
		children = null;
	}
	
}
