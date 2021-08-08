package com.yarenyarsilikal.noteapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.yarenyarsilikal.noteapp.R
import com.yarenyarsilikal.noteapp.data.model.NoteModel
import com.yarenyarsilikal.noteapp.data.model.ValidErrorType
import com.yarenyarsilikal.noteapp.databinding.FragmentNoteDetailBinding
import com.yarenyarsilikal.noteapp.ui.viewmodel.NoteDetailViewModel
import com.yarenyarsilikal.noteapp.util.OperationButton
import com.yarenyarsilikal.noteapp.util.extensions.navigate
import com.yarenyarsilikal.noteapp.util.logInfo
import com.yarenyarsilikal.noteapp.util.showAlertDialog
import com.yarenyarsilikal.noteapp.util.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class NoteDetailFragment : Fragment() {

    private var binding: FragmentNoteDetailBinding? = null
    var noteModel: NoteModel? = null

    private val viewModel: NoteDetailViewModel by lazy {
        ViewModelProvider(this).get(NoteDetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteDetailBinding.inflate(inflater, container, false)
        binding!!.lifecycleOwner = viewLifecycleOwner
        binding!!.viewModel = viewModel
        viewModel.setUI(arguments)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeCreateOperationButton()
        observeOperationButtonClick()
    }

    private fun observeCreateOperationButton() {
        viewModel.clickedCreateOperationButton.observe(viewLifecycleOwner, {

            it.getContentIfNotHandledOrReturnNull()?.let { value ->
                val message =
                    if (ValidErrorType.INVALIDNOTE.errorCode == value)
                        getString(R.string.error_title_or_desc_empty)
                    else getString(R.string.error_invalid_url)
                if (value < 0) {
                    view?.let { it1 ->
                        showSnackBar(
                            it1,
                            message
                        )
                    }
                    return@observe
                }
                navigateToListFragment()
            }
        })
    }

    private fun observeOperationButtonClick() {
        viewModel.clickedEditOrDeleteOperationButton.observe(viewLifecycleOwner, {
            if (it == OperationButton.OperationButtonType.EDIT.ordinal) {
                handleEditClick()
            }
            if (it == OperationButton.OperationButtonType.DELETE.ordinal) {
                handleDeleteClick()
            }
        })
    }

    private fun navigateToListFragment() {
        this.navigate(R.id.action_noteDetailFragment_to_noteListFragment)
    }

    private fun handleEditClick() {
        context?.let { it1 ->
            showAlertDialog(
                it1,
                getString(R.string.edit_note),
                { dialog ->
                    viewModel.editNote()
                    dialog.dismiss()
                }
            ) { dialog ->
                dialog.dismiss()
                logInfo("noteOperation", "User cancelled edit operation.")
            }
        }
    }

    private fun handleDeleteClick() {
        context?.let { it1 ->
            showAlertDialog(
                it1,
                getString(R.string.delete_note),
                { dialog ->
                    viewModel.deleteNote()
                    dialog.dismiss()
                }
            ) { dialog ->
                dialog.dismiss()
                logInfo("noteOperation", "User cancelled delete operation.")
            }
        }
    }

}