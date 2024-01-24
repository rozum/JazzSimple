package com.example.jazz.simple.ui.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.RememberObserver
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.Event.ON_DESTROY
import androidx.lifecycle.Lifecycle.Event.ON_RESUME
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.get
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

class ScopedViewModelStoreOwner : ViewModel(), ViewModelStoreOwner, RememberObserver {

    override val viewModelStore = ViewModelStore()
    var isChangingConfiguration = false

    override fun onAbandoned() {
        if (!isChangingConfiguration) {
            viewModelStore.clear()
        }
    }

    override fun onForgotten() {
        if (!isChangingConfiguration) {
            viewModelStore.clear()
        }
    }

    override fun onRemembered() = Unit

    override fun onCleared() {
        viewModelStore.clear()
    }
}

@Composable
fun OnLifecycleEvent(onEvent: (owner: LifecycleOwner, event: Lifecycle.Event) -> Unit) {
    val eventHandler = rememberUpdatedState(onEvent)
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)

    DisposableEffect(lifecycleOwner.value) {
        val lifecycle = lifecycleOwner.value.lifecycle
        val observer = LifecycleEventObserver { owner, event ->
            eventHandler.value(owner, event)
        }

        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
}

@Composable
fun scopedViewModelStoreOwner(): ScopedViewModelStoreOwner {
    val activity = requireNotNull(LocalContext.current).findActivity()
    val parentStoreOwner = checkNotNull(LocalViewModelStoreOwner.current)
    val factory = viewModelFactory {
        initializer { ScopedViewModelStoreOwner() }
    }
    val storeOwner: ScopedViewModelStoreOwner =
        ViewModelProvider(parentStoreOwner.viewModelStore, factory, CreationExtras.Empty).get()

    OnLifecycleEvent { _, event ->
        when (event) {
            ON_RESUME -> storeOwner.isChangingConfiguration = false
            ON_DESTROY -> storeOwner.isChangingConfiguration = activity.isChangingConfigurations
            else -> Unit
        }
    }
    return storeOwner
}

@Composable
inline fun <reified VM : ViewModel> scopedViewModel(
    viewModelStoreOwner: ViewModelStoreOwner = scopedViewModelStoreOwner(),
    key: String? = null,
    factory: ViewModelProvider.Factory? = null,
    extras: CreationExtras = if (viewModelStoreOwner is HasDefaultViewModelProviderFactory) {
        viewModelStoreOwner.defaultViewModelCreationExtras
    } else {
        CreationExtras.Empty
    }
): VM {
    val storeOwner = remember { viewModelStoreOwner }
    return viewModel(VM::class.java, storeOwner, key, factory, extras)
}

fun Context.findActivity(): Activity {
    var ctx = this
    while (ctx is ContextWrapper) {
        if (ctx is Activity) {
            return ctx
        }
        ctx = ctx.baseContext
    }
    throw IllegalStateException(
        "Expected an activity context for detecting configuration changes for a NavBackStackEntry but instead found: $ctx"
    )
}