package com.kanykeinu.simpleweather.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kanykeinu.simpleweather.MainActivity.Companion.OFFICE_LAT
import com.kanykeinu.simpleweather.MainActivity.Companion.OFFICE_LNG
import com.kanykeinu.simpleweather.R
import com.kanykeinu.simpleweather.base.extension.*
import com.kanykeinu.simpleweather.data.model.WeatherResponse
import com.kanykeinu.simpleweather.data.network.Network
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_weather_info.*
import kotlin.math.roundToInt

class WeatherInfoFragment : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "WeatherInfoFragment"
        private const val LAT = "lat"
        private const val LNG = "lng"

        fun newInstance(lat: Double, lng: Double) =
            WeatherInfoFragment().apply {
                arguments = Bundle().apply {
                    putDouble(LAT, lat)
                    putDouble(LNG, lng)
                }
            }
    }

    private val locationLat: Double by lazy { arguments?.getDouble(LAT) ?: 0.0 }
    private val locationLng: Double by lazy { arguments?.getDouble(LNG) ?: 0.0 }
    private val compositeDisposable by lazy { CompositeDisposable() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_weather_info, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getWeather()
    }

    override fun getTheme(): Int =
        R.style.BottomSheetDialogTheme

    private fun getWeather() {
        compositeDisposable.add(
            Network.getApi().getWeather(locationLat, locationLng)
                .doOnSubscribe { progress.show() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ weather ->
                    updateView(weather)
                    data_container.show()
                    error_view.hide()
                    progress.hide()
                }, {
                    error_view.show()
                    data_container.hide()
                    progress.hide()
                })
        )
    }

    private fun updateView(weather: WeatherResponse) {
        val currentWeather = weather.currentWeather
        val date = currentWeather.date.asString(timezone = weather.timezone)

        tv_date.text = "Today is ${date}"
        tv_description.text =
            "${currentWeather.weather[0].main}/ ${currentWeather.weather[0].description}"
        tv_temp.text = "${currentWeather.temp.roundToInt()}°C"
        tv_uv.text = "${currentWeather.uvIndex}"
        tv_wind.text = "${currentWeather.windSpeed} metre/sec"
        tv_coords.text = if (LatLng(locationLat, locationLng) == LatLng(
                OFFICE_LAT,
                OFFICE_LNG
            )
        ) "Siilo Office" else
            "${locationLat.format(4)};${locationLng.format(4)}"

        val iconCode = currentWeather.weather[0].icon
        Glide.with(this)
            .load("${Network.IMAGE_URL}img/wn/${iconCode}@2x.png")
            .into(img_weather)
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }
}
