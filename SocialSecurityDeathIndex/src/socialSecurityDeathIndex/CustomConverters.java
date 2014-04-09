package socialSecurityDeathIndex;

import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.conversion.IConverter;

public final class CustomConverters {

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
