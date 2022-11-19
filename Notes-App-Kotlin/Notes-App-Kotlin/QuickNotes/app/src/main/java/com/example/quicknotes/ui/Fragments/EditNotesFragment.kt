package com.example.quicknotes.ui.Fragments

import android.content.Context
import android.os.Bundle
import android.text.format.DateFormat
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.example.quicknotes.Model.Notes
import com.example.quicknotes.R
import com.example.quicknotes.ViewModel.NotesViewModel
import com.example.quicknotes.databinding.FragmentEditNotesBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.*
import androidx.activity.OnBackPressedCallback




class EditNotesFragment : Fragment() {

    private val oldNotes by navArgs<EditNotesFragmentArgs>()
    private lateinit var binding:FragmentEditNotesBinding
    private val viewModel : NotesViewModel by viewModels()

    private var priority:String = "1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentEditNotesBinding.inflate(layoutInflater,container,false)

        setHasOptionsMenu(true)
        binding.etTitle.setText(oldNotes.data.title)
        binding.etSubtitle.setText(oldNotes.data.subTitle)
        binding.etNotes.setText(oldNotes.data.notes)

        when (oldNotes.data.priority){
            "1" -> {
                priority="1"
                binding.pGreen.setImageResource(R.drawable.ic_baseline_check_24)
                binding.pRed.setImageResource(0)
                binding.pYellow.setImageResource(0)
            }
            "2" -> {
                priority="2"
                binding.pYellow.setImageResource(R.drawable.ic_baseline_check_24)
                binding.pRed.setImageResource(0)
                binding.pGreen.setImageResource(0)
            }
            "3" -> {
                priority="3"
                binding.pRed.setImageResource(R.drawable.ic_baseline_check_24)
                binding.pGreen.setImageResource(0)
                binding.pYellow.setImageResource(0)
            }
        }

        binding.pGreen.setOnClickListener{
            binding.pGreen.setImageResource(R.drawable.ic_baseline_check_24)
            binding.pRed.setImageResource(0)
            binding.pYellow.setImageResource(0)
            priority="1"
        }
        binding.pYellow.setOnClickListener{
            binding.pYellow.setImageResource(R.drawable.ic_baseline_check_24)
            binding.pRed.setImageResource(0)
            binding.pGreen.setImageResource(0)
            priority="2"
        }
        binding.pRed.setOnClickListener{
            binding.pRed.setImageResource(R.drawable.ic_baseline_check_24)
            binding.pGreen.setImageResource(0)
            binding.pYellow.setImageResource(0)
            priority="3"
        }

        binding.btnEditSaveNotes.setOnClickListener {
            updateNotes(it)
        }

        return binding.root
//        return inflater.inflate(R.layout.fragment_edit_notes, container, false)
    }

    private fun updateNotes(it: View?) {
        val title = binding.etTitle.text.toString()
        val subTitle = binding.etSubtitle.text.toString()
        val notes = binding.etNotes.text.toString()
        val d = Date()
        val notesDate: CharSequence = DateFormat.format("MMMM d, yyyy ", d.time)

        //yahan we gotta update id on clicking
        val data = Notes(oldNotes.data.id,title,subTitle,notes,notesDate.toString(),priority)
        viewModel.updateNotes(data)

        Toast.makeText(requireContext(),"Note updated", Toast.LENGTH_SHORT).show()

        //end karne ke liye
        Navigation.findNavController(it!!).navigate(R.id.action_editNotesFragment_to_homeFragment)

        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().getWindowToken(), 0)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        println(item)
        when(item.itemId) {
            R.id.menu_delete -> {
                val bottomSheet:BottomSheetDialog= BottomSheetDialog(requireContext(),R.style.BottomSheetStyle)
                bottomSheet.setContentView(R.layout.dialog_delete)

                val textViewYes=bottomSheet.findViewById<TextView>(R.id.dialog_yes)
                val textViewNo=bottomSheet.findViewById<TextView>(R.id.dialog_no)

                textViewYes?.setOnClickListener {
                    viewModel.deleteNotes(oldNotes.data.id!!)
                    bottomSheet.dismiss()
                    Navigation.findNavController(binding.btnEditSaveNotes).navigate(R.id.action_editNotesFragment_to_homeFragment)

                }
                textViewNo?.setOnClickListener {
                    bottomSheet.dismiss()
                }

                bottomSheet.show()
            }

            android.R.id.home -> {
                activity?.onBackPressed()
            }

        }
        return super.onOptionsItemSelected(item)
    }
}