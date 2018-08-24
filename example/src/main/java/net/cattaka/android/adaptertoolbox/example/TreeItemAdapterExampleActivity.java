package net.cattaka.android.adaptertoolbox.example;

import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import net.cattaka.android.adaptertoolbox.adapter.listener.ListenerRelay;
import net.cattaka.android.adaptertoolbox.example.adapter.MyTreeItemAdapter;
import net.cattaka.android.adaptertoolbox.example.data.MyTreeItem;
import net.cattaka.android.adaptertoolbox.example.logic.SnackbarLogic;
import net.cattaka.android.adaptertoolbox.example.utils.ExampleDataGenerator;

import java.util.Arrays;
import java.util.List;

/**
 * Created by cattaka on 16/05/21.
 */
public class TreeItemAdapterExampleActivity extends AppCompatActivity {
    private ListenerRelay<MyTreeItemAdapter, MyTreeItemAdapter.ViewHolder> mListenerRelay = new ListenerRelay<MyTreeItemAdapter, MyTreeItemAdapter.ViewHolder>() {
        @Override
        public void onClick(@NonNull RecyclerView recyclerView, @NonNull MyTreeItemAdapter adapter, @NonNull MyTreeItemAdapter.ViewHolder viewHolder, @NonNull View view) {
            if (recyclerView.getId() == R.id.recycler) {
                MyTreeItem item = adapter.getItemAt(viewHolder.getAdapterPosition()).getItem();
                mSnackbarLogic.make(view, "Clicked: " + item.getText(), Snackbar.LENGTH_SHORT).show();
            }
        }

        @Override
        public boolean onLongClick(@NonNull RecyclerView recyclerView, @NonNull MyTreeItemAdapter adapter, @NonNull MyTreeItemAdapter.ViewHolder viewHolder, @NonNull View view) {
            if (recyclerView.getId() == R.id.recycler) {
                MyTreeItem item = adapter.getItemAt(viewHolder.getAdapterPosition()).getItem();
                mSnackbarLogic.make(view, "Long clicked: " + item.getText(), Snackbar.LENGTH_SHORT).show();
                return true;
            }
            return false;
        }
    };

    SnackbarLogic mSnackbarLogic = new SnackbarLogic();

    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_item_adapter);

        // find views
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);

        { // set adapter
            List<MyTreeItem> items = ExampleDataGenerator.generateMyTreeItem(Arrays.asList(5, 3, 2), 0);
            MyTreeItemAdapter adapter = new MyTreeItemAdapter(this, items);
            adapter.setListenerRelay(mListenerRelay);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            mRecyclerView.setAdapter(adapter);
        }
    }
}
