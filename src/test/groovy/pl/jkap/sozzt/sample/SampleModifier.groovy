package pl.jkap.sozzt.sample

import java.lang.reflect.Modifier

class SampleModifier {

    static <T> T with(Class<T> clazz, T toChange, Map<String, Object> properties = [:]) {
        Map baseObjectProperties = clazz.declaredFields
                .findAll { !it.synthetic && !Modifier.isFinal(it.getModifiers()) }
                .collectEntries { field ->
                    [field.name, toChange."$field.name"]
                }
        Map changedProperties = baseObjectProperties + properties
        return clazz.newInstance(changedProperties)
    }
}
