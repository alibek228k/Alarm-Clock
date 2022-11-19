package kz.ali.alarmclock.ui.presentation.alarmsound

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AlarmSoundViewModel: ViewModel() {

    private val ringtoneAvailability by lazy{ MutableLiveData<Boolean?>(null) }
    fun getRingtoneAvailability(): LiveData<Boolean?> = ringtoneAvailability

    fun isRingtoneAvailable(isAvailable: Boolean){
        ringtoneAvailability.postValue(isAvailable)
    }

}