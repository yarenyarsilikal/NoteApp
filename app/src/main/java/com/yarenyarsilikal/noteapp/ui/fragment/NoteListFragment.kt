package com.yarenyarsilikal.noteapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.yarenyarsilikal.noteapp.R
import com.yarenyarsilikal.noteapp.data.model.NoteDetail
import com.yarenyarsilikal.noteapp.databinding.FragmentNoteListBinding
import com.yarenyarsilikal.noteapp.ui.viewmodel.NoteListViewModel
import com.yarenyarsilikal.noteapp.util.extensions.navigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteListFragment : Fragment() {

    private var binding: FragmentNoteListBinding? = null

    private val viewModel: NoteListViewModel by lazy {
        ViewModelProvider(this).get(NoteListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteListBinding.inflate(inflater, container, false)
        binding!!.lifecycleOwner = viewLifecycleOwner
        binding!!.viewModel = viewModel
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeSelectedItem()
        observeCreateNewNote()
        viewModel.getNotes()
    }

    private fun observeSelectedItem() {
        viewModel.selectedItem.observe(viewLifecycleOwner, {
            it.getContentIfNotHandledOrReturnNull()?.let { noteModel ->
                val args = bundleOf(
                    Pair("argNoteModel", noteModel),
                    Pair("argsDetailViewType", NoteDetail.VIEW.ordinal)
                )
                this.navigate(R.id.action_noteListFragment_to_noteDetailFragment, args)
            }
        })
    }

    private fun observeCreateNewNote() {
        viewModel.createNewNote.observe(viewLifecycleOwner, {
            it.getContentIfNotHandledOrReturnNull()?.let { value ->
                val args = bundleOf(
                    Pair("argsDetailViewType", value)
                )
                this.navigate(R.id.action_noteListFragment_to_noteDetailFragment, args)
            }
        })
    }
}