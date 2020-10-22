package com.example.todochecklist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.fragment.app.DialogFragment;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class DeleteDialogFragment extends DialogFragment {
    private ItemListsManager itemListsManager = null;
    private ChecklistLister checklistLister = null;

    DeleteDialogFragment(ItemListsManager itemListsManager, ChecklistLister checklistLister){
        this.itemListsManager = itemListsManager;
        this.checklistLister = checklistLister;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final ArrayList<Integer> selectedItems = new ArrayList<>();
        ArrayList<String> checklistNames = itemListsManager.getChecklistNames();

        final String[] names = checklistNames.toArray(new String[checklistNames.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("title")
                .setMultiChoiceItems(names, null,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if(isChecked){
                                    selectedItems.add(which);
                                } else if(selectedItems.contains(which)){
                                    selectedItems.remove(Integer.valueOf(which));
                                }
                            }
                        })
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                for(int num: selectedItems){
                                    itemListsManager.deleteChecklistNameWhereNameIs(names[num]);
                                }
                                checklistLister.updateListView();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //
                            }
                        });

        return builder.create();
    }

}
