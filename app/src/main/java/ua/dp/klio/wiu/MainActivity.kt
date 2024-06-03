package ua.dp.klio.wiu

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.leanback.widget.BrowseFrameLayout
import ua.dp.klio.wiu.fragment.AnimeSeriesFragment
import ua.dp.klio.wiu.fragment.BroadcastFragment
import ua.dp.klio.wiu.fragment.CartoonsFragment
import ua.dp.klio.wiu.fragment.CountriesFragment
import ua.dp.klio.wiu.fragment.GenresFragment
import ua.dp.klio.wiu.fragment.KlioFragment
import ua.dp.klio.wiu.fragment.MoviesFragment
import ua.dp.klio.wiu.fragment.SettingsFragment
import ua.dp.klio.wiu.fragment.TvShowsFragment
import ua.dp.klio.wiu.search.SearchActivity
import ua.dp.klio.wiu.utils.Common
import ua.dp.klio.wiu.utils.Constants

class MainActivity : FragmentActivity(), View.OnKeyListener {
    lateinit var navBar: BrowseFrameLayout
    lateinit var fragmentContainer: FrameLayout

    lateinit var btnSearch: TextView
    lateinit var btnHome: TextView
    lateinit var btnKlio: TextView
    lateinit var btnMovie: TextView
    lateinit var btnTvshow: TextView
    lateinit var btnCartoon: TextView
    lateinit var btnMultserial: TextView
    lateinit var btnBroadcast: TextView
    lateinit var btnGenre: TextView
    lateinit var btnCountry: TextView
    lateinit var btnSetting: TextView

    var SIDE_MENU = false
    var selectedMenu = Constants.MENU_HOME

    lateinit var lastSelectedMenu: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        fragmentContainer = findViewById(R.id.container)
        navBar = findViewById(R.id.blfNavBar)

        btnSearch = findViewById(R.id.btn_search)
        btnHome = findViewById(R.id.btn_home)
        btnKlio = findViewById(R.id.btn_klio)
        btnMovie = findViewById(R.id.btn_movies)
        btnTvshow = findViewById(R.id.btn_tvshows)
        btnCartoon = findViewById(R.id.btn_cartoons)
        btnMultserial = findViewById(R.id.btn_animation)
        btnBroadcast = findViewById(R.id.btn_broadcast)
        btnGenre = findViewById(R.id.btn_genres)
        btnCountry  = findViewById(R.id.btn_countries)
        btnSetting  = findViewById(R.id.btn_settings)

        btnSearch.setOnKeyListener(this)
        btnHome.setOnKeyListener(this)
        btnKlio.setOnKeyListener(this)
        btnMovie.setOnKeyListener(this)
        btnTvshow.setOnKeyListener(this)
        btnCartoon.setOnKeyListener(this)
        btnMultserial.setOnKeyListener(this)
        btnBroadcast.setOnKeyListener(this)
        btnGenre.setOnKeyListener(this)
        btnCountry.setOnKeyListener(this)
        btnSetting.setOnKeyListener(this)

        lastSelectedMenu = btnHome
        lastSelectedMenu.isActivated = true
        changeFragment(HomeFragment())
    }

    fun changeFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }

    override fun onKey(view: View?, i: Int, key_event: KeyEvent?): Boolean {
        when (i) {
            KeyEvent.KEYCODE_DPAD_CENTER -> {
                lastSelectedMenu.isActivated = false
                view?.isActivated = true
                lastSelectedMenu = view!!

                when (view.id) {
                    R.id.btn_search -> {
                        if (selectedMenu != Constants.MENU_SEARCH) {
                            selectedMenu = Constants.MENU_SEARCH
                            // changeFragment(SearchFragment())
                            val intent = Intent(this, SearchActivity::class.java)
                            startActivity(intent)
                        }
                    }
                    R.id.btn_home -> {
                        selectedMenu = Constants.MENU_HOME
                        changeFragment(HomeFragment())
                    }
                    R.id.btn_klio -> {
                        selectedMenu = Constants.MENU_KLIO
                        changeFragment(KlioFragment())
                    }
                    R.id.btn_movies -> {
                        selectedMenu = Constants.MENU_MOVIES
                        changeFragment(MoviesFragment())
                    }
                    R.id.btn_tvshows -> {
                        selectedMenu = Constants.MENU_TVSHOWS
                        changeFragment(TvShowsFragment())
                    }
                    R.id.btn_cartoons -> {
                        selectedMenu = Constants.MENU_CARTOONS
                        changeFragment(CartoonsFragment())
                    }
                    R.id.btn_animation -> {
                        selectedMenu = Constants.MENU_ANIMESERIES
                        changeFragment(AnimeSeriesFragment())
                    }
                    R.id.btn_broadcast -> {
                        selectedMenu = Constants.MENU_BROADCAST
                        changeFragment(BroadcastFragment())
                    }
                    R.id.btn_genres -> {
                        selectedMenu = Constants.MENU_GENRES
                        changeFragment(GenresFragment())
                    }
                    R.id.btn_countries -> {
                        selectedMenu = Constants.MENU_COUNTRIES
                        changeFragment(CountriesFragment())
                    }
                    R.id.btn_settings -> {
                        selectedMenu = Constants.MENU_SETTINGS
                        changeFragment(SettingsFragment())
                    }
                }
            }
            KeyEvent.KEYCODE_DPAD_LEFT -> {
                if (!SIDE_MENU) {
                    switchToLastSelectedMenu()
                    openMenu()
                    SIDE_MENU = true
                }
            }
        }
        return false
    }

    fun switchToLastSelectedMenu() {
        when (selectedMenu) {
            Constants.MENU_SEARCH -> {
                btnSearch.requestFocus()
            }
            Constants.MENU_HOME -> {
                btnHome.requestFocus()
            }
            Constants.MENU_KLIO -> {
                btnKlio.requestFocus()
            }
            Constants.MENU_MOVIES -> {
                btnMovie.requestFocus()
            }
            Constants.MENU_TVSHOWS -> {
                btnTvshow.requestFocus()
            }
            Constants.MENU_CARTOONS -> {
                btnCartoon.requestFocus()
            }
            Constants.MENU_ANIMESERIES -> {
                btnMultserial.requestFocus()
            }
            Constants.MENU_BROADCAST -> {
                btnBroadcast.requestFocus()
            }
            Constants.MENU_GENRES -> {
                btnGenre.requestFocus()
            }
            Constants.MENU_COUNTRIES -> {
                btnCountry.requestFocus()
            }
            Constants.MENU_SETTINGS -> {
                btnSetting.requestFocus()
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT && SIDE_MENU) {
            SIDE_MENU = false
            closeMenu()
        }

        return super.onKeyDown(keyCode, event)
    }

    override fun onBackPressed() {
        if (SIDE_MENU) {
            SIDE_MENU = false
            closeMenu()
        } else {
            super.onBackPressed()
        }
    }

    fun openMenu() {
        val animSlide : Animation = AnimationUtils.loadAnimation(this, R.anim.slide_in_left)
        navBar.startAnimation(animSlide)

        navBar.requestLayout()
        navBar.layoutParams.width = Common.getWidthInPercent(this, 16)
    }

    fun closeMenu() {
        navBar.requestLayout()
        navBar.layoutParams.width = Common.getWidthInPercent(this, 5)

        fragmentContainer.requestFocus()
        SIDE_MENU = false
    }
}