//package de.cuzim1tigaaa.brainbow.test;
//
//import org.bukkit.Location;
//import org.bukkit.World;
//import org.bukkit.util.BlockVector;
//import org.jetbrains.annotations.NotNull;
//import org.joml.*;
//
//import java.lang.Math;
//
//public class Vector {
//
//	private double x, y, z;
//
//	public Vector() {
//		this.x = 0;
//		this.y = 0;
//		this.z = 0;
//	}
//
//	public Vector(int x, int y, int z) {
//		this.x = x;
//		this.y = y;
//		this.z = z;
//	}
//
//	public Vector(double x, double y, double z) {
//		this.x = x;
//		this.y = y;
//		this.z = z;
//	}
//
//	public Vector(float x, float y, float z) {
//		this.x = x;
//		this.y = y;
//		this.z = z;
//	}
//
//	@NotNull
//	public Vector add(@NotNull Vector vec) {
//		this.x += vec.x;
//		this.y += vec.y;
//		this.z += vec.z;
//		return this;
//	}
//
//	@NotNull
//	public Vector subtract(@NotNull Vector vec) {
//		this.x -= vec.x;
//		this.y -= vec.y;
//		this.z -= vec.z;
//		return this;
//	}
//
//	@NotNull
//	public Vector multiply(@NotNull Vector vec) {
//		this.x *= vec.x;
//		this.y *= vec.y;
//		this.z *= vec.z;
//		return this;
//	}
//
//	@NotNull
//	public Vector divide(@NotNull Vector vec) {
//		this.x /= vec.x;
//		this.y /= vec.y;
//		this.z /= vec.z;
//		return this;
//	}
//
//	@NotNull
//	public Vector copy(@NotNull Vector vec) {
//		return new Vector(this.x, this.y, this.z);
//	}
//
//	public double length() {
//		return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
//	}
//
//	public double lengthSquared() {
//		return this.x * this.x + this.y * this.y + this.z * this.z;
//	}
//
////	public double distance(@NotNull Vector o) {
////		return super.distance(o);
////	}
////
////	public double distanceSquared(@NotNull Vector o) {
////		return super.distanceSquared(o);
////	}
////
////	public float angle(@NotNull Vector other) {
////		return super.angle(other);
////	}
////
////	@NotNull
////	public Vector midpoint(@NotNull Vector other) {
////		return super.midpoint(other);
////	}
////
////	@NotNull
////	public Vector getMidpoint(@NotNull Vector other) {
////		return super.getMidpoint(other);
////	}
//
//	@NotNull
//	public Vector multiply(int m) {
//		return multiply(new Vector(m, m, m));
//	}
//
//	@NotNull
//	public Vector multiply(double m) {
//		return multiply(new Vector(m, m, m));
//	}
//
//	@NotNull
//	public Vector multiply(float m) {
//		return multiply(new Vector(m, m, m));
//	}
//
//	public double dot(@NotNull Vector other) {
//		return this.x * other.x + this.y * other.y + this.z * other.z;
//	}
//
//	@NotNull
//	public Vector crossProduct(@NotNull Vector o) {
//		this.x = this.y * o.z - this.z * o.y;
//		this.y = this.z * o.x - this.x * o.z;
//		this.z = this.x * o.y - this.y * o.x;
//		return this;
//	}
//
//	@NotNull
//	public Vector getCrossProduct(@NotNull Vector o) {
//		return new Vector(
//				this.y * o.z - this.z * o.y,
//				this.z * o.x - this.x * o.z,
//				this.x * o.y - this.y * o.x);
//	}
//
//	@NotNull
//	public Vector normalize() {
//		return super.normalize();
//	}
//
//	@NotNull
//	public Vector zero() {
//		return super.zero();
//	}
//
//	public boolean isZero() {
//		return super.isZero();
//	}
//
//	public boolean isInAABB(@NotNull Vector min, @NotNull Vector max) {
//		return super.isInAABB(min, max);
//	}
//
//	public boolean isInSphere(@NotNull Vector origin, double radius) {
//		return super.isInSphere(origin, radius);
//	}
//
//	public boolean isNormalized() {
//		return super.isNormalized();
//	}
//
//	@NotNull
//	public Vector rotateAroundX(double angle) {
//		return super.rotateAroundX(angle);
//	}
//
//	@NotNull
//	public Vector rotateAroundY(double angle) {
//		return super.rotateAroundY(angle);
//	}
//
//	@NotNull
//	public Vector rotateAroundZ(double angle) {
//		return super.rotateAroundZ(angle);
//	}
//
//	@NotNull
//	public Vector rotateAroundAxis(@NotNull Vector axis, double angle) throws IllegalArgumentException {
//		return super.rotateAroundAxis(axis, angle);
//	}
//
//	@NotNull
//	public Vector rotateAroundNonUnitAxis(@NotNull Vector axis, double angle) throws IllegalArgumentException {
//		return super.rotateAroundNonUnitAxis(axis, angle);
//	}
//
//	public double getX() {
//		return super.getX();
//	}
//
//	public int getBlockX() {
//		return super.getBlockX();
//	}
//
//	public double getY() {
//		return super.getY();
//	}
//
//	public int getBlockY() {
//		return super.getBlockY();
//	}
//
//	public double getZ() {
//		return super.getZ();
//	}
//
//	public int getBlockZ() {
//		return super.getBlockZ();
//	}
//
//	@NotNull
//	public Vector setX(int x) {
//		return super.setX(x);
//	}
//
//	@NotNull
//	public Vector setX(double x) {
//		return super.setX(x);
//	}
//
//	@NotNull
//	public Vector setX(float x) {
//		return super.setX(x);
//	}
//
//	@NotNull
//	public Vector setY(int y) {
//		return super.setY(y);
//	}
//
//	@NotNull
//	public Vector setY(double y) {
//		return super.setY(y);
//	}
//
//	@NotNull
//	public Vector setY(float y) {
//		return super.setY(y);
//	}
//
//	@NotNull
//	public Vector setZ(int z) {
//		return super.setZ(z);
//	}
//
//	@NotNull
//	public Vector setZ(double z) {
//		return super.setZ(z);
//	}
//
//	@NotNull
//	public Vector setZ(float z) {
//		return super.setZ(z);
//	}
//
//	public boolean equals(Object obj) {
//		return super.equals(obj);
//	}
//
//	public int hashCode() {
//		return super.hashCode();
//	}
//
//	@NotNull
//	public Vector clone() {
//		return new Vector(this.x, this.y, this.z);
//	}
//
//	public String toString() {
//		return super.toString();
//	}
//
//	@NotNull
//	public Location toLocation(@NotNull World world) {
//		return super.toLocation(world);
//	}
//
//	@NotNull
//	public Location toLocation(@NotNull World world, float yaw, float pitch) {
//		return super.toLocation(world, yaw, pitch);
//	}
//
//	@NotNull
//	public BlockVector toBlockVector() {
//		return super.toBlockVector();
//	}
//
//	@NotNull
//	public Vector3f toVector3f() {
//		return super.toVector3f();
//	}
//
//	@NotNull
//	public Vector3d toVector3d() {
//		return super.toVector3d();
//	}
//
//	@NotNull
//	public Vector3i toVector3i(int roundingMode) {
//		return super.toVector3i(roundingMode);
//	}
//
//	@NotNull
//	public Vector3i toVector3i() {
//		return super.toVector3i();
//	}
//
//	public void checkFinite() throws IllegalArgumentException {
//		super.checkFinite();
//	}
//}