package ir.ac.kntu.util;
@FunctionalInterface
public interface ChangeListener<T> {
    public void onChange(T oldValue,T newValue);
}
