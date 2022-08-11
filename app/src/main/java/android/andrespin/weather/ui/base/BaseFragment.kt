package android.andrespin.weather.ui.base

import android.andrespin.weather.ui.hideKeyboard
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel> : Fragment() {

    private lateinit var _viewBinding: VB

    protected lateinit var model: VM

    abstract val viewModelClass: Class<VM>

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    protected val binding: VB
        get() = _viewBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("Fragment lifecycle", "onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        Log.d("Fragment lifecycle", "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("Fragment lifecycle", "onCreateView")
        _viewBinding = bindingInflater.invoke(inflater, container, false)
        return _viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListeners()
        observeViewModel()
        process()
        initHideKeyBoardListener()
        Log.d("Fragment lifecycle", "onViewCreated")
    }


    private fun initHideKeyBoardListener() {
        binding.root.setOnClickListener {
            hideKeyboard()
        }
    }

    abstract fun initClickListeners()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("Fragment lifecycle", "onActivityCreated")
    }

    override fun onStart() {
        super.onStart()
        Log.d("Fragment lifecycle", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Fragment lifecycle", "onResume")
    }


    override fun onPause() {
        super.onPause()
        pause()
        Log.d("Fragment lifecycle", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Fragment lifecycle", "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("Fragment lifecycle", "onDestroyView")
    }

    open fun pause() {

    }

    override fun onDestroy() {
        super.onDestroy()
        destroy()
        Log.d("Fragment lifecycle", "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("Fragment lifecycle", "onDetach")
    }

    open fun destroy() {}

    open fun process() {

    }


    abstract fun observeViewModel()

    private fun initViewModel() {
        model = ViewModelProvider(this).get(viewModelClass)
    }

    fun toastMessage(text: String) {
        val duration = Toast.LENGTH_LONG
        val toast = Toast.makeText(requireContext(), text, duration)
        toast.show()
    }

}