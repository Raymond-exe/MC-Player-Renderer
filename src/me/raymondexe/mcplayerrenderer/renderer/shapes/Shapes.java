package me.raymondexe.mcplayerrenderer.renderer.shapes;

/** 
 * A static class detailing some basic shapes made from Tetrahedrons
 * @author https://github.com/Raymond-exe/
 * @version 1.0
 * @since 0.1
*/

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import me.raymondexe.mcplayerrenderer.renderer.point.Point3d;

public class Shapes {
	
	/**
	 * A 3D multi-colored Cube
	 * @param scale The scale the cube should be.
	 * @return A 3D multi-colored cube
	 */
	public static Tetrahedron getCube(double scale) {
		Point3d pointA = new Point3d(scale, -scale, -scale);
		Point3d pointB = new Point3d(scale, scale, -scale);
		Point3d pointC = new Point3d(scale, scale, scale);
		Point3d pointD = new Point3d(scale, -scale, scale);
		Point3d pointE = new Point3d(-scale, -scale, -scale);
		Point3d pointF = new Point3d(-scale, scale, -scale);
		Point3d pointG = new Point3d(-scale, scale, scale);
		Point3d pointH = new Point3d(-scale, -scale, scale);
		
		Tetrahedron cube = new Tetrahedron(
				new Polygon3d(Color.cyan, pointA, pointB, pointC, pointD),
				new Polygon3d(Color.blue, pointE, pointF, pointG, pointH),
				new Polygon3d(Color.orange, pointA, pointD, pointH, pointE),
				new Polygon3d(Color.green, pointA, pointB, pointF, pointE),
				new Polygon3d(Color.white, pointD, pointC, pointG, pointH),
				new Polygon3d(Color.red, pointC, pointB, pointF, pointG));
		
		return cube;
	}
	
	/**
	 * A rectuangular prism
	 * @param xScale The length the rectangular prism should be along the x axis.
	 * @param yScale The length the rectangular prism should be along the y axis.
	 * @param zScale The length the rectangular prism should be along the z axis.
	 * @return A rectangular prism with the given scales
	 */
	public static Tetrahedron getRectangularPrism(double xScale, double yScale, double zScale) {
		Point3d pointA = new Point3d(xScale, -yScale, -zScale);
		Point3d pointB = new Point3d(xScale, yScale, -zScale);
		Point3d pointC = new Point3d(xScale, yScale, zScale);
		Point3d pointD = new Point3d(xScale, -yScale, zScale);
		Point3d pointE = new Point3d(-xScale, -yScale, -zScale);
		Point3d pointF = new Point3d(-xScale, yScale, -zScale);
		Point3d pointG = new Point3d(-xScale, yScale, zScale);
		Point3d pointH = new Point3d(-xScale, -yScale, zScale);
		
		Tetrahedron prism = new Tetrahedron(
				new Polygon3d(Color.cyan, pointA, pointB, pointC, pointD),		//FRONT	
				new Polygon3d(Color.blue, pointE, pointF, pointG, pointH),		//BACK
				new Polygon3d(Color.orange, pointA, pointD, pointH, pointE),	//LEFT
				new Polygon3d(Color.green, pointA, pointB, pointF, pointE),		//BOTTOM
				new Polygon3d(Color.white, pointD, pointC, pointG, pointH),		//TOP
				new Polygon3d(Color.red, pointC, pointB, pointF, pointG));		//RIGHT	
		
		return prism;
	}

	/**
	 * A minecraft-like 3D figure
	 * @param scale The scale the figure should be
	 * @return A multi-colored figure of a minecraft player.
	 */
	public static List<Tetrahedron> getFigure(int scale) {
		Tetrahedron head = getCube(scale);
		Tetrahedron torso = getRectangularPrism(0.5*scale, 1*scale, 1.5*scale);
		Tetrahedron lArm = getRectangularPrism(0.5*scale, 0.5*scale, 1.5*scale);
		Tetrahedron rArm = getRectangularPrism(0.5*scale, 0.5*scale, 1.5*scale);
		Tetrahedron lLeg = getRectangularPrism(0.5*scale, 0.5*scale, 1.5*scale);
		Tetrahedron rLeg = getRectangularPrism(0.5*scale, 0.5*scale, 1.5*scale);
		
		//SET LOCATIONS
		head.setLocation(0*scale, 0*scale, 2.5*scale);
		torso.setLocation(0*scale, 0*scale, 0*scale);
		lArm.setLocation(0.838776*scale, -1.5*scale, -0.350786*scale);
		rArm.setLocation(0.838776*scale, 1.5*scale, -0.350786*scale);
		lLeg.setLocation(-1.14952*scale, 0.75*scale, -1.88823*scale);
		rLeg.setLocation(-1.14952*scale, -0.75*scale, -1.88823*scale);
		
		List<Tetrahedron> figure = new ArrayList<>();
		figure.add(head);
		figure.add(torso);
		figure.add(lArm);
		figure.add(rArm);
		figure.add(lLeg);
		figure.add(rLeg);
		return figure;
	}
	
	/**
	 * Fixes the rotation values for each of the prisms of the figure 
	 * from the getFigugre() method above.
	 * @param figure The figure to fix.
	 * @see getFigure()
	 */
	public static void fixFigureRotation(List<Tetrahedron> figure) {
		if(figure.size() != 6)
			return;

		final int HEAD = 0;
		final int TORSO = 1;
		final int L_ARM= 2;
		final int R_ARM = 3;
		final int L_LEG = 4;
		final int R_LEG = 5;
		
		
		//SET ROTATIONS
		figure.get(HEAD).setRotation(0, 0, 90);
		figure.get(TORSO).setRotation(0, 0, 90);
		figure.get(L_ARM).setRotation(0, -40, 90);
		figure.get(R_ARM).setRotation(0, -40, 90);
		figure.get(L_LEG).setRotation(0, -75, 105);
		figure.get(R_LEG).setRotation(0, -75, 75);
	}
	
}
