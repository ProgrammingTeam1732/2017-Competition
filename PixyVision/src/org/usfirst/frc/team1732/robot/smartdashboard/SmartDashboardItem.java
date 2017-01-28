package org.usfirst.frc.team1732.robot.smartdashboard;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class SmartDashboardItem<T> {

	private final String		key;
	private T					value;
	private final Supplier<T>	supplier;
	// if we ever find out we need to switch between sending and receiving data
	// on the same key we can make this non-final

	protected SmartDashboardItem(String key, T value, Supplier<T> supplier) {
		if (key == null) {
			throw new IllegalArgumentException("Key must be non-null");
		}
		if (key.isEmpty()) {
			throw new IllegalArgumentException("Key must be non-empty");
		}
		if (value == null) {
			throw new IllegalArgumentException("Value must be non-null");
		}
		this.key = key;
		this.value = value;
		this.supplier = supplier;
		SmartDashboard.putString(key, value.toString());
	}

	/**
	 * Theoretically, when using the smartDashboard, one should only be sending
	 * or receiving data, but not both.
	 * </nl>
	 * If one was trying to do both, the sending data would overwrite any
	 * changes the user makes on the smartdashboard and receiving data would not
	 * function
	 */
	protected abstract void run();

	public String getKey() {
		return key;
	}

	public T getValue() {
		return value;
	}

	protected void setValue(T value) {
		this.value = value;
	}

	protected T supplierGet() {
		return supplier.get();
	}

}