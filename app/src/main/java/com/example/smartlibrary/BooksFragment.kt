package com.example.smartlibrary

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

/**
 * Fragment displaying available library books.
 * Each book item provides an "Issue Book" button to initiate book issuance.
 */
class BooksFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_books, container, false)

        val btnIssue1 = view.findViewById<Button>(R.id.btnIssueBook1)
        val tvTitle1 = view.findViewById<TextView>(R.id.tvBookTitle1)

        val btnIssue2 = view.findViewById<Button>(R.id.btnIssueBook2)
        val tvTitle2 = view.findViewById<TextView>(R.id.tvBookTitle2)

        val btnIssue3 = view.findViewById<Button>(R.id.btnIssueBook3)
        val tvTitle3 = view.findViewById<TextView>(R.id.tvBookTitle3)

        val btnIssue4 = view.findViewById<Button>(R.id.btnIssueBook4)
        val tvTitle4 = view.findViewById<TextView>(R.id.tvBookTitle4)

        btnIssue1.setOnClickListener {
            issueBook(tvTitle1.text.toString(), "John Doe")
        }

        btnIssue2.setOnClickListener {
            issueBook(tvTitle2.text.toString(), "Mark Smith")
        }

        btnIssue3.setOnClickListener {
            issueBook(tvTitle3.text.toString(), "James Wilson")
        }

        btnIssue4.setOnClickListener {
            issueBook(tvTitle4.text.toString(), "Korth")
        }

        return view
    }

    /**
     * Passes book information to IssueBookActivity using an Intent with extras.
     */
    private fun issueBook(bookName: String, author: String) {
        val intent = Intent(requireContext(), IssueBookActivity::class.java).apply {
            putExtra("BOOK_NAME", bookName)
            putExtra("AUTHOR", author)
        }
        startActivity(intent)
    }
}
