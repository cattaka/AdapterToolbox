package net.cattaka.android.snippets.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import net.cattaka.android.snippets.example.adapter.MyTreeItemAdapter;
import net.cattaka.android.snippets.example.data.MyTreeItem;
import net.cattaka.android.snippets.example.utils.ExampleDataGenerator;

import java.util.Arrays;
import java.util.List;

/**
 * Created by cattaka on 16/05/21.
 */
public class TreeItemAdapterExampleActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosable_tree_item_adapter);

        // find views
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);

        { // set adapter
            List<MyTreeItem> items = ExampleDataGenerator.generateMyTreeItem(Arrays.asList(5, 3, 2), 0);
            MyTreeItemAdapter adapter = new MyTreeItemAdapter(this, items);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            mRecyclerView.setAdapter(adapter);
        }
    }
}
