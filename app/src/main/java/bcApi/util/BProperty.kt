package bcApi.util

class BProperty<T : Any>(var value: T) {

    val observer = mutableMapOf<Long, (oldValue: T, newValue: T) -> Unit>()

    fun observe(oldValue: T, newValue: T) {
        this.observer.values.forEach { it(oldValue, newValue) }
    }
}