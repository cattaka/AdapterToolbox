package net.cattaka.android.adaptertoolbox.example;

import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import net.cattaka.android.adaptertoolbox.adapter.AbsChoosableTreeItemAdapter;
import net.cattaka.android.adaptertoolbox.example.adapter.ChoosableMyTreeItemAdapter;
import net.cattaka.android.adaptertoolbox.example.data.MyTreeItem;
import net.cattaka.android.adaptertoolbox.example.logic.SnackbarLogic;
import net.cattaka.android.adaptertoolbox.example.utils.ExampleDataGenerator;

import java.util.Arrays;
import java.util.List;

/**
 * Created by cattaka on 16/05/21.
 */
public class SingleChoosableTreeItemAdapterExampleActivity extends AppCompatActivity implements View.OnClickListener {
    SnackbarLogic mSnackbarLogic = new SnackbarLogic();

    RecyclerView mRecyclerView;
    ChoosableMyTreeItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_choosable_tree_item_adapter);

        // find views
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);

        // bind event handlers
        findViewById(R.id.button_ok).setOnClickListener(this);

        { // set adapter
            List<MyTreeItem> items = ExampleDataGenerator.generateMyTreeItem(Arrays.asList(5, 3, 2), 0);
            mAdapter = new ChoosableMyTreeItemAdapter(this, items);
            mAdapter.setChoiceMode(AbsChoosableTreeItemAdapter.CHOICE_MODE_SINGLE);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_ok) {
            List<MyTreeItem> checkedItems = mAdapter.getCheckedItems();
            StringBuilder sb = new StringBuilder();
            for (MyTreeItem item : checkedItems) {
                if (sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append(item.getText());
            }
            mSnackbarLogic.make(mRecyclerView, sb.toString(), Snackbar.LENGTH_SHORT).show();
        }
    }
}
