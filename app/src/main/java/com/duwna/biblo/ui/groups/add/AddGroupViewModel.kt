package com.duwna.biblo.ui.groups.add

import android.net.Uri
import com.duwna.biblo.ui.base.BaseViewModel
import com.duwna.biblo.ui.base.IViewModelState
import com.duwna.biblo.ui.base.Notify

class AddGroupViewModel : BaseViewModel<AddGroupState>(AddGroupState()) {

    fun validateInput(name: String, currency: String): Boolean = when {
        name.trim().isBlank() -> {
            notify(Notify.TextMessage("Имя не может быть пустым"))
            false
        }
        currency.trim().isBlank() -> {
            notify(Notify.TextMessage("Валюта не может быть пустой"))
            false
        }
        else -> true
    }

    fun setImageUri(uri: Uri?) {
        updateState { copy(memberAvatarUri = uri) }
    }

}

data class AddGroupState(
    val memberAvatarUri: Uri? = null
) : IViewModelState