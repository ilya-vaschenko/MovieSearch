package com.example.moviesearch.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import coil.transform.GrayscaleTransformation
import com.example.moviesearch.BuildConfig
import com.example.moviesearch.R
import com.example.moviesearch.databinding.DetailFragmentBinding
import com.example.moviesearch.model.Country
import com.example.moviesearch.model.Film
import com.example.moviesearch.viewmodel.AppState
import com.example.moviesearch.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.detail_fragment.*

class DetailFragment : Fragment() {

    companion object {
        const val FILM_EXTRA = "FILM_EXTRA"
        const val COUNTRIES_EXTRA = "COUNTRIES_EXTRA"

        fun newInstance(bundle: Bundle): DetailFragment {
            val fragment = DetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    var noteString = ""
    lateinit var film: Film

    private val viewModel: DetailViewModel by lazy {
        ViewModelProvider(this).get(DetailViewModel::class.java)
    }

    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!

    var country: Country = Country()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.detail_fragment, container, false)

        _binding = DetailFragmentBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        film = arguments?.getParcelable<Film>(FILM_EXTRA) ?: Film()

        viewModel.liveData.observe(viewLifecycleOwner) {
            renderData(it)
        }
        viewModel.getFilmFromRemoteDataSource(film)

        add_note_button.setOnClickListener {
            noteString = note.text.toString()
            Thread {
                viewModel.saveFilm(film, noteString)
            }.start()
            add_note_button.text = "Сохранено"
        }

        detail_country.setOnClickListener {
            permissionResult.launch(Manifest.permission.ACCESS_FINE_LOCATION)

            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) return@setOnClickListener
        }
    }

    private val permissionResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            when {
                result -> activity?.supportFragmentManager?.let {
                    it.beginTransaction()
                        .replace(R.id.container, MapsFragment.newInstance(Bundle().apply {
                            putParcelable(
                                MapsFragment.COUNTRIES_EXTRA,
                                country
                            )
                        }))
                        .addToBackStack("")
                        .commit()
                }

                !ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) -> {

                    AlertDialog.Builder(requireContext())
                        .setTitle("Доступ к геолокации")

                        .setMessage(
                            "Вы запретили доступ к геолокации! \n" +
                                    "Ваше местоположение не ннайдено!\n" +
                                    "Открыть доступ?"
                        )

                        .setPositiveButton("Открыть настройки") { _, _ ->
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)

                            val uri: Uri =
                                Uri.fromParts("package", requireActivity().packageName, null)
                            intent.data = uri
                            startActivity(intent)
                        }
                        .setNegativeButton("Нет") { dialog, _ -> dialog.dismiss() }
                        .create()
                        .show()
                }
                else -> Toast.makeText(
                    requireContext(),
                    "No access to permission",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    private fun renderData(state: AppState) {
        when (state) {
            is AppState.Loading -> {
                binding.loadingLayout.show()
                binding.mainViewDetail.hide()
            }

            is AppState.Success -> {
                binding.loadingLayout.hide()
                binding.mainViewDetail.show()

                val film = state.filmsList.first()

                Thread {
                    viewModel.saveFilm(film, noteString)
                }.start()

                with(binding) {
                    detailDate.text = film.date.toString()
                    detailName.text = film.name
                    detailGenre.text = film.genre
                    description.text = film.description

                    detailImg.load("https://image.tmdb.org/t/p/w500${film.posterPath}?api_key=${BuildConfig.FILM_API_KEY}") {
                        crossfade(true)
                        transformations(GrayscaleTransformation())
                    }
                    detailCountry.text = film.country
                }
                country.name = film.country
                Log.d("fff", "country ${country.name}")
            }

            is AppState.Error -> {
                binding.loadingLayout.hide()
                binding.mainViewDetail.showSnackBar(
                    "ERROR",
                    "Reload",
                    { viewModel.getFilmFromRemoteDataSource(Film()) }
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}