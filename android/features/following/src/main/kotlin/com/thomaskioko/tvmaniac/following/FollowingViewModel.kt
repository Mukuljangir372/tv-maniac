package com.thomaskioko.tvmaniac.following

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomaskioko.tvmaniac.core.util.CoroutineScopeOwner
import com.thomaskioko.tvmaniac.details.api.interactor.ObserveFollowingInteractor
import com.thomaskioko.tvmaniac.shared.core.ui.Store
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowingViewModel @Inject constructor(
    private val interactor: ObserveFollowingInteractor,
) : Store<WatchlistLoaded, WatchlistAction, WatchlistEffect>,
    CoroutineScopeOwner, ViewModel() {

    override val coroutineScope: CoroutineScope
        get() = viewModelScope

    override val state: MutableStateFlow<WatchlistLoaded> = MutableStateFlow(WatchlistLoaded.Empty)

    private val sideEffect = MutableSharedFlow<WatchlistEffect>()

    init {
        dispatch(WatchlistAction.LoadWatchlist)
    }

    override fun observeState(): StateFlow<WatchlistLoaded> = state

    override fun observeSideEffect(): Flow<WatchlistEffect> = sideEffect

    override fun dispatch(action: WatchlistAction) {
        val oldState = state.value

        when (action) {
            is WatchlistAction.Error -> {
                coroutineScope.launch {
                    sideEffect.emit(WatchlistEffect.Error(action.message))
                }
            }
            WatchlistAction.LoadWatchlist -> {
                with(state) {
                    interactor.execute(Unit) {
                        onStart {
                            coroutineScope.launch { emit(oldState.copy(isLoading = true)) }
                        }

                        onNext {
                            coroutineScope.launch {
                                emit(
                                    oldState.copy(
                                        isLoading = false,
                                        list = it
                                    )
                                )
                            }
                        }

                        onError {
                            coroutineScope.launch { emit(oldState.copy(isLoading = false)) }
                            dispatch(WatchlistAction.Error(it.message ?: "Something went wrong"))
                        }
                    }
                }
            }
        }
    }
}
