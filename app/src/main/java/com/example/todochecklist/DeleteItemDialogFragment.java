package com.example.todochecklist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

public class DeleteItemDialogFragment extends DialogFragment {
    private String checklistName;
    private ItemListsManager itemListsManager = null;
    private ItemLister itemLister = null;

    DeleteItemDialogFragment(ItemListsManager itemListsManager, ItemLister itemLister, String checklistName){
        this.itemListsManager = itemListsManager;
        this.itemLister = itemLister;
        this.checklistName = checklistName;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final ArrayList<Integer> selectedItems = new ArrayList<>();
        ArrayList<String> itemLabels = itemListsManager.getItemLabels(checklistName);

        final String[] labels = itemLabels.toArray(new String[itemLabels.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.delete_checklist_title)
                .setMultiChoiceItems(labels, null,
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
                                    itemListsManager.deleteItem(checklistName, labels[num]);
                                }
                                itemLister.updateListView();
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
