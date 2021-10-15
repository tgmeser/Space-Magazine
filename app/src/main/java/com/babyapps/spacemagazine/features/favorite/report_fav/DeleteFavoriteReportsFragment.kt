package com.babyapps.spacemagazine.features.favorite.report_fav

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteFavoriteReportsFragment : DialogFragment() {
    private val viewmodel: DeleteFavoriteReportsViewModel by viewModels()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setTitle("Removing favorites")
            .setMessage("Are you sure?")
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Yes") { _, _ ->
                viewmodel.onConfirmClick()
            }.create()

}