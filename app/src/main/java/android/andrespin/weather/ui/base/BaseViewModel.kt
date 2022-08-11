package android.andrespin.weather.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel : ViewModel()

abstract class AppViewModel<i : Intent, s : State, e : Event> : BaseViewModel() {

    abstract val tag: String

    val intent = MutableSharedFlow<i>()
    protected val getIntent : SharedFlow<i> = intent

    abstract val sendState: MutableStateFlow<s>
    abstract val state: StateFlow<s>

    protected val sendEvent = MutableSharedFlow<e>()
    val event: SharedFlow<e> get() = sendEvent

    abstract fun handleIntent()

}