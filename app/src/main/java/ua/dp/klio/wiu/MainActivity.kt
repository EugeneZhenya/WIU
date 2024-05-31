package ua.dp.klio.wiu

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.leanback.widget.BrowseFrameLayout
import ua.dp.klio.wiu.utils.Common

class MainActivity : FragmentActivity(), View.OnKeyListener {
    lateinit var navBar: BrowseFrameLayout

    lateinit var btnSearch: TextView
    lateinit var btnHome: TextView
    lateinit var btnMovie: TextView
    lateinit var btnTvshow: TextView
    lateinit var btnCartoon: TextView
    lateinit var btnMultserial: TextView
    lateinit var btnGenre: TextView
    lateinit var btnCountry: TextView
    lateinit var btnSetting: TextView

    var SIDE_MENU = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        navBar = findViewById(R.id.blfNavBar)

        btnSearch = findViewById(R.id.btn_search)
        btnHome = findViewById(R.id.btn_home)
        btnMovie = findViewById(R.id.btn_movies)
        btnTvshow = findViewById(R.id.btn_tvshows)
        btnCartoon = findViewById(R.id.btn_cartoons)
        btnMultserial = findViewById(R.id.btn_animation)
        btnGenre = findViewById(R.id.btn_genres)
        btnCountry  = findViewById(R.id.btn_countries)
        btnSetting  = findViewById(R.id.btn_settings)

        btnSearch.setOnKeyListener(this)
        btnHome.setOnKeyListener(this)
        btnMovie.setOnKeyListener(this)
        btnTvshow.setOnKeyListener(this)
        btnCartoon.setOnKeyListener(this)
        btnMultserial.setOnKeyListener(this)
        btnGenre.setOnKeyListener(this)
        btnCountry.setOnKeyListener(this)
        btnSetting.setOnKeyListener(this)

        changeFragment(HomeFragment())
    }

    fun changeFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }

    override fun onKey(view: View?, i: Int, key_event: KeyEvent?): Boolean {
        when (i) {
            KeyEvent.KEYCODE_DPAD_LEFT -> {
                if (!SIDE_MENU) {
                    openMenu()
                    SIDE_MENU = true
                }
            }
        }
        return false
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
        navBar.requestLayout()
        navBar.layoutParams.width = Common.getWidthInPercent(this, 16)
    }

    fun closeMenu() {
        navBar.requestLayout()
        navBar.layoutParams.width = Common.getWidthInPercent(this, 5)
    }
}