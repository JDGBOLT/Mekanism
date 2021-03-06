package mekanism.common;

/**
 * Code taken from UE and modified to fit Mekanism.
 */
public class EnergyDisplay
{
	public static enum ElectricUnit
	{
		AMPERE("Amp", "I"), 
		AMP_HOUR("Amp Hour", "Ah"), 
		VOLTAGE("Volt", "V"), 
		WATT("Watt", "W"),
		WATT_HOUR("Watt Hour", "Wh"), 
		RESISTANCE("Ohm", "R"), 
		CONDUCTANCE("Siemen", "S"),
		JOULES("Joule", "J");

		public String name;
		public String symbol;

		private ElectricUnit(String s, String s1)
		{
			name = s;
			symbol = s1;
		}

		public String getPlural()
		{
			return name + "s";
		}
	}

	/** Metric system of measurement. */
	public static enum MeasurementUnit
	{
		MICRO("Micro", "u", 0.000001D), 
		MILLI("Milli", "m", 0.001D), 
		BASE("", "", 1),
		KILO("Kilo", "k", 1000D), 
		MEGA("Mega", "M", 1000000D), 
		GIGA("Giga", "G", 1000000000D),
		TERA("Tera", "T", 1000000000000D), 
		PETA("Peta", "P", 1000000000000000D),
		EXA("Exa", "E", 1000000000000000000D), 
		ZETTA("Zetta", "Z", 1000000000000000000000D),
		YOTTA("Yotta", "Y", 1000000000000000000000000D);

		/** long name for the unit */
		public String name;
		
		/** short unit version of the unit */
		public String symbol;
		
		/** Point by which a number is consider to be of this unit */
		public double value;

		private MeasurementUnit(String s, String s1, double v)
		{
			name = s;
			symbol = s1;
			value = v;
		}

		public String getName(boolean getShort)
		{
			if(getShort)
			{
				return symbol;
			}
			else {
				return name;
			}
		}

		public double process(double d)
		{
			return d / value;
		}

		public boolean above(double d)
		{
			return d > value;
		}

		public boolean below(double d)
		{
			return d < value;
		}
	}

	/**
	 * Displays the unit as text. Does handle negative numbers, and will place a negative sign in
	 * front of the output string showing this. Use string.replace to remove the negative sign if
	 * unwanted
	 */
	public static String getDisplay(double value, ElectricUnit unit, int decimalPlaces, boolean isShort)
	{
		String unitName = unit.name;
		String prefix = "";
		
		if(value < 0)
		{
			value = Math.abs(value);
			prefix = "-";
		}

		if(isShort)
		{
			unitName = unit.symbol;
		}
		else if(value > 1)
		{
			unitName = unit.getPlural();
		}

		if(value == 0)
		{
			return value + " " + unitName;
		}
		else {
			for(int i = 0; i < MeasurementUnit.values().length; i++)
			{
				MeasurementUnit lowerMeasure = MeasurementUnit.values()[i];
				
				if(lowerMeasure.below(value) && lowerMeasure.ordinal() == 0)
				{
					return prefix + roundDecimals(lowerMeasure.process(value), decimalPlaces) + " " + lowerMeasure.getName(isShort) + unitName;
				}
				
				if(lowerMeasure.ordinal() + 1 >= MeasurementUnit.values().length)
				{
					return prefix + roundDecimals(lowerMeasure.process(value), decimalPlaces) + " " + lowerMeasure.getName(isShort) + unitName;
				}
				
				MeasurementUnit upperMeasure = MeasurementUnit.values()[i + 1];
				
				if((lowerMeasure.above(value) && upperMeasure.below(value)) || lowerMeasure.value == value)
				{
					return prefix + roundDecimals(lowerMeasure.process(value), decimalPlaces) + " " + lowerMeasure.getName(isShort) + unitName;
				}
			}
		}

		return prefix + roundDecimals(value, decimalPlaces) + " " + unitName;
	}

	public static String getDisplay(double value, ElectricUnit unit)
	{
		return getDisplay(value, unit, 2, false);
	}

	public static String getDisplayShort(double value, ElectricUnit unit)
	{
		return getDisplay(value, unit, 2, true);
	}

	public static String getDisplayShort(double value, ElectricUnit unit, int decimalPlaces)
	{
		return getDisplay(value, unit, decimalPlaces, true);
	}

	public static String getDisplaySimple(double value, ElectricUnit unit, int decimalPlaces)
	{
		if(value > 1)
		{
			if(decimalPlaces < 1)
			{
				return (int)value + " " + unit.getPlural();
			}

			return roundDecimals(value, decimalPlaces) + " " + unit.getPlural();
		}

		if(decimalPlaces < 1)
		{
			return (int)value + " " + unit.name;
		}

		return roundDecimals(value, decimalPlaces) + " " + unit.name;
	}

	public static double roundDecimals(double d, int decimalPlaces)
	{
		int j = (int)(d*Math.pow(10, decimalPlaces));
		return j/Math.pow(10, decimalPlaces);
	}

	public static double roundDecimals(double d)
	{
		return roundDecimals(d, 2);
	}
	
	public static enum EnergyType
	{
		J,
		RF,
		EU,
		MJ
	}
}
