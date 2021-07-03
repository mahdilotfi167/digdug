package ir.ac.kntu.core.rigidbody;
@FunctionalInterface
public interface ChangeListener<T> {
    public void onChange(T oldValue,T newValue);
}
