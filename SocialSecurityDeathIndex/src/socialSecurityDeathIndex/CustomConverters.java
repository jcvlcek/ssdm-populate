package socialSecurityDeathIndex;

import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.conversion.IConverter;

/**
 * Utility class, containing customized converters for binding model object properties
 * to SWT widget properties.
 * @author Jim Vlcek
 *
 */
public final class CustomConverters {

	/**
	 * Gets an {@link org.eclipse.core.databinding.conversion.IConverter} that
	 * inverts a {@link java.lang.Boolean} value.<br>
	 * Used to enable a widget when a {@link java.lang.Boolean} value
	 * in the model has the value <code>false</code>.
	 * @return an {@link org.eclipse.core.databinding.UpdateValueStrategy} whose converter
	 * has been set to a logical inversion
	 */
	public static UpdateValueStrategy getBooleanInverter()
	{
		return new UpdateValueStrategy().setConverter(new IConverter() {
			@Override
			public Object getToType() {
			    return Boolean.TYPE;
			}
			   @Override
			public Object getFromType() {
			    return Boolean.TYPE;
			}
			@Override
			    public Object convert(Object fromObject) {
			    if (fromObject instanceof Boolean) {
			   // return Inverse of source value
			        return ((Boolean) fromObject).booleanValue() ? Boolean.FALSE : Boolean.TRUE;
			        }
			  return Boolean.FALSE;
			}
			});
	}

	/**
	 * Gets an {@link org.eclipse.core.databinding.conversion.IConverter} that
	 * converts an {@link java.lang.Integer} value to a {@link java.lang.String}
	 * without a thousands separator.<br>
	 * Used to represent {@link java.lang.Integer} values, such as IP port numbers,
	 * that are traditionally displayed without thousands separators.
	 * @return an {@link org.eclipse.core.databinding.UpdateValueStrategy} whose converter
	 * generates a {@link java.lang.String} representation of an {@link java.lang.Integer} 
	 * without thousands separators.
	 */
	public static UpdateValueStrategy getIntToStringConverter()
	{
		return new UpdateValueStrategy().setConverter(new IConverter() {
			@Override
			public Object getToType() {
			    return String.class;
			}
			   @Override
			public Object getFromType() {
			    return Integer.TYPE;
			}
			@Override
			    public Object convert(Object fromObject) {
			    if (fromObject instanceof Integer) {
			   // return string representation without commas
			        return String.format("%d", (Integer)fromObject);
			        }
			  return "0";
			}
			});
	}
}
