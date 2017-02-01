
package org.usfirst.frc.team1732.robot.smartdashboard;

import java.util.function.Consumer;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class SmartDashboardItem<T> {

	private final String		key;
	private T					value;
	private final Supplier<T>	supplier;
	// if we ever find out we need to switch between sending and receiving data
	// on the same key we can make this non-final

	private SmartDashboardItem(String key, T value, Supplier<T> supplier) {
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
	}

	protected abstract void init();

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

	public static SmartDashboardItem<Double> newDoubleSender(String key, Supplier<Double> supplier) {
		return new SmartDashboardItem<Double>(key, supplier.get(), supplier) {
			@Override
			protected void run() {
				setValue(supplierGet());
				SmartDashboard.putNumber(getKey(), getValue());
			}

			@Override
			protected void init() {
				SmartDashboard.putNumber(getKey(), getValue());
			}
		};
	}

	public static SmartDashboardItem<Double> newDoubleReciever(String key, Double value, Consumer<Double> consumer) {
		return new SmartDashboardItem<Double>(key, value, null) {
			@Override
			protected void run() {
				setValue(SmartDashboard.getNumber(getKey(), getValue()));
				consumer.accept(getValue());
			}

			@Override
			protected void init() {
				SmartDashboard.putNumber(getKey(), getValue());
			}
		};
	}

	public static SmartDashboardItem<String> newStringSender(String key, Supplier<String> supplier) {
		return new SmartDashboardItem<String>(key, supplier.get(), supplier) {
			@Override
			protected void run() {
				setValue(supplierGet());
				SmartDashboard.putString(getKey(), getValue());
			}

			@Override
			protected void init() {
				SmartDashboard.putString(getKey(), getValue());
			}
		};
	}

	public static SmartDashboardItem<String> newStringReciever(String key, String value, Consumer<String> consumer) {
		return new SmartDashboardItem<String>(key, value, null) {
			@Override
			protected void run() {
				setValue(SmartDashboard.getString(getKey(), getValue()));
				consumer.accept(getValue());
			}

			@Override
			protected void init() {
				SmartDashboard.putString(getKey(), getValue());
			}
		};
	}

	public static SmartDashboardItem<Boolean> newBooleanSender(String key, Supplier<Boolean> supplier) {
		return new SmartDashboardItem<Boolean>(key, supplier.get(), supplier) {
			@Override
			protected void run() {
				setValue(supplierGet());
				SmartDashboard.putBoolean(getKey(), getValue());
			}

			@Override
			protected void init() {
				SmartDashboard.putBoolean(getKey(), getValue());
			}
		};
	}

	public static SmartDashboardItem<Boolean> newBooleanReciever(String key, Boolean value,
			Consumer<Boolean> consumer) {
		return new SmartDashboardItem<Boolean>(key, value, null) {
			@Override
			protected void run() {
				setValue(SmartDashboard.getBoolean(getKey(), getValue()));
				consumer.accept(getValue());
			}

			@Override
			protected void init() {
				SmartDashboard.putBoolean(getKey(), getValue());
			}
		};
	}

}