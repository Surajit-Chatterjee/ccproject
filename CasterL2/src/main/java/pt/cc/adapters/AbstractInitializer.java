package pt.cc.adapters;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import jakarta.annotation.PostConstruct;
import pt.cc.annotations.CheckObject;

public class AbstractInitializer extends GenericAbstractAdapter implements Initializer {

	@PostConstruct
	public void init() {
		super.init();
		checkObject(this);

	}

	private void checkObject(Object targetAdapterObject) {
		logger.logInfo("checking object for targetAdapterObject " + targetAdapterObject.getClass());
		Map<Field, CheckObject> fields = getFieldsIncludingSuperClass(targetAdapterObject, CheckObject.class);
		for (Map.Entry<Field, CheckObject> entry : fields.entrySet()) {
			Field field = entry.getKey();
			CheckObject annotation = entry.getValue();
			try {
				field.setAccessible(true);

				logger.logInfo("Field " + field.getName() + " annotation " + annotation.getClass());

				Object object = field.get(targetAdapterObject);
				if (object == null) {
					throw new NullPointerException("'" + field.getName() + "' must not be null!");
					// logger.logInfo("Field " + field.getName()+" Not Instantiated");
				}
				field.setAccessible(false);
			} catch (Exception ex) {
				logger.logInfo("Error at accessing field " + field.getName() + " in " + targetAdapterObject);
				ex.printStackTrace();
			}
		}
	}

	public <T extends Annotation> Map<Field, T> getFieldsIncludingSuperClass(Object targetObject, Class<T> annotation) {
		Map<Field, T> ret = new HashMap<Field, T>(4);
		Class<?> clazz = targetObject.getClass();
		while (clazz != null) {
			Field[] ff = clazz.getDeclaredFields();
			for (Field f : ff) {
				T anno = f.getAnnotation(annotation);
				if (anno != null) {
					ret.put(f, anno);
				}
			}
			clazz = clazz.getSuperclass();
		}
		return ret;
	}

}
