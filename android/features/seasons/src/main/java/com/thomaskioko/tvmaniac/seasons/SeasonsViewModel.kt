package com.thomaskioko.tvmaniac.seasons

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomaskioko.tvmaniac.core.util.CoroutineScopeOwner
import com.thomaskioko.tvmaniac.seasonepisodes.api.Loading
import com.thomaskioko.tvmaniac.seasonepisodes.api.ObserveSeasonWithEpisodesInteractor
import com.thomaskioko.tvmaniac.seasonepisodes.api.SeasonsAction
import com.thomaskioko.tvmaniac.seasonepisodes.api.SeasonsAction.Error
import com.thomaskioko.tvmaniac.seasonepisodes.api.SeasonsAction.LoadSeasons
import com.thomaskioko.tvmaniac.seasonepisodes.api.SeasonsEffect
import com.thomaskioko.tvmaniac.seasonepisodes.api.SeasonsLoaded
import com.thomaskioko.tvmaniac.seasonepisodes.api.SeasonsViewState
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
class SeasonsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val observeSeasonsInteractor: ObserveSeasonWithEpisodesInteractor,
) : Store<SeasonsViewState, SeasonsAction, SeasonsEffect>, CoroutineScopeOwner, ViewModel() {

    private val showId: Long = savedStateHandle["showId"]!!

    override val state: MutableStateFlow<SeasonsViewState> = MutableStateFlow(Loading)

    private val uiEffects = MutableSharedFlow<SeasonsEffect>(extraBufferCapacity = 100)

    init {
        dispatch(LoadSeasons)
    }

    override val coroutineScope: CoroutineScope
        get() = viewModelScope

    override fun observeState(): StateFlow<SeasonsViewState> = state

    override fun observeSideEffect(): Flow<SeasonsEffect> = uiEffects

    override fun dispatch(action: SeasonsAction) {
        when (action) {
            LoadSeasons -> fetchSeason()
            is Error -> {
                coroutineScope.launch {
                    uiEffects.emit(SeasonsEffect.Error(action.message))
                }
            }
        }
    }

    private fun fetchSeason() {
        with(state) {
            observeSeasonsInteractor.execute(showId) {
                onStart {
                    coroutineScope.launch { emit(Loading) }
                }
                onNext {
                    coroutineScope.launch {
                        emit(SeasonsLoaded(result = it))
                    }
                }
                onError {
                    dispatch(Error(it.message ?: "Something went wrong"))
                }
            }
        }
    }
}
