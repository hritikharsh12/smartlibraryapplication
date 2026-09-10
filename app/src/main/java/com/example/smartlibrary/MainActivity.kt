package com.example.smartlibrary

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

/**
 * MainActivity serves as the main screen of SmartLibrary.
 * It displays the library logo, name, short description, and options to navigate to:
 * 1. Books Fragment
 * 2. Library Rules Fragment
 * 3. Contact Library Fragment
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnBooks = findViewById<Button>(R.id.btnNavBooks)
        val btnRules = findViewById<Button>(R.id.btnNavRules)
        val btnContact = findViewById<Button>(R.id.btnNavContact)

        // Load BooksFragment by default when Activity is first launched
        if (savedInstanceState == null) {
            loadFragment(BooksFragment())
        }

        // Set click listeners to switch between fragments using FragmentTransaction
        btnBooks.setOnClickListener {
            loadFragment(BooksFragment())
        }

        btnRules.setOnClickListener {
            loadFragment(LibraryRulesFragment())
        }

        btnContact.setOnClickListener {
            loadFragment(ContactLibraryFragment())
        }
    }

    /**
     * Replaces the fragment container view with the selected Fragment.
     */
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
