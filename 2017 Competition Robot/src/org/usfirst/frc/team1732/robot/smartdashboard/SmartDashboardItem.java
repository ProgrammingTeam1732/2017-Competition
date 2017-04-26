
package org.usfirst.frc.team1732.robot.smartdashboard;

import java.util.function.Consumer;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class SmartDashboardItem<T> {

	private final String	key;
	private T				value;
	// if we ever find out we need to switch between sending and receiving data
	// on the same key we can make this non-final

	private SmartDashboardItem(String key, T value) {
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
	}

	protected abstract void init();

	/*
	 * Theoretically, when using the smartDashboard, one should only be sending
	 * or receiving data, but not both.
	 * 
	 * If one was trying to do both, the sending data would overwrite any
	 * changes the user makes on the smartdashboard and receiving data would not
	 * function
	 * 
	 * This is why there is only 1 function, run(), not a send() and recieve()
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

	public static SmartDashboardItem<Number> newNumberSender(String key, Supplier<Number> supplier) {
		return new SmartDashboardItem<Number>(key, supplier.get()) {
			@Override
			protected void run() {
				setValue(supplier.get());
				SmartDashboard.putNumber(getKey(), getValue().doubleValue());
			}

			@Override
			protected void init() {
				SmartDashboard.putNumber(getKey(), getValue().doubleValue());
			}
		};
	}

	public static SmartDashboardItem<Double> newDoubleReciever(String key, Double value, Consumer<Double> consumer) {
		return new SmartDashboardItem<Double>(key, value) {
			@Override
			protected void run() {
				setValue(SmartDashboard.getNumber(getKey(), getValue()));
				consumer.accept(getValue());
			}

			@Override
			protected void init() {
				SmartDashboard.putNumber(getKey(), getValue().doubleValue());
			}
		};
	}

	public static SmartDashboardItem<Double> newDoubleReciever(String key, Double value) {
		return new SmartDashboardItem<Double>(key, value) {
			@Override
			protected void run() {
				setValue(SmartDashboard.getNumber(getKey(), getValue()));
			}

			@Override
			protected void init() {
				SmartDashboard.putNumber(getKey(), getValue().doubleValue());
			}
		};
	}

	public static SmartDashboardItem<String> newStringSender(String key, Supplier<String> supplier) {
		return new SmartDashboardItem<String>(key, supplier.get()) {
			@Override
			protected void run() {
				setValue(supplier.get());
				SmartDashboard.putString(getKey(), getValue());
			}

			@Override
			protected void init() {
				SmartDashboard.putString(getKey(), getValue());
			}
		};
	}

	public static SmartDashboardItem<String> newStringReciever(String key, String value, Consumer<String> consumer) {
		return new SmartDashboardItem<String>(key, value) {
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

	public static SmartDashboardItem<String> newStringReciever(String key, String value) {
		return new SmartDashboardItem<String>(key, value) {
			@Override
			protected void run() {
				setValue(SmartDashboard.getString(getKey(), getValue()));
			}

			@Override
			protected void init() {
				SmartDashboard.putString(getKey(), getValue());
			}
		};
	}

	public static SmartDashboardItem<Boolean> newBooleanSender(String key, Supplier<Boolean> supplier) {
		return new SmartDashboardItem<Boolean>(key, supplier.get()) {
			@Override
			protected void run() {
				setValue(supplier.get());
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
		return new SmartDashboardItem<Boolean>(key, value) {
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

	public static SmartDashboardItem<Boolean> newBooleanReciever(String key, Boolean value) {
		return new SmartDashboardItem<Boolean>(key, value) {
			@Override
			protected void run() {
				setValue(SmartDashboard.getBoolean(getKey(), getValue()));
			}

			@Override
			protected void init() {
				SmartDashboard.putBoolean(getKey(), getValue());
			}
		};
	}

}